import React, { Fragment, useState } from "react";
import { Modal, Button } from "react-bootstrap";
import { useFormik } from "formik";
import * as Yup from "yup";
import Input from "../../Containers/Input";
const Company = () => {
	const [showModal, setShow] = useState(false);
	const handleClose = () => setShow(false);
	const handleShow = (id) => setShow(true);

	const formik = useFormik({
		initialValues: {
      name:""
		},
		validationSchema: Yup.object({
			name: Yup.string()
				.required("Not Required")
				.min(2, "Must be 2 characters or more")
				.max(40, "lest than 40 characters"),
		}),
		onSubmit: (values) => {
			console.log(values);
		},
	});
	return (
		<Fragment>
			<div className="row">
				<div className="col-12">
					<div className="page-title-box d-flex align-items-center justify-content-between">
						<h4 className="mb-0 font-size-18">Danh sách Công Ty</h4>
						<div className="page-title-right">
							<ol className="breadcrumb m-0">
								<li className="breadcrumb-item">
									<a href="#">ISC Quang Trung</a>
								</li>
								<li className="breadcrumb-item active">Quản lý Công Ty</li>
							</ol>
						</div>
					</div>
				</div>
			</div>
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
											onClick={()=>handleShow(0)}
											data-target=".addStudentModal"
											className="btn btn-success btn-rounded waves-effect waves-light mb-2 mr-2"
										>
											<i className="bx bxs-add-to-queue" /> Thêm mới công ty
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
											<th>Mã HV</th>
											<th>Họ tên</th>
											<th>Số điện thoại</th>
											<th>Email</th>
											<th>Trạng thái</th>
											<th>Khóa học</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody>
										<tr>
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
											<td>
												<a href="#" className="text-body font-weight-bold">
													#ISC2547
												</a>{" "}
											</td>
											<td>Dustin Moser</td>
											<td>0328 05 05 20</td>
											<td>doanquocdung55@gmail.com</td>
											<td>
												<span className="badge badge-pill badge-soft-success font-size-12">
													Đang học
												</span>
											</td>
											<td>ISC-13</td>
											<td>
												<a
													href="#"
													className="mr-3 text-success"
													data-toggle="modal"
													data-target=".viewStudentModal"
												>
													<i className="far fa-eye font-size-18" />
												</a>
												<a href="#" className="mr-3 text-primary">
													<i className="fas fa-user-edit font-size-18" />
												</a>
												<a href="#" className="text-danger">
													<i className="far fa-trash-alt font-size-18" />
												</a>
											</td>
										</tr>
										<tr>
											<td>
												<div className="custom-control custom-checkbox">
													<input
														type="checkbox"
														className="custom-control-input"
														id="customCheck10"
													/>
													<label
														className="custom-control-label"
														htmlFor="customCheck10"
													>
														&nbsp;
													</label>
												</div>
											</td>
											<td>
												<a href="#" className="text-body font-weight-bold">
													#ISC2548
												</a>{" "}
											</td>
											<td>Clark Benson</td>
											<td>0328 05 05 20</td>
											<td>doanquocdung55@gmail.com</td>
											<td>
												<span className="badge badge-pill badge-soft-warning font-size-12">
													Bảo lưu
												</span>
											</td>
											<td>ISC-12</td>
											<td>
												<a
													href="#"
													className="mr-3 text-success"
													data-toggle="modal"
													data-target=".viewStudentModal"
												>
													<i className="far fa-eye font-size-18" />
												</a>
												<a href="#" className="mr-3 text-primary">
													<i className="fas fa-user-edit font-size-18" />
												</a>
												<a href="#" className="text-danger">
													<i className="far fa-trash-alt font-size-18" />
												</a>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<ul className="pagination pagination-rounded justify-content-end mb-2">
								<li className="page-item disabled">
									<a className="page-link" href="#" aria-label="Previous">
										<i className="mdi mdi-chevron-left" />
									</a>
								</li>
								<li className="page-item active">
									<a className="page-link" href="#">
										1
									</a>
								</li>
								<li className="page-item">
									<a className="page-link" href="#">
										2
									</a>
								</li>
								<li className="page-item">
									<a className="page-link" href="#">
										3
									</a>
								</li>
								<li className="page-item">
									<a className="page-link" href="#">
										4
									</a>
								</li>
								<li className="page-item">
									<a className="page-link" href="#">
										5
									</a>
								</li>
								<li className="page-item">
									<a className="page-link" href="#" aria-label="Next">
										<i className="mdi mdi-chevron-right" />
									</a>
								</li>
							</ul>
						</div>
					</div>
				</div>      
			</div>
      <Modal
                show={showModal}
                onHide={handleClose}
                backdrop="static"
                keyboard={false}>
                <Modal.Header closeButton>
                    <Modal.Title>Add Company</Modal.Title>
                </Modal.Header>
                <form onSubmit={formik.handleSubmit}>
                <Modal.Body>
                <Input
                           id="txtMajor"
                           type="text"
                           label="Name"
                           frmField={formik.getFieldProps("name")}
                           error={formik.touched.name && formik.errors.name}
                           errorMessage={formik.errors.name}
                       />
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" type="submit" disabled={!(formik.isValid && formik.dirty)}>Save</Button>
                </Modal.Footer>
                </form>
            </Modal>

		</Fragment>
	);
};
export default Company;
