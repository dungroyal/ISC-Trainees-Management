import { Fragment } from "react";
import React, { useState, useEffect } from "react";
import universityService from "../../Services/universityService";
import Input from "../../Controls/input";
import { Button, Modal } from "react-bootstrap";
import { useFormik } from "formik";
import * as Yup from "yup";
import { confirmAlert } from "react-confirm-alert";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import moment from "moment";
import Pagination from "../../Controls/pagination";
//import ReactPaginate from 'react-paginate';
toast.configure({
  autoClose: 2000,
  position: "top-right",
  newestOnTop: true,
  pauseOnHover: true,
  closeOnClick: true,
});
const University = (props) => {
  const [university, setUniversity] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const handleModalClose = () => setModalShow(false);
  const [uniId, setUniId] = useState(0);
  //const [pagi, setPagi] = React.useState({ activePage: 1 });
  const [modalUpdate, setModalUpdate] = useState(false);

  //pagination

  //load data
  const loadData = () => {
    // universityService.getAll().then((res) => {
    //   if (res.status === 0) {
    //     setUniversity(res.data);
    //   } else {
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
    universityService
      .pagination(filters.page, filters.size, filters.key)
      .then((res) => {
        if (res.status === 0) {
          let sizeP = res.data[0].size;
          let totalElementsP = res.data[0].totalElements;
          let pageP = res.data[0].pageable.pageNumber;
          let keyP = res.data[0].key;
          let dataP = res.data[0].content;
          setPagination({ pageP, sizeP, keyP, totalElementsP });
          setUniversity(dataP);
        }
      });
  };
  const handlePageChange = (newPage) => {
    setFilters({
      ...filters,
      page: newPage,
    });
  };

  const handleFormSubmit = (data) => {
    if (uniId === 0) {
      universityService.add(data).then((res) => {
        if (res.status === 0) {
          loadData();
          handleModalClose();
          toast.success("THÊM THÀNH CÔNG");
        } else {
          toast.error("DỮ LIỆU KHÔNG HỢP LỆ");
        }
      });
    } else {
      universityService.update(uniId, data).then((res) => {
        if (res.status === 0) {
          loadData();
          handleModalClose();
          toast.success("CẬP NHẬT THÀNH CÔNG");
        } else {
          toast.success("CẬP NHẬT THẤT BẠI");
        }
      });
    }
  };

  const formik = useFormik({
    initialValues: {
      nameUni: "",
      addressUni: "",
      contactPerson: "",
      websiteUni: "",
      noteUni: "",
      createdBy: "",
      updatedBy: "",
      createdDate: "",
      updatedDate: "",
    },
    validationSchema: Yup.object({
      nameUni: Yup.string()
        .required("Required")
        .max(100, "Less than 100 characters")
        .nullable(false),
      addressUni: Yup.string().required("Required"),
      contactPerson: Yup.string()
        .required("Required")
        .max(150, "Less than 150 characters"),
    }),
    onSubmit: (values) => {
      handleFormSubmit(values);
    },
  });

  const handleModalShow = (e, dataId) => {
    if (e) e.preventDefault();
    setUniId(dataId);
    if (dataId > 0) {
      setModalUpdate(true);
      universityService.get(dataId).then((res) => {
        formik.setValues(res.data[0]);
        setModalShow(true);
      });
    } else {
      setModalUpdate(false);
      formik.resetForm();
      setModalShow(true);
    }
  };
  const submitDelete = (e, dataId) => {
    confirmAlert({
      title: "WARNING!",
      message: "Are you sure to delete this?  ",
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
  const deleteRow = (e, dataId) => {
    e.preventDefault();
    universityService.remove(dataId).then((res) => {
      if (res.status === 0) {
        loadData();
      } else {
      }
    });
  };

  const [key, setKey] = useState("");
  const searchRoom = (e, key) => {
    e.preventDefault();
    universityService
      .pagination(filters.page, filters.size, key)
      .then((res) => {
        if (res.status === 0) {
          let sizeP = res.data[0].size;
          let totalElementsP = res.data[0].totalElements;
          let pageP = res.data[0].pageable.pageNumber;
          let keyP = res.data[0].key;
          let dataP = res.data[0].content;
          setPagination({ pageP, sizeP, keyP, totalElementsP });
          setUniversity(dataP);
          console.log(dataP);
        } else {
          setUniversity([]);
        }
      });
  };

  useEffect(() => {
    loadData();
  }, [filters]);
  return (
    <Fragment>
      <div className="row">
        <div className="col-12">
          <div className="page-title-box d-flex align-items-center justify-content-between">
            <h4 className="mb-0 font-size-18">Danh sách Trường Học</h4>
            <div className="page-title-right">
              <ol className="breadcrumb m-0">
                <li className="breadcrumb-item">
                  <a href="#">ISC Quang Trung</a>
                </li>
                <li className="breadcrumb-item active">Quản lý Trường Học</li>
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
                      <i className="bx bxs-add-to-queue" /> Thêm mới trường học
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
                      <th>#</th>
                      <th>University Name</th>
                      <th>Address</th>
                      <th>Contact Person</th>
                      <th>Website</th>
                      <th>Note</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    {university.map((university, idx) => {
                      return (
                        <tr key={university.id}>
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
                              {university.nameUni}
                            </a>{" "}
                          </td>
                          <td>{university.addressUni}</td>
                          <td>{university.contactPerson}</td>
                          <td>{university.websiteUni}</td>
                          <td>{university.noteUni}</td>
                          <td>
                            <a
                              href="#"
                              className="mr-3 text-primary"
                              onClick={(e) => handleModalShow(e, university.id)}
                            >
                              <i className="fas fa-user-edit font-size-18" />
                            </a>
                            <a
                              href="#"
                              className="text-danger"
                              onClick={(e) => submitDelete(e, university.id)}
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
                  <Modal.Title>Cập nhật trường học</Modal.Title>
                ) : (
                  <Modal.Title>Thêm mới trường học</Modal.Title>
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
                        size="50px"
                        frmField={formik.getFieldProps("nameUni")}
                        err={formik.touched.nameUni && formik.errors.nameUni}
                        errMessage={formik.errors.nameUni}
                      />
                    </div>
                    <div className="col-sm-6">
                      <Input
                        id="txtAdd"
                        type="text"
                        label="Địa chỉ"
                        frmField={formik.getFieldProps("addressUni")}
                        err={
                          formik.touched.addressUni && formik.errors.addressUni
                        }
                        errMessage={formik.errors.addressUni}
                      />
                    </div>
                  </div>
                  <br />
                  <div className="row">
                    <div className="col-sm-6">
                      <Input
                        id="txtWeb"
                        type="text"
                        label="Website"
                        frmField={formik.getFieldProps("websiteUni")}
                        err={
                          formik.touched.websiteUni && formik.errors.websiteUni
                        }
                        errMessage={formik.errors.websiteUni}
                      />
                    </div>
                    <div className="col-sm-6">
                      <Input
                        id="txtNote"
                        type="text"
                        label="Ghi chú"
                        frmField={formik.getFieldProps("noteUni")}
                        err={formik.touched.noteUni && formik.errors.noteUni}
                        errMessage={formik.errors.noteUni}
                      />
                    </div>
                  </div>
                  <br />
                  <div className="row">
                    <div className="col-sm-6">
                      <Input
                        id="txtConP"
                        type="text"
                        label="Người liên hệ"
                        frmField={formik.getFieldProps("contactPerson")}
                        err={
                          formik.touched.contactPerson &&
                          formik.errors.contactPerson
                        }
                        errMessage={formik.errors.contactPerson}
                      />
                    </div>
                    <div className="col-sm-6">
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
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-sm-6"></div>
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
                        ""
                      )}
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-sm-6">
                      {modalUpdate ? (
                        <Input
                          rows="1"
                          disabled
                          label="Ngày tạo"
                          value={moment(formik.values.createdDate).format(
                            "DD-MM-YYYY"
                          )}
                        />
                      ) : (
                        ""
                      )}
                    </div>
                    {modalUpdate ? (
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
                    ) : (
                      ""
                    )}
                  </div>
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

export default University;
