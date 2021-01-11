import React, { useState, Fragment, useEffect } from "react";
import store from './../../Store/store';
import studentService from './../../Services/studentService';
import universityService from './../../Services/universityService';
import companyService from './../../Services/companyService';
import intakeService from './../../Services/intakeService';

const Dashboard = (props) => {
  const [countStudent, setCountStudent] = useState(0);
  const [studentType, setStudentType] = useState([1,2,3]);
  const [countUniversity, setCountUniversity] = useState(0);
  const [countCompany, setCountCompany] = useState(0);
  const [listIntake, setListIntake] = useState();

  
  const loadData = () => {
    studentService.getAll().then((res) => {
      if (res.status === 0) {
        const listStudent  = res.data;
        console.log(listStudent)
        setCountStudent(listStudent.length)

        var studentStudying = 0;
        var studentGraduate = 0;
        var studentReserve = 0;

        for (let i = 0; i < listStudent.length; i++) {
          if(listStudent[i].typeStu === "Studying"){
            studentStudying+=1;
          }
          if(listStudent[i].typeStu === "Reserve"){
            studentReserve+=1;
          }else{
            studentGraduate+=1;
          }
        }
        setStudentType([studentStudying,studentGraduate,studentReserve])
      }
    });

    universityService.getAll().then((res) => {
      if (res.status === 0) {
        setCountUniversity(res.data.length)
      }
    });

    companyService.getAll().then((res) => {
      if (res.status === 0) {
        setCountCompany(res.data.length)
      }
    });

    intakeService.getAll().then((res) => {
      if (res.status === 0) {
        setListIntake(res.data);
      }
    });
  };

  useEffect(() => {
    loadData();
    document.title = "Dashboard - ISC Quang Trung Management";
  }, []);

  return (
    <Fragment>
      <div className="container-fluid">
        <div className="row">
          <div className="col-12">
            <div className="page-title-box d-flex align-items-center justify-content-between">
              <h4 className="mb-0 font-size-18">Dashboard</h4>
              <div className="page-title-right">
                <ol className="breadcrumb m-0">
                  <li className="breadcrumb-item"><a href="javascript: void(0);">Dashboards</a></li>
                  <li className="breadcrumb-item active">Dashboard</li>
                </ol>
              </div>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col-xl-4">
            <div className="card overflow-hidden">
              <div className="bg-soft-primary">
                <div className="row">
                  <div className="col-12">
                    <div className="text-primary p-3">
                      <h5 className="text-primary">Welcome Back !</h5>
                      <p>ISC Quang Trung Dashboard</p>
                    </div>
                  </div>
                </div>
              </div>
              <div className="card-body pt-0">
                <div className="row">
                  <div className="col-sm-12">
                    <div className="avatar-md profile-user-wid mb-4">
                      <img src={`https://ui-avatars.com/api/?background=0D8ABC&color=fff&bold=true&name=${store.getState().auth.currentUser}`} alt={store.getState().auth.currentUser}  className="img-thumbnail rounded-circle" />
                    </div>
                    <h5 className="font-size-15 text-truncate name_user"><strong>{store.getState().auth.currentUser}</strong> <small>(Admin)</small></h5>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="col-xl-8">
            <div className="row">
              <div className="col-md-4">
                <div className="card mini-stats-wid">
                  <div className="card-body">
                    <div className="media">
                      <div className="media-body">
                        <p className="text-muted font-weight-medium">Students </p>
                        <h4 className="mb-0">{countStudent}</h4>
                      </div>
                      <div className="mini-stat-icon avatar-sm rounded-circle bg-primary align-self-center">
                        <span className="avatar-title">
                          <i className="bx bx-copy-alt font-size-24" />
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-md-4">
                <div className="card mini-stats-wid">
                  <div className="card-body">
                    <div className="media">
                      <div className="media-body">
                        <p className="text-muted font-weight-medium">University</p>
                        <h4 className="mb-0">{countUniversity}</h4>
                      </div>
                      <div className="mini-stat-icon avatar-sm rounded-circle bg-primary align-self-center">
                        <span className="avatar-title">
                          <i className="bx bx-copy-alt font-size-24" />
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-md-4">
                <div className="card mini-stats-wid">
                  <div className="card-body">
                    <div className="media">
                      <div className="media-body">
                        <p className="text-muted font-weight-medium">Company {studentType.studentStudying}</p>
                        <h4 className="mb-0">{countCompany}</h4>
                      </div>
                      <div className="mini-stat-icon avatar-sm rounded-circle bg-primary align-self-center">
                        <span className="avatar-title">
                          <i className="bx bx-copy-alt font-size-24" />
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col-xl-4">
            <div className="card">
              <div className="card-body">
                <h4 className="card-title mb-4">Social Source</h4>
                <div className="text-center">
                  <div className="avatar-sm mx-auto mb-4">
                    <span className="avatar-title rounded-circle bg-soft-primary font-size-24">
                      <i className="mdi mdi-facebook text-primary" />
                    </span>
                  </div>
                  <p className="font-16 text-muted mb-2" />
                  <h5><a href="#" className="text-dark">Facebook - <span className="text-muted font-16">125 sales</span> </a></h5>
                  <p className="text-muted">Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus tincidunt.</p>
                  <a href="#" className="text-primary font-16">Learn more <i className="mdi mdi-chevron-right" /></a>
                </div>
                <div className="row mt-4">
                  <div className="col-4">
                    <div className="social-source text-center mt-3">
                      <div className="avatar-xs mx-auto mb-3">
                        <span className="avatar-title rounded-circle bg-primary font-size-16">
                          <i className="mdi mdi-facebook text-white" />
                        </span>
                      </div>
                      <h5 className="font-size-15">Facebook</h5>
                      <p className="text-muted mb-0">125 sales</p>
                    </div>
                  </div>
                  <div className="col-4">
                    <div className="social-source text-center mt-3">
                      <div className="avatar-xs mx-auto mb-3">
                        <span className="avatar-title rounded-circle bg-info font-size-16">
                          <i className="mdi mdi-twitter text-white" />
                        </span>
                      </div>
                      <h5 className="font-size-15">Twitter</h5>
                      <p className="text-muted mb-0">112 sales</p>
                    </div>
                  </div>
                  <div className="col-4">
                    <div className="social-source text-center mt-3">
                      <div className="avatar-xs mx-auto mb-3">
                        <span className="avatar-title rounded-circle bg-pink font-size-16">
                          <i className="mdi mdi-instagram text-white" />
                        </span>
                      </div>
                      <h5 className="font-size-15">Instagram</h5>
                      <p className="text-muted mb-0">104 sales</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="col-xl-4">
            <div className="card">
              <div className="card-body">
                <h4 className="card-title mb-5">Intakes</h4>
                <ul className="verti-timeline list-unstyled">                  
                  <li className="event-list active">
                    <div className="event-timeline-dot">
                      <i className="bx bxs-right-arrow-circle font-size-18 bx-fade-right" />
                    </div>
                    <div className="media">
                      <div className="mr-3">
                        <h5 className="font-size-14">ISC 13 <i className="bx bx-right-arrow-alt font-size-16 text-primary align-middle ml-2" /></h5>
                      </div>
                      <div className="media-body">
                        <div>
                          Joined the group “Boardsmanship Forum”
                        </div>
                      </div>
                    </div>
                  </li>
                  
                  <li className="event-list">
                    <div className="event-timeline-dot">
                      <i className="bx bx-right-arrow-circle font-size-18" />
                    </div>
                    <div className="media">
                      <div className="mr-3">
                        <h5 className="font-size-14">ISC 12 <i className="bx bx-right-arrow-alt font-size-16 text-primary align-middle ml-2" /></h5>
                      </div>
                      <div className="media-body">
                        <div>
                          Responded to need “Volunteer Activities
                        </div>
                      </div>
                    </div>
                  </li>


                  <li className="event-list">
                    <div className="event-timeline-dot">
                      <i className="bx bx-right-arrow-circle font-size-18" />
                    </div>
                    <div className="media">
                      <div className="mr-3">
                        <h5 className="font-size-14">ISC 11 <i className="bx bx-right-arrow-alt font-size-16 text-primary align-middle ml-2" /></h5>
                      </div>
                      <div className="media-body">
                        <div>
                          Responded to need “In-Kind Opportunity”
                        </div>
                      </div>
                    </div>
                  </li>
                </ul>
                <div className="text-center mt-4"><a href className="btn btn-primary waves-effect waves-light btn-sm">View More <i className="mdi mdi-arrow-right ml-1" /></a></div>
              </div>
            </div>
          </div>
          <div className="col-xl-4">
            <div className="card">
              <div className="card-body">
                <h4 className="card-title mb-4">Students</h4>
                <div className="text-center">
                  <div className="mb-4">
                    <i className="bx bx-map-pin text-primary display-4" />
                  </div>
                  <h3>{countStudent}</h3>
                  <p>Student</p>
                </div>
                <div className="table-responsive mt-4">
                  <table className="table table-centered table-nowrap">
                    <tbody>
                      <tr>
                        <td style={{width: '30%'}}>
                          <p className="mb-0">Studying</p>
                        </td>
                        <td style={{width: '25%'}}>
                          <h5 className="mb-0">{studentType[0]}</h5></td>
                        <td>
                          <div className="progress bg-transparent progress-sm">
                            <div className="progress-bar bg-primary rounded" 
                            role="progressbar" 
                            style={{width: '94%'}} 
                            aria-valuenow={94} 
                            aria-valuemin={0} aria-valuemax={100} />
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <p className="mb-0">Graduate</p>
                        </td>
                        <td>
                          <h5 className="mb-0">{studentType[1]}</h5>
                        </td>
                        <td>
                          <div className="progress bg-transparent progress-sm">
                            <div className="progress-bar bg-success rounded" role="progressbar" style={{width: '82%'}} aria-valuenow={82} aria-valuemin={0} aria-valuemax={100} />
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <p className="mb-0">Reserve</p>
                        </td>
                        <td>
                          <h5 className="mb-0">{studentType[2]}</h5>
                        </td>
                        <td>
                          <div className="progress bg-transparent progress-sm">
                            <div className="progress-bar bg-warning rounded" role="progressbar" style={{width: '70%'}} aria-valuenow={70} aria-valuemin={0} aria-valuemax={100} />
                          </div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col-lg-12">
            <div className="card">
              <div className="card-body">
                <h4 className="card-title mb-4">Latest Class</h4>
                <div className="table-responsive">
                  <table className="table table-centered table-nowrap mb-0">
                    <thead className="thead-light">
                      <tr>
                        <th style={{width: 20}}>
                          <div className="custom-control custom-checkbox">
                            <input type="checkbox" className="custom-control-input" id="customCheck1" />
                            <label className="custom-control-label" htmlFor="customCheck1">&nbsp;</label>
                          </div>
                        </th>
                        <th>Order ID</th>
                        <th>Billing Name</th>
                        <th>Date</th>
                        <th>Total</th>
                        <th>Payment Status</th>
                        <th>Payment Method</th>
                        <th>View Details</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>
                          <div className="custom-control custom-checkbox">
                            <input type="checkbox" className="custom-control-input" id="customCheck2" />
                            <label className="custom-control-label" htmlFor="customCheck2">&nbsp;</label>
                          </div>
                        </td>
                        <td><a href="javascript: void(0);" className="text-body font-weight-bold">#SK2540</a> </td>
                        <td>Neal Matthews</td>
                        <td>
                          07 Oct, 2019
                        </td>
                        <td>
                          $400
                        </td>
                        <td>
                          <span className="badge badge-pill badge-soft-success font-size-12">Paid</span>
                        </td>
                        <td>
                          <i className="fab fa-cc-mastercard mr-1" /> Mastercard
                        </td>
                        <td>
                          {/* Button trigger modal */}
                          <button type="button" className="btn btn-primary btn-sm btn-rounded waves-effect waves-light" data-toggle="modal" data-target=".exampleModal">
                            View Details
                          </button>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </Fragment>
  );
};

export default Dashboard;
