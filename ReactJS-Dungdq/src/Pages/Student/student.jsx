import React, { useState, Fragment, useEffect } from "react";

import { useFormik } from "formik";
import * as Yup from "yup";
import { Link } from "react-router-dom";
import { Button, Modal } from "react-bootstrap";
import Input from "../../Controls/input";
import CustomInput from "../../Controls/customInput";
import studentService from "../../Services/studentService";
import univerService from "../../Services/universityService";
import companyService from "../../Services/companyService";
import intakeService from "../../Services/intakeService";
import api from "../../Services/api";


// import MultiSelectCheckboxTypeStu from "../controlsStudent/selectBoxTypeStu";
// import MultiSelectCheckboxWorkingStatus from "../controlsStudent/selectBoxWorkingStatus";
// import MultiSelectCheckboxUniversity from "../controlsStudent/selectBoxUniversity";


import "./student.css";
import "./reviewImageUpload";
import studentCompanyService from "../../Services/studentCompanyService";
import studentIntakeService from "../../Services/studentIntakeService";

const Student = (props) => {
  //Test
  const test = [{ intakeId: null }];
  const test2 = [];
  // Upload Filet
  const [selectedFiles, setSelectedFiles] = useState(undefined);
  const [currentFile, setCurrentFile] = useState(undefined);
  const [progress, setProgress] = useState(0);
  const [message, setMessage] = useState("");
  const [fileInfos, setFileInfos] = useState([]);
  const selectFile = (event) => {
    setSelectedFiles(event.target.files);
  };
  // Get One university
  const [oneUniver, setOneUniver] = useState();
  const showOneUniver = (id) => {
    univerService.get(id).then((res) => {
      setOneUniver(res.data);
    });
  };
  // Option For MultiCheckBox Type Student
  const optionsTypeStu = [
    { label: "Studying", value: "Studying", disabled: false },
    { label: "Graduate", value: "Graduate", disabled: false },
    { label: "Reserve", value: "Reserve", disabled: false },
  ];
  const [selectedTypeStu, setSelectedTypeStu] = useState([]);
  // handle onChange event of the dropdown
  const handleChangeTypeStu = (e) => {
    setSelectedTypeStu(Array.isArray(e) ? e.map((x) => x.value) : []);
  };
  // Custom Select OneMultiSelect Type Student
  for (var i = 0; i < optionsTypeStu.length; i++) {
    if (selectedTypeStu.length >= 1) {
      optionsTypeStu[i].disabled = true;
    }
  }

  // Option For MultiCheckBox University
  const [selectedUniver, setSelectedUniver] = useState([]);
  const [univers, setuniver] = useState([]);

  // Option For MultiCheckBox Company
  const [selectedCompany, setSelectedCompany] = useState([]);
  const [companies, setCompanies] = useState([]);

  // Option For MultiCheckBox Intake
  const [selectedIntake, setSelectedIntake] = useState([]);
  const [intakes, setIntakes] = useState([]);

  //Option For MultiCheckBox Working Status
  const optionsWorkingStatus = [
    { label: "Active", value: "Active", disabled: false },
    { label: "Inactive", value: "Inactive", disabled: false },
  ];
  const [selectedWorkingStatus, setSelectedWorkingStatus] = useState([]);
  // handle onChange event of the dropdown
  const handleChangeWorkingStatus = (e) => {
    setSelectedWorkingStatus(Array.isArray(e) ? e.map((x) => x.value) : []);
  };
  // Custom Select OneMultiSelect Type Student
  for (var i = 0; i < optionsWorkingStatus.length; i++) {
    if (selectedWorkingStatus.length >= 1) {
      optionsWorkingStatus[i].disabled = true;
    }
  }

  // Student
  const [students, setStudent] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const handleModalClose = () => setModalShow(false);

  //[] Student Intake ID
  const [studentIntakeId, setStudentIntakeId] = useState([]);
  const [showIntakes, setshowIntakes] = useState([]);
  // Formik
  const formik = useFormik({
    initialValues: {
      studentCode: "",
      lastName: "",
      firstName: "",
      email: "",
      GPA: "",
      phone: "",
      address: "",
      createdBy: "",
      note: "",
      image: "",
      typeStudent: "",
      workingStatus: "",
      companies: "",
      university: "",
    },
    validationSchema: Yup.object({
      studentCode: Yup.string()
        .required("Required")
        .min(2, "Up to 255 characters"),
      lastName: Yup.string()
        .required("Required")
        .min(2, "Up to 200 characters"),
      firstName: Yup.string()
        .required("Required")
        .min(2, "Up to 255 characters"),
      email: Yup.string().required("Required").min(2, "Up to 200 characters"),
      GPA: Yup.string().required("Required"),
      phone: Yup.string().required("Required").min(2, "Up to 200 characters"),
      address: Yup.string().required("Required").min(2, "Up to 255 characters"),
      createdBy: Yup.string().min(2, "Up to 200 characters"),
      note: Yup.string().min(2, "Up to 2000 characters"),
    }),
    onSubmit: (values) => {
      handleFormSubmit(values);
    },
  });
  // console.log(students[students.length - 1].id);

  // Load Data
  const loadData = () => {
    studentService.getAll().then((res) => {
      if (res.status === 0) {
        setStudent(res.data);
      }
    });
    //Get all university
    univerService.getAll().then((res) => {
      if (res.status === 0) {
        setuniver(res.data);
        // univers.forEach((e) => console.log(e));
      }
    });
    //Get all company
    companyService.getAll().then((res) => {
      if (res.status === 0) {
        setCompanies(res.data);
      }
    });
    //Get all intake
    intakeService.getAll().then((res) => {
      if (res.status === 0) {
        setIntakes(res.data);
        setshowIntakes(res.data);
      }
    });
    // Get Intake Id by Student Id

    for (var i = 0; i < students.length; i++) {
      studentIntakeService.get(students[i].id).then((res) => {
        setStudentIntakeId(res.data);
        test.push(res.data);
        // console.log("aaa");
        // console.log(studentIntakeId);
        // studentIntakeId.map((e) => {
        //   console.log(e.id.intakeId);
        //   console.log(e.id.studentId);
        // });
      });
    }
    // showIntakes.map((e) => {
    //   console.log(e.id);
    //   console.log(e.nameIntake);
    // });
  };

  //Didmount load data major
  useEffect(() => {
    loadData();
  }, []);

  //Update Student State
  const [studentId, setStudentId] = useState(0);
  //Check Form Update And Add Student
  const handleModalShow = (e, dataId) => {
    if (e) e.preventDefault();
    setStudentId(dataId);
    // setModalShow(true);
    if (dataId > 0) {
      //check form Update Student
      studentService.get(dataId).then((res) => {
        formik.setValues(res.data[0]);
        console.log(res.data[0]);
        setModalShow(true);
      });
    } else {
      //check form Add Student
      // studentService.get;
      formik.resetForm();
      setModalShow(true);
    }
  };
  //Add new Student
  const handleFormSubmit = () => {
    //Check for Adding or upadating
    if (studentId === 0) {
      //File
      // let currentFile = selectedFiles[0];
      // setCurrentFile(currentFile);

      // studentService.add(data).then((res) => {
      //   formik.setValues(res.data);
      //   loadData();
      //   handleModalClose();
      // });
      studentService
        .add1(
          formik.values.firstName,
          formik.values.lastName,
          formik.values.studentCode,
          formik.values.address,
          formik.values.phone,
          formik.values.email,
          selectedTypeStu[0],
          formik.values.GPA,
          selectedWorkingStatus[0],
          formik.values.note,
          formik.values.image,
          formik.values.createdBy,
          selectedUniver[0].value
        )
        .then((stu) => {
          const companyId = [];
          const intakeId = [];
          //Get [] company Id
          for (var i = 0; i < selectedCompany.length; i++) {
            companyId.push(selectedCompany[i].value);
          }
          //Get [] intake Id
          for (var i = 0; i < selectedIntake.length; i++) {
            intakeId.push(selectedIntake[i].value);
          }
          studentCompanyService
            .add(students[students.length - 1].id + 1, companyId)
            .then((stuCom) => {
              loadData();
            });

          studentIntakeService
            .add(students[students.length - 1].id + 1, intakeId)
            .then((stuIntake) => {
              loadData();
            });
          formik.setValues(stu.data);
          loadData();
          handleModalClose();
        });
    } else {
      studentService
        .updateImg(
          formik.values.firstName,
          formik.values.lastName,
          formik.values.studentCode,
          formik.values.address,
          formik.values.phone,
          formik.values.email,
          selectedTypeStu[0],
          formik.values.GPA,
          selectedWorkingStatus[0],
          formik.values.note,
          formik.values.image,
          formik.values.createdBy,
          selectedUniver[0].value,
          studentId
        )
        .then((res) => {
          if (res.status === 0) {
            //Show success message
            // toast.info("Update Success");
            loadData();
            handleModalClose();
          } else {
            //Show error
            // toast.error("Update Error");
          }
        });
    }
    loadData();
    handleModalClose();
  };

  return (
    <Fragment>
      <div className="container-fluid">
        {/* <!-- start page title --> */}
        <div className="row">
          <div className="col-12">
            <div className="page-title-box d-flex align-items-center justify-content-between">
              <h4 className="mb-0 font-size-18">Student List</h4>

              <div className="page-title-right">
                <ol className="breadcrumb m-0">
                  <li className="breadcrumb-item">
                    <a href="javascript: void(0);">ISC Quang Trung</a>
                  </li>
                  <li className="breadcrumb-item active">
                    Management Students
                  </li>
                </ol>
              </div>
            </div>
          </div>
        </div>
        {/* <!-- end page title --> */}

        <div className="row">
          <div className="col-12">
            <div className="card">
              <div className="card-body">
                <div className="row mb-2">
                  <div className="col-sm-4">
                    <div className="search-box mr-2 mb-2 d-inline-block">
                      <div className="position-relative">
                        <input
                          type="text"
                          className="form-control"
                          placeholder="Tìm kiếm..."
                        />
                        <i className="bx bx-search-alt search-icon"></i>
                      </div>
                    </div>
                  </div>
                  <div className="col-sm-8">
                    <div className="text-sm-right">
                      <button
                        type="button"
                        className="btn btn-primary"
                        data-toggle="modal"
                        onClick={() => handleModalShow(null, 0)}
                      >
                        <i className="fas fa-plus"></i> Add
                      </button>
                    </div>
                  </div>
                </div>

                <div className="table-responsive">
                  <table className="table table-centered table-nowrap">
                    <thead className="thead-light">
                      <tr>
                        <th
                          style={{
                            verticalAlign: "middle",
                            textAlign: "center",
                          }}
                        >
                          {/* <div class="custom-control custom-checkbox">
                                  <input
                                    type="checkbox"
                                    class="custom-control-input"
                                    id="customCheck1"
                                  />
                                  <label
                                    class="custom-control-label"
                                    for="customCheck1"
                                  >
                                    &nbsp;
                                  </label>
                                </div> */}
                          No.
                        </th>
                        <th
                          style={{
                            verticalAlign: "middle",
                            textAlign: "center",
                          }}
                        >
                          Student Code
                        </th>
                        <th
                          style={{
                            verticalAlign: "middle",
                            textAlign: "center",
                          }}
                        >
                          Full name
                        </th>
                        <th
                          style={{
                            verticalAlign: "middle",
                            textAlign: "center",
                          }}
                        >
                          Avatar
                        </th>
                        <th
                          style={{
                            verticalAlign: "middle",
                            textAlign: "center",
                          }}
                        >
                          Phone
                        </th>
                        <th
                          style={{
                            verticalAlign: "middle",
                            textAlign: "center",
                          }}
                        >
                          Email
                        </th>
                        <th
                          style={{
                            verticalAlign: "middle",
                            textAlign: "center",
                          }}
                        >
                          Status
                        </th>
                        {/* <th>Intake</th> */}
                        <th>University</th>
                        {/* <th>Company</th> */}
                        <th>Edit</th>
                      </tr>
                    </thead>
                    <tbody>
                      {students.map((student, idx) => {
                        return (
                          <tr key={student.id}>
                            <td
                              style={{
                                verticalAlign: "middle",
                                textAlign: "center",
                              }}
                            >
                              {idx + 1}
                            </td>
                            <td
                              style={{
                                verticalAlign: "middle",
                                textAlign: "center",
                              }}
                            >
                              {student.codeStu}
                            </td>
                            <td
                              style={{
                                verticalAlign: "middle",
                                textAlign: "center",
                              }}
                            >
                              {student.firstName + " " + student.lastName}
                            </td>
                            <td
                              style={{
                                verticalAlign: "middle",
                                textAlign: "center",
                              }}
                            >
                              <img
                                style={{
                                  width: "100px",
                                  height: "100px",
                                }}
                                src={api.url.image + student.image}
                                alt={"Hinh " + student.lastName}
                              />
                            </td>
                            <td
                              style={{
                                verticalAlign: "middle",
                                textAlign: "center",
                              }}
                            >
                              {student.phoneStu}
                            </td>
                            <td
                              style={{
                                verticalAlign: "middle",
                                textAlign: "center",
                              }}
                            >
                              {student.emailStu}
                            </td>
                            <td
                              style={{
                                verticalAlign: "middle",
                                textAlign: "center",
                              }}
                            >
                              <span className="badge badge-pill badge-soft-success font-size-12">
                                {student.typeStu}
                              </span>
                            </td>
                            {/* <td>
                                  {test.map((e) => {
                                    return <p>{e.intakeId}</p>;
                                  })}
                                </td> */}
                            <td
                              style={{
                                verticalAlign: "middle",
                                textAlign: "center",
                              }}
                            >
                              {student.university.nameUni}
                            </td>
                            {/* <td></td> */}
                            <td
                              style={{
                                verticalAlign: "middle",
                                textAlign: "center",
                              }}
                            >
                              <a
                                href="#"
                                onClick={(e) => handleModalShow(e, student.id)}
                              >
                                <i className="fas fa-edit text-primary mr-3"></i>
                              </a>
                              <a
                                href="#"
                                // onClick={(e) => deleteRow(e, major.id)}
                              >
                                <i className="fas fa-trash-alt text-danger"></i>
                              </a>
                            </td>
                          </tr>
                        );
                      })}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      {/* Modal  */}
      <Modal
        show={modalShow}
        onHide={handleModalClose}
        backdrop="static"
        keyboard={false}
        size="xl"
      >
        <Modal.Header closeButton>
          <Modal.Title>New Student</Modal.Title>
        </Modal.Header>
        <form onSubmit={formik.handleSubmit}>
          <Modal.Body>
            <div className="row">         
              <div className="col-sm-12 col-lg-2">
                <div className="avatar-upload-jsk py-2">
                  <div className="avatar-edit">
                    <input type="file" name="avatar" id="imageUpload" accept=".png, .jpg, .jpeg" />
                  </div>
                  <div className="avatar-preview-jsk">
                    <label htmlFor="imageUpload" className="imageUpload"> <i className="bx bxs-cloud-upload" /></label>
                    <div id="imagePreview" style={{backgroundImage: 'url("assets/images/avata-playhoder.jpg")'}} />
                  </div>
                </div>
              </div>
              <div className="col-sm-12 col-lg-10">
                <div className="row">
                <div className="col-md-6">
                    <div className="form-group">
                      <label htmlFor="formrow-password-input">Khóa học</label>
                      <select className="custom-select">
                        <option selected>Chọn trạng thái</option>
                        <option value={1}>One</option>
                        <option value={2}>Two</option>
                        <option value={3}>Three</option>
                      </select>
                    </div>
                  </div>
                <CustomInput
                    column="6"
                    id="txtStuLastName"
                    type="text"
                    label="Last Name:"
                    labelSize="6"
                    rows="1"
                    frmField={formik.getFieldProps("lastName")}
                    err={formik.touched.lastName && formik.errors.lastName}
                    errMessage={formik.errors.lastName}
                    value={formik.values.lastName}
                    onChange={formik.handleChange}
                  />
                  <CustomInput
                    column="6"
                    id="txtStuLastName"
                    type="text"
                    label="Last Name:"
                    labelSize="6"
                    rows="1"
                    frmField={formik.getFieldProps("lastName")}
                    err={formik.touched.lastName && formik.errors.lastName}
                    errMessage={formik.errors.lastName}
                    value={formik.values.lastName}
                    onChange={formik.handleChange}
                  />
                  <CustomInput
                    column="6"
                    id="txtStuLastName"
                    type="text"
                    label="Last Name:"
                    labelSize="6"
                    rows="1"
                    frmField={formik.getFieldProps("lastName")}
                    err={formik.touched.lastName && formik.errors.lastName}
                    errMessage={formik.errors.lastName}
                    value={formik.values.lastName}
                    onChange={formik.handleChange}
                  />
                  <CustomInput
                    column="6"
                    id="txtStuLastName"
                    type="text"
                    label="Last Name:"
                    labelSize="6"
                    rows="1"
                    frmField={formik.getFieldProps("lastName")}
                    err={formik.touched.lastName && formik.errors.lastName}
                    errMessage={formik.errors.lastName}
                    value={formik.values.lastName}
                    onChange={formik.handleChange}
                  />
                  <CustomInput
                    column="6"
                    id="txtStuLastName"
                    type="text"
                    label="Last Name:"
                    labelSize="6"
                    rows="1"
                    frmField={formik.getFieldProps("lastName")}
                    err={formik.touched.lastName && formik.errors.lastName}
                    errMessage={formik.errors.lastName}
                    value={formik.values.lastName}
                    onChange={formik.handleChange}
                  />
                  <CustomInput
                    column="6"
                    id="txtStuLastName"
                    type="text"
                    label="Last Name:"
                    labelSize="6"
                    rows="1"
                    frmField={formik.getFieldProps("lastName")}
                    err={formik.touched.lastName && formik.errors.lastName}
                    errMessage={formik.errors.lastName}
                    value={formik.values.lastName}
                    onChange={formik.handleChange}
                  />
                  <CustomInput
                    column="6"
                    id="txtStuLastName"
                    type="text"
                    label="Last Name:"
                    labelSize="6"
                    rows="1"
                    frmField={formik.getFieldProps("lastName")}
                    err={formik.touched.lastName && formik.errors.lastName}
                    errMessage={formik.errors.lastName}
                    value={formik.values.lastName}
                    onChange={formik.handleChange}
                  />
                  <div className="col-md-6">
                    <div className="form-group">
                      <label htmlFor="formrow-password-input">Trường</label>
                      <select className="custom-select">
                        <option selected>Chọn khóa trường</option>
                        <option value={1}>One</option>
                        <option value={2}>Two</option>
                        <option value={3}>Three</option>
                      </select>
                    </div>
                  </div>
                  <div className="col-md-12">
                    <div className="form-group">
                      <label htmlFor="formrow-password-input">Ghi chú</label>
                      <textarea className="form-control" id="productdesc" placeholder="Mô tả, giới thiệu về học viên." rows={2} defaultValue={""} />
                    </div>
                  </div>
                </div>
              </div>
            </div>

          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleModalClose}>
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

export default Student;
