// import {Modal,Button} from "react-bootstrap"
// import React, {useState} from "react";
// import Input from "../../Containers/Input";
// import {useFormik} from "formik";
// import * as Yup from "yup";

// const ModalCompany = ({toggle}) => {

//     const [showModal, setShow] = useState(toggle);
    
//     const handleClose = () => setShow(false);
    
   
// const formik =  useFormik({
//     initialValues: {
//       name: ""
//   },
//     validationSchema: Yup.object({
//       name: Yup.string().required("Not Required").min(2,"Must be 2 characters or more").max(40, "lest than 40 characters"),
//   }),
//   onSubmit: (values)=>{
//       console.log(values)
//     }
//   })

//     return (
// 		<Modal
//         show={showModal}
//         onHide={handleClose}
//         backdrop="static"
//         keyboard={false}
//       >
//         <Modal.Header closeButton>
//           <Modal.Title>Modal title</Modal.Title>
//         </Modal.Header>
//         <Modal.Body>
//         <Input
//               id="txtCompany"
//               type="text"
//               label="Company"
//               frmField={formik.getFieldProps("company")}
//               error={formik.touched.company && formik.errors.company}
//               errorMessage={formik.errors.company}
//           />
//           <Input
//               id="txtAddress"
//               type="text"
//               label="Address"
//               frmField={formik.getFieldProps("address")}
//               error={formik.touched.address && formik.errors.address}
//               errorMessage={formik.errors.address}
//           />
//         </Modal.Body>
//         <Modal.Footer>
//           <Button variant="secondary" onClick={handleClose}>
//             Close
//           </Button>
//           <Button variant="primary">Save</Button>
//         </Modal.Footer>
//       </Modal>
// 	);
// };

// export default ModalCompany;
