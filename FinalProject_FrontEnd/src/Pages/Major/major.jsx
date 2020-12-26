import { Fragment } from "react";
import React, { useState, useEffect } from "react";
import majorService from "../../Services/majorService";
import { Button, Form, Modal, Col, Row } from "react-bootstrap";
import Input from "../../Controls/input";
import { useFormik } from "formik";
import * as Yup from "yup";

const Major = () => {
  const [major, setMajor] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const handleModalClose = () => setModalShow(false);
  const [majorId, setMajorId] = useState(0);

  const loadData = () => {
    majorService.getAll().then((res) => {
      if (res.status === 0) {
        setMajor(res.data);
      }
    });
  };

  const handleFormSubmit = (data) => {
    if (majorId === 0) {
      majorService.add(data).then((res) => {
        if (res.status === 0) {
          loadData();
          handleModalClose();
        } else {
        }
      });
    } else {
      majorService.update(majorId, data).then((res) => {
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
      codeMajor: "",
      nameMajor: "",
      descriptionMajor: "",
      createdBy: "",
      createdDate: "",
      updatedBy: "",
      updatedDate: "",
    },

    validationSchema: Yup.object({
      codeMajor: Yup.string()
        .required("Major code is required")
        .max(50, "Major code must be lester than 50 characters"),
      nameMajor: Yup.string()
        .required("Major name is required")
        .max(50, "Major name must be lester than 50 characters"),
      descriptionMajor: Yup.string()
        .required("Major description is required")
        .max(2000, "Major description must be lester than 2000 characters"),
    }),

    onSubmit: (values) => {
      console.log(values);
      handleFormSubmit(values);
    },
  });

  const handleModalShow = (e, dataId) => {
    if (e) e.preventDefault();
    setMajorId(dataId);
    if (dataId > 0) {
      majorService.get(dataId).then((res) => {
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
    majorService.remove(dataId).then((res) => {
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
            <h4 className="mb-0 font-size-18">Danh sách chuyên ngành</h4>
            <div className="page-title-right">
              <ol className="breadcrumb m-0">
                <li className="breadcrumb-item">
                  <a href="#">ISC Quang Trung</a>
                </li>
                <li className="breadcrumb-item active">Quản lý chuyên ngành</li>
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
                      <i className="bx bxs-add-to-queue" /> Thêm mới chuyên
                      ngành
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
                      <th>Mã chuyên ngành</th>
                      <th>Tên chuyên ngành</th>
                      <th>Mô tả</th>
                      <th>Người tạo</th>
                      <th>Ngày tạo</th>
                      <th>Người cập nhật</th>
                      <th>Ngày cập nhật</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    {major.map((item) => {
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
                              {item.codeMajor}
                            </a>{" "}
                          </td>
                          <td>{item.nameMajor}</td>
                          <td>{item.descriptionMajor}</td>
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
          <Modal.Title>Thêm chuyên ngành</Modal.Title>
        </Modal.Header>
        <form onSubmit={formik.handleSubmit}>
          <Modal.Body>
            <div className="row px-4">
              <Input
                column="6"
                typeInput="1"
                rows="1"
                id="codeMajor"
                type="text"
                label="Mã chuyên ngành"
                frmField={formik.getFieldProps("codeMajor")}
                err={formik.touched.codeMajor && formik.errors.codeMajor}
                errMessage={formik.errors.codeMajor}
              />

              <Input
                column="6"
                typeInput="1"
                rows="1"
                id="nameMajor"
                type="text"
                label="Tên chuyên ngành"
                frmField={formik.getFieldProps("nameMajor")}
                err={formik.touched.nameMajor && formik.errors.nameMajor}
                errMessage={formik.errors.nameMajor}
              />

              <Input
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
              />

              <Input
                column="12"
                typeInput="1"
                rows="8"
                id="descriptionMajor"
                type="date"
                label="Mô tả"
                frmField={formik.getFieldProps("descriptionMajor")}
                err={
                  formik.touched.descriptionMajor &&
                  formik.errors.descriptionMajor
                }
                errMessage={formik.errors.descriptionMajor}
              />
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

export default Major;
