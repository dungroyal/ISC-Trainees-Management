import React, { Fragment, useRef, useState, useEffect } from "react";
import lecturerService from "../../Services/lecturerService";
import queryString from "query-string";
import { useFormik } from "formik";
import * as Yup from "yup";
import {toast} from 'react-toastify';  
import $ from "jquery";
import { Modal, Button } from "react-bootstrap";
import { confirmAlert } from "react-confirm-alert";
import Input from "../../Controls/input";
import Pagination from "../../Controls/pagination";
import Select from "react-select";
import SearchLecturer from "./searchLecturer";
import api from "../../Services/api";

const Lecturer = () => {
	const [showModal, setShow] = useState(false);
	const [titleModal, setTitleModal] = useState(false);
	const [listLecturer, setListLecturer] = useState([]);
	const handleClose = () => {
		setShow(false); 
		formik.resetForm(); 
		setUpdateDate(false);
	};
	//pagination
	const [pagination,setPagination]= useState({
		page: 0,
		size: 2,
		totalElements: 1
	});
	 const [filters,setFilters]= useState({
		 size: 4,
		 page: 0,
		 sort: "ASC",
		 search: "",
	 });
  const [idLecturer, setIdLecturer] = useState(0);
  const [selectedLec,setSelectedLec] = useState({ label: 'Active', value: 'Active' });
  const [uploadImg,setUploadImg] = useState(false);
  const [updateDate,setUpdateDate] = useState(false);
  const options =[
    { label: "Active", value: "Active"},
    { label: "Inactive", value: "Inactive"},
  ]
  for (var i = 0; i < options.length; i++) {
    if (selectedLec.length >= 1) {
		options[i].disabled = true;
    }
  }
	useEffect(() => {
		document.title = "Lecturers - ISC Quang Trung Management";
		loadData();
		
	}, [filters]);

	useEffect(() => {
		$("#imageUpload1").change(function () {
			readURL(this);
		});
	});
	const selectedValueLec=(newOptions)=>{
		console.log("options",newOptions)
		setSelectedLec(newOptions)
		console.log("selected1", selectedLec)
	}
	const handledSearchChange = (newSearch) => {
		setFilters({
		  ...filters,
		  page: 0,
		  search: newSearch.searchTerm,
		})
	  };
	const handlePagination=()=>{
		const paramsFilters = queryString.stringify(filters);
		lecturerService.paginationLecturer(paramsFilters).then(res=>{
			if(res.status ===0){
				let sizeP = res.data[0].size;
				let totalElementsP= res.data[0].totalElements;
				let pageP = res.data[0].pageable.pageNumber;
				let dataP = res.data[0].content;
				  setPagination({pageP,sizeP,totalElementsP});
				  setListLecturer(dataP);
			}
		});
	}
	const handlePageChange=(newPage)=>{
		setFilters({
			...filters,
			page: newPage
		});
		
	}
	const readURL = (input) => {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function (e) {
				$("#imagePreview").css(
					"background-image",
					"url(" + e.target.result + ")"
				);
				$("#imagePreview").hide();
				$("#imagePreview").fadeIn(650);
			};
			formik.setFieldValue("image", input.files[0]);
			reader.readAsDataURL(input.files[0]);
		}
	};
	// getAll lecturer
	const loadData = () => {
		handlePagination();
	};
	//delete Lecturer confirm
	const submitDelete = (id) => {
		confirmAlert({
			title: "Confirm to submit",
			message: "Are you sure?",
			buttons: [
				{
					label: "Yes",
					onClick: () => deleteLecturer(id),
				},
				{
					label: "No",
				},
			],
		});
	};
	const deleteLecturer = (id) => {
		lecturerService.deleteLecturer(id).then((res) => {
			if (res.status === 0) {
				loadData();
				toast.success("Delete Lecturer Success!")
			}else{
				toast.warning(" Delete Lecturer failed !")
			}
		});
	};
	const handleShowModel = (id) => {
		setIdLecturer(id);
		if (id > 0) {
			setTitleModal(true);
			setUpdateDate(true);
			lecturerService.getLecturer(id).then((res) => {
				if (res.status === 0) {
					setShow(true);
					let item = res.data[0]
					formik.setValues(item);
					formik.setFieldValue("statusLec", {label: item.statusLec, value: item.statusLec})
				}
			});
			setUploadImg(true);
		}else{
			setTitleModal(false);
			setUploadImg(false);
			setShow(true);
		}
	};

	// add new lecturer
	const addNewLecturer = (data) => {
		lecturerService
			.addNewLecturer(
				"Admin",
				"Admin",
				data.codeLec,
				data.firstName,
				data.lastName,
				data.addressLec,
				data.phoneLec,
				data.emailLec,
				data.degree,
				formik.values.statusLec.value,
				data.noteLec,
				data.image
			)
			.then((res) => {
				if (res.status === 0){
                    toast.success('Add new Lecturer Success');
                    loadData();
                    handleClose();
                  }else{
					if (res.message == "Duplicate Lecturer Code") {
						formik.setFieldError('codeLec',res.message);
					}
					if(res.message == "Duplicate Lecturer Email"){
						formik.setFieldError('emailLec',res.message);
					}
                  }  
			});
	};

	const updateLecturerHasImage = (id, data) => {
		lecturerService
			.updateLecturerHasImage(
				id,
				"Admin",
				"Admin",
				data.codeLec,
				data.firstName,
				data.lastName,
				data.addressLec,
				data.phoneLec,
				data.emailLec,
				data.degree,
				formik.values.statusLec.value,
				data.noteLec,
				data.image
			)
			.then((res) => {
				if (res.status === 0) {
					setUpdateDate(true);
					loadData();
					setShow(false);
					toast.success(" Update Lecturer Success!")
				}else{
					toast.warning(" Update Lecturer failed !")
				}
			});
	};
	const updateLecturerNotImage = (id, data) => {
		// console.log("updateLecturerNotImage: ", id, data);
		lecturerService
			.updateLecturerNotImage(
				id,
				"Admin",
				"Admin",
				data.codeLec,
				data.firstName,
				data.lastName,
				data.addressLec,
				data.phoneLec,
				data.emailLec,
				data.degree,
				formik.values.statusLec.value,
				data.noteLec,
			)
			.then((res) => {
				if (res.status === 0) {
					loadData();
					setShow(false);
					toast.success(" Update Lecturer success !")
				}else{
					if (res.message == "Duplicate Lecturer Code") {
						formik.setFieldError('codeLec',res.message);
					}
					if(res.message == "Duplicate Lecturer Email"){
						formik.setFieldError('emailLec',res.message);
					}
				}
			});
	};
	
	const formik = useFormik({
		initialValues: {
			codeLec: "",
			image: "",
			lastName: "",
			firstName: "",
			emailLec: "",
			phoneLec: "",
			addressLec: "",
			createdBy: "",
			statusLec: { label: 'Active', value: 'Active' },
			noteLec: "",
			updatedBy: "",
			createdDate:"",
			updatedDate:"",
			degree: "",
		},
		validationSchema: Yup.object({
			codeLec: Yup.string().required("Required"),
			image: Yup.string().required("Required"),
			lastName: Yup.string().required("Required"),
			firstName: Yup.string().required("Required"),
			emailLec: Yup.string().required("Required"),
			phoneLec: Yup.string().required("Required"),
			addressLec: Yup.string().required("Required"),
			degree: Yup.string().required("Required"),
		}),
		onSubmit: (values) => {
			if (idLecturer === 0) {
				addNewLecturer(values);
			} else {
				if (values.image.name === undefined) {
					updateLecturerNotImage(idLecturer, values);
					formik.resetForm();
				} else {
					updateLecturerHasImage(idLecturer, values);
					formik.resetForm();
				}
			}
		},
	});
	
	return (
		<Fragment>
			<div className="row">
				<div className="col-12">
					<div className="page-title-box d-flex align-items-center justify-content-between">
						<h4 className="mb-0 font-size-18">Lecturer List</h4>
						<div className="page-title-right">
							<ol className="breadcrumb m-0">
								<li className="breadcrumb-item">
									<a href="#">ISC Quang Trung</a>
								</li>
								<li className="breadcrumb-item active">Lecturer List</li>
							</ol>
						</div>
					</div>
				</div>
			</div>

			<div className="row">
				<div className="col-12">
					<div className="card">
						<div className="card-body">
							<div className="row mb-2">
								<div className="col-sm-4">
									<div className="search-box mr-2 mb-2 d-inline-block">
										<div className="position-relative">
											
											<SearchLecturer onSubmit={handledSearchChange}/>
											<i className="bx bx-search-alt search-icon" />
										</div>
									</div>
								</div>
								<div className="col-sm-8">
									<div className="text-sm-right">
										<button
											type="button"
											data-toggle="modal"
											data-target=".addStudentModal"
											onClick={() => {
												handleShowModel(0);
											}}
											className="btn btn-success btn-rounded waves-effect waves-light mb-2 mr-2"
										>
											<i className="bx bxs-add-to-queue" /> Add new Lecturer
										</button>
									</div>
								</div>
							</div>
							<div className="table-responsive">
								<table className="table table-centered table-nowrap">
									<thead className="thead-light">
										<tr>
											<th>STT</th>
											<th>Code</th>
											<th>Image</th>
											<th>Full Name</th>
											<th>Email</th>
											<th>Phone</th>
											<th>Address</th>
											<th>Status</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										
									{listLecturer.length == 0 ? (
										<tr className="text-center">
											<td colspan="9">Not found lecturer!</td>
										</tr>
									):(
										listLecturer.map((lecturer, index) => {
											return (
												<tr key={index}>
													<td>{index + 1}</td>
													<td><a href="javascript: void(0);" className="text-body font-weight-bold">{lecturer.codeLec}</a> </td>
													<td style={{width: 100}}>
														<img className="img-fluid img-thumbnai rounded mx-auto" 
															src={api.url.image + lecturer.image}
															alt={"Hinh " + lecturer.lastName} />
													</td>
													<td>{lecturer.lastName} {lecturer.firstName}</td>
													<td>{lecturer.emailLec}</td>
													<td>{lecturer.phoneLec}</td>
													<td>{lecturer.addressLec}</td>
													<td>
														{lecturer.statusLec ==="Active"?
														<span style={{backgroundColor: "#0101DF", color: "white",borderRadius: 40, padding:3}}>{lecturer.statusLec}</span>: 
														<span style={{backgroundColor: "#FFBF00", borderRadius: 40, padding:3}}>{lecturer.statusLec}</span>
														}
													</td>
													<td>
														<a
															type="button"
															className="mr-3 text-primary"
															onClick={() => handleShowModel(lecturer.id)}
														>
															<i className="fas fa-user-edit font-size-18" />
														</a>
														<a
															type="button"
															className="text-danger"
															onClick={() => submitDelete(lecturer.id)}
														>
															<i className="far fa-trash-alt font-size-18" />
														</a>
													</td>
												</tr>
											);
										})
									)}
									</tbody>
								</table>
							</div>
							
							<Pagination pagination={pagination} onPageChange={handlePageChange}/>
						</div>
					</div>
				</div>
			</div>
			<Modal
				show={showModal}
				onHide={handleClose}
				size="xl"
				backdrop="static"
				keyboard={false}>
				<Modal.Header closeButton>
					<Modal.Title>{titleModal ? "Update Lecturer": "Add new Lecturer"}</Modal.Title>
				</Modal.Header>
				<form onSubmit={formik.handleSubmit}>
					<Modal.Body>
						<div className="row">
							<div className="col-sm-12 col-lg-2">
								<div className="avatar-upload-jsk py-2">
									<div className="avatar-edit">
										<Input
											typeInput="1"
											column="6"
											rows="1"
											type="file"
											name="image"
											onChange={(event) => {
												formik.setFieldValue(
													"image",
													event.currentTarget.files[0]
												);
											}}
											id="imageUpload1"
										/>
									</div>
									<div className="avatar-preview-jsk">
										<label htmlFor="imageUpload1" className="imageUpload">
											{" "}
											<i className="bx bxs-cloud-upload"></i>
										</label>
										{uploadImg ? (<div id="imagePreview" style={{ backgroundImage: "url(" + `${api.url.image + formik.values.image}` + ")" }}></div>):
										
										<div id="imagePreview" style={{ backgroundImage: "url('https://timvieclam.xyz/images/avata-playhoder.jpg')" }}></div>
											
										}
										
									</div>
								</div>
							</div>
							<div className="col-sm-12 col-lg-10">
								<div className="row">
									<Input
										typeInput="1"
										column="6"
										rows="1"
										id="txtCode"
										type="text"
										label="Code Lecturer *"
										frmField={formik.getFieldProps("codeLec")}
										err={formik.touched.codeLec && formik.errors.codeLec}
										errMessage={formik.errors.codeLec}
									/>
									
									<div className="col-6">
										<div className="form-group">
										<label htmlFor="setSelected"> Status</label>
										<Select
											id="setSelected"
											onChange={(val)=> { formik.setFieldValue('statusLec',val) }}
											value={formik.values.statusLec}
											options={options}
											closeMenuOnSelect={true}
											/>
										</div>
									</div>

									<Input
										typeInput="1"
										column="6"
										rows="1"
										id="txtLastName"
										type="text"
										label="Last Name *"
										obligatory="*"
										frmField={formik.getFieldProps("lastName")}
										err={formik.touched.lastName && formik.errors.lastName}
										errMessage={formik.errors.lastName}
									/>
									<Input
										typeInput="1"
										column="6"
										rows="1"
										id="txtFirstName"
										type="text"
										label="First Name *"
										frmField={formik.getFieldProps("firstName")}
										err={formik.touched.firstName && formik.errors.firstName}
										errMessage={formik.errors.firstName}
									/>
									<Input
										typeInput="1"
										column="6"
										rows="1"
										id="txtEmail"
										type="email"
										label="Email *"
										frmField={formik.getFieldProps("emailLec")}
										err={formik.touched.emailLec && formik.errors.emailLec}
										errMessage={formik.errors.emailLec}
									/>
									<Input
										typeInput="1"
										column="6"
										rows="1"
										id="txtPhone"
										type="text"
										label="Phone"
										frmField={formik.getFieldProps("phoneLec")}
										err={formik.touched.phoneLec && formik.errors.phoneLec}
										errMessage={formik.errors.phoneLec}
									/>
									
									<Input
										typeInput="1"
										column="6"
										rows="1"
										id="txtAddress"
										type="text"
										label="Address *" 
										frmField={formik.getFieldProps("addressLec")}
										err={ formik.touched.addressLec && formik.errors.addressLec}
										errMessage={formik.errors.addressLec}
									/>
									
									<Input
										typeInput="1"
										column="6"
										rows="1"
										id="txtDegree"
										type="text"
										label="Degree *"
										frmField={formik.getFieldProps("degree")}
										err={formik.touched.degree && formik.errors.degree}
										errMessage={formik.errors.degree}
									/>
									<Input
										column="12"
										rows="2"
										id="txtNote"
										type="text"
										label="Note"
										frmField={formik.getFieldProps("noteLec")}
										err={formik.touched.noteLec && formik.errors.noteLec}
										errMessage={formik.errors.noteLec}
									/>
									 {updateDate ? (
										 <Fragment>
										 <Input
										   typeInput="1"
										   column="6"
										   rows="1"
										   readOnly
										   type="text"
										   label="Created by"
										   value = {`${formik.values.createdBy} - ${new Date(formik.values.createdDate).toLocaleDateString('vi-VI',{year: 'numeric', month: 'numeric', day: 'numeric', hour: "2-digit", minute: "2-digit"})}`}
										   />
										   {formik.values.updatedBy != null ?(
										   <Fragment>
											 <Input
											 typeInput="1"
											 column="6"
											 rows="1"
											 readOnly
											 type="text"
											 label="Update by"
											 value = {`${formik.values.updatedBy} - ${new Date(formik.values.updatedDate).toLocaleDateString('vi-VI',{year: 'numeric', month: 'numeric', day: 'numeric', hour: "2-digit", minute: "2-digit"})}`}
											 />
										   </Fragment>
										   ):("")}
										 </Fragment>
									 ) : ("")}
								</div>
							</div>
						</div>
					</Modal.Body>
						<div className="row justify-content-end small">
							<div className="col-auto pr-5">
								<p className="p-0">
								Field required (<span className="text-danger">*</span>)
								</p>
							</div>
						</div>
					<Modal.Footer>
						<Button variant="secondary" onClick={handleClose}>
							Close
						</Button>
						<Button variant="primary" type="submit">
							Save
						</Button>
					</Modal.Footer>
				</form>
			</Modal>
		</Fragment>

	);

};

export default Lecturer;
