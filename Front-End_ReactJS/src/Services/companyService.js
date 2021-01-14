import api from './api'

const getAll = () => {return api.get(api.url.listCompany).then(res => res.data);}
const get = (id) => api.get(`${api.url.getCompany}?id=${id}`).then(res => res.data);
const add = (data) => api.post(api.url.newCompany,data).then(res => res.data);
const update = (id,data) => api.put(`${api.url.editCompany}?id=${id}`,data).then(res => res.data);
const remove = (id) => api.delete(`${api.url.deleteCompany}?id=${id}`).then(res => res.data);
//const search = (key) => api.get(`${api.url.searchCompany}?keyWord=${key}`).then(res => res.data);
const pagination=(page,size,key)=>{
    return api.get(`${api.url.paginationCompany}?page=${page}&size=${size}&sort=ASC&key=${key}`).then(res=>res.data)
}

const companyService = {
    getAll, add, update,remove, get, pagination
};

export default companyService;
