import api from './api'

const getAll = () => {return api.get(api.url.listJobTitle).then(res => res.data);}
const get = (id) => api.get(`${api.url.getJobTitle}?id=${id}`).then(res => res.data);
const add = (data) => api.post(api.url.newJobTitle,data).then(res => res.data);
const update = (id,data) => api.put(`${api.url.editJobTitle}?id=${id}`,data).then(res => res.data);
const remove = (id) => api.delete(`${api.url.deleteJobTitle}?id=${id}`).then(res => res.data);
//const search = (key) => api.get(`${api.url.searchJobTitle}?keyWord=${key}`).then(res => res.data);
const pagination=(page,size,key)=>{
    return api.get(`${api.url.paginationJobTitle}?page=${page}&size=${size}&sort=ASC&key=${key}`).then(res=>res.data)
}
const jobTitleService = {
    getAll, add, get, update, remove,pagination
};

export default jobTitleService;