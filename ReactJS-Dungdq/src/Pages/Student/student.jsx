import React, { useState, Fragment, useEffect } from "react";
import $ from 'jquery';
import { useFormik } from "formik";
import * as Yup from "yup";
import { Link } from "react-router-dom";
import { Button, Modal } from "react-bootstrap";
import MultiSelect from "react-multi-select-component";
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import Select from 'react-select';
import Input from "../../Controls/input";
import studentService from "../../Services/studentService";
import univerService from "../../Services/universityService";
import companyService from "../../Services/companyService";
import intakeService from "../../Services/intakeService";
import api from "../../Services/api";

import "./student.css";
import studentCompanyService from "../../Services/studentCompanyService";
import studentIntakeService from "../../Services/studentIntakeService";


// Importing toastify module 
import {toast} from 'react-toastify';  
// Import toastify css file 
import 'react-toastify/dist/ReactToastify.css';  
toast.configure()

const Student = (props) => {
  //Test
  const test = [];
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
  const [selectedTypeStu, setSelectedTypeStu] = useState({ label: "Studying", value: "Studying", disabled: false });

  // Custom Select OneMultiSelect Type Student
  for (var i = 0; i < optionsTypeStu.length; i++) {
    if (selectedTypeStu.length >= 1) {
      optionsTypeStu[i].disabled = true;
    }
  }

  // Option For MultiCheckBox University
  const [selectedUniver, setSelectedUniver] = useState();
  const [univers, setUniver] = useState([]);

  // Option For MultiCheckBox Company
  const [selectedCompany, setSelectedCompany] = useState([]);
  const [companies, setCompanies] = useState([]);

  // Option For MultiCheckBox Intake
  const [selectedIntake, setSelectedIntake] = useState([]);
  const [intakes, setIntakes] = useState([]);

  //Option For MultiCheckBox Working Status
  const optionsWorkingStatus = [
    { label: "Active", value: "Active", disabled: false},
    { label: "Inactive", value: "Inactive", disabled: false},
  ];
  const [selectedWorkingStatus, setSelectedWorkingStatus] = useState({ label: "Inactive", value: "Inactive", disabled: false});

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

  const [modalUpdate, setModalUpdate] = useState(false);
  
  // Formik
  const formik = useFormik({
    initialValues: {
      codeStu: "",
      lastName: "",
      firstName: "",
      emailStu: "",
      gpa: "",
      phoneStu: "",
      addressStu: "",
      createdBy: "Admin",
      updatedBy:"Admin",
      noteStu: "",
      image: "",
      typeStudent: "",
      workingStatus: "",
      companies: "",
      university: "",
    },
    validationSchema: Yup.object({
      codeStu: Yup.string()
        .required("Required")
        .min(2, "Up to 255 characters"),
      lastName: Yup.string()
        .required("Required")
        .min(2, "Up to 200 characters"),
      firstName: Yup.string()
        .required("Required")
        .min(2, "Up to 255 characters"),
      emailStu: Yup.string().required("Required").min(2, "Up to 200 characters"),
      gpa: Yup.string().required("Required"),
      phoneStu: Yup.string().required("Required").min(2, "Up to 200 characters"),
      addressStu: Yup.string().required("Required").min(2, "Up to 255 characters"),
      noteStu: Yup.string().min(2, "Up to 2000 characters"),
    }),
    onSubmit: (values) => {
      handleFormSubmit(values);
    },
  }); 

  // Load Data
  const loadData = () => {
    studentService.getAll().then((res) => {
      if (res.status === 0) {
        setStudent(res.data);
      }
    });

    //Get all company
    companyService.getAll().then((res) => {
      if (res.status === 0) {
        setCompanies(res.data);
      }
    });

    //Get all university
    univerService.getAll().then((res) => {
      if (res.status === 0) {
        setUniver(res.data);
      }
    });
    // Get all intake
    intakeService.getAll().then((res) => {
      if (res.status === 0) {
        setIntakes(res.data);
        setshowIntakes(res.data);
        console.log(res.data);
      }
    });
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
      setModalUpdate(true);
      //check form Update Student
      studentService.get(dataId).then((res) => {
        formik.setValues(res.data[0]);
        console.log(res.data[0]);
        setModalShow(true);
      });
    } else {
      setModalUpdate(false);
      formik.resetForm();
      setModalShow(true);
    }
  };

  const confirmDeleteStudent = (e, studentId) => {
    if (e) e.preventDefault();
    confirmAlert({
      title: 'Confirm to submit',
      message: 'Bạn có muốn xóa sinh viên này?',
      buttons: [
        {
          label: 'Đồng ý',
          onClick: () => handleDeleteStudent(studentId)
        },
        {
          label: 'Hủy bỏ'
        }
      ]
    });
  };

  //Delete new Student
  const handleDeleteStudent = (studentId) => {
    studentIntakeService.get(studentId).then((res) => {
      const listIntaked = res.data;
      for (let d = 0; d <= listIntaked.length; d++) {
        if(d < listIntaked.length){
          studentIntakeService.remove(studentId,listIntaked[d].id.intakeId).then((res) => {
            if (res.status === 1) {
              console.error("Delete Student Intake error : " +res.status);
            }
          });
        }else{
          studentService.remove(studentId).then((res) => {
            if (res.status === 0) {
              toast.success("Delete Student Success");
              loadData();
            } else {
              toast.error(res.message);
            }
          });
        }
      }
    });
  };

  //Add new Student
  const handleFormSubmit = () => {
    if (studentId === 0) {
      studentService
        .add1(
          formik.values.firstName,
          formik.values.lastName,
          formik.values.codeStu,
          formik.values.addressStu,
          formik.values.phoneStu,
          formik.values.emailStu,
          selectedTypeStu.value,
          formik.values.gpa,
          selectedWorkingStatus.value,
          formik.values.noteStu,
          formik.values.image,
          formik.values.createdBy,
          selectedUniver.value
        )
        .then((stu) => {
          toast.success('Add new student success');
          const studentId = stu.data[0].id;
          const intakeId = selectedIntake.value;

          studentIntakeService
            .add(studentId, intakeId)
            .then((stuIntake) => {
              loadData();
            });
          formik.setValues(stu.data);
          loadData();
          handleModalClose();
        });
    } else {
      if (typeof (formik.values.image) === "string") {
        studentService
        .updateNoImages(
          studentId,
          formik.values.firstName,
          formik.values.lastName,
          formik.values.codeStu,
          formik.values.addressStu,
          formik.values.phoneStu,
          formik.values.emailStu,
          selectedTypeStu.value,
          formik.values.gpa,
          selectedWorkingStatus.value,
          formik.values.noteStu,
          "Admin",
          selectedUniver.value
        )
        .then((res) => {
          const studentId = res.data[0].id;
            studentIntakeService.get(studentId).then((res) => {
            const arrayIntaked = Object.keys(res.data).map(function(k){return res.data[k].id.intakeId}).join(",");
            const arrayNewIntake =  Object.keys(selectedIntake).map(function(k){return selectedIntake[k].value}).join(",");
              studentIntakeService.updateIntakeOfStuArray(arrayNewIntake,studentId,arrayIntaked).then((res) => {
                if (res.status === 0) {
                  toast.success("Update Success");
                  console.info("Update Student-Intake success");
                  loadData();
                  handleModalClose();
                } else {
                  toast.error("Update Student-Intake error :(");
                }
              });
          });
        }).catch((error) => {
          console.error(error);
        });
      }else{
        studentService
        .updateHasImages(
          studentId,
          formik.values.firstName,
          formik.values.lastName,
          formik.values.codeStu,
          formik.values.addressStu,
          formik.values.phoneStu,
          formik.values.emailStu,
          selectedTypeStu.value,
          formik.values.gpa,
          selectedWorkingStatus.value,
          formik.values.noteStu,
          formik.values.image,
          "Admin",
          selectedUniver.value
        )
        .then((res) => {
          const studentId = res.data[0].id;
            studentIntakeService.get(studentId).then((res) => {
            const arrayIntaked = Object.keys(res.data).map(function(k){return res.data[k].id.intakeId}).join(",");
            const arrayNewIntake =  Object.keys(selectedIntake).map(function(k){return selectedIntake[k].value}).join(",");
              studentIntakeService.updateIntakeOfStuArray(arrayNewIntake,studentId,arrayIntaked).then((res) => {
                if (res.status === 0) {
                  toast.success("Update Success");
                  console.info("Update Student-Intake success");
                  loadData();
                  handleModalClose();
                } else {
                  toast.error("Update Student-Intake error :(");
                }
              });
          });
        }).catch((error) => {
          console.error(error);
        });
      }
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
                                onClick={(e) => confirmDeleteStudent(e, student.id)}
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
              {modalUpdate ? (
                <Modal.Title>Update Student</Modal.Title>
              ) : (
                <Modal.Title>New Student</Modal.Title>
              )}
        </Modal.Header>
        <form onSubmit={formik.handleSubmit} enctype="multipart/form-data">
          <Modal.Body>
            <div className="row px-3">         
              <div className="col-sm-12 col-lg-2">
                <div className="avatar-upload-jsk py-2">
                  <div className="avatar-edit">
                    <input 
                      type="file" 
                      name="image" 
                      id="imageUpload"
                      onChange={(event)=>{
                        formik.setFieldValue("image",event.currentTarget.files[0]);
                        if (event.currentTarget.files && event.currentTarget.files[0]) {
                            var reader = new FileReader();
                            reader.onload = function(e) {
                                $('#imagePreview').css('background-image', 'url('+e.target.result +')');
                                $('#imagePreview').hide();
                                $('#imagePreview').fadeIn(650);
                            }
                            reader.readAsDataURL(event.currentTarget.files[0]);
                        }
                      }}
                    />
                  </div>
                  <div className="avatar-preview-jsk">
                    <label htmlFor="imageUpload" className="imageUpload"> <i className="bx bxs-cloud-upload" /></label>
                    {modalUpdate ? (
                     <div id="imagePreview" style={{backgroundImage: 'url("'+api.url.image + formik.values.image +'")'}} />
                    ) : (
                     <div id="imagePreview" style={{backgroundImage: 'url("assets/images/avata-playhoder.jpg")'}} />
                    )}
                  </div>
                </div>
              </div>
              <div className="col-sm-12 col-lg-10">
                <div className="row">

                  <div className="col-6">
                    <div class="form-group">
                      <label htmlFor="multicheckIntake"> Khóa học </label>
                      {modalUpdate ? (
                        <Select
                        id="multicheckIntake"
                        isMulti
                        placeholder="Chọn khóa học..."
                        onChange={(val)=> {setSelectedIntake(val)}}
                        value={selectedIntake}
                        closeMenuOnSelect={true}
                        options={intakes.map((e) => ({
                          label: e.nameIntake,
                          value: e.id,
                          statusIntake: e.statusIntake,
                        }))}
                        isOptionDisabled={(option) => option.statusIntake !== 'Doing'}
                      />
                      ) : (
                        <Select
                          id="multicheckIntake"
                          placeholder="Chọn khóa học..."
                          onChange={(val)=> {setSelectedIntake(val)}}
                          value={selectedIntake}
                          closeMenuOnSelect={true}
                          options={intakes.map((e) => ({
                            label: e.nameIntake,
                            value: e.id,
                            statusIntake: e.statusIntake,
                          }))}
                          isOptionDisabled={(option) => option.statusIntake !== 'Doing'}
                        />
                      )}
                  
                      </div>
                    </div>

                    {/* {modalUpdate ? (
                      <div className="col-6">
                        <div class="form-group">
                          <label htmlFor="setSelectedWorkingStatus"> Trạng thái việc làm</label>
                          <Select
                              id="setSelectedWorkingStatus"
                              placeholder="Chọn trạng thái làm việc..."
                              onChange={(val)=> {setSelectedWorkingStatus(val)}}
                              value={selectedWorkingStatus}
                              closeMenuOnSelect={true}
                              options={optionsWorkingStatus}
                            />
                        </div>
                      </div>
                    ) : ("")} */}

                    <div className="col-6">
                      <div class="form-group">
                        <label htmlFor="selectedTypeStu"> Trạng thái học viên</label>
                        <Select
                            id="selectedTypeStu"
                            placeholder="Chọn trạng  thái học viên..."
                            onChange={(val)=> {setSelectedTypeStu(val)}}
                            value={selectedTypeStu}
                            closeMenuOnSelect={true}
                            options={optionsTypeStu}
                          />
                      </div>
                    </div>

                    <div className="col-6">
                      <div class="form-group">
                        <label htmlFor="selectedUniver"> Trường</label>
                        <Select
                            id="selectedUniver"
                            placeholder="Chọn trường..."
                            onChange={(val)=> {setSelectedUniver(val)}}
                            value={selectedUniver}
                            closeMenuOnSelect={true}
                            options={univers.map((e) => ({
                              label: e.nameUni,
                              value: e.id
                            }))}
                          />
                      </div>
                    </div>

            <Input
              typeInput="1"
              column="6"
              rows="1"
              id="codeStu"
              name="codeStu"
              type="text"
              label="Mã sinh viên"
              frmField={formik.getFieldProps("codeStu")}
              err={formik.touched.codeStu && formik.errors.codeStu}
              errMessage={formik.errors.codeStu}
              value={formik.values.codeStu}
              onChange={formik.handleChange}
            />
            
            <Input
              typeInput="1"
              column="6"
              rows="1"
              id="txtStuFirstName"
              type="text"
              label="Họ và tên lót"
              frmField={formik.getFieldProps("firstName")}
              err={formik.touched.firstName && formik.errors.firstName}
              errMessage={formik.errors.firstName}
              value={formik.values.firstName}
              onChange={formik.handleChange}
            />

            <Input
              typeInput="1"
              column="6"
              rows="1"
              id="txtStuLastName"
              type="text"
              label="Tên"
              frmField={formik.getFieldProps("lastName")}
              err={formik.touched.lastName && formik.errors.lastName}
              errMessage={formik.errors.lastName}
              value={formik.values.lastName}
              onChange={formik.handleChange}
            />

            <Input
              typeInput="1"
              column="6"
              rows="1"
              id="txtStuEmail"
              type="email"
              label="Email"
              frmField={formik.getFieldProps("emailStu")}
              err={formik.touched.emailStu && formik.errors.emailStu}
              errMessage={formik.errors.emailStu}
              value={formik.values.emailStu}
              onChange={formik.handleChange}
            />
            <Input
              typeInput="1"
              column="6"
              rows="1"
              id="txtStuPhone"
              type="text"
              label="Số điện thoại"
              frmField={formik.getFieldProps("phoneStu")}
              err={formik.touched.phoneStu && formik.errors.phoneStu}
              errMessage={formik.errors.phoneStu}
              value={formik.values.phoneStu}
              onChange={formik.handleChange}
            />
            <Input
              typeInput="1"
              column="6"
              rows="1"
              id="txtStuAddress"
              type="text"
              label="Địa chỉ"
              frmField={formik.getFieldProps("addressStu")}
              err={formik.touched.addressStu && formik.errors.addressStu}
              errMessage={formik.errors.addressStu}
              value={formik.values.addressStu}
              onChange={formik.handleChange}
            />

            <Input
              typeInput="1"
              column="6"
              rows="1"
              id="txtStuGPA"
              type="number"
              label="Điển trung bình"
              frmField={formik.getFieldProps("gpa")}
              err={formik.touched.gpa && formik.errors.gpa}
              errMessage={formik.errors.gpa}
              value={formik.values.gpa}
              onChange={formik.handleChange}
            />
            <Input
              typeInput="1"
              column="6"
              rows="2"
              id="txtStuNote"
              type="text"
              label="Note:"
              frmField={formik.getFieldProps("noteStu")}
              err={formik.touched.note && formik.errors.note}
              errMessage={formik.errors.note}
              value={formik.values.note}
              onChange={formik.handleChange}
            />

                {/* {modalUpdate ? (
                  <Fragment>
                  <Input
                    typeInput="1"
                    column="6"
                    rows="1"
                    readOnly
                    type="text"
                    label="Người tạo"
                    value = {formik.values.createdBy}
                  />
                  <Input
                    typeInput="1"
                    column="6"
                    rows="1"
                    readOnly
                    type="text"
                    label="Ngày tạo"
                    value = {formik.values.createdDate}
                  />
                    </Fragment>
                    ) : ("")} */}
              
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
