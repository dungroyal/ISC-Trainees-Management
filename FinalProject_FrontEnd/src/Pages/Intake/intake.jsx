import { Fragment } from "react";
import React, { useState, useEffect } from "react";
import intakeService from "../../Services/intakeService";
import { Button, Form, Modal, Col, Row } from "react-bootstrap";
import Input from "../../Controls/input";
import { useFormik } from "formik";
import * as Yup from "yup";
import majorService from "../../Services/majorService";
import Select from "react-select";

// Intake
const Intake = () => {
  const [intake, setIntake] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const handleModalClose = () => setModalShow(false);

  const optionIntakeStatus = [
    { label: "Chưa kích hoat", value: "None", disabled: false },
    { label: "Đang học", value: "Doing", disabled: false },
    { label: "Đã hoàn thành", value: "Done", disabled: false },
  ];

  const [selectStatusIntake, setSelectStatusIntake] = useState({
    label: "Chưa kích hoat",
    value: "None",
    disabled: false,
  });

  // Custom select Intake Status
  for (var i = 0; i < optionIntakeStatus.length; i++) {
    if (selectStatusIntake.length >= 1) {
      optionIntakeStatus[i].disabled = true;
    }
  }

  // Major
  const [major, setMajor] = useState([]);
  const [selectMajor, setSelectMajor] = useState();

  // Formik
  const formik = useFormik({
    initialValues: {
      codeIntake: "",
      nameIntake: "",
      startDay: "",
      endDay: "",
      statusIntake: selectStatusIntake.value,
      // statusIntake: "",
      createdBy: "Admin",
      createdDate: "",
      updatedBy: "",
      updatedDate: "",
      selectMajor: "",
    },

    validationSchema: Yup.object({
      // selectMajor: Yup.string().required("Major is required"),
      codeIntake: Yup.string()
        .required("Intake code is required")
        .max(50, "Intake code must be lester than 50 characters"),
      nameIntake: Yup.string()
        .required("Intake name is required")
        .max(200, "Intake name must be lester than 200 characters"),
      startDay: Yup.string().required("Started Date is required"),
      endDay: Yup.string().required("Ended Date is required"),
    }),

    onSubmit: (values) => {
      console.log(values);
      handleFormSubmit(values);
      console.log(selectMajor.value);
    },
  });

  // Load data
  const loadData = () => {
    // console.log(selectStatusIntake.value);
    // get all intake
    intakeService.getAll().then((res) => {
      if (res.status === 0) {
        setIntake(res.data);
      }
    });
    // get all major
    majorService.getAll().then((res) => {
      if (res.status === 0) {
        setMajor(res.data);
      }
    });
  };

  // Update Intake
  const [intakeId, setIntakeId] = useState(0);
  const handleModalShow = (e, dataId) => {
    if (e) e.preventDefault();
    setIntakeId(dataId);
    if (dataId > 0) {
      intakeService.get(dataId).then((res) => {
        formik.setValues(res.data[0]);
        setModalShow(true);
      });
    } else {
      // setModalUpdate(false);
      formik.resetForm();
      setModalShow(true);
    }
  };
  // Add new intake
  // const handleFormSubmit = (data) => {
  //   if (intakeId === 0) {
  //     intakeService.add(data, selectMajor.value).then((res) => {
  //       // const intakeId = res.data[0].id;
  //       console.log(res.data);
  //       // formik.setValues(res);
  //       loadData();
  //       handleModalClose();
  //     });
  //   } else {
  //     console.log(intakeId);
  //     intakeService.update(intakeId, selectMajor.value, data).then((res) => {
  //       if (res.status === 0) {
  //         loadData();
  //         handleModalClose();
  //         console.log(data);
  //       } else {
  //       }
  //     });
  //   }
  // };

  const handleFormSubmit = (data) => {
    if (intakeId === 0) {
      console.log(data);
      intakeService.add(data, selectMajor.value).then((res) => {
        if (res.status === 0) {
          loadData();
          handleModalClose();
        } else {
        }
      });
    } else {
      intakeService.update(intakeId, selectMajor.value, data).then((res) => {
        if (res.status === 0) {
          loadData();
          handleModalClose();
          console.log(data);
        } else {
        }
      });
    }
  };

  const deleteRow = (e, dataId) => {
    e.preventDefault();
    intakeService.remove(dataId).then((res) => {
      if (res.status === 0) {
        loadData();
      } else {
      }
    });
  };

  useEffect(() => {
    loadData();
  }, []);

  return (
    <Fragment>
      <div className="row">
        <div className="col-12">
          <div className="page-title-box d-flex align-items-center justify-content-between">
            <h4 className="mb-0 font-size-18">Danh sách Khóa Học</h4>
            <div className="page-title-right">
              <ol className="breadcrumb m-0">
                <li className="breadcrumb-item">
                  <a href="#">ISC Quang Trung</a>
                </li>
                <li className="breadcrumb-item active">Quản lý Khóa Học</li>
              </ol>
            </div>
          </div>
        </div>
      </div>
      {/* end page title */}
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
                      <i className="bx bx-search-alt search-icon" />
                    </div>
                  </div>
                </div>
                <div className="col-sm-8">
                  <div className="text-sm-right">
                    <button
                      type="button"
                      data-toggle="modal"
                      onClick={() => handleModalShow(null, 0)}
                      className="btn btn-success btn-rounded waves-effect waves-light mb-2 mr-2"
                    >
                      <i className="bx bxs-add-to-queue" /> Thêm mới khóa học
                    </button>
                  </div>
                </div>
                {/* end col*/}
              </div>
              <div className="table-responsive">
                <table className="table table-centered table-nowrap">
                  <thead className="thead-light">
                    <tr>
                      <th style={{ width: 20 }}>
                        <div className="custom-control custom-checkbox">
                          <input
                            type="checkbox"
                            className="custom-control-input"
                            id="customCheck1"
                          />
                          <label
                            className="custom-control-label"
                            htmlFor="customCheck1"
                          >
                            &nbsp;
                          </label>
                        </div>
                      </th>
                      <th>Mã khóa học</th>
                      <th>Tên khóa học</th>
                      <th>Ngày bắt đầu</th>
                      <th>Ngày kết thúc</th>
                      <th>Trạng thái</th>
                      <th>Người tạo</th>
                      <th>Ngày tạo</th>
                      <th>Người cập nhật</th>
                      <th>Ngày cập nhật</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    {intake.map((item) => {
                      return (
                        <tr key={item.id}>
                          <td>
                            <div className="custom-control custom-checkbox">
                              <input
                                type="checkbox"
                                className="custom-control-input"
                                id="customCheck2"
                              />
                              <label
                                className="custom-control-label"
                                htmlFor="customCheck2"
                              >
                                &nbsp;
                              </label>
                            </div>
                          </td>
                          <td>
                            <a href="#" className="text-body font-weight-bold">
                              {item.codeIntake}
                            </a>{" "}
                          </td>
                          <td>{item.nameIntake}</td>
                          <td>{item.startDay}</td>
                          <td>{item.endDay}</td>
                          <td>{item.statusIntake}</td>
                          <td>{item.createdBy}</td>
                          <td>{item.createdDate}</td>
                          <td>{item.updatedBy}</td>
                          <td>{item.updatedDate}</td>
                          <td>
                            <a
                              href="#"
                              className="mr-3 text-primary"
                              data-toggle="modal"
                              onClick={(e) => handleModalShow(e, item.id)}
                            >
                              <i className="fas fa-user-edit font-size-18" />
                            </a>
                            <a
                              href="#"
                              className="text-danger"
                              onClick={(e) => deleteRow(e, item.id)}
                            >
                              <i className="far fa-trash-alt font-size-18" />
                            </a>
                          </td>
                        </tr>
                      );
                    })}
                  </tbody>
                </table>
              </div>
              <ul className="pagination pagination-rounded justify-content-end mb-2">
                <li className="page-item disabled">
                  <a className="page-link" href="#" aria-label="Previous">
                    <i className="mdi mdi-chevron-left" />
                  </a>
                </li>
                <li className="page-item active">
                  <a className="page-link" href="#">
                    1
                  </a>
                </li>
                <li className="page-item">
                  <a className="page-link" href="#">
                    2
                  </a>
                </li>
                <li className="page-item">
                  <a className="page-link" href="#">
                    3
                  </a>
                </li>
                <li className="page-item">
                  <a className="page-link" href="#">
                    4
                  </a>
                </li>
                <li className="page-item">
                  <a className="page-link" href="#">
                    5
                  </a>
                </li>
                <li className="page-item">
                  <a className="page-link" href="#" aria-label="Next">
                    <i className="mdi mdi-chevron-right" />
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>

      <Modal
        show={modalShow}
        onHide={handleModalClose}
        size="xl"
        backdropClassName="static"
        keyboard={false}
      >
        <Modal.Header closeButton>
          {/* {modalUpdate ? (
            <Modal.Title>Cập nhật khóa học</Modal.Title>
          ) : {
            <Modal.Title>Thêm khóa học</Modal.Title>
          }} */}
          <Modal.Title>Thêm khóa học</Modal.Title>
        </Modal.Header>
        <form onSubmit={formik.handleSubmit}>
          <Modal.Body>
            <div className="row px-4">
              <Input
                column="6"
                typeInput="1"
                rows="1"
                id="codeIntake"
                type="text"
                label="Mã khóa học"
                frmField={formik.getFieldProps("codeIntake")}
                err={formik.touched.codeIntake && formik.errors.codeIntake}
                errMessage={formik.errors.codeIntake}
              />

              <div className="col-6">
                <div className="form-group">
                  <label htmlFor="selectedMajor"> Chuyên ngành</label>
                  <Select
                    id="selectedMajor"
                    placeholder="Chọn chuyên ngành..."
                    // err={
                    //   formik.touched.selectMajor && formik.errors.selectMajor
                    // }
                    // errMessage={formik.errors.selectMajor}
                    onChange={(val) => {
                      setSelectMajor(val);
                    }}
                    value={selectMajor}
                    closeMenuOnSelect={true}
                    options={major.map((e) => ({
                      label: e.nameMajor,
                      value: e.id,
                    }))}
                  />
                </div>
              </div>

              <Input
                column="6"
                typeInput="1"
                rows="1"
                id="nameIntake"
                type="text"
                label="Tên khóa học"
                frmField={formik.getFieldProps("nameIntake")}
                err={formik.touched.nameIntake && formik.errors.nameIntake}
                errMessage={formik.errors.nameIntake}
              />

              <div className="col-md-6">
                <div className="form-group">
                  <label htmlFor="setSelectStatusIntake">Trạng thái</label>
                  <Select
                    id="setSelectStatusIntake"
                    placeholder="Chọn trạng thái khóa học..."
                    onChange={(val) => {
                      setSelectStatusIntake(val);
                      console.log(val);
                    }}
                    value={selectStatusIntake}
                    closeMenuOnSelect={true}
                    // options={optionIntakeStatus.map((item) => ({
                    //   label: item.label,
                    //   value: item.value,
                    // }))}
                    options={optionIntakeStatus}
                  />
                </div>
              </div>

              <Input
                column="6"
                typeInput="1"
                rows="1"
                id="startDay"
                type="date"
                label="Ngày bắt đầu"
                frmField={formik.getFieldProps("startDay")}
                err={formik.touched.startDay && formik.errors.startDay}
                errMessage={formik.errors.startDay}
              />

              <Input
                column="6"
                typeInput="1"
                rows="1"
                id="endDay"
                type="date"
                label="Ngày kết thúc"
                frmField={formik.getFieldProps("endDay")}
                err={formik.touched.endDay && formik.errors.endDay}
                errMessage={formik.errors.endDay}
              />

              {/* <Input
                column="6"
                typeInput="1"
                rows="1"
                id="createdBy"
                type="text"
                label="Người tạo"
                frmField={formik.getFieldProps("createdBy")}
                err={formik.touched.createdBy && formik.errors.createdBy}
                errMessage={formik.errors.createdBy}
              />

              <Input
                column="6"
                typeInput="1"
                rows="1"
                id="updatedBy"
                type="text"
                label="Người cập nhật"
                frmField={formik.getFieldProps("updatedBy")}
                err={formik.touched.updatedBy && formik.errors.updatedBy}
                errMessage={formik.errors.updatedBy}
              /> */}
            </div>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleModalClose}>
              Đóng
            </Button>
            <Button variant="primary" type="submit">
              Lưu
            </Button>
          </Modal.Footer>
        </form>
      </Modal>
    </Fragment>
  );
};

export default Intake;
