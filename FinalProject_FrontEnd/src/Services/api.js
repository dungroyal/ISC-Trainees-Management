import axios from "axios";

const url = {
  baseURL: "http://localhost:8080/api",
  //intake
  listIntake: "/intake/listIntake",
  getIntake: "/intake/getIntake",
  newIntake: "/intake/newIntake",
  editIntake: "/intake/editIntake",
  deleteIntake: "/intake/deleteIntake",
  paginationIntake: "/intake/pagination",
  searchStudent: "/student/searchIntake",

  //subject
  listMajor: "/major/listMajor",
  getMajor: "/major/getMajor",
  newMajor: "/major/newMajor",
  editMajor: "/major/editMajor",
  deleteMajor: "/major/deleteMajor",
};
const instance = axios.create({
  baseURL: url.baseURL,
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
});
const api = {
  url, // url: url
  instance: instance,
  get: instance.get,
  post: instance.post,
  put: instance.put,
  delete: instance.delete,
};
export default api;
