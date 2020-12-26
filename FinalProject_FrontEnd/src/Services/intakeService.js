import api from "./api";
const getAll = () => {
  return api.get(api.url.listIntake).then((res) => res.data);
};
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
};

export default intakeService;
