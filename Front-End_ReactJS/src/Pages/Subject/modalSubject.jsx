import React, { useImperativeHandle, useState } from 'react';
import { forwardRef } from 'react';
import { useFormik } from 'formik';
import * as Yup from "yup";
import { Button, Modal } from 'react-bootstrap';
import StatusSubjectSelection from './../../Controls/StatusSubjectSelection';
import Input from '../../Controls/input';
import subjectService from '../../Services/subjectService';


const ModalSubject = forwardRef((props,ref) => {
    useImperativeHandle(
        ref,
        () => ({
            showAlert() {
                setShow(true)
            }
        }),
    );
    
    const handleFormSubmit = (data) => {
        subjectService.add(data).then((res)=>{
            if (res.status === 0) {
                handleModalClose();
            }
        })
    }

    const [showModal, setShow] = useState(false);
    const handleModalClose = () => setShow(false);
    const formik = useFormik({
        initialValues:{
                    name:""
                },
                validationSchema: Yup.object({
                    code: Yup.string()
                        .required("Required")
                        .min(4,"Must be 4 character or more.")
                        .max(10,"Less than 10 characters. "),
                    name: Yup.string()
                        .required("Required")
                        .min(2,"Must be 2 character or more.")
                        .max(40,"less than 40 characters."),
                    credit: Yup.string()
                        .required("Required")
                        .min(1,"Must more than one.")
                        .max(3,"Less than 3 characters."),
                    passScore: Yup.string()
                        .required("Required"),
                    
                }),
                onSubmit:(values) =>{
                    handleFormSubmit(values);
                },
        });

    return (
        <Modal show={showModal} onHide={handleModalClose} size="lg" backdrop="static" keyboard={false}>
        <Modal.Header closeButton>
            <Modal.Title>Add Subject</Modal.Title>
        </Modal.Header>
        <form onSubmit={formik.handleSubmit}>
            <Modal.Body>
            <div className="row">
            <div className="col-sm-12 col-lg-6">
                <Input
                    id="txtNameSubject"
                    type="text"
                    label="Tên môn học"
                    frmField={formik.getFieldProps("name")}
                    error={formik.touched.name && formik.errors.name}
                    errorMessage={formik.errors.name}
                />
                <Input
                    id="txtCodeSubject"
                    type="text"
                    label="Mã môn học"
                    frmField={formik.getFieldProps("code")}
                    error={formik.touched.code && formik.errors.code}
                    errorMessage={formik.errors.code}
                    />     
                <StatusSubjectSelection id="status" label="Trạng thái" />    
				</div>
                <div className="col-sm-12 col-lg-6">
                
                    <Input
                        id="credit"
                        type="number"
                        label="Số tín chỉ"
                        frmField={formik.getFieldProps("credit")}
                        error={formik.touched.credit && formik.errors.credit}
                        errorMessage={formik.errors.credit}
                    />
                    <Input
                        id="passScore"
                        type="number"
                        label="Điểm qua môn"
                        frmField={formik.getFieldProps("passScore")}
                        error={formik.touched.passScore && formik.errors.passScore}
                        errorMessage={formik.errors.passScore}
                    />
                        <Input
                        id="note"
                        type="text"
                        label="Ghi chú"
                        rows="3"
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
	);
    // useImperativeHandle(
    //     ref,
    //     () => ({
    //         showAlert(){
    //             setShow(true)
    //         }
    //     })
    // )
    // const [showModal,setShow] = useState(false);
    // const handleModalClose = setShow(false);

    // const formik = useFormik({
    //     initialValues:{
    //         name:""
    //     },
    //     validationSchema: Yup.object({
    //         code: Yup.string()
    //             .required("Required")
    //             .min(4,"Must be 4 character or more.")
    //             .max(10,"Less than 10 characters. "),
    //         name: Yup.string()
    //             .required("Required")
    //             .min(2,"Must be 2 character or more.")
    //             .max(40,"less than 40 characters."),
    //         credit: Yup.string()
    //             .required("Required")
    //             .min(1,"Must more than one."),
    //         passScore: Yup.string()
    //             .required("Required"),
            
    //     }),
    //     onSubmit:(values) =>{
    //         console.log(values);
    //     }
    // });
    // return(
    //     <Modal
    //         show={showModal}
    //         onHide={handleModalClose}
    //         size="xl"
    //         backdrop="static"
    //         keyboard={false}>
    //         <Modal.Header closeButton>
    //             <Modal.Title>Add Subject</Modal.Title>
    //         </Modal.Header>
    //         <form onSubmit={formik.handleSubmit}>
    //         <Modal.Body>
    //         <div className="col-sm-12 col-lg-10">
	// 						<div className="row">
	// 							<Input
	// 								id="txtNameSub"
	// 								type="text"
	// 								label="name"
	// 								frmField={formik.getFieldProps("name")}
	// 								error={formik.touched.name && formik.errors.name}
	// 								errorMessage={formik.errors.name}
	// 							/>
    //                             </div>
	// 					</div>
    //         </Modal.Body>
    //         <Modal.Footer>
            // <Button variant="secondary" onClick={handleModalClose}>
            //             Close
            //         </Button>
            //         <Button variant="primary" type="submit" disabled={!(formik.isValid && formik.dirty)}>
            //             Save
            //         </Button>
            // </Modal.Footer>
    //         </form>
    //     </Modal>
    // )
})
export default ModalSubject;