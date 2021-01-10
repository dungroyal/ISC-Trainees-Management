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
    size: 1,
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
          toast.success("Add new success");
        } else {
          toast.error("Add new error");
        }
      });
    } else {
      universityService.update(uniId, data).then((res) => {
        if (res.status === 0) {
          loadData();
          handleModalClose();
          toast.success("Update success");
        } else {
          toast.success("Update error");
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
    document.title = "University - ISC Quang Trung Management";
  }, [filters]);
  return (
    <Fragment>
      <div className="row">
        <div className="col-12">
          <div className="page-title-box d-flex align-items-center justify-content-between">
            <h4 className="mb-0 font-size-18">University List</h4>
            <div className="page-title-right">
              <ol className="breadcrumb m-0">
                <li className="breadcrumb-item">
                  <a href="#">ISC Quang Trung</a>
                </li>
                <li className="breadcrumb-item active">University List</li>
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
                      <i className="bx bxs-add-to-queue" /> Add new University
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
                      <th>University Name</th>
                      <th>Address</th>
                      <th>Website</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    {university.map((university, idx) => {
                      return (
                        <tr key={university.id}>
                          <td>{idx + 1}</td>
                          <td>
                            <a href="#" className="text-body font-weight-bold">
                              {university.nameUni}
                            </a>{" "}
                          </td>
                          <td>{university.addressUni}</td>
                          <td>{university.websiteUni}</td>
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
              size="lg"
            >
              <Modal.Header closeButton>
                {modalUpdate ? (
                  <Modal.Title>Update university</Modal.Title>
                ) : (
                  <Modal.Title>Add new university</Modal.Title>
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
                        label="Name university *"
                        size="50px"
                        frmField={formik.getFieldProps("nameUni")}
                        err={formik.touched.nameUni && formik.errors.nameUni}
                        errMessage={formik.errors.nameUni}
                      />
                      <Input
                        typeInput="1"
                        column="12"
                        rows="1"
                        id="txtAdd"
                        type="text"
                        label="Address university *"
                        frmField={formik.getFieldProps("addressUni")}
                        err={
                          formik.touched.addressUni && formik.errors.addressUni
                        }
                        errMessage={formik.errors.addressUni}
                      />
                      <Input
                        typeInput="1"
                        column="6"
                        rows="1"
                        id="txtWeb"
                        type="text"
                        label="Website"
                        frmField={formik.getFieldProps("websiteUni")}
                        err={
                          formik.touched.websiteUni && formik.errors.websiteUni
                        }
                        errMessage={formik.errors.websiteUni}
                      />
                      
                      <Input
                        typeInput="1"
                        column="6"
                        rows="1"
                        id="txtConP"
                        type="text"
                        label="Contact Person *"
                        frmField={formik.getFieldProps("contactPerson")}
                        err={
                          formik.touched.contactPerson &&
                          formik.errors.contactPerson
                        }
                        errMessage={formik.errors.contactPerson}
                      />

                      <Input
                        typeInput="1"
                        column="12"
                        rows="2"
                        id="txtNote"
                        type="text"
                        label="Note"
                        frmField={formik.getFieldProps("noteUni")}
                        err={formik.touched.noteUni && formik.errors.noteUni}
                        errMessage={formik.errors.noteUni}
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

export default University;
