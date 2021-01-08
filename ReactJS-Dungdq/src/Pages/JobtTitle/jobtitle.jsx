import { Fragment } from "react";
import React, { useState, useEffect } from "react";
import jobService from "../../Services/jobTitleService";
import { Button, Modal } from "react-bootstrap";
import { useFormik } from "formik";
import Input from "../../Controls/input";
import Select from "../../Controls/select";
import * as Yup from "yup";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import moment from "moment";
import { confirmAlert } from "react-confirm-alert";
import Pagination from "../../Controls/pagination";
//import ModalJobTitle from "./modalJobTitle";
const JobTitle = (props) => {
  const [jobTitle, setJobTitle] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const handleModalClose = () => setModalShow(false);
  const [jobId, setJobId] = useState(0);

  const [modalUpdate, setModalUpdate] = useState(false);

  //load data
  const loadData = () => {
    // jobService.getAll().then((res) => {
    //   if (res.status === 0) {
    //     setJobTitle(res.data);
    //   }
    // });
    handlePagination();
  };
  //pagination
  const [pagination, setPagination] = useState({
    page: 2,
    size: 5,
    key: "",
    totalElements: 1,
  });
  const [filters, setFilters] = useState({
    size: 5,
    page: 0,
    key: "",
  });

  const handlePagination = () => {
    jobService
      .pagination(filters.page, filters.size, filters.key)
      .then((res) => {
        if (res.status === 0) {
          let sizeP = res.data[0].size;
          let totalElementsP = res.data[0].totalElements;
          let pageP = res.data[0].pageable.pageNumber;
          let dataP = res.data[0].content;
          let keyP = res.data[0].key;
          setPagination({ pageP, sizeP, keyP, totalElementsP });
          setJobTitle(dataP);
        }
      });
  };
  const handlePageChange = (newPage) => {
    setFilters({
      ...filters,
      page: newPage,
    });
  };
  const handleModalShow = (e, dataId) => {
    if (e) e.preventDefault();
    setJobId(dataId);
    if (dataId > 0) {
      setModalUpdate(true);
      jobService.get(dataId).then((res) => {
        setModalShow(true);
        console.log(res.data);
        formik.setValues(res.data[0]);
      });
    } else {
      setModalUpdate(false);
      formik.resetForm();
      setModalShow(true);
    }
  };
  const formik = useFormik({
    initialValues: {
      nameJob: "",
      jobStatus: "0",
      createdBy: "",
      updatedBy: "",
      createdDate: "",
      updatedDate: "",
    },
    validationSchema: Yup.object({
      nameJob: Yup.string().required("Required").max(100, "Max 100 character"),
      jobStatus: Yup.string().required("Required"),
    }),
    onSubmit: (values) => {
      console.log("formik: ", values);
      handleFormSubmit(values);
    },
  });

  const handleFormSubmit = (data) => {
    if (jobId === 0) {
      console.log(data);
      jobService.add(data).then((res) => {
        if (res.status === 0) {
          loadData();
          handleModalClose();
          toast.success("THÊM THÀNH CÔNG");
          console.log("aaa");
        } else {
          toast.error("DỮ LIỆU KHÔNG HỢP LỆ");
        }
      });
    } else {
      jobService.update(jobId, data).then((res) => {
        if (res.status === 0) {
          loadData();
          handleModalClose();
          toast.success("CẬP NHẬT THÀNH CÔNG");
        } else {
          toast.error("CẬP NHẬT THẤT BẠI");
        }
      });
    }
  };

  const deleteRow = (e, dataId) => {
    e.preventDefault();
    jobService.remove(dataId).then((res) => {
      if (res.status === 0) {
        loadData();
      } else {
      }
    });
  };
  const submitDelete = (e, dataId) => {
    confirmAlert({
      title: "WARNING!",
      message: "Are you sure to delete this?",
      buttons: [
        {
          label: "Yes",
          onClick: () => deleteRow(e, dataId),
        },
        {
          label: "No",
        },
      ],
    });
  };
  //load search
  const [key, setKey] = useState("");
  const searchRoom = (e, key) => {
    e.preventDefault();
    jobService.pagination(filters.page, filters.size, key).then((res) => {
      if (res.status === 0) {
        let sizeP = res.data[0].size;
        let totalElementsP = res.data[0].totalElements;
        let pageP = res.data[0].pageable.pageNumber;
        let keyP = res.data[0].key;
        let dataP = res.data[0].content;
        setPagination({ pageP, sizeP, keyP, totalElementsP });
        setJobTitle(dataP);
      } else {
        setJobTitle([]);
      }
    });
  };

  useEffect(() => {
    loadData();
    document.title = "Jobs - ISC Quang Trung Management";
  }, []);

  return (
    <Fragment>
      <div className="row">
        <div className="col-12">
          <div className="page-title-box d-flex align-items-center justify-content-between">
            <h4 className="mb-0 font-size-18">Danh sách Công Việc</h4>
            <div className="page-title-right">
              <ol className="breadcrumb m-0">
                <li className="breadcrumb-item">
                  <a href="#">ISC Quang Trung</a>
                </li>
                <li className="breadcrumb-item active">Quản lý Công Việc</li>
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
                        // onKeyDown={(e) => searchRoom(e, key)}
                        onChange={(e) => setKey(e.target.value)}
                        onKeyUp={(e) => searchRoom(e, key)}
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
                      data-target=".addStudentModal"
                      className="btn btn-success btn-rounded waves-effect waves-light mb-2 mr-2"
                      onClick={() => handleModalShow(null, 0)}
                    >
                      <i className="bx bxs-add-to-queue" /> Thêm mới công việc
                    </button>
                  </div>
                </div>
                {/* end col*/}
              </div>
              <div className="table-responsive">
                <table className="table table-centered table-nowrap">
                  <thead className="thead-light">
                    <tr>
                      <th style={{ width: 20, textAlign: "center" }}>
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

                      <th>#</th>
                      <th>Job Title Name</th>
                      <th>Status</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    {jobTitle.map((jobTitle, idx) => {
                      return (
                        <tr key={jobTitle.id}>
                          <td>
                            <div className="custom-control custom-checkbox">
                              <input
                                type="checkbox"
                                className="custom-control-input"
                                id="customCheck9"
                              />
                              <label
                                className="custom-control-label"
                                htmlFor="customCheck9"
                              >
                                &nbsp;
                              </label>
                            </div>
                          </td>

                          <td>{idx + 1}</td>
                          <td>
                            <a href="#" className="text-body font-weight-bold">
                              {jobTitle.nameJob}
                            </a>{" "}
                          </td>
                          <td>
                            <span className="badge badge-pill badge-soft-success font-size-12">
                              {jobTitle.jobStatus}
                            </span>
                          </td>
                          <td>
                            <a
                              href="#"
                              className="mr-3 text-primary"
                              onClick={(e) => handleModalShow(e, jobTitle.id)}
                            >
                              <i className="fas fa-user-edit font-size-18" />
                            </a>
                            <a
                              href="#"
                              className="text-danger"
                              onClick={(e) => submitDelete(e, jobTitle.id)}
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
              <Pagination
                pagination={pagination}
                onPageChange={handlePageChange}
              />
            </div>
            <Modal
              show={modalShow}
              onHide={handleModalClose}
              backdropClassName="static"
              keyboard={false}
              size="xl"
            >
              <Modal.Header closeButton>
                {modalUpdate ? (
                  <Modal.Title>Cập nhật công việc</Modal.Title>
                ) : (
                  <Modal.Title>Thêm mới công việc</Modal.Title>
                )}
              </Modal.Header>
              <form onSubmit={formik.handleSubmit}>
                <Modal.Body>
                  <div className="row">
                    <div className="col-sm-6">
                      <Input
                        id="txtName"
                        type="text"
                        label="Tên"
                        frmField={formik.getFieldProps("nameJob")}
                        err={formik.touched.nameJob && formik.errors.nameJob}
                        errMessage={formik.errors.nameJob}
                      />
                    </div>
                    <div className="col-sm-6">
                      {modalUpdate ? (
                        <Select
                          label="Trạng thái"
                          name="status"
                          id="jobStatus"
                          value={formik.values.jobStatus}
                          frmField={formik.getFieldProps("jobStatus")}
                        >
                          <option value={formik.values.jobStatus} hiden>
                            {formik.values.jobStatus}
                          </option>
                          {formik.values.jobStatus == "Active" ? (
                            <option label="Inactive" value="Inactive">
                              Inactive
                            </option>
                          ) : (
                            <option label="Active" value="Active">
                              Active
                            </option>
                          )}
                        </Select>
                      ) : (
                        <Select
                          disabled
                          label="Trạng thái"
                          name="status"
                          id="jobStatus"
                        >
                          <option value="Active">Active</option>
                          {/* <option value="Inactive">Không hoạt động</option> */}
                        </Select>
                      )}
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-sm-6">
                      {modalUpdate ? (
                        <Input
                          id="txtCreatedBy"
                          type="text"
                          label="Người tạo"
                          frmField={formik.getFieldProps("createdBy")}
                          err={
                            formik.touched.createdBy && formik.errors.createdBy
                          }
                          errMessage={formik.errors.createdBy}
                        />
                      ) : (
                        ""
                      )}
                    </div>
                    <div className="col-sm-6">
                      {modalUpdate ? (
                        <Input
                          id="txtUpdatedBy"
                          type="text"
                          label="Người cập nhật"
                          frmField={formik.getFieldProps("updatedBy")}
                          err={
                            formik.touched.updatedBy && formik.errors.updatedBy
                          }
                          errMessage={formik.errors.updatedBy}
                        />
                      ) : (
                        <Input
                          id="txtCreatedBy"
                          type="text"
                          label="Người tạo"
                          frmField={formik.getFieldProps("createdBy")}
                          err={
                            formik.touched.createdBy && formik.errors.createdBy
                          }
                          errMessage={formik.errors.createdBy}
                        />
                      )}
                    </div>
                  </div>
                  {modalUpdate ? (
                    <div className="row">
                      <div className="col-sm-6">
                        <Input
                          rows="1"
                          disabled
                          label="Ngày tạo"
                          value={moment(formik.values.createdDate).format(
                            "DD-MM-YYYY"
                          )}
                        />
                      </div>
                      <div className="col-sm-6">
                        {formik.values.updatedDate === null ? (
                          <Input
                            label="Ngày cập nhật"
                            rows="1"
                            disabled
                            value=""
                          />
                        ) : (
                          <Input
                            label="Ngày cập nhật"
                            rows="1"
                            disabled
                            value={moment(formik.values.updatedDate).format(
                              "DD-MM-YYYY"
                            )}
                          />
                        )}
                      </div>
                    </div>
                  ) : (
                    ""
                  )}
                </Modal.Body>
                <Modal.Footer>
                  <Button variant="secondary" onClick={handleModalClose}>
                    Hủy
                  </Button>
                  <Button variant="primary" type="submit">
                    Lưu
                  </Button>
                </Modal.Footer>
              </form>
            </Modal>
          </div>
        </div>
      </div>
    </Fragment>
  );
};

export default JobTitle;
