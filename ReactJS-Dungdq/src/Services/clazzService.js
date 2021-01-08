import api from './api'

const getAll = () => {return api.get(api.url.listClazz).then(res => res.data);}
const get = (id) => api.get(`${api.url.getClazz}?id=${id}`).then(res => res.data);
const add = (data) => api.post(api.url.newClazz,data).then(res => res.data);
const update = (id,data) => api.put(`${api.url.editClazz}?id=${id}`,data).then(res => res.data);
const remove = (id) => api.delete(`${api.url.deleteClazz}?id=${id}`).then(res => res.data);
//const search = (key) => api.get(`${api.url.searchClazz}?keyWord=${key}`).then(res => res.data);
const pagination=(page,size,key)=>{
    return api.get(`${api.url.paginationClazz}?page=${page}&size=${size}&sort=ASC&key=${key}`).then(res=>res.data)
}
const clazzService = {
    getAll, add, get, update, remove, pagination
};

export default clazzService;