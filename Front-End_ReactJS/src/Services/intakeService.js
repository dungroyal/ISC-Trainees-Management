import api from "./api";

const getAll = () => { return api.get(api.url.listIntake).then((res) => res.data) };
const paginationIntake = (paramsFilters) => api.get(`${api.url.paginationIntake}?${paramsFilters}`).then((res) => res.data);
const searchIntake = (keyword) => api.get(`${api.url.searchIntake}?keyWord=${keyword}`).then((res) => res.data);
const get = (id) => api.get(`${api.url.getOneIntake}?id=${id}`).then((res) => res.data);
const remove = (id) => api.delete(`${api.url.deleteIntake}?id=${id}`, {
  headers: {
    "Content-Type": "application/json",
  },
}).then((res) => res.data);

const addNew = (codeIntake, nameIntake, startDay, endDay, statusIntake, createdBy, majorId ) => {
  const data = {codeIntake, nameIntake, startDay, endDay, statusIntake, createdBy};
  return api.post(`${api.url.addIntake}?majorId=${majorId}`, data, {
    headers: {
      "Content-Type": "application/json",
    },
  }).then((res) => res.data);
};

const update = (intakeId, codeIntake, nameIntake, startDay, endDay, statusIntake, updatedBy, majorId ) => {
  const data = {codeIntake, nameIntake, startDay, endDay, statusIntake, updatedBy};
  return api.put(`${api.url.updateIntake}?id=${intakeId}&majorId=${majorId}`, data, {
    headers: {
      "Content-Type": "application/json",
    },
  }).then((res) => res.data);
};

const intakeService = {
  getAll,
  addNew,
  get,
  update,
  remove,
  paginationIntake,
  searchIntake,
};

export default intakeService;
