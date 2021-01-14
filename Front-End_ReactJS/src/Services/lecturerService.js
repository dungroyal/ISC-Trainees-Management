import api from "./api";

const getAll = () => { return api.get(api.url.listIntake).then((res) => res.data) };
const paginationLecturer = (paramsFilters) => api.get(`${api.url.paginationLecturer}?${paramsFilters}`).then((res) => res.data);
const searchLecturer = (keyword) => api.get(`${api.url.searchIntake}?keyWord=${keyword}`).then((res) => res.data);
const getLecturer = (id) => api.get(`${api.url.getLecturer}?id=${id}`).then((res) => res.data);
const deleteLecturer = (id) => api.delete(`${api.url.deleteLecturer}?id=${id}`, {
  headers: {
    "Content-Type": "application/json",
  },
}).then((res) => res.data);

const addNewLecturer = (createdBy, updatedBy, codeLec, firstName, lastName, 
    addressLec, phoneLec, emailLec, degree, statusLec, noteLec, image) => {
    let formData = new FormData();
    formData.append("createdBy", createdBy);
    formData.append("updatedBy", updatedBy);
    formData.append("codeLec", codeLec);
    formData.append("firstName", firstName);
    formData.append("lastName", lastName);
    formData.append("addressLec", addressLec);
    formData.append("phoneLec", phoneLec);
    formData.append("emailLec", emailLec);
    formData.append("degree", degree);
    formData.append("statusLec", statusLec);
    formData.append("noteLec", noteLec);
    formData.append("image", image);
    return api.post(api.url.newLecturer,formData, {
        headers: {
            "Content-Type": "multipart/form-data",
          },
    }).then(res => res.data);
}

const updateLecturerNotImage = (id, createdBy, updatedBy, codeLec, firstName, lastName, 
    addressLec, phoneLec, emailLec, degree, statusLec, noteLec) => {
    let formData = new FormData();
    formData.append("id", id);
    formData.append("createdBy", createdBy);
    formData.append("updatedBy", updatedBy);
    formData.append("codeLec", codeLec);
    formData.append("firstName", firstName);
    formData.append("lastName", lastName);
    formData.append("addressLec", addressLec);
    formData.append("phoneLec", phoneLec);
    formData.append("emailLec", emailLec);
    formData.append("degree", degree);
    formData.append("statusLec", statusLec);
    formData.append("noteLec", noteLec);
    return api.put(api.url.updateLecturerNotImage,formData, {
        headers: {
            "Content-Type": "multipart/form-data",
          },
    }).then(res => res.data);
}

const updateLecturerHasImage = (id, createdBy, updatedBy, codeLec, firstName, lastName, 
    addressLec, phoneLec, emailLec, degree, statusLec, noteLec, image) => {
    let formData = new FormData();
    formData.append("id", id);
    formData.append("createdBy", createdBy);
    formData.append("updatedBy", updatedBy);
    formData.append("codeLec", codeLec);
    formData.append("firstName", firstName);
    formData.append("lastName", lastName);
    formData.append("addressLec", addressLec);
    formData.append("phoneLec", phoneLec);
    formData.append("emailLec", emailLec);
    formData.append("degree", degree);
    formData.append("statusLec", statusLec);
    formData.append("noteLec", noteLec);
    formData.append("image", image);
    return api.put(api.url.updateLecturerHasImage,formData, {
        headers: {
            "Content-Type": "multipart/form-data",
          },
    }).then(res => res.data);
}

const lecturerService = {
  getAll,
  addNewLecturer,
  updateLecturerNotImage,
  updateLecturerHasImage,
  getLecturer,
  deleteLecturer,
  paginationLecturer,
  searchLecturer,
};

export default lecturerService;
