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
              // console.log(selectedSubjectStatus.value);
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
        console.log(JSON.stringify(res))
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
    // subjectService.getAll().then(res=>{
    //   if (res.status === 0) {
    //     setSubjects(res.data)
    //   }
    // })
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
  },[filters,searchSubject])

    return ( 
        <Fragment>
        <div className="row">
          <div className="col-12">
            <div className="page-title-box d-flex align-items-center justify-content-between">
              <h4 className="mb-0 font-size-18">Danh sách Môn Học</h4>
              <div className="page-title-right">
                <ol className="breadcrumb m-0">
                  <li className="breadcrumb-item"><a href="#">ISC Quang Trung</a></li>
                  <li className="breadcrumb-item active">Quản lý Môn Học</li>
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
                      className="btn btn-success btn-rounded waves-effect waves-light mb-2 mr-2"><i className="bx bxs-add-to-queue" /> Thêm mới môn học</button>
                    </div>
                  </div>{/* end col*/}
                </div>
                <div className="table-responsive">
                  
                  <table className="table table-centered table-nowrap">
                    <thead className="thead-light">
                      <tr>
                        <th style={{width: 20}}>
                          {/* <div className="custom-control custom-checkbox">
                            <input type="checkbox" className="custom-control-input" id="customCheck1" />
                            <label className="custom-control-label" htmlFor="customCheck1">&nbsp;</label>
                          </div> */}
                          #
                        </th>
                        <th>Mã Môn học</th>
                        <th>Tên môn học</th>
                        <th>Tín chỉ môn học</th>
                        <th>Điểm qua môn</th>
                        <th>Trạng thái</th>
                        <th>Ghi chú</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
                      {subjects == null?(
                        <tr className="text-center">
                            <td colspan="9">Not found subject with keyword: "<strong>{searchSubject.searchTerm}</strong>"!</td>
                          </tr>
                      ):(subjects.map((subject,idx) => {
                        return(
                          <tr key={subject.id}>
                        <td>
                          {/* <div className="custom-control custom-checkbox">
                            <input type="checkbox" className="custom-control-input" id="customCheck9" />
                            <label className="custom-control-label" htmlFor="customCheck9">&nbsp;</label>
                          </div> */}
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
                          {/* <a href="#" className="mr-3 text-success" data-toggle="modal" data-target=".viewStudentModal"><i className="far fa-eye font-size-18" /></a> */}
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
            <div className="row">
            <div className="col-sm-12 col-lg-6">
                <Input
                    id="txtNameSubject"
                    type="text"
                    label="Tên môn học"
                    frmField={formik.getFieldProps("nameSub")}
                    error={formik.touched.nameSub && formik.errors.nameSub}
                    errorMessage={formik.errors.nameSub}
                />
                <Input
                    id="txtCodeSubject"
                    type="text"
                    label="Mã môn học"
                    frmField={formik.getFieldProps("codeSub")}
                    error={formik.touched.codeSub && formik.errors.codeSub}
                    errorMessage={formik.errors.codeSub}
                    /> 

                {modalUpdate?(
                <div className="col-6">
                        <div className="form-group">
                          <label htmlFor="aaaaaaaaaaa">Trạng thái môn học</label>
                          <Select
                              id="aaaaaaaaaaa"
                              placeholder="Chọn trạng thái môn học..."
                              // onChange={(val) => {setSelectedSubjectStatus(val)}}
                              // onChange={(val) => {console.log(val.value);}}
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
                    ):(<div className="form-group">
                    <label htmlFor="aaaaaaaaaaa">Trạng thái môn học</label>
                    <Select
                        id="aaaaaaaaaaa"
                        placeholder="Chọn trạng thái môn học..."
                        // onChange={(val) => {setSelectedSubjectStatus(val)}}
                        // onChange={(val) => {console.log(val.value);}}
                        onChange = {(val) => {
                          formik.setFieldValue("statusSub", val.value);
                          setSelectedSubjectStatus(val);
                        }}
                        value={selectedSubjectStatus}
                        closeMenuOnSelect={true}
                        options={optionsSubjectStatus}
                      />
                  </div>)}
                   
				</div>
                <div className="col-sm-12 col-lg-6">
                
                    <Input
                        id="creditSub"
                        type="number"
                        label="Số tín chỉ"
                        frmField={formik.getFieldProps("creditSub")}
                        error={formik.touched.creditSub && formik.errors.creditSub}
                        errorMessage={formik.errors.creditSub}
                    />
                    <Input
                        id="passCore"
                        type="number"
                        label="Điểm qua môn"
                        frmField={formik.getFieldProps("passCore")}
                        error={formik.touched.passCore && formik.errors.passCore}
                        errorMessage={formik.errors.passCore}
                    />
                        <Input
                        id="noteSub"
                        type="text"
                        label="Ghi chú"
                        rows="3"
                        frmField={formik.getFieldProps("noteSub")}
                        error={formik.touched.noteSub && formik.errors.noteSub}
                        errorMessage={formik.errors.noteSub}
                    />  
                </div>
                </div>
            
            </Modal.Body>
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