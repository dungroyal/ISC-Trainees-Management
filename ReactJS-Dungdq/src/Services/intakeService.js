import api from "./api";

const getAll = () => { return api.get(api.url.listIntake).then((res) => res.data) };
const paginationIntake = (paramsFilters) => api.get(`${api.url.paginationIntake}?${paramsFilters}`).then((res) => res.data);
const searchIntake = (keyword) => api.get(`${api.url.searchIntake}?keyWord=${keyword}`).then((res) => res.data);
const get = (id) => api.get(`${api.url.getOneIntake}?id=${id}`).then((res) => res.data);
const remove = (id) => api.delete(`${api.url.deleteIntake}?id=${id}`).then((res) => res.data);

const addNew = (codeIntake, nameIntake, startDay, endDay, statusIntake, createdBy, majorId ) => {
  const data = {codeIntake, nameIntake, startDay, endDay, statusIntake, createdBy};
  return api.post(`${api.url.addIntake}?majorId=${majorId}`, data, {
    headers: {
      "Content-Type": "application/json",
    },
  }).then((res) => res.data);
};


const updateIntake = (
  id,
  codeIntake,
  nameIntake,
  startDay,
  endDay,
  statusIntake,
  updatedBy,
  majorId
) => {
  let formData = new FormData();
  formData.append("id", id);
  formData.append("codeIntake", codeIntake);
  formData.append("nameIntake", nameIntake);
  formData.append("startDay", startDay);
  formData.append("endDay", endDay);
  formData.append("statusIntake", statusIntake);
  formData.append("updatedBy", updatedBy);
  formData.append("majorId", majorId);
  return api
    .put(api.url.updateIntake, formData, {
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    })
    .then((res) => res.data);
};

const intakeService = {
  getAll,
  addNew,
  get,
  updateIntake,
  remove,
  paginationIntake,
  searchIntake,
};

export default intakeService;
