import {Fragment, useEffect, useRef, useState} from "react";
import subjectService from './../../Services/subjectService';
import Input from './../../Controls/input';
import queryString from 'query-string';
import { useFormik } from 'formik';
import * as Yup from "yup";
import { Button, Modal } from 'react-bootstrap';
import Select from 'react-select';
import SearchSubject from "./searchSubject";
import Pagination from "./paginationSubject";


const Subject = (props) => {
  const childRef = useRef();

  
  const [selectedSubjectStatus,setSelectedSubjectStatus] = useState([{value:'Active',label:'Active',disabled:false}]);
  //add
    //selection
    const optionsSubjectStatus = [
      {value:'Active',label:'Active',disabled:false},
      {value:'Inactive',label:'Inactive',disabled:false}
    ];

    for (var index = 0; index < optionsSubjectStatus.length; index++) {
      if (selectedSubjectStatus.length >= 1) {
        optionsSubjectStatus[index].disabled=true;
      }
    }
  //modal
  const [showModal, setModalShow] = useState(false);
  const [modalUpdate, setModalUpdate] = useState(false);
  //update 
  const [subjectId,setSubjectId] = useState(0);
  //check form update and add
  const handleModalShow = (e,dataId) => {
    if(e) e.preventDefault();
    setSubjectId(dataId);
    if (dataId > 0) {
      setModalUpdate(true);
      subjectService.get(dataId).then((res)=>{
        const subjectById = res.data[0];
        formik.setValues(subjectById);
        setSelectedSubjectStatus({label:subjectById.statusSub,value:subjectById.statusSub,disabled:false})
        setModalShow(true);
      });
    }else{
      setModalUpdate(false);
      formik.resetForm();
      formik.setFieldValue("statusSub","Active");
      setModalShow(true);
    }
  }
    
  //delete
  const deleteRow = ((e,dataId) => {
      e.preventDefault();
      subjectService.remove(dataId).then(res=>{
        if (res.status === 0) {
            loadData();
        }
      })
      
    
  })

  const [subjects,setSubjects] = useState([]);

const handleModalClose = () => setModalShow(false);
const formik = useFormik({
    initialValues:{
                codeSub:"",
                nameSub:"",
                statusSub: "",
                creditSub:"",
                passCore:"",
                noteSub:""
            },
            validationSchema: Yup.object({
                codeSub: Yup.string()
                    .required("Required")
                    .min(4,"Must be 4 character or more.")
                    .max(10,"Less than 10 characters. "),
                nameSub: Yup.string()
                    .required("Required")
                    .min(2,"Must be 2 character or more.")
                    .max(40,"less than 40 characters."),
                creditSub: Yup.string()
                    .required("Required")
                    .min(1,"Must more than one.")
                    .max(3,"Less than 3 characters."),
                passCore: Yup.string()
                    .required("Required"),
                noteSub: Yup.string()
                    .max(255,"Over 255 Character"),
                
            }),
            onSubmit:(values) =>{
              console.log("data",values);
              handleFormSubmit(values);
            },
    });
//search
const [searchSubject, setSearchSubject] = useState({
  keyword: "",
  searchTerm : ""
});
const handleSearchChange = (newSearch) => {
  setSearchSubject({
    ...searchSubject,
    keyWord: newSearch,
  })
};
//pagination
      const [filters, setFilters] = useState({
        page: 0,
        size: 4,
      });
      const handlePageChange = (newPage) => {
        setFilters({
          ...filters,
          page: newPage,
        })
      };
  const loadData = () =>{
    if (searchSubject.searchTerm == "" || searchSubject.keyWord == "") {
      const paramsFilters = queryString.stringify(filters);
      subjectService.paginationSubject(paramsFilters).then((res) => {
        const totalRows = res.data[0].totalElements;
        const totalPage = res.data[0].totalPages;
        const size = res.data[0].size;
        const pageCurrent = res.data[0].pageable.pageNumber;
        if (res.status === 0) {
          setSubjects(res.data[0].content);
          setPagination({
            page: pageCurrent,
            size: size,
            totalRows: totalRows,
          })
        }
      });
    }else{
      const keyword = searchSubject.searchTerm;
      subjectService.searchSubject(searchSubject.searchTerm).then((res) => {
        if (res.status === 0) {
          setSubjects(res.data);
        }else{
          setSubjects(res.data);
        }
      });
    }
  }
  const handleFormSubmit = (data) => {
    subjectService
    .add(data)
    .then((res)=>{
        if (res.status === 0) {
            loadData();
            handleModalClose();

        }
    })
}
  const [pagination,setPagination] = useState({
    page:0,
    size:5,
    totalRows: 1
  })
  useEffect(()=>{
    loadData();
    document.title = "Subjects - ISC Quang Trung Management";
  },[filters,searchSubject])

    return ( 
        <Fragment>
        <div className="row">
          <div className="col-12">
            <div className="page-title-box d-flex align-items-center justify-content-between">
              <h4 className="mb-0 font-size-18">Subjects List</h4>
              <div className="page-title-right">
                <ol className="breadcrumb m-0">
                  <li className="breadcrumb-item"><a href="#">ISC Quang Trung</a></li>
                  <li className="breadcrumb-item active">Subjects List</li>
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
                        <SearchSubject onSubmit={handleSearchChange}/>
                        <i className="bx bx-search-alt search-icon" />
                      </div>
                    </div>
                  </div>
                  <div className="col-sm-8">
                    <div className="text-sm-right">
                      <button type="button" 
                      data-toggle="modal" 
                      onClick={()=>handleModalShow(null,0)} 
                      data-target=".addStudentModal" 
                      className="btn btn-success btn-rounded waves-effect waves-light mb-2 mr-2"><i className="bx bxs-add-to-queue" /> Add new subject</button>
                    </div>
                  </div>{/* end col*/}
                </div>
                <div className="table-responsive">
                  
                  <table className="table table-centered table-nowrap">
                    <thead className="thead-light">
                      <tr>
                        <th style={{width: 20}}>
                          #
                        </th>
                        <th>Code Subject</th>
                        <th>Name Subject</th>
                        <th>Credit Subject</th>
                        <th>Pass the subject</th>
                        <th>Status</th>
                        <th>Note</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
                      {subjects == null?(
                        <tr className="text-center">
                            <td colspan="9">Not found subject!</td>
                          </tr>
                      ):(subjects.map((subject,idx) => {
                        return(
                          <tr key={subject.id}>
                        <td>
                          {idx+1}
                        </td>
                        <td><a href="#" className="text-body font-weight-bold"></a>{subject.codeSub}</td>
                        <td>{subject.nameSub}</td>
                        <td>
                          {subject.creditSub}
                        </td>
                        <td>
                          {subject.passCore}
                        </td>
                        <td>
                          <span className="badge badge-pill badge-soft-success font-size-12">{subject.statusSub}</span>
                        </td>
                        <td>
                          {subject.noteSub}
                        </td>
                        <td>
                          <a href="#" onClick={(e) => handleModalShow(e,subject.id)} className="mr-3 text-primary"><i className="fas fa-user-edit font-size-18" /></a>
                          <a href="#" onClick={(e) => deleteRow(e,subject.id)} className="text-danger"><i className="far fa-trash-alt font-size-18" /></a>
                        </td>
                      </tr>
                        )
                      }))
                      }
                    </tbody>
                  </table>
                </div>
                {searchSubject.keyword == ""  ?(
                  <Pagination pagination={pagination} onPageChange={handlePageChange}/>
                ):("")

                }
              </div>
            </div>
          </div>
        </div>

        {/* modal */}
        <Modal show={showModal} onHide={handleModalClose} size="lg" backdrop="static" keyboard={false}>
        <Modal.Header closeButton>
        {modalUpdate ? (
                <Modal.Title>Update Subject</Modal.Title>
              ) : (
                <Modal.Title>Add Subject</Modal.Title>
              )}
        </Modal.Header>
        <form onSubmit={formik.handleSubmit}>
            <Modal.Body>
            <div className="row px-3">
                <Input
                    typeInput="1"
                    column="12"
                    rows="1"
                    id="txtNameSubject"
                    type="text"
                    label="Name Subject *"
                    frmField={formik.getFieldProps("nameSub")}
                    err={formik.touched.nameSub && formik.errors.nameSub}
                    errMessage={formik.errors.nameSub}
                />
                <Input
                    typeInput="1"
                    column="6"
                    rows="1"
                    id="txtCodeSubject"
                    type="text"
                    label="Code Subject *"
                    frmField={formik.getFieldProps("codeSub")}
                    err={formik.touched.codeSub && formik.errors.codeSub}
                    errMessage={formik.errors.codeSub}
                    /> 

                {modalUpdate?(
                <div className="col-6">
                        <div className="form-group">
                          <label>Status Subject *</label>
                          <Select
                              onChange = {(val) => {
                                formik.setFieldValue("statusSub", val.value);
                                setSelectedSubjectStatus(val);
                              }}
                              value={selectedSubjectStatus}
                              closeMenuOnSelect={true}
                              options={optionsSubjectStatus}
                            />
                        </div>
                  </div> 
                    ):(
                    
                  <div className="col-6">
                    <div className="form-group">
                      <label>Status Subject</label>
                      <Select
                          onChange = {(val) => {
                            formik.setFieldValue("statusSub", val.value);
                            setSelectedSubjectStatus(val);
                          }}
                          value={selectedSubjectStatus}
                          closeMenuOnSelect={true}
                          options={optionsSubjectStatus}
                        />
                    </div>
                  </div>)}
                    <Input
                        typeInput="1"
                        column="6"
                        rows="1"
                        id="creditSub"
                        type="number"
                        label="Credit *"
                        frmField={formik.getFieldProps("creditSub")}
                        err={formik.touched.creditSub && formik.errors.creditSub}
                        errMessage={formik.errors.creditSub}
                    />
                    <Input
                        typeInput="1"
                        column="6"
                        rows="1"
                        id="passCore"
                        type="number"
                        label="Pass the subject *"
                        frmField={formik.getFieldProps("passCore")}
                        err={formik.touched.passCore && formik.errors.passCore}
                        errMessage={formik.errors.passCore}
                    />
                        <Input
                        typeInput="1"
                        column="12"
                        rows="2"
                        id="noteSub"
                        type="text"
                        label="Note"
                        frmField={formik.getFieldProps("noteSub")}
                        err={formik.touched.noteSub && formik.errors.noteSub}
                        errMessage={formik.errors.noteSub}
                    />  
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
                    <Button variant="primary" type="submit" disabled={!(formik.isValid && formik.dirty)}>
                        Save
                    </Button>
            </Modal.Footer>
        </form>
    </Modal>
        </Fragment>
     );
}
 
export default Subject;