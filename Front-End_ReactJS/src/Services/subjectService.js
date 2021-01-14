import api from "./api";

const getAll = () => api.get(api.url.subject).then(res => res.data);
const get = (id) => api.get(`${api.url.subject}/${id}`).then(res=>res.data);
const add = (data) => api.post(api.url.subject,data).then(res=>res.data);
const put = (id,data) => api.put(`${api.url.subject}/${id}`,data).then(res=>res.data);
const remove = (id) => api.delete(`${api.url.subject}/${id}`).then(res=>res.data);
//pagination
const paginationSubject = (paramsFilters) =>
                 api.get(`${api.url.paginationSubject}?${paramsFilters}`).then(res => res.data);
//search
const searchSubject = (keyword) => 
                api.get(`${api.url.searchSubject}?keyWord=${keyword}`).then(res => res.data);

                
const subjectService = {
    getAll,get,add,put,remove,paginationSubject,searchSubject
}
 
export default subjectService;