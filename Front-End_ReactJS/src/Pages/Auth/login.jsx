import React,{Fragment, useState} from 'react';
import logo from "./../../Assets/images/favicon.ico";
import "./login.css";
import userService from "../../Services/userService";
import ActionTypes from "../../Store/actions";
import {useHistory} from "react-router-dom";
import {connect} from 'react-redux';
const Login = (props) => {
    const [message, setMessage] = useState("");
    const {onUserLogin} = props;
    const  userNameRef = React.createRef();
    const  passwordRef = React.createRef();
    const history = useHistory();
    const submitHandler= (event) =>{
        event.preventDefault();
        const userName = userNameRef.current.value;
        const password = passwordRef.current.value;
        //call api
        userService.login(userName, password).then(res=>{
            if (res.status > 0){
                setMessage("Wrong username or password")
            }else{
                setMessage(res.message)
                onUserLogin(res.accessToken, res.username);
                history.push("/dashboard")
            }
        })
    }
    return (
        <Fragment>

            <div className="account-pages my-5 pt-sm-5">
                <div className="container">
                    <div className="row justify-content-center">
                        <div className="col-md-8 col-lg-6 col-xl-5">
                            <div className="card overflow-hidden">
                            <div className="bg-soft-primary">
                                <div className="row">
                                <div className="col-7">
                                    <div className="text-primary p-4">
                                    <h5 className="text-primary">Welcome Back !</h5>
                                    <p>Sign In Admin Site</p>
                                    </div>
                                </div>
                                <div className="col-5 align-self-end">
                                    {/* <img src={profile} alt="hello" className="img-fluid" /> */}
                                </div>
                                </div>
                            </div>
                            <div className="card-body pt-0"> 
                                <div>
                                <a href="index.html">
                                    <div className="avatar-md profile-user-wid mb-4">
                                    <span className="avatar-title rounded-circle bg-light">
                                        <img src={logo} alt="hello" className="rounded-circle" height={60} />
                                    </span>
                                    </div>
                                </a>
                                </div>
                                <div className="p-2">
                                <form className="form-horizontal" onSubmit={submitHandler}>
                                    <div className="row">
                                        <div className="col">
                                            <div className="text-center text-danger"> {message}</div>
                                        </div>
                                    </div>
                                    <div className="form-group">
                                    <label htmlFor="username">Username</label>
                                    <input type="text" className="form-control" id="username" placeholder="Enter username" ref={userNameRef}/>
                                    </div>
                                    <div className="form-group">
                                    <label htmlFor="userpassword">Password</label>
                                    <input type="password" className="form-control" id="userpassword" placeholder="Enter password" ref={passwordRef}/>
                                    </div>
                                    <div className="custom-control custom-checkbox">
                                    <input type="checkbox" className="custom-control-input" id="customControlInline" />
                                    <label className="custom-control-label" htmlFor="customControlInline">Remember me</label>
                                    </div>
                                    <div className="mt-3">
                                    <button className="btn btn-primary btn-block waves-effect waves-light" type="submit">Log In</button>
                                    </div>     
                                    <div className="mt-4 text-center">
                                    <a href="auth-recoverpw.html" className="text-muted"><i className="mdi mdi-lock mr-1" /> Forgot your password?</a>
                                    </div>
                                </form>
                                </div>
                            </div>
                            </div>
                        </div>
                    </div>
                    <div className="pyro"><div className="before" /><div className="after" /></div>
                </div>
            </div>
                            

        </Fragment>
    )
}
const mapDispatchToProps = (dispatch)=>({
    onUserLogin: (token, currentUser)=>
        dispatch({
            type: ActionTypes.LOGIN_USER,
            token,
            currentUser 
        }),

})
export default  connect(null, mapDispatchToProps)(Login);
