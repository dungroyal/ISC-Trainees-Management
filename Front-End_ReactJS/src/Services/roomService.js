import api from './api'

const getAll = () => {return api.get(api.url.listRoom).then(res => res.data);}
const get = (id) => api.get(`${api.url.getRoom}?id=${id}`).then(res => res.data);
const add = (data) => api.post(api.url.newRoom,data).then(res => res.data);
const update = (id,data) => api.put(`${api.url.editRoom}?id=${id}`,data).then(res => res.data);
const remove = (id) => api.delete(`${api.url.deleteRoom}?id=${id}`).then(res => res.data);
//const search = (key) => api.get(`${api.url.searchRoom}?keyWord=${key}`).then(res => res.data);
const pagination=(page,size,key)=>{
    return api.get(`${api.url.paginationRoom}?page=${page}&size=${size}&sort=ASC&key=${key}`).then(res=>res.data)
}

const roomService = {
    getAll, add, update,remove, get, pagination
};

export default roomService;