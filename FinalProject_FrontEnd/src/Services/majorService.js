import api from "./api";
const getAll = () => {
  return api.get(api.url.listMajor).then((res) => res.data);
};
const get = (id) =>
  api.get(`${api.url.getMajor}?id=${id}`).then((res) => res.data);
const add = (data) => api.post(api.url.newMajor, data).then((res) => res.data);
const update = (id, data) =>
  api.put(`${api.url.editMajor}?id=${id}`, data).then((res) => res.data);
const remove = (id) =>
  api.delete(`${api.url.deleteMajor}?id=${id}`).then((res) => res.data);

const majorService = {
  getAll,
  add,
  update,
  remove,
  get,
};

export default majorService;
