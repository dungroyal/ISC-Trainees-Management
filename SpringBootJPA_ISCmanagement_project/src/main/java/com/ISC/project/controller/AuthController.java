package com.ISC.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ISC.project.dao.RoleRepository;
import com.ISC.project.dao.UserRepository;
import com.ISC.project.exception.ResourseNotFoundException;
import com.ISC.project.model.ERole;
import com.ISC.project.model.Major;
import com.ISC.project.model.Role;
import com.ISC.project.model.User;
import com.ISC.project.payload.JwtResponse;
import com.ISC.project.payload.LoginRequest;
import com.ISC.project.payload.MessageResponse;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.payload.SignupRequest;
import com.ISC.project.security.JwtUtils;
import com.ISC.project.service.UserDetailsImpl;
import com.ISC.project.service.UserDetailsServiceImpl;

import io.jsonwebtoken.impl.DefaultClaims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Tag(name = "Authorization", description = "User Signup and Signin")
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userService;

	// Signin
	@PostMapping(value = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = "application/json")
	// DOC For signin
	@Operation(summary = "Login", description = "Login for use API")
	@ApiResponse(responseCode = "200", description = "Login success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	public ResponseEntity<JwtResponse> authenticateUser(
			@Parameter(description = "Enter username & password", required = true) @Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(0, "Signin Success", jwt, userDetails.getId(),
				userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	// Signup
	@PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = "application/json")
	// DOC For signin
	@Operation(summary = "Register", description = "Register")
	@ApiResponse(responseCode = "200", description = "Register success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MessageResponse.class))))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	public ResponseEntity<MessageResponse> registerUser(
			@Parameter(description = "Enter infomation", required = true) @Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse(1, "Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse(1, "Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new ResourseNotFoundException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new ResourseNotFoundException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new ResourseNotFoundException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new ResourseNotFoundException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse(0, "User registered successfully!"));
	}

	// Test
	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}

	// get all user
	// Doc for getAll user
	@Operation(summary = "Get all users", description = "Show all users under the databse")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200", description = "Get all users success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/listUser")
	public ResultRespon listuser() {
		return new ResultRespon(0, "Success", this.userService.listAllUser());
	}

	// get one user
	// DOC for getOne user
	@Operation(summary = "Get one user", description = "Show one user under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200", description = "Get one user success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/getUser")
	public ResultRespon getuser(
			@Parameter(description = "The user's id is required", required = true) @RequestParam("id") long id) {
		List<User> user = new ArrayList<>();
		user.add(userService.findById(id).orElseThrow(() -> new ResourseNotFoundException("Not found user")));
		return new ResultRespon(0, "Success", user);
	}

	// post user
	// DOC for add new user
	@Operation(summary = "Add new user", description = "Add new user from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200", description = "Add user success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PostMapping(value = "/newUser")
	public ResultRespon addUser(@Parameter(required = true) @RequestBody User user){
			
		List<User> us = new ArrayList<User>();
		us.add(user);
		us.get(0).setCreatedDate(LocalDateTime.now());
		
		if(userRepository.existsByUsername(user.getUsername())) {
			throw new ResourseNotFoundException("Duplicate username");
		}else if(userRepository.existsByEmail(user.getEmail())) {
			throw new ResourseNotFoundException("Duplicate email");
		}
		else {
			this.userService.save(us.get(0));
			return new ResultRespon(0, "Add user Success", us);
		}
			
		}

//update user
	// DOC for update user
	@Operation(summary = "Update user", description = "Update user from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200", description = "Update user success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PutMapping(value = "/editUser", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon edituser(
			@Parameter(required = true)
			@RequestBody User user,
			@Parameter(required = true, description = "user ID")
			@RequestParam("id") long id) {
		List<User> compa = new ArrayList<User>();
		User olduser = userService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found user with id: " + id));

		if (this.userService.getUserNameById(id).equals(user.getUsername()) || this.userService.getEmailById(id).equals(user.getEmail())) {
			olduser.setUsername(user.getUsername());
			olduser.setEmail(user.getEmail());
			olduser.setAddress(user.getAddress());
			olduser.setFirstName(user.getFirstName());
			olduser.setLastName(user.getLastName());
			olduser.setPhone(user.getPhone());
			olduser.setPassword(user.getPassword());
			olduser.setCreatedBy(user.getCreatedBy());
			olduser.setUpdatedBy(user.getUpdatedBy());
			olduser.setUpdatedDate(LocalDateTime.now());
			compa.add(olduser);
			this.userService.save(compa.get(0));
			return new ResultRespon(0, "Update user success", compa);
		} else {
			if (this.userService.checkUserName(user.getUsername()).isEmpty() && this.userService.checkEmail(user.getEmail()).isEmpty()) {
				olduser.setUsername(user.getUsername());
				olduser.setEmail(user.getEmail());
				olduser.setAddress(user.getAddress());
				olduser.setFirstName(user.getFirstName());
				olduser.setLastName(user.getLastName());
				olduser.setPhone(user.getPhone());
				olduser.setPassword(user.getPassword());
				olduser.setCreatedBy(user.getCreatedBy());
				olduser.setUpdatedBy(user.getUpdatedBy());
				olduser.setUpdatedDate(LocalDateTime.now());
				compa.add(olduser);
				this.userService.save(compa.get(0));
				return new ResultRespon(0, "Update user success", compa);
			} else if(!this.userService.checkUserName(user.getUsername()).isEmpty()) {
				throw new ResourseNotFoundException("Duplicate username");
			} else {
				throw new ResourseNotFoundException("Duplicate email");
			}
		}
	}
	
	// delete user
		// DOC for delete user
		@Operation(summary = "Delete user", description = "Delete user from the database")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200", description = "Delete user success")
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server") })
		@DeleteMapping(value = "/deleteUser")
		public ResultRespon deleteuser(@RequestParam("id") Long id) {
			User user = this.userService.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Not Found user"));
			try {
				this.userService.delete(user.getId());
			} catch (Exception e) {
				throw new ResourseNotFoundException("User can not be delete");
			}
			return new ResultRespon(0, "Delete user Success");
		}
		
		// Pagination
		// DOC for pagination user
		@Operation(summary = "pagination user", description = "pagination user from the database")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200", description = "pagination user success")
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server") })
		@GetMapping(value = "/pagination")
		public ResultRespon paginationUniversity(
				@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
				@RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
				@RequestParam(name = "sort", required = false, defaultValue = "1") String sort,
				@RequestParam(name = "key", required = false, defaultValue = "") String key) {
			Sort sortable = null;
			if (sort.equals("ASC")) {
				sortable = Sort.by("username").ascending();
			}
			if (sort.equals("DESC")) {
				sortable = Sort.by("username").descending();
			}
			List<Page<User>> users = new ArrayList<Page<User>>();
			Pageable pageable = PageRequest.of(page, size, sortable);
			Pageable pageableSearch = PageRequest.of(page,size,sortable);
			if(!key.equals("")) {
				Page<User> usSea = userService.searchUser(key, pageableSearch);
				users.add(usSea);
			}else {
				Page<User> us = userService.findUser(pageable);
				users.add(us);
			}
		
			return new ResultRespon(0, "Success", users);
		}
	}
