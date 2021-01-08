import { Fragment } from "react";
import React, { useState, useEffect } from "react";
import clazzService from "../../Services/clazzService";
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
//import ModalClazz from "./modalClazz";
const Clazz = (props) => {
  const [clazz, setClazz] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const handleModalClose = () => setModalShow(false);
  const [clzId, setClzId] = useState(0);

  const [modalUpdate, setModalUpdate] = useState(false);

  //load data
  const loadData = () => {
    // clazzService.getAll().then((res) => {
    //   if (res.status === 0) {
    //     setClazz(res.data);
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
    clazzService
      .pagination(filters.page, filters.size, filters.key)
      .then((res) => {
        if (res.status === 0) {
          let sizeP = res.data[0].size;
          let totalElementsP = res.data[0].totalElements;
          let pageP = res.data[0].pageable.pageNumber;
          let dataP = res.data[0].content;
          let keyP = res.data[0].key;
          setPagination({ pageP, sizeP, keyP, totalElementsP });
          setClazz(dataP);
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
    setClzId(dataId);
    if (dataId > 0) {
      setModalUpdate(true);
      clazzService.get(dataId).then((res) => {
        formik.setValues(res.data[0]);
        setModalShow(true);
      });
    } else {
      setModalUpdate(false);
      formik.resetForm();
      setModalShow(true);
    }
  };

  const formik = useFormik({
    initialValues: {
      nameClazz: "",
      numOfStu: "",
      pointGra: "",
      clazzStatus: "0",
      createdBy: "",
      updatedBy: "",
      createdDate: "",
      updatedDate: "",
    },
    validationSchema: Yup.object({
      nameClazz: Yup.string()
        .required("Required")
        .max(100, "The field should have lower than 100 character"),
      numOfStu: Yup.number()
        .required("Required")
        .label("Number of student")
        .integer(),
      pointGra: Yup.number()
        .required("Required")
        .positive()
        .label("Point Graduate"),
      clazzStatus: Yup.string().required("Required"),
    }),
    onSubmit: (values) => {
      handleFormSubmit(values);
    },
  });

  const handleFormSubmit = (data) => {
    if (clzId === 0) {
      clazzService.add(data).then((res) => {
        if (res.status === 0) {
          //formik.setValues(res.data);
          loadData();
          handleModalClose();
          toast.success("THÊM THÀNH CÔNG");
        } else {
          toast.error("DỮ LIỆU KHÔNG HỢP LỆ");
        }
      });
    } else {
      clazzService.update(clzId, data).then((res) => {
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
    clazzService.remove(dataId).then((res) => {
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
    clazzService.pagination(filters.page, filters.size, key).then((res) => {
      if (res.status === 0) {
        let sizeP = res.data[0].size;
        let totalElementsP = res.data[0].totalElements;
        let pageP = res.data[0].pageable.pageNumber;
        let keyP = res.data[0].key;
        let dataP = res.data[0].content;
        setPagination({ pageP, sizeP, keyP, totalElementsP });
        setClazz(dataP);
        console.log(dataP);
      } else {
        setClazz([]);
        console.log("zzzz");
      }
    });
  };

  useEffect(() => {
    loadData();
    document.title = "Class - ISC Quang Trung Management";
  }, [filters]);
  return (
    <Fragment>
      <div className="row">
        <div className="col-12">
          <div className="page-title-box d-flex align-items-center justify-content-between">
            <h4 className="mb-0 font-size-18">Danh sách Lớp Học</h4>
            <div className="page-title-right">
              <ol className="breadcrumb m-0">
                <li className="breadcrumb-item">
                  <a href="#">ISC Quang Trung</a>
                </li>
                <li className="breadcrumb-item active">Quản lý Lớp Học</li>
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
                      <i className="bx bxs-add-to-queue" /> Thêm mới lớp học
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
                      <th>Class Name</th>
                      <th>Number of student</th>
                      <th>Point Graduate</th>
                      <th>Status</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    {clazz.map((clazz, idx) => {
                      return (
                        <tr key={clazz.id}>
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
                              {clazz.nameClazz}
                            </a>{" "}
                          </td>
                          <td>{clazz.numOfStu}</td>
                          <td>{clazz.pointGra}</td>
                          <td>
                            <span className="badge badge-pill badge-soft-success font-size-12">
                              {clazz.clazzStatus}
                            </span>
                          </td>
                          <td>
                            <a
                              href="#"
                              className="mr-3 text-primary"
                              onClick={(e) => handleModalShow(e, clazz.id)}
                            >
                              <i className="fas fa-user-edit font-size-18" />
                            </a>
                            <a
                              href="#"
                              className="text-danger"
                              onClick={(e) => submitDelete(e, clazz.id)}
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
                  <Modal.Title>Cập nhật lớp học</Modal.Title>
                ) : (
                  <Modal.Title>Thêm mới lớp học</Modal.Title>
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
                        rows="1"
                        frmField={formik.getFieldProps("nameClazz")}
                        err={
                          formik.touched.nameClazz && formik.errors.nameClazz
                        }
                        errMessage={formik.errors.nameClazz}
                      />
                    </div>
                    <div className="col-sm-6">
                      <Input
                        id="txtnumOfStu"
                        type="text"
                        label="Số lượng học viên"
                        rows="1"
                        frmField={formik.getFieldProps("numOfStu")}
                        err={formik.touched.numOfStu && formik.errors.numOfStu}
                        errMessage={formik.errors.numOfStu}
                      />
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-sm-6">
                      <Input
                        id="txtpointGra"
                        type="number"
                        label="Điểm đậu"
                        rows="1"
                        frmField={formik.getFieldProps("pointGra")}
                        err={formik.touched.pointGra && formik.errors.pointGra}
                        errMessage={formik.errors.pointGra}
                      />
                    </div>
                    <div className="col-sm-6">
                      {modalUpdate ? (
                        <Select
                          label="Trạng thái"
                          name="status"
                          id="clazzStatus"
                          value={formik.values.clazzStatus}
                          frmField={formik.getFieldProps("clazzStatus")}
                        >
                          <option value={formik.values.clazzStatus} hiden>
                            {formik.values.clazzStatus}
                          </option>
                          {formik.values.clazzStatus == "Active" ? (
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
                          id="clazzStatus"
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

export default Clazz;
