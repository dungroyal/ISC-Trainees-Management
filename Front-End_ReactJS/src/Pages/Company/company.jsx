import { Fragment } from "react";
import React, { useState, useEffect } from "react";
import companyService from "../../Services/companyService";
import { Button, Modal } from "react-bootstrap";
import { useFormik } from "formik";
import Input from "../../Controls/input";
import * as Yup from "yup";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import moment from "moment";
import { confirmAlert } from "react-confirm-alert";
import Pagination from "../../Controls/pagination";
//import ModalCompany from "./modalCompany";
const Company = (props) => {
  const [company, setCompany] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const handleModalClose = () => setModalShow(false);
  const [comId, setComId] = useState(0);

  const [modalUpdate, setModalUpdate] = useState(false);

  //load data
  const loadData = () => {
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
    companyService
      .pagination(filters.page, filters.size, filters.key)
      .then((res) => {
        if (res.status === 0) {
          let sizeP = res.data[0].size;
          let totalElementsP = res.data[0].totalElements;
          let pageP = res.data[0].pageable.pageNumber;
          let dataP = res.data[0].content;
          let keyP = res.data[0].key;
          setPagination({ pageP, sizeP, keyP, totalElementsP });
          setCompany(dataP);
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
    setComId(dataId);
    if (dataId > 0) {
      setModalUpdate(true);
      companyService.get(dataId).then((res) => {
        setModalShow(true);
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
      nameCom: "",
      addresCom: "",
      contactPerson: "",
      websiteCom: "",
      noteCom: "",
      statusCom: "",
      createdBy: "",
      updatedBy: "",
      createdDate: "",
      updatedDate: "",
    },
    validationSchema: Yup.object({
      nameCom: Yup.string().required("Required"),
      addresCom: Yup.string().required("Required"),
      statusCom: Yup.string().required("Required"),
    }),
    onSubmit: (values) => {
      handleFormSubmit(values);
    },
  });

  const handleFormSubmit = (data) => {
    if (comId === 0) {
      companyService.add(data).then((res) => {
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
      companyService.update(comId, data).then((res) => {
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

  const deleteRow = (e, dataId) => {
    e.preventDefault();
    companyService.remove(dataId).then((res) => {
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
    companyService.pagination(filters.page, filters.size, key).then((res) => {
      if (res.status === 0) {
        let sizeP = res.data[0].size;
        let totalElementsP = res.data[0].totalElements;
        let pageP = res.data[0].pageable.pageNumber;
        let keyP = res.data[0].key;
        let dataP = res.data[0].content;
        setPagination({ pageP, sizeP, keyP, totalElementsP });
        setCompany(dataP);
      } else {
        setCompany([]);
      }
    });
  };

  useEffect(() => {
    loadData();
    document.title = "Companies - ISC Quang Trung Management";
  }, [filters]);
  return (
    <Fragment>
      <div className="row">
        <div className="col-12">
          <div className="page-title-box d-flex align-items-center justify-content-between">
            <h4 className="mb-0 font-size-18">List Company</h4>
            <div className="page-title-right">
              <ol className="breadcrumb m-0">
                <li className="breadcrumb-item">
                  <a href="#">ISC Quang Trung</a>
                </li>
                <li className="breadcrumb-item active">List Company</li>
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
                      <i className="bx bxs-add-to-queue" /> Add new Company
                    </button>
                  </div>
                </div>
                {/* end col*/}
              </div>
              <div className="table-responsive">
                <table className="table table-centered table-nowrap">
                  <thead className="thead-light">
                    <tr>
                      <th>#</th>
                      <th>Company Name</th>
                      {/* <th with={'10%'}>Address</th> */}
                      <th>Contact Person</th>
                      <th>Website</th>
                      <th>Status</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    {company.map((company, idx) => {
                      return (
                        <tr key={company.id}>

                          <td>{idx + 1}</td>
                          <td>
                            <a href="#" className="text-body font-weight-bold">
                              {company.nameCom}
                            </a>{" "}
                          </td>
                          {/* <td with={'10%'}>{company.addresCom}</td> */}
                          <td>{company.contactPerson}</td>
                          <td>{company.websiteCom}</td>
                          <td>
                            <span className="badge badge-pill badge-soft-success font-size-12">
                              Active
                              {/* {company.statusCom} */}
                            </span>
                          </td>
                          <td>
                            <a
                              href="#"
                              className="mr-3 text-primary"
                              onClick={(e) => handleModalShow(e, company.id)}
                            >
                              <i className="fas fa-user-edit font-size-18" />
                            </a>
                            <a
                              href="#"
                              className="text-danger"
                              onClick={(e) => submitDelete(e, company.id)}
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
              size="lg"
            >
              <Modal.Header closeButton>
                {modalUpdate ? (
                  <Modal.Title>Update Company</Modal.Title>
                ) : (
                  <Modal.Title>Add new Company</Modal.Title>
                )}
              </Modal.Header>
              <form onSubmit={formik.handleSubmit}>
                <Modal.Body>
                  <div className="row px-3">
                      <Input
                        typeInput="1"
                        column="12"
                        rows="1"
                        id="txtName"
                        type="text"
                        label="Name Company"
                        frmField={formik.getFieldProps("nameCom")}
                        err={formik.touched.nameCom && formik.errors.nameCom}
                        errMessage={formik.errors.nameCom}
                      />
                      <Input
                        typeInput="1"
                        column="6"
                        rows="1"
                        id="txtAdd"
                        type="text"
                        label="Address"
                        frmField={formik.getFieldProps("addresCom")}
                        err={
                          formik.touched.addresCom && formik.errors.addresCom
                        }
                        errMessage={formik.errors.addresCom}
                      />
                      <Input
                        typeInput="1"
                        column="6"
                        rows="1"
                        id="txtStatus"
                        type="text"
                        label="Status"
                        frmField={formik.getFieldProps("statusCom")}
                        err={
                          formik.touched.statusCom && formik.errors.statusCom
                        }
                        errMessage={formik.errors.statusCom}
                      />
                      <Input
                        typeInput="1"
                        column="6"
                        rows="1"
                        id="txtWeb"
                        type="text"
                        label="Website"
                        frmField={formik.getFieldProps("websiteCom")}
                        err={
                          formik.touched.websiteCom && formik.errors.websiteCom
                        }
                        errMessage={formik.errors.websiteCom}
                      />
                      <Input
                        typeInput="1"
                        column="6"
                        rows="1"
                        id="txtConP"
                        type="text"
                        label="Contact Person"
                        frmField={formik.getFieldProps("contactPerson")}
                        err={
                          formik.touched.contactPerson &&
                          formik.errors.contactPerson
                        }
                        errMessage={formik.errors.contactPerson}
                      />
                      {modalUpdate ? (
                        <Input
                          typeInput="1"
                          column="6"
                          rows="1"
                          id="txtNote"
                          type="text"
                          label="Ghi chú"
                          frmField={formik.getFieldProps("noteCom")}
                          err={formik.touched.noteCom && formik.errors.noteCom}
                          errMessage={formik.errors.noteCom}
                        />
                      ) : (
                        ""
                      )}
                      {modalUpdate ? (
                        <Input
                          typeInput="1"
                          column="6"
                          rows="1"
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
                          typeInput="1"
                          column="12"
                          rows="2"
                          id="txtNote"
                          type="text"
                          label="Note"
                          frmField={formik.getFieldProps("noteCom")}
                          err={formik.touched.noteCom && formik.errors.noteCom}
                          errMessage={formik.errors.noteCom}
                        />
                      )}

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
                </Modal.Body>
                <div className="row justify-content-end small">
                  <div className="col-auto pr-5">
                    <p className="p-0">
                    Field required (<span className="text-danger">*</span>)
                    </p>
                  </div>
                </div>
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

export default Company;
