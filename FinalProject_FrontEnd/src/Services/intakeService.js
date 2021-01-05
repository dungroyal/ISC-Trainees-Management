import api from "./api";
const getAll = () => {
  return api.get(api.url.listIntake).then((res) => res.data);
};

const paginationIntake = (paramsFilters) =>
  api
    .get(`${api.url.paginationIntake} ? {parasFilters}`)
    .then((res) => res.data);

const searchIntake = (keyword) =>
  api.get(`${api.url.searchIntake}?keyWord=${keyword}`).then((res) => res.data);
const get = (id) =>
  api.get(`${api.url.getIntake}?id=${id}`).then((res) => res.data);
const add = (data, majorId) =>
  api
    .post(`${api.url.newIntake}?majorId=${majorId}`, data)
    .then((res) => res.data);

const update = (id, majorId, data) =>
  api
    .put(`${api.url.editIntake}?id=${id}&majorId=${majorId}`, data)
    .then((res) => res.data);

const remove = (id) =>
  api.delete(`${api.url.deleteIntake}?id=${id}`).then((res) => res.data);

const intakeService = {
  getAll,
  add,
  update,
  remove,
  get,
  paginationIntake,
  searchIntake,
};

export default intakeService;
