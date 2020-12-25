import api from './api';

const getAll = () => {return api.get(api.url.listStudent).then(res => res.data);}
const get = (id) => api.get(`${api.url.getOneStudent}?id=${id}`).then(res => res.data);
const remove = (id) => api.delete(`${api.url.deleteStudent}?id=${id}`).then(res => res.data);
const add = (data) => api.post(api.url.addStudent, data).then(res => res.data);
const add1 = (firstName,
    lastName, code, address, phoneNumber, email, typeStudent, GPA,
    workingStatus, note, image,createdBy, univerId) => {

    let formData = new FormData();

    formData.append("firstName", firstName);
    formData.append("lastName", lastName);
    formData.append("code", code);
    formData.append("address", address);
    formData.append("phoneNumber", phoneNumber);
    formData.append("email", email);
    formData.append("typeStudent", typeStudent);
    formData.append("GPA", GPA);
    formData.append("workingStatus", workingStatus);
    formData.append("note", note);
    formData.append("image", image);
    formData.append("createdBy", createdBy);
    formData.append("univerId", univerId);
    return api.post(api.url.addStudent,formData, {
        headers: {
            "Content-Type": "multipart/form-data",
          },
    }).then(res => res.data);
}

const updateHasImages = (id, firstName,
    lastName, code, address, phoneNumber, email, typeStudent, GPA,
    workingStatus, note, image, updatedBy, univerId) => {
    let formData = new FormData();
    formData.append("id", id);
    formData.append("firstName", firstName);
    formData.append("lastName", lastName);
    formData.append("code", code);
    formData.append("address", address);
    formData.append("phoneNumber", phoneNumber);
    formData.append("email", email);
    formData.append("typeStudent", typeStudent);
    formData.append("GPA", GPA);
    formData.append("workingStatus", workingStatus);
    formData.append("note", note);
    formData.append("image", image);
    formData.append("updatedBy", updatedBy);
    formData.append("univerId", univerId);
    return api.put(api.url.updateStudentHasImg,formData, {
        headers: {
            "Content-Type": "multipart/form-data",
          },
    }).then(res => res.data);
}

const updateNoImages = (id, firstName,
    lastName, code, address, phoneNumber, email, typeStudent, GPA,
    workingStatus, note, updatedBy, univerId) => {

    let formData = new FormData();
    formData.append("id", id);
    formData.append("firstName", firstName);
    formData.append("lastName", lastName);
    formData.append("code", code);
    formData.append("address", address);
    formData.append("phoneNumber", phoneNumber);
    formData.append("email", email);
    formData.append("typeStudent", typeStudent);
    formData.append("GPA", GPA);
    formData.append("workingStatus", workingStatus);
    formData.append("note", note);
    formData.append("updatedBy", updatedBy);
    formData.append("univerId", univerId);
    return api.put(api.url.updateStudentNoImg,formData, {
        headers: {
            "Content-Type": "multipart/form-data",
          },
    }).then(res => res.data);
}

const studentService = {
    getAll, add, get, add1, updateHasImages, updateNoImages,remove
};

export default studentService;