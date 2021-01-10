import React, { useState, Fragment, useEffect } from "react";
import { toast } from "react-toastify";
import { useFormik } from "formik";
import * as Yup from "yup";
import Select from "react-select";
import { Button, Modal } from "react-bootstrap";
import { confirmAlert } from "react-confirm-alert";
import queryString from "query-string";
import DatePicker, { registerLocale } from "react-datepicker";
import Input from "../../Controls/input";
import intakeService from "../../Services/intakeService";
import majorService from "../../Services/majorService";
import Pagination from "./paginationIntake";
import SearchIntake from "./searchIntake";


const Intake = (props) => {
  // Option for Intake Status
  const optionsIntakeStatus = [
    { label: "None", value: "None" },
    { label: "Doing", value: "Doing" },
    { label: "Done", value: "Done" },
  ];
  const [selectedIntakeStatus, setSelectedIntakeStatus] = useState({
    label: "None",
    value: "None",
  });

  // Custom Select Intake Status
  for (var i = 0; i < optionsIntakeStatus.length; i++) {
    if (selectedIntakeStatus.length >= 1) {
      optionsIntakeStatus[i].disabled = true;
    }
  }

  // Option for Major
  const [major, setMajor] = useState([]);
  const [startDayState, setStartDayState] = useState(new Date());
  const [modalUpdate, setModalUpdate] = useState(false);

  const formik = useFormik({
    initialValues: {
      codeIntake: "",
      nameIntake: "",
      startDay: "",
      endDay: "",
      statusIntake: { label: "None", value: "None" },
      createdBy: "Admin",
      updatedBy: "Admin",
      selectMajor: "",
    },
    validationSchema: Yup.object({
      codeIntake: Yup.string()
        .required("Intake code is required")
        .min(3)
        .max(50, "Intake code must be lester than 50 characters"),
      nameIntake: Yup.string()
        .required("Intake name is required")
        .max(200, "Intake name must be lester than 200 characters"),
      startDay: Yup.date().required("Start Date is required").typeError('INVALID_DATE'),
      endDay: Yup.date().required("Ended Date is required").min(new Date(startDayState),"Finish date must not be greater than the start date"),
      selectMajor: Yup.object().required("Major is required"),
    }),
    onSubmit: (values) => {
      const newValue = {
        ...values,
        startDay: new Date(values.startDay).toLocaleDateString('vi-VI',{year: 'numeric', month: 'numeric', day: 'numeric'}),
        endDay: new Date(values.endDay).toLocaleDateString('vi-VI',{year: 'numeric', month: 'numeric', day: 'numeric'}),
      }
      handleFormSubmit(values);
    },
  });

  //   Intake
  const [intake, setIntake] = useState([]);
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
    // typeIntake: "n",
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
    //Get Intake with Filters
    const paramsFilters = queryString.stringify(filters);
    intakeService.paginationIntake(paramsFilters).then((res) => {
      const totalRows = res.data[0].totalElements;
      const size = res.data[0].size;
      const pageCurrent = res.data[0].pageable.pageNumber;
      if (res.status === 0) {
        setIntake(res.data[0].content);
        setPagination({
          page: pageCurrent,
          size: size,
          totalRows: totalRows,
        });
      }
    });

    //Get all major
    majorService.getAll().then((res) => {
      if (res.status === 0) {
        setMajor(res.data);
      }
    });
  };

  //Didmount load data
  useEffect(() => {
    loadData();
    document.title = "Intakes - ISC Quang Trung Management";
  }, [filters]);

  // Update Intake
  const [intakeId, setIntakeId] = useState(0);
  //Check Form Update And Add Intake
  const handleModalShow = (e, dataId) => {
    if (e) e.preventDefault();
    setIntakeId(dataId);
    if (dataId > 0) {
      setModalUpdate(true);
      setModalShow(true);
      intakeService.get(dataId).then((res) => {
        const intakeById = res.data[0];
        formik.setFieldValue('codeIntake',intakeById.codeIntake)
        formik.setFieldValue('nameIntake',intakeById.nameIntake)
        formik.setFieldValue('startDay', new Date(intakeById.startDay))
        formik.setFieldValue('endDay', new Date(intakeById.endDay))
        formik.setFieldValue('selectMajor',{ label: intakeById.major.nameMajor, value: intakeById.major.id })
        formik.setFieldValue('statusIntake', { label: intakeById.statusIntake, value: intakeById.statusIntake })
        formik.setFieldValue('createdDate',intakeById.createdDate)
        formik.setFieldValue('createdBy',intakeById.createdBy)
        formik.setFieldValue('updatedDate',intakeById.updatedDate)
        formik.setFieldValue('updatedBy',intakeById.updatedBy)
      });
    } else {
      setModalUpdate(false);
      formik.resetForm();
      setModalShow(true);
    }
  };

  const confirmDeleteIntake = (e, intakeId) => {
    if (e) e.preventDefault();
    confirmAlert({
      title: "Confirm to submit",
      message: "Bạn có muốn xóa khóa học này?",
      buttons: [
        {
          label: "Đồng ý",
          onClick: () => handleDeleteIntake(intakeId),
        },
        {
          label: "Hủy bỏ",
        },
      ],
    });
  };

  //Delete new Intake
  const handleDeleteIntake = (intakeId) => {
    intakeService.remove(intakeId).then((res) => {
      if (res.status === 0) {
        toast.success("Delete Intake Success");
        loadData();
      } else {
        toast.error(res.message);
      }
    });
  };

  //   Add new Intake
  const handleFormSubmit = () => {
    if (intakeId === 0) {
      intakeService.addNew(
          formik.values.codeIntake,
          formik.values.nameIntake,
          new Date(formik.values.startDay).toISOString(),
          new Date(formik.values.endDay).toISOString(),
          formik.values.statusIntake.value,
          "Admin",
          formik.values.selectMajor.value,
        )
        .then((res) => {
          if (res.status === 0) {
            toast.success("Add new intake success");
            loadData();
            handleModalClose();
          } else {
            toast.error("Add intake error, " + res.message);
          }
        });
    } else {
      intakeService
        .update(
          intakeId,
          formik.values.codeIntake,
          formik.values.nameIntake,
          new Date(formik.values.startDay).toISOString(),
          new Date(formik.values.endDay).toISOString(),
          formik.values.statusIntake.value,
          "Admin",
          formik.values.selectMajor.value,
        )
        .then((res) => {
          if (res.status === 0) {
            toast.success("Update Success");
            loadData();
            handleModalClose();
          } else {
            toast.error("Update intake error");
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
        <div className="row">
          <div className="col-12">
            <div className="page-title-box d-flex align-items-center justify-content-between">
              <h4 className="mb-0 font-size-18">Intake List</h4>

              <div className="page-title-right">
                <ol className="breadcrumb m-0">
                  <li className="breadcrumb-item">
                    <a href="javascript: void(0);">ISC Quang Trung</a>
                  </li>
                  <li className="breadcrumb-item active">Management Intakes</li>
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
                        <SearchIntake onSubmit={handedSearchChange} />
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
                        <i className="bx bxs-add-to-queue" /> Add New Intake
                      </button>
                    </div>
                  </div>
                </div>
                <div className="table-responsive">
                  <table className="table table-centered table-nowrap">
                    <thead className="thead-light">
                      <tr>
                        <th style={{ width: 10 }}>#</th>
                        <th>Intake Code</th>
                        <th>Name Intake</th>
                        <th>Start Day</th>
                        <th>Finish Day</th>
                        <th>During</th>
                        <th>Status</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
                      {intake.length == 0 ? (
                        <tr className="text-center">
                          <td colspan="9">
                            Not found intake :(
                            <strong>{filters.search}</strong>"!
                          </td>
                        </tr>
                      ) : (
                        intake.map((intake, idx) => {
                          return (
                            <tr>
                              <td>{idx + 1}</td>
                              <td className="font-weight-bold">{intake.codeIntake}</td>
                              <td>{intake.nameIntake}</td>
                              <td>
                                {new Date(intake.startDay).toLocaleDateString('vi-VI',{year: 'numeric', month: 'numeric', day: 'numeric'})}
                              </td>
                              <td>
                                {new Date(intake.endDay).toLocaleDateString('vi-VI',{year: 'numeric', month: 'numeric', day: 'numeric'})}
                              </td>
                              <td>
                                {Math.round((new Date(intake.endDay) - new Date(intake.startDay)) / (7 * 24 * 60 * 60 * 1000))} tuần
                              </td>
                              <td>{intake.statusIntake}</td>
                              <td>
                                <a href="javascript:void(0);"
                                  onClick={(e) => handleModalShow(e, intake.id)}
                                  className="mr-3 text-primary">
                                  <i className="fas fa-user-edit font-size-15" />
                                </a>
                                {intake.statusIntake === "None" ? (
                                  <a  href="javascript:void(0);"
                                  onClick={(e) =>
                                    confirmDeleteIntake(e, intake.id)
                                  }
                                  className="text-danger" >
                                  <i className="far fa-trash-alt font-size-15" />
                                </a>
                                ):("")}
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

      {/* Modal  */}
      <Modal
        show={modalShow}
        onHide={handleModalClose}
        backdrop="static"
        keyboard={false}
        size="lg"
      >
        <Modal.Header closeButton>
          {modalUpdate ? (
            <Modal.Title>Update Intake</Modal.Title>
          ) : (
            <Modal.Title>New Intake</Modal.Title>
          )}
        </Modal.Header>
        <form onSubmit={formik.handleSubmit} enctype="application/json">
          <Modal.Body>
            <div className="row px-4">
              <Input
                typeInput="1"
                column="6"
                rows="1"
                id="codeIntake"
                name="codeIntake"
                type="text"
                label="Intake code *"
                frmField={formik.getFieldProps("codeIntake")}
                err={formik.touched.codeIntake && formik.errors.codeIntake}
                errMessage={formik.errors.codeIntake}
                value={formik.values.codeIntake}
                onChange={formik.handleChange}
              />

              <div className="col-6">
                <div class="form-group">
                  <label htmlFor="selectedMajor">Major</label>
                  <Select
                    id="selectedMajor"
                    placeholder="Select major..."
                    onChange={(val) => {
                      formik.setFieldValue("selectMajor", val);
                    }}
                    value={formik.values.selectMajor}
                    closeMenuOnSelect={true}
                    options={major.map((e) => ({
                      label: e.nameMajor,
                      value: e.id,
                    }))}
                  />
                  {formik.touched.selectMajor && formik.errors.selectMajor ? (
                    <small class="text-danger">{formik.errors.selectMajor}</small>
                  ) : (
                    ""
                  )}
                </div>
              </div>

              <Input
                typeInput="1"
                column="12"
                rows="1"
                id="nameIntake"
                name="nameIntake"
                type="text"
                label="Intake name *"
                frmField={formik.getFieldProps("nameIntake")}
                err={formik.touched.nameIntake && formik.errors.nameIntake}
                errMessage={formik.errors.nameIntake}
                value={formik.values.nameIntake}
                onChange={formik.handleChange}
              />

              {modalUpdate ? (
                <div className="col-12">
                  <div class="form-group">
                    <label htmlFor="setSelectedIntakeStatus">Status intake</label>
                    <Select
                      id="setSelectedIntakeStatus"
                      placeholder="Select status intake..."
                      onChange={(val) => {
                        formik.setFieldValue("statusIntake", val);
                      }}
                      value={formik.values.statusIntake}
                      options={optionsIntakeStatus}
                      closeMenuOnSelect={true}
                      isOptionDisabled={(option) => option.value === 'None'}
                    />
                  </div>
                </div>
              ) : (
                ""
              )}

              <div className="col-6">
                <div className="form-group row">
                  <label className="col-12">Start day *</label>
                  <div className="col-12">
                  <DatePicker
                    selectsStart
                    selected={formik.values.startDay}
                    onChange={(date) => {
                      setStartDayState(date);
                      formik.setFieldValue("startDay", date);
                    }}
                    minDate={new Date()}
                    className="col-12 form-control"                    
                    placeholderText="Select a date start intake"
                    dateFormat="dd/MM/yyyy"
                  />
                  {formik.touched.startDay && formik.errors.startDay ? (
                    <small class="text-danger">{formik.errors.startDay}</small>
                  ) : (
                    ""
                  )}
                  </div>
                </div>
              </div>

              <div className="col-6">
                <div className="form-group row">
                  <label className="col-12">Finish day *</label>
                  <div className="col-12">
                  <DatePicker
                    selectsEnd
                    selected={formik.values.endDay}
                    onChange={(date) => {
                      formik.setFieldValue("endDay", date);
                    }}
                    minDate={formik.values.startDay}
                    className="col-12 form-control"                    
                    placeholderText="Select a date finish intake"
                    dateFormat="dd/MM/yyyy"
                  />
                  {formik.touched.endDay && formik.errors.endDay ? (
                    <small class="text-danger">{formik.errors.endDay}</small>
                  ) : (
                    ""
                  )}
                  </div>
                </div>
              </div>

              {modalUpdate ? (
                <Fragment>
                  <Input
                    typeInput="1"
                    column="6"
                    rows="1"
                    readOnly
                    type="text"
                    label="Create by"
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
                  {formik.values.updatedDate != null ? (
                    <Fragment>
                      <Input
                        typeInput="1"
                        column="6"
                        rows="1"
                        readOnly
                        type="text"
                        label="Latest Update by"
                        value={`${formik.values.updatedBy} - ${new Date(
                          formik.values.updatedDate
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

export default Intake;
