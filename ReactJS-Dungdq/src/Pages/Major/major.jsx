import React, { useState, Fragment, useEffect } from "react";
import $, { map } from "jquery";
import { useFormik } from "formik";
import * as Yup from "yup";
import { Link } from "react-router-dom";
import { Button, Modal } from "react-bootstrap";
import MultiSelect from "react-multi-select-component";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";
import queryString from "query-string";
import Select from "react-select";
import Input from "../../Controls/input";
import majorService from "../../Services/majorService";
import intakeService from "../../Services/intakeService";
// Importing toastify module
import { toast } from "react-toastify";
// Import toastify css file
import "react-toastify/dist/ReactToastify.css";
import Pagination from "./paginationMajor";
import SearchMajor from "./searchMajor";
toast.configure();

const Major = (props) => {
  const [modalUpdate, setModalUpdate] = useState(false);

  const formik = useFormik({
    initialValues: {
      codeMajor: "",
      nameMajor: "",
      descriptionMajor: "",
      createdBy: "",
      updatedBy: "",
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
      console.log("formik: ", values);
      handleFormSubmit(values);
    },
  });

  //   Major
  const [major, setMajor] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const handleModalClose = () => setModalShow(false);

  const [pagination, setPagination] = useState({
    page: 0,
    size: 4,
    totalRows: 1,
  });

  const [filters, setFilters] = useState({
    page: 0,
    size: 4,
    sort: "ASC",
    typeMajor: "n",
    search: "",
  });

  const handlePageChange = (newPage) => {
    setFilters({
      ...filters,
      page: newPage,
    });
  };

  const handedSearchChange = (newSearch) => {
    setFilters({
      ...filters,
      page: 0,
      search: newSearch.searchTerm,
    });
  };

  //   Load Data
  const loadData = () => {
    //Get Major with Filters
    const paramsFilters = queryString.stringify(filters);
    majorService.paginationMajor(paramsFilters).then((res) => {
      const totalRows = res.data[0].totalElements;
      const size = res.data[0].size;
      const pageCurrent = res.data[0].pageable.pageNumber;
      if (res.status === 0) {
        setMajor(res.data[0].content);
        setPagination({
          page: pageCurrent,
          size: size,
          totalRows: totalRows,
        });
      }
    });
  };

  //Didmount load data
  useEffect(() => {
    loadData();
  }, [filters]);

  //   Update Major
  const [majorId, setMajorId] = useState(0);
  //Check Form Update And Add Major
  const handleModalShow = (e, dataId) => {
    if (e) e.preventDefault();
    setMajorId(dataId);

    if (dataId > 0) {
      setModalUpdate(true);
      setModalShow(true);
      majorService.get(dataId).then((res) => {
        const majorById = res.data[0];
        console.log(majorById);
        formik.setValues(majorById);
      });
    } else {
      setModalUpdate(false);
      formik.resetForm();
      setModalShow(true);
    }
  };

  const confirmDeleteMajor = (e, majorId) => {
    if (e) e.preventDefault();
    confirmAlert({
      title: "Confirm to submit",
      message: "Bạn có muốn xóa chuyên ngành này?",
      buttons: [
        {
          label: "Đồng ý",
          onClick: () => confirmDeleteMajor(majorId),
        },
        {
          label: "Hủy bỏ",
        },
      ],
    });
  };

  //Delete new Major
  const handleDeleteMajor = (majorId) => {
    intakeService.get(majorId).then((res) => {
      const listIntake = res.data;
      for (let d = 0; d <= listIntake.length; d++) {
        if (d < listIntake.length) {
          intakeService
            .remove(majorId, listIntake[d].id.intakeId)
            .then((res) => {
              if (res.data === 1) {
                console.error("Delete intake error: " + res.status);
              }
            });
        } else {
          majorService.remove(majorId).then((res) => {
            if (res.status === 0) {
              toast.success("Delete Major Success");
              loadData();
            } else {
              toast.error(res.message);
            }
          });
        }
      }
    });
  };

  //   Add new major
  const handleFormSubmit = () => {
    if (majorId === 0) {
      majorService
        .add1(
          formik.values.codeMajor,
          formik.values.nameMajor,
          formik.values.descriptionMajor,
          "Admin"
        )
        .then((res) => {
          if (res.status === 0) {
            toast.success("Add new major success");
            loadData();
            handleModalClose();
          } else {
            toast.error("Add major error, " + res.message);
          }
        });
    } else {
      majorService
        .updateMajor(
          majorId,
          formik.values.codeMajor,
          formik.values.nameMajor,
          formik.values.descriptionMajor,
          "Admin"
        )
        .then((res) => {
          if (res.status === 0) {
            toast.success("Update Success");
            loadData();
            handleModalClose();
          } else {
            toast.error("Update major error");
          }
        })
        .catch((error) => {
          console.error(error);
        });
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
              <h4 className="mb-0 font-size-18">Major List</h4>

              <div className="page-title-right">
                <ol className="breadcrumb m-0">
                  <li className="breadcrumb-item">
                    <a href="javascript: void(0);">ISC Quang Trung</a>
                  </li>
                  <li className="breadcrumb-item active">Management Majors</li>
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
                        <SearchMajor onSubmit={handedSearchChange} />
                        <i className="bx bx-search-alt search-icon" />
                      </div>
                    </div>
                  </div>
                  <div className="col-sm-8">
                    <div className="text-sm-right">
                      <button
                        type="button"
                        onClick={() => handleModalShow(null, 0)}
                        className="btn btn-success btn-rounded mb-2 mr-2"
                      >
                        <i className="bx bxs-add-to-queue" /> Add New Major
                      </button>
                    </div>
                  </div>
                </div>
                <div className="table-responsive">
                  <table className="table table-centered table-nowrap">
                    <thead className="thead-light">
                      <tr>
                        <th style={{ width: 10 }}>#</th>
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
                      {major.length == 0 ? (
                        <tr className="text-center">
                          <td colspan="9">
                            Not found major with keyword: "
                            <strong>{filters.search}</strong>"!
                          </td>
                        </tr>
                      ) : (
                        major.map((major, idx) => {
                          return (
                            <tr>
                              <td>{idx + 1}</td>
                              <td>
                                <a
                                  href="javascript: void(0);"
                                  className="text-body font-weight-bold"
                                >
                                  {major.codeMajor}
                                </a>{" "}
                              </td>
                              {/* <td style={{width: 100}}>
                                <img className="img-fluid img-thumbnai rounded mx-auto" 
                                    src={api.url.image + student.image}
                                    alt={"Hinh " + student.lastName} />
                              </td> */}
                              <td>{major.nameMajor}</td>
                              <td>{major.descriptionMajor}</td>
                              <td>{major.createdBy}</td>
                              <td>{major.createdDate}</td>
                              <td>{major.updatedBy}</td>
                              <td>{major.updatedDate}</td>

                              <td>
                                <a
                                  href="javascript:void(0);"
                                  onClick={(e) => handleModalShow(e, major.id)}
                                  className="mr-3 text-primary"
                                >
                                  <i className="fas fa-user-edit font-size-15" />
                                </a>
                                <a
                                  href="javascript:void(0);"
                                  onClick={(e) =>
                                    confirmDeleteMajor(e, major.id)
                                  }
                                  className="text-danger"
                                >
                                  <i className="far fa-trash-alt font-size-15" />
                                </a>
                              </td>
                            </tr>
                          );
                        })
                      )}
                    </tbody>
                  </table>
                </div>
                <Pagination
                  pagination={pagination}
                  onPageChange={handlePageChange}
                />
              </div>
            </div>
          </div>
        </div>
        {/* Table */}
      </div>

      <Modal
        show={modalShow}
        onHide={handleModalClose}
        backdrop="static"
        keyboard={false}
        size="xl"
      >
        <Modal.Header closeButton>
          {modalUpdate ? (
            <Modal.Title>Update Major</Modal.Title>
          ) : (
            <Modal.Title>New Major</Modal.Title>
          )}
        </Modal.Header>
        <form onSubmit={formik.handleSubmit} enctype="application/json">
          <Modal.Body>
            <div className="row px-4">
              <Input
                typeInput="1"
                column="12"
                rows="1"
                id="codeMajor"
                name="codeMajor"
                type="text"
                label="Major code *"
                frmField={formik.getFieldProps("codeMajor")}
                err={formik.touched.codeMajor && formik.errors.codeMajor}
                errMessage={formik.errors.codeMajor}
                value={formik.values.codeMajor}
                onChange={formik.handleChange}
              />

              <Input
                typeInput="1"
                column="12"
                rows="1"
                id="nameMajor"
                name="nameMajor"
                type="text"
                label="Major name *"
                frmField={formik.getFieldProps("nameMajor")}
                err={formik.touched.nameMajor && formik.errors.nameMajor}
                errMessage={formik.errors.nameMajor}
                value={formik.values.nameMajor}
                onChange={formik.handleChange}
              />

              <Input
                typeInput="1"
                column="12"
                rows="1"
                id="descriptionMajor"
                name="descriptionMajor"
                type="text"
                label="Major description *"
                frmField={formik.getFieldProps("descriptionMajor")}
                err={
                  formik.touched.descriptionMajor &&
                  formik.errors.descriptionMajor
                }
                errMessage={formik.errors.descriptionMajor}
                value={formik.values.descriptionMajor}
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
                    label="Người tạo"
                    value={`${formik.values.createdBy} - ${new Date(
                      formik.values.createdDate
                    ).toLocaleDateString("vi-VI", {
                      year: "numeric",
                      month: "numeric",
                      day: "numeric",
                      hour: "2-digit",
                      minute: "2-digit",
                    })}`}
                  />
                  {formik.values.updatedBy != null ? (
                    <Fragment>
                      <Input
                        typeInput="1"
                        column="6"
                        rows="1"
                        readOnly
                        type="text"
                        label="Người cập nhật"
                        value={`${formik.values.updatedBy} - ${new Date(
                          formik.values.createdDate
                        ).toLocaleDateString("vi-VI", {
                          year: "numeric",
                          month: "numeric",
                          day: "numeric",
                          hour: "2-digit",
                          minute: "2-digit",
                        })}`}
                      />
                    </Fragment>
                  ) : (
                    ""
                  )}
                </Fragment>
              ) : (
                ""
              )}
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
