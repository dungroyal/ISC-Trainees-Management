import { Fragment } from "react";
import React, { useState, useEffect } from "react";
import roomService from "../../Services/roomService";
import Input from "../../Controls/input";
import { Button, Modal } from "react-bootstrap";
import { useFormik } from "formik";
import Select from "../../Controls/select";
import * as Yup from "yup";
import { confirmAlert } from "react-confirm-alert";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import moment from "moment";
import Pagination from "../../Controls/pagination";
const Room = (props) => {
  const [room, setRoom] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const handleModalClose = () => setModalShow(false);
  const [roomId, setRoomId] = useState(0);
  const [modalUpdate, setModalUpdate] = useState(false);
  //load data
  const loadData = () => {
    // roomService.getAll().then((res) => {
    //   if (res.status === 0) {
    //     setRoom(res.data);
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
    roomService
      .pagination(filters.page, filters.size, filters.key)
      .then((res) => {
        if (res.status === 0) {
          let sizeP = res.data[0].size;
          let totalElementsP = res.data[0].totalElements;
          let pageP = res.data[0].pageable.pageNumber;
          let keyP = res.data[0].key;
          let dataP = res.data[0].content;
          setPagination({ pageP, sizeP, keyP, totalElementsP });
          setRoom(dataP);
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
    setRoomId(dataId);
    if (dataId > 0) {
      setModalUpdate(true);
      roomService.get(dataId).then((res) => {
        setModalShow(true);
        console.log(formik.values.statusRoom);
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
      codeRoom: "",
      nameRoom: "",
      typeRoom: "",
      statusRoom: "0",
      noteRoom: "",
      createdBy: "",
      updatedBy: "",
      createdDate: "",
      updatedDate: "",
    },
    validationSchema: Yup.object({
      codeRoom: Yup.string()
        .required("Required")
        .max(50, "Less than 50 characters"),
      nameRoom: Yup.string()
        .required("Required")
        .max(50, "Less than 50 characters"),
      typeRoom: Yup.string().required("Required"),
      noteRoom: Yup.string().max(1000, "Less than 1000 characters"),
    }),
    onSubmit: (values) => {
      handleFormSubmit(values);
    },
  });

  const handleFormSubmit = (data) => {
    if (roomId === 0) {
      roomService.add(data).then((res) => {
        if (res.status === 0) {
          loadData();
          handleModalClose();
          toast.success("THÊM THÀNH CÔNG");
        } else {
          toast.error("DỮ LIỆU KHÔNG HỢP LỆ");
        }
      });
    } else {
      roomService.update(roomId, data).then((res) => {
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
    roomService.remove(dataId).then((res) => {
      if (res.status === 0) {
        loadData();
      } else {
      }
    });
  };

  //load search

  const [key, setKey] = useState("");
  const searchRoom = (e, key) => {
    e.preventDefault();
    roomService.pagination(filters.page, filters.size, key).then((res) => {
      if (res.status === 0) {
        let sizeP = res.data[0].size;
        let totalElementsP = res.data[0].totalElements;
        let pageP = res.data[0].pageable.pageNumber;
        let keyP = res.data[0].key;
        let dataP = res.data[0].content;
        setPagination({ pageP, sizeP, keyP, totalElementsP });
        setRoom(dataP);
      } else {
        setRoom([]);
      }
    });
  };
  useEffect(() => {
    loadData();
    document.title = "Rooms - ISC Quang Trung Management";
  }, [filters]);
  return (
    <Fragment>
      <div className="row">
        <div className="col-12">
          <div className="page-title-box d-flex align-items-center justify-content-between">
            <h4 className="mb-0 font-size-18">Rooms List</h4>
            <div className="page-title-right">
              <ol className="breadcrumb m-0">
                <li className="breadcrumb-item">
                  <a href="#">ISC Quang Trung</a>
                </li>
                <li className="breadcrumb-item active">Rooms List</li>
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
                      <i className="bx bxs-add-to-queue" /> Add new room
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
                      <th>Code</th>
                      <th>Name</th>
                      <th>Type</th>
                      <th>Status</th>
                      <th>Active</th>
                    </tr>
                  </thead>
                  <tbody>
                    {room.map((room, idx) => {
                      return (
                        <tr key={room.id}>
                          <td>{idx + 1}</td>
                          <td>
                            <a href="#" className="text-body font-weight-bold">
                              {room.codeRoom}
                            </a>{" "}
                          </td>
                          <td>{room.nameRoom}</td>
                          <td>{room.typeRoom}</td>
                          <td>
                            <span className="badge badge-pill badge-soft-success font-size-12">
                              {room.statusRoom}
                            </span>
                          </td>
                          <td>
                            <a
                              href="#"
                              className="mr-3 text-primary"
                              onClick={(e) => handleModalShow(e, room.id)}
                            >
                              <i className="fas fa-user-edit font-size-18" />
                            </a>
                            <a
                              href="#"
                              className="text-danger"
                              onClick={(e) => submitDelete(e, room.id)}
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
                  <Modal.Title>Update rooms</Modal.Title>
                ) : (
                  <Modal.Title>Add rooms</Modal.Title>
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
                        label="Name room *"
                        frmField={formik.getFieldProps("nameRoom")}
                        err={formik.touched.nameRoom && formik.errors.nameRoom}
                        errMessage={formik.errors.nameRoom}
                      />
                      <Input
                        typeInput="1"
                        column="6"
                        rows="1"
                        id="txtCode"
                        type="text"
                        label="Code room *"
                        name="codeRoom"
                        frmField={formik.getFieldProps("codeRoom")}
                        err={formik.touched.codeRoom && formik.errors.codeRoom}
                        errMessage={formik.errors.codeRoom}
                      />
                      {modalUpdate ? (
                            <div className="col-6">
                                <div className="form-group">
                                  <label htmlFor="setSelected"> Status</label>
                                      <Select
                                        label="Type room *"
                                        name="type"
                                        id="typeRoom"
                                        value={formik.values.typeRoom}
                                        frmField={formik.getFieldProps("typeRoom")}
                                      >
                                        <option value={formik.values.typeRoom} hiden>
                                          {formik.values.typeRoom}
                                        </option>
                                        {formik.values.typeRoom == "Lab" ? (
                                          <option label="Theory" value="Theory">
                                            Theory
                                          </option>
                                        ) : (
                                          <option label="Lab" value="Lab">
                                            {" "}
                                            Lab
                                          </option>
                                        )}
                                      </Select>
                                    </div>
                                </div>
                      ) : (
                        <div className="col-6">
                          <div className="form-group">
                          <Select
                            label="Type room *"
                            name="type"
                            id="typeRoom"
                            value={formik.values.typeRoom}
                            frmField={formik.getFieldProps("typeRoom")}
                            required
                          >
                            <option value="Lab">Lab</option>
                            <option value="Theory">Theory</option>
                          </Select>
                            </div>
                        </div>
                      )}


                      {modalUpdate ? (
                        <Select
                          label="Active"
                          name="status"
                          id="statusRoom"
                          value={formik.values.statusRoom}
                          frmField={formik.getFieldProps("statusRoom")}
                        >
                          <option value={formik.values.statusRoom} hiden>
                            {formik.values.statusRoom}
                          </option>
                          {formik.values.statusRoom == "Active" ? (
                            <option label="Inactive" value="Inactive">
                              Inactive
                            </option>
                          ) : (
                            <option label="Active" value="Active">
                              Active
                            </option>
                          )}
                        </Select>
                      ) : ("")}
                      <Input
                        typeInput="1"
                        column="12"
                        rows="2"
                        id="txtNote"
                        type="text"
                        label="Note"
                        frmField={formik.getFieldProps("noteRoom")}
                        err={formik.touched.noteRoom && formik.errors.noteRoom}
                        errMessage={formik.errors.noteRoom}
                      />
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
                        ""
                      )}
                      {modalUpdate ? (
                        <Input
                          typeInput="1"
                          column="6"
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
                  <div className="row justify-content-end small">
                    <div className="col-auto pr-5">
                      <p className="p-0">
                      Field required (<span className="text-danger">*</span>)
                      </p>
                    </div>
                  </div>
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
          </div>
        </div>
      </div>
    </Fragment>
  );
};

export default Room;
