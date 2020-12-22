import {Modal,Button} from "react-bootstrap"
import React, {useState,useEffect, forwardRef, useImperativeHandle} from "react";
import Input from "../../Containers/Input";
import {useFormik} from "formik";
import * as Yup from "yup";
import avatar  from "./../../plugin/images/avata-playhoder.jpg";
import $ from "jquery";

const ModalCompany = forwardRef((props, ref) => {

  useEffect(() => {
		$("#imageUpload1").change(function() {
			readURL(this)
		});
	  });
	  const readURL=(input)=> {
		  alert("here")
		if (input.files && input.files[0]) {
		  var reader = new FileReader();
		  reader.onload = function(e) {
			$('#imagePreview').css('background-image', 'url('+e.target.result +')');
			$('#imagePreview').hide();
			$('#imagePreview').fadeIn(650);
		  }
		  reader.readAsDataURL(input.files[0]);
		}
	  }
	
    useImperativeHandle(
        ref,
        () => ({
            showAlert() {
                setShow(true)
            }
        }),
    );  
    const [showModal, setShow] = useState(false);
    const handleClose = () => setShow(false);
const formik = useFormik({
		initialValues: {
      name:""
		},
		validationSchema: Yup.object({
			name: Yup.string()
				.required("Not Required")
				.min(2, "Must be 2 characters or more")
				.max(40, "lest than 40 characters"),
			phone: Yup.string()
				.required("Not Required")
				.min(2, "Must be 2 characters or more")
				.max(40, "lest than 40 characters"),
			email: Yup.string()
				.required("Not Required")
				.min(2, "Must be 2 characters or more")
				.max(40, "lest than 40 characters"),			
		}),
		onSubmit: (values) => {
			console.log(values);
		},
	});

    return (
		 <Modal
            show={showModal}
				    onHide={handleClose}
				    size="xl"
                backdrop="static"
                keyboard={false}>
                <Modal.Header closeButton>
                    <Modal.Title>Add Company</Modal.Title>
                </Modal.Header>
                <form onSubmit={formik.handleSubmit}>
                <Modal.Body>
					<div className="row">
						<div className="col-sm-12 col-lg-2">
							<div className="avatar-upload-jsk py-2">
								<div className="avatar-edit">
									<input type='file' name="avatar" id="imageUpload1" accept=".png, .jpg, .jpeg" />
								</div>
								<div className="avatar-preview-jsk">
									<label htmlFor="imageUpload1" className="imageUpload"> <i className="bx bxs-cloud-upload"></i></label>
									<div id="imagePreview" style={{backgroundImage: "url(" + avatar + ")"}}></div>
								</div>
							</div>
						</div>
						<div className="col-sm-12 col-lg-10">
							<div className="row">
								<Input
									id="txtMajor"
									type="text"
									label="Name"
									frmField={formik.getFieldProps("name")}
									error={formik.touched.name && formik.errors.name}
									errorMessage={formik.errors.name}
								/>
								<Input
									id="txtEmail"
									type="text"
									label="Email"
									frmField={formik.getFieldProps("email")}
									error={formik.touched.email && formik.errors.email}
									errorMessage={formik.errors.email}
									/>
								<Input
									id="txtPhone"
									type="text"
									label="Phone"
									frmField={formik.getFieldProps("phone")}
									error={formik.touched.phone && formik.errors.phone}
									errorMessage={formik.errors.phone}
								/>
						</div>
						</div>
					</div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" type="submit" disabled={!(formik.isValid && formik.dirty)}>Save</Button>
                </Modal.Footer>
                </form>
            </Modal>
	);
});

export default ModalCompany;
