import api from "./api";

const getAll = () => {
  return api.get(api.url.listMajor).then((res) => res.data);
};
const paginationMajor = (paramsFilters) =>
  api
    .get(`${api.url.paginationMajor}?${paramsFilters}`)
    .then((res) => res.data);
const searchMajor = (keyword) =>
  api.get(`${api.url.searchMajor}?keyWord=${keyword}`).then((res) => res.data);
const get = (id) =>
  api.get(`${api.url.getOneMajor}?id=${id}`).then((res) => res.data);
const remove = (id) =>
  api.delete(`${api.url.deleteMajor}?id=${id}`).then((res) => res.data);
const add = (data) => api.post(api.url.addMajor, data,{
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
}).then((res) => res.data);

const add1 = (codeMajor, nameMajor, descriptionMajor, createdBy) => {
  let formData = new FormData();

  formData.append("codeMajor", codeMajor);
  formData.append("nameMajor", nameMajor);
  formData.append("descriptionMajor", descriptionMajor);
  formData.append("createdBy", createdBy);

  return api
    .post(api.url.addMajor, formData, {
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    })
    .then((res) => res.data);
};

const updateMajor = (id, codeMajor, nameMajor, descriptionMajor, updatedBy) => {
  let formData = new FormData();
  formData.append("id", id);
  formData.append("codeMajor", codeMajor);
  formData.append("nameMajor", nameMajor);
  formData.append("descriptionMajor", descriptionMajor);
  formData.append("updatedBy", updatedBy);

  return api
    .put(api.url.updateMajor, formData, {
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    })
    .then((res) => res.data);
};

const majorService = {
  getAll,
  add,
  get,
  add1,
  updateMajor,
  remove,
  paginationMajor,
  searchMajor,
};

export default majorService;
