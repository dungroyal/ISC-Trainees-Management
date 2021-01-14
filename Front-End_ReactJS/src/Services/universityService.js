import api from './api'
const getAll = () => {return api.get(api.url.listUniversity).then(res => res.data);}
const get = (id) => api.get(`${api.url.getUniversity}?id=${id}`).then(res => res.data);
const add = (data) => api.post(api.url.newUniversity,data).then(res => res.data);
const update = (id,data) => api.put(`${api.url.editUniversity}?id=${id}`,data).then(res => res.data);
const remove = (id) => api.delete(`${api.url.deleteUniversity}?id=${id}`).then(res => res.data);
//const search = (key) => api.get(`${api.url.searchUniversity}?keyWord=${key}`).then(res => res.data);
const pagination=(page,size,key)=>{
    return api.get(`${api.url.paginationUniversity}?page=${page}&size=${size}&sort=ASC&key=${key}`).then(res=>res.data)
}
const universityService = {
    getAll, add, update,remove, get, pagination
};

export default universityService;