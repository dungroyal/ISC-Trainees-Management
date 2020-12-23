import api from "./api";
const getAll = () => {
  return api.get(api.url.listIntake).then((res) => res.data);
};
const get = (id) =>
  api.get(`${api.url.getIntake}?id=${id}`).then((res) => res.data);
const add = (data) => api.post(api.url.newIntake, data).then((res) => res.data);
const update = (id, data) =>
  api.put(`${api.url.editIntake}?id=${id}`, data).then((res) => res.data);
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
