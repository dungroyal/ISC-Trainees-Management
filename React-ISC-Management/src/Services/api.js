import axios from 'axios';

const url = {
    baseURL : 'localhost:8080/api',
         university: '/university'

};
const instance = axios.create({
    baseURL : url.baseURL,
    headers: {
        "Content-Type" :"application/json",
        "Accept": "application/json"
    }
});
const api = {
    url, // url: url
    instance: instance,
    get: instance.get,
    post: instance.post,
    put: instance.put,

    delete: instance.delete
}
export  default api;