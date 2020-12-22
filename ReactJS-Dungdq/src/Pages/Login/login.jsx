import React from 'react'
import "./login.css"
const login = () => {
    return (
      <div className="main-w3layouts-agileinfo">
            <div className="wthree-form">
                <h2>Fill out the form below to login</h2>
                    <form action="#" method="post">
                        <div className="form-sub-w3">
                            <input type="text" name="Username" placeholder="Username " required />
                            <div className="icon-w3">
                            <i className="fa fa-user" aria-hidden="true" />
                            </div>
                        </div>
                        <div className="form-sub-w3">
                            <input type="password" name="Password" placeholder="Password" required />
                            <div className="icon-w3">
                            <i className="fa fa-unlock-alt" aria-hidden="true" />
                            </div>
                        </div>
                        <label className="anim">
                            <input type="checkbox" className="checkbox" />
                            <span>Remember Me</span> 
                            <a href="#">Forgot Password</a>
                        </label> 
                        <div className="clear" />
                        <div className="submit-agileits">
                            <input type="submit" defaultValue="Login" />
                        </div>
                    </form>
            </div>
        </div>
    )
}

export default login
