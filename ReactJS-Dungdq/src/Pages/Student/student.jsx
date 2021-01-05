import React, { useState, Fragment, useEffect } from "react";
import $, { map } from 'jquery';
import { useFormik } from "formik";
import * as Yup from "yup";
import { Link } from "react-router-dom";
import { Button, Modal } from "react-bootstrap";
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import queryString from 'query-string';
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
import Pagination from './paginationStudent';
import SearchStudent from './searchStudent';
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
    { label: "Studying", value: "Studying" },
    { label: "Graduate", value: "Graduate" },
    { label: "Reserve", value: "Reserve" },
  ];
  const [selectedTypeStu, setSelectedTypeStu] = useState({ label: "Studying", value: "Studying"});

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
    { label: "Active", value: "Active"},
    { label: "Inactive", value: "Inactive"},
  ];
  const [selectedWorkingStatus, setSelectedWorkingStatus] = useState({ label: "Inactive", value: "Inactive"});

  // Custom Select OneMultiSelect Type Student
  for (var i = 0; i < optionsWorkingStatus.length; i++) {
    if (selectedWorkingStatus.length >= 1) {
      optionsWorkingStatus[i].disabled = true;
    }
  }

  
  //[] Student Intake ID
  const [allStudentIntake, setAllStudentIntake] = useState([]);
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
      typeStudent: { label: "Studying", value: "Studying" },
      workingStatus: { label: "Inactive", value: "Inactive"},
      university: "",
      intake: "",
      updateIntake: [{label: 'fake', values: 'fake'}],
      image: "",
    },
    validationSchema: Yup.object({
      codeStu: Yup.string()
        .required("Required")
        .min(2, "Up to 255 characters").max(255,"Max 255 characters"),
      lastName: Yup.string()
        .required("Required")
        .min(2, "Up to 200 characters").max(255,"Max 255 characters"),
      firstName: Yup.string()
        .required("Required")
        .min(2, "Up to 255 characters").max(255,"Max 255 characters"),
      emailStu: Yup.string().email().required("Required").min(2, "Up to 200 characters").max(255,"Max 255 characters"),
      gpa: Yup.number().required("Required"),
      phoneStu: Yup.string().required("Required").min(2, "Up to 200 characters"),
      addressStu: Yup.string().required("Required").min(2, "Up to 255 characters"),
      noteStu: Yup.string().min(2, "Up to 2000 characters").max(2000,"Max 2000 characters"),
      intake: Yup.object().required("Required"),
      updateIntake: Yup.array().required("Required"),
      university: Yup.object().required("Required"),
      image: Yup.mixed()
      .required("required")
      .test(
        "fileSize",
        "too large (<2Mb)",
        value => value && value.size <= 2000000 //2MB
      )
      .test(
        "fileFormat",
        "unsupported Format",
        value => value && [
          "image/jpg",
          "image/jpeg",
          "image/gif",
          "image/png"
        ].includes(value.type)
      ),
    }),
    onSubmit: (values) => {
      console.log("formik: ",values);
      handleFormSubmit(values);
    },
  }); 

  // Student
  const [students, setStudent] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const handleModalClose = () => setModalShow(false);

  const [pagination, setPagination] = useState({
    page:0,
    size:4,
    totalRows: 1
  });

  const [filters, setFilters] = useState({
    page: 0,
    size: 4,
    sort: 'ASC',
    typeStudent: 'n',
    search: '',
  });
  
  const handlePageChange = (newPage) => {
    setFilters({
      ...filters,
      page: newPage,
    })
  };

  const handedSearchChange = (newSearch) => {
    setFilters({
      ...filters,
      page: 0,
      search: newSearch.searchTerm,
    })
  };

  // Load Data
  const loadData = () => {
    //Get Student with Filters
    const paramsFilters = queryString.stringify(filters);
      studentService.paginationStudent(paramsFilters).then((res) => {
        const totalRows = res.data[0].totalElements;
        const size = res.data[0].size;
        const pageCurrent = res.data[0].pageable.pageNumber;
        if (res.status === 0) {
          setStudent(res.data[0].content);
          setPagination({
            page: pageCurrent,
            size: size,
            totalRows: totalRows,
          })
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
      }
    });

     // Get all Student Intake
     studentIntakeService.getAll().then((res) => {
      if (res.status === 0) {
        setAllStudentIntake(res.data);
      }
    });
  };

  //Didmount load data major
  useEffect(() => {
    loadData();
  }, [filters]);

  //Update Student State
  const [studentId, setStudentId] = useState(0);
  //Check Form Update And Add Student
  const handleModalShow = (e, dataId) => {
    if (e) e.preventDefault();
    setStudentId(dataId);

    if (dataId > 0) {
      setModalUpdate(true);
      setModalShow(true);
      studentService.get(dataId).then((res) => {
        const studentById = res.data[0];
        console.log(studentById);
        formik.setValues(studentById);
        formik.setFieldValue('typeStudent',{ label: studentById.typeStu, value: studentById.typeStu})
        formik.setFieldValue('workingStatus',{ label: studentById.workingStatus, value: studentById.workingStatus})
        formik.setFieldValue('university',{value: studentById.university.id,label: studentById.university.nameUni})
        formik.setFieldValue('intake',{label:'fake', values: 'fake'})
        formik.setFieldValue('updateIntake',[{label:'fake', values: 'fake'}])
        formik.setFieldValue('oldImage',studentById.image)
        formik.setFieldValue('image', {name: "fake_image", size: 1, type: "image/png"})
        setSelectedIntake([]);
        for (let i = 0; i < allStudentIntake.length; i++) {
          if (allStudentIntake[i].id.studentId == dataId) {
            for (let j = 0; j < intakes.length; j++) {
              if (intakes[j].id == allStudentIntake[i].id.intakeId) {
                setSelectedIntake(oldArray => [...oldArray, {label:intakes[j].nameIntake , value:allStudentIntake[i].id.intakeId }]);
              }
            }
            
          }          
        }
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
          formik.values.typeStudent.value,
          formik.values.gpa,
          formik.values.workingStatus.value,
          formik.values.noteStu,
          formik.values.image,
          "Admin",
          formik.values.university.value,
        )
        .then((stu) => {
          if (stu.status === 0) {
            const studentId = stu.data[0].id;
            const intakeId = formik.values.intake.value;
            studentIntakeService
              .add(studentId, intakeId)
                .then((stuIntake) => {
                  if (stuIntake.status === 0){
                    toast.success('Add new student success');
                    loadData();
                    handleModalClose();
                  }else{
                    toast.error('Add student error, '+ stuIntake.message);
                  }
                });
          }else{
            if (stu.message == "Duplicate Email Student") {
              formik.setFieldError('emailStu',stu.message);
            }
            if(stu.message == "Required request part 'image' is not present"){
              formik.setFieldError('image',stu.message);
            }
            if(stu.message == "Duplicate Student Code"){
              formik.setFieldError('codeStu',stu.message);
            }
          }
        });
    } else {
      if (formik.values.image.name === "fake_image") {
        studentService
        .updateNoImages(
          studentId,
          formik.values.firstName,
          formik.values.lastName,
          formik.values.codeStu,
          formik.values.addressStu,
          formik.values.phoneStu,
          formik.values.emailStu,
          formik.values.typeStudent.value,
          formik.values.gpa,
          formik.values.workingStatus.value,
          formik.values.noteStu,
          "Admin",
          formik.values.university.value,
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
          formik.values.typeStudent.value,
          formik.values.gpa,
          formik.values.workingStatus.value,
          formik.values.noteStu,
          formik.values.image,
          "Admin",
          formik.values.university.value,
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
        {/* Table */}
        <div className="row">
            <div className="col-12">
              <div className="card">
                <div className="card-body">
                  <div className="row mb-2">
                    <div className="col-sm-4">
                      <div className="search-box mr-2 mb-2 d-inline-block">
                        <div className="position-relative">
                          <SearchStudent onSubmit={handedSearchChange}/>
                          <i className="bx bx-search-alt search-icon" />
                        </div>
                      </div>
                    </div>
                    <div className="col-sm-8">
                      <div className="text-sm-right">
                        <button type="button" onClick={() => handleModalShow(null, 0)} className="btn btn-success btn-rounded mb-2 mr-2">
                          <i className="bx bxs-add-to-queue"/> Add New Student</button>
                      </div>
                    </div>
                  </div>
                  <div className="table-responsive">
                    <table className="table table-centered table-nowrap">
                      <thead className="thead-light">
                        <tr>
                          <th style={{width: 10}}>#</th>
                          <th>Code</th>
                          <th>Avatar</th>
                          <th>Full name</th>
                          <th>Phone</th>
                          <th>Email</th>
                          <th>University</th>
                          <th>Status</th>
                          <th>Action</th>
                        </tr>
                      </thead>
                      <tbody>
                        {students.length == 0 ? (
                          <tr className="text-center">
                            <td colspan="9">Not found student with keyword: "<strong>{filters.search}</strong>"!</td>
                          </tr>
                        ):(
                          students.map((student, idx) => {
                            return (
                            <tr>
                              <td>{idx + 1}</td>
                              <td><a href="javascript: void(0);" className="text-body font-weight-bold">{student.codeStu}</a> </td>
                              <td style={{width: 100}}>
                                <img className="img-fluid img-thumbnai rounded mx-auto" 
                                    src={api.url.image + student.image}
                                    alt={"Hinh " + student.lastName} />
                              </td>
                              <td>{student.firstName} {student.lastName}</td>
                              <td>{student.phoneStu}</td>
                              <td>{student.emailStu}</td>
                              {/* <td>ISC-13</td> */}
                              <td>{student.university.nameUni}</td>
                              <td>
                                {(() => {
                                  switch (student.typeStu) {
                                    case "Studying":
                                      return <span className="badge badge-pill badge-success font-size-12">{student.typeStu}</span>;
                                    case "Graduate":
                                      return <span className="badge badge-pill badge-primary font-size-12">{student.typeStu}</span>;
                                    case "Reserve":
                                      return <span className="badge badge-pill badge-warning font-size-12">{student.typeStu}</span>;
                                  }
                                })()}
                              </td>
                              <td>
                                <a href="javascript:void(0);" onClick={(e) => handleModalShow(e, student.id)} className="mr-3 text-primary"><i className="fas fa-user-edit font-size-15" /></a>
                                <a href="javascript:void(0);" onClick={(e) => confirmDeleteStudent(e, student.id)} className="text-danger"><i className="far fa-trash-alt font-size-15" /></a>
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
        {/* Table */}
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
                <div className="avatar-upload-jsk py-2 text-center">
                  <div className="avatar-edit">
                    <input 
                      type="file" 
                      name="image" 
                      id="imageUpload"
                      accept=".png, .jpg, .jpeg, .gif"
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
                  <div className="avatar-preview-jsk mb-2">
                    <label htmlFor="imageUpload" className="imageUpload"> <i className="bx bxs-cloud-upload" /></label>
                    {modalUpdate ? (
                     <div id="imagePreview" style={{backgroundImage: 'url("'+api.url.image + formik.values.oldImage +'")'}} />
                    ) : (
                     <div id="imagePreview" style={{backgroundImage: 'url("https://timvieclam.xyz/images/avata-playhoder.jpg")'}} />
                    )}
                  </div>
                  {(formik.touched.image && formik.errors.image)?(
                    <small class="text-danger font-size-13 font-weight-bold m-auto pt-5">
                      Avatar is {formik.errors.image}
                  </small>
                  ):("")}
                </div>
              </div>
              <div className="col-sm-12 col-lg-10">
                <div className="row">

                  <div className="col-6">
                    <div class="form-group">
                      <label htmlFor="multicheckIntake"> Intake<sup>*</sup> </label>
                      {modalUpdate ? (
                        <Fragment>
                        <Select
                        id="multicheckIntake"
                        isMulti
                        placeholder="Choose a intake..."
                        onChange={(val)=> {
                          setSelectedIntake(val)
                          formik.setFieldValue('updateIntake',val)
                          console.log(val);
                        }}
                        value={selectedIntake}
                        closeMenuOnSelect={true}
                        options={intakes.map((e) => ({
                          label: e.nameIntake,
                          value: e.id,
                          statusIntake: e.statusIntake,
                        }))}
                        isOptionDisabled={(option) => option.statusIntake !== 'Doing'}
                      />
                      {(formik.touched.updateIntake && formik.errors.updateIntake) ?(
                        <small class="text-danger">
                          Required
                        </small>
                      ):("")}
                      </Fragment>
                      ) : (
                        <Fragment>
                        <Select
                          id="multicheckIntake"
                          placeholder="Choose a intake..."
                          onChange={(val)=> { formik.setFieldValue('intake',val) }}
                          value={formik.values.intake}
                          closeMenuOnSelect={true}
                          options={intakes.map((e) => ({
                            label: e.nameIntake,
                            value: e.id,
                            statusIntake: e.statusIntake,
                          }))}
                          isOptionDisabled={(option) => option.statusIntake !== 'Doing'}
                        />
                        {(formik.touched.intake && formik.errors.intake) ?(
                          <small class="text-danger">
                            {formik.errors.intake}
                          </small>
                        ):("")}
                      </Fragment>
                      )}
                  
                      </div>
                    </div>
                    
                    <div className="col-6">
                      <div class="form-group">
                        <label htmlFor="selectedUniver"> University<sup>*</sup></label>
                        <Select
                            id="selectedUniver"
                            placeholder="Choose a university..."
                            onChange={(val)=> { formik.setFieldValue('university',val) }}
                            value={formik.values.university}
                            closeMenuOnSelect={true}
                            options={univers.map((e) => ({
                              label: e.nameUni,
                              value: e.id
                            }))}
                          />
                          {(formik.touched.university && formik.errors.university) ?(
                            <small class="text-danger">
                              {formik.errors.university}
                            </small>
                          ):("")}
                      </div>
                    </div>

                    {modalUpdate ? (
                      <div className="col-3">
                        <div class="form-group">
                          <label htmlFor="setSelectedWorkingStatus"> Working status</label>
                          <Select
                              id="setSelectedWorkingStatus"
                              placeholder="Choose a working status..."
                              onChange={(val)=> { formik.setFieldValue('workingStatus',val) }}
                              value={formik.values.workingStatus}
                              options={optionsWorkingStatus}
                              closeMenuOnSelect={true}
                            />
                        </div>
                      </div>
                    ) : ("")}


                    <div className={`col-${modalUpdate ? (3):(6)}`}>
                      <div class="form-group">
                        <label htmlFor="selectedTypeStu"> Learing status<sup>*</sup></label>
                        <Select
                            id="selectedTypeStu"
                            placeholder="Choose a type students..."
                            onChange={(val)=> { formik.setFieldValue('typeStudent',val) }}
                            frmField={formik.getFieldProps("typeStudent")}
                            value={formik.values.typeStudent}
                            closeMenuOnSelect={true}
                            err={formik.touched.typeStudent && formik.errors.typeStudent}
                            errMessage={formik.errors.typeStudent}
                            options={optionsTypeStu}
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
              label="Student code *"
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
              label="First name *"
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
              label="Last name *"
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
              label="Email *"
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
              label="Phone number"
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
              label="Address"
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
              label="GPA *"
              frmField={formik.getFieldProps("gpa")}
              err={formik.touched.gpa && formik.errors.gpa}
              errMessage={formik.errors.gpa}
              value={formik.values.gpa}
              onChange={formik.handleChange}
            />
            <Input
              typeInput="1"
              column="12"
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

                {modalUpdate ? (
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
