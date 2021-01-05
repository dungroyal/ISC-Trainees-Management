import React, { Fragment } from "react";
// import logo from "../../public/logo192.png";
// import logolight from "%PUBLIC_URL%/favicon.ico";

const Login = () => {
    return ( 
        <Fragment>
          <div className="account-pages my-0 pt-sm-5">
            <div className="container">
              <div className="row justify-content-center">
                <div className="col-md-8 col-lg-6 col-xl-5">
                  <div className="card overflow-hidden">
                    <div className="bg-soft-primary">
                      <div className="row">
                        <div className="col-12">
                          <div className="text-primary p-4">
                            <h5 className="text-primary font-weight-bold">Welcome Back !</h5>
                            <p><strong>Sign in</strong> to continue to <strong>ISC Quang Trung Management</strong>.</p>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="card-body pt-0"> 
                      <div>
                          <div className="avatar-md profile-user-wid mb-4">
                            <span className="avatar-title rounded-circle bg-light">
                              <img src={process.env.PUBLIC_URL + 'favicon.ico'} alt className="" height={50} />
                            </span>
                          </div>
                      </div>
                      <div className="p-2">
                        <form className="form-horizontal">
                          <div className="form-group">
                            <label htmlFor="username">Username</label>
                            <input type="text" className="form-control" id="username" placeholder="Enter username" />
                          </div>
                          <div className="form-group">
                            <label htmlFor="userpassword">Password</label>
                            <input type="password" className="form-control" id="userpassword" placeholder="Enter password" />
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
                  <div className="mt-5 text-center">
                    <div>
                      <p>Don't have an account ? <a href="#" className="font-weight-medium text-primary"> Contact admin now </a> </p>
                      <p>© 2020 ISC Quang Trung Management</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </Fragment>
     );
}
 
export default Login;