import { Fragment } from "react";
import React, { useState, useEffect } from "react";
import intakeService from "../../Services/intakeService";
import { Button, Form, Modal, Col, Row } from "react-bootstrap";

import Input from "../../Containers/Input";
import { useFormik } from "formik";
import * as Yup from "yup";

const Intke = () => {
  const [intake, setIntake] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const handleModalClose = () => setModalShow(false);
  const [intakeId, setIntakeId] = useState(0);

  const loadData = () => {
    intakeService.getAll().then((res) => {
      if (res.status === 0) {
        setIntake(res.data);
      }
    });
  };

  const handleFormSubmit = (data) => {
    if (intakeId === 0) {
      intakeService.add(data).then((res) => {
        if (res.status === 0) {
          loadData();
          handleModalClose();
        } else {
        }
      });
    } else {
      intakeService.update(intakeId, data).then((res) => {
        if (res.status === 0) {
          loadData();
          handleModalClose();
          console.log(data);
        } else {
        }
      });
    }
  };

  const formik = useFormik({
    initialValues: {
      codeIntake: "",
      nameIntake: "",
      startDay: "",
      endDay: "",
      statusIntake: "",
      createdBy: "",
      createdDate: "",
      updatedBy: "",
      updatedDate: "",
      majorId: "",
    },

    validationSchema: Yup.object({
      majorId: Yup.string().required("Major is required"),
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
    },
  });

  const handleModalShow = (e, dataId) => {
    if (e) e.preventDefault();
    setIntakeId(dataId);
    if (dataId > 0) {
      intakeService.get(dataId).then((res) => {
        formik.setValues(res.data[0]);
        setModalShow(true);
      });
    } else {
      formik.resetForm();
      setModalShow(true);
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
                    {intake.map((item, idx) => {
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
                            {/* <a
                                      href="javascript:void(0);"
                                      className="mr-3 text-success"
                                      data-toggle="modal"
                                      data-target=".viewStudentModal"
                                    >
                                      <i className="far fa-eye font-size-18" />
                                    </a> */}
                            <a
                              href="#"
                              className="mr-3 text-primary"
                              data-toggle="modal"
                              //   data-target=".editStudentModal"
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
          <Modal.Title>Add New Intake</Modal.Title>
        </Modal.Header>
        <form onSubmit={formik.handleSubmit}>
          <Modal.Body>
            <div className="row">
              <div className="col-6">
                <Input
                  id="codeIntake"
                  type="text"
                  label="Mã khóa học"
                  frmField={formik.getFieldProps("codeIntake")}
                  err={formik.touched.codeIntake && formik.errors.codeIntake}
                  errMessage={formik.errors.codeIntake}
                />
              </div>

              <div className="col-6">
                <Input
                  id="nameIntake"
                  type="text"
                  label="Tên khóa học"
                  frmField={formik.getFieldProps("nameIntake")}
                  err={formik.touched.nameIntake && formik.errors.nameIntake}
                  errMessage={formik.errors.nameIntake}
                />
              </div>
              <div className="col-6">
                <Input
                  id="startDay"
                  type="date"
                  label="Ngày bắt đầu"
                  frmField={formik.getFieldProps("startDay")}
                  err={formik.touched.startDay && formik.errors.startDay}
                  errMessage={formik.errors.startDay}
                />
              </div>
              <div className="col-6">
                <Input
                  id="endDay"
                  type="date"
                  label="Ngày kết thúc"
                  frmField={formik.getFieldProps("endDay")}
                  err={formik.touched.endDay && formik.errors.endDay}
                  errMessage={formik.errors.endDay}
                />
              </div>

              <div class="col-md-6">
                <div class="form-group">
                  <label>Trạng thái</label>
                  <select class="custom-select">
                    <option selected value="0">
                      Chưa kích hoạt
                    </option>
                    <option value="1">Đang hoạt động</option>
                    <option value="2">Đã hoàn thành</option>
                  </select>
                </div>
              </div>
            </div>

            {/* <Input
              id="codeIntake"
              type="text"
              label="Mã khóa học"
              frmField={formik.getFieldProps("codeIntake")}
              err={formik.touched.codeIntake && formik.errors.codeIntake}
              errMessage={formik.errors.codeIntake}
            />
            <Input
              id="nameIntake"
              type="text"
              label="Tên khóa học"
              frmField={formik.getFieldProps("nameIntake")}
              err={formik.touched.nameIntake && formik.errors.nameIntake}
              errMessage={formik.errors.nameIntake}
            />
            <Input
              id="startDay"
              type="date"
              label="Ngày bắt đầu"
              frmField={formik.getFieldProps("startDay")}
              err={formik.touched.startDay && formik.errors.startDay}
              errMessage={formik.errors.startDay}
            />
            <Input
              id="endDay"
              type="date"
              label="Ngày kết thúc"
              frmField={formik.getFieldProps("endDay")}
              err={formik.touched.endDay && formik.errors.endDay}
              errMessage={formik.errors.endDay}
            />
            <Input
              id="statusIntake"
              type="text"
              label="Trạng thái"
              frmField={formik.getFieldProps("statusIntake")}
              err={formik.touched.statusIntake && formik.errors.statusIntake}
              errMessage={formik.errors.statusIntake}
            />

            <Form.Group
              controlId="statusIntake"
              frmField={formik.getFieldProps("statusIntake")}
              err={formik.touched.statusIntake && formik.errors.statusIntake}
              errMessage={formik.errors.statusIntake}
            >
              <Form.Row>
                <Form.Label>Trạng thái</Form.Label>
                <Form.Control as="select">
                  <option selected value={0}>
                    Chưa kích hoạt
                  </option>
                  <option value={1}>Đang hoạt động</option>
                  <option value={2}>Đã hoàn thành</option>
                </Form.Control>
              </Form.Row>
            </Form.Group>

            <select
              controlId="statusIntake"
              frmField={formik.getFieldProps("statusIntake")}
              err={formik.touched.statusIntake && formik.errors.statusIntake}
              errMessage={formik.errors.statusIntake}
            >
              <option selected value={0}>
                Chưa kích hoạt
              </option>
              <option value={1}>Đang hoạt động</option>
              <option value={2}>Đã hoàn thành</option>
            </select>

            <Input
              id="createdBy"
              type="text"
              label="Người tạo"
              frmField={formik.getFieldProps("createdBy")}
              err={formik.touched.createdBy && formik.errors.createdBy}
              errMessage={formik.errors.createdBy}
            />

            <Input
              id="updatedBy"
              type="text"
              label="Người cập nhật"
              frmField={formik.getFieldProps("updatedBy")}
              err={formik.touched.updatedBy && formik.errors.updatedBy}
              errMessage={formik.errors.updatedBy}
            /> */}
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

      {/* Modal add */}
      {/* <div
        className="modal fade addStudentModal"
        tabIndex={-1}
        role="dialog"
        aria-hidden="true"
        onHide={handleModalClose}
        show={modalShow}
        onHide={handleModalClose}
        backdropClassName="static"
        keyboard={false}
      >
        <div
          className="modal-dialog modal-xl modal-dialog-centered"
          role="document"
        >
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="exampleModalLabel">
                Thêm mới khóa học
              </h5>
              <button
                type="button"
                className="close"
                data-dismiss="modal"
                aria-label="Close"
              >
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div className="modal-body px-4">
              <div className="row">
                <div className="col-sm-12 col-lg-2">
                  <div className="avatar-upload-jsk py-2">
                    <div className="avatar-edit">
                      <input
                        type="file"
                        name="avatar"
                        id="imageUpload"
                        accept=".png, .jpg, .jpeg"
                      />
                    </div>
                    <div className="avatar-preview-jsk">
                      <label htmlFor="imageUpload" className="imageUpload">
                        {" "}
                        <i className="bx bxs-cloud-upload" />
                      </label>
                      <div
                        id="imagePreview"
                        style={{
                          backgroundImage:
                            'url("assets/images/avata-playhoder.jpg")',
                        }}
                      />
                    </div>
                  </div>
                </div>
                <div
                  className="col-sm-12 col-lg-10"
                  onSubmit={formik.handleSubmit}
                >
                  <div className="row">
                    <div className="col-md-6">
                      <div className="form-group">
                        <label>Mã khóa học</label>
                        <input
                          id="codeIntake"
                          type="text"
                          className="form-control"
                          frmField={formik.getFieldProps("codeIntake")}
                          err={
                            formik.touched.codeIntake &&
                            formik.errors.codeIntake
                          }
                          errMessage={formik.errors.codeIntake}
                        />
                      </div>
                    </div>

                    <div className="col-md-6">
                      <div className="form-group">
                        <label>Tên khóa học</label>
                        <input
                          id="nameIntake"
                          type="text"
                          className="form-control"
                          frmField={formik.getFieldProps("nameIntake")}
                          err={
                            formik.touched.nameIntake &&
                            formik.errors.nameIntake
                          }
                          errMessage={formik.errors.nameIntake}
                        />
                      </div>
                    </div>

                    <div className="col-md-6">
                      <div className="form-group">
                        <label>Ngày bắt đầu</label>
                        <input
                          id="startDay"
                          type="date"
                          className="form-control"
                          frmField={formik.getFieldProps("startDay")}
                          err={
                            formik.touched.startDay && formik.errors.startDay
                          }
                          errMessage={formik.errors.startDay}
                        />
                      </div>
                    </div>

                    <div className="col-md-6">
                      <div className="form-group">
                        <label>Ngày kết thúc</label>
                        <input
                          id="endDay"
                          type="date"
                          className="form-control"
                          frmField={formik.getFieldProps("endDay")}
                          err={formik.touched.endDay && formik.errors.endDay}
                          errMessage={formik.errors.endDay}
                        />
                      </div>
                    </div>

                    <div className="col-md-6">
                      <div className="form-group">
                        <label>Trường</label>
                        <select
                          className="custom-select"
                          id="statusIntake"
                          frmField={formik.getFieldProps("statusIntake")}
                          err={
                            formik.touched.statusIntake &&
                            formik.errors.statusIntake
                          }
                          errMessage={formik.errors.statusIntake}
                        >
                          <option selected>Trạng thái</option>
                          <option value={0}>Chưa kích hoạt</option>
                          <option value={1}>Đang hoạt động</option>
                          <option value={2}>Đã hoàn thành</option>
                        </select>
                      </div>
                    </div>

                    <div className="col-md-6">
                      <div className="form-group">
                        <label>Người tạo</label>
                        <input
                          id="createdBy"
                          type="text"
                          className="form-control"
                          frmField={formik.getFieldProps("createdBy")}
                          err={
                            formik.touched.createdBy && formik.errors.createdBy
                          }
                          errMessage={formik.errors.createdBy}
                        />
                      </div>
                    </div>

                    <div className="col-md-6">
                      <div className="form-group">
                        <label>Người cập nhật</label>
                        <input
                          id="updatedBy"
                          type="text"
                          className="form-control"
                          frmField={formik.getFieldProps("updatedBy")}
                          err={
                            formik.touched.updatedBy && formik.errors.updatedBy
                          }
                          errMessage={formik.errors.updatedBy}
                        />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                //   data-dismiss="modal"
                onClick={handleModalClose}
              >
                Đóng
              </button>
              <button type="button submit" className="btn btn-success">
                Thêm mới
              </button>
            </div>
          </div>
        </div>
      </div> */}
      {/* End modal add */}
    </Fragment>
  );
};

export default Intke;
