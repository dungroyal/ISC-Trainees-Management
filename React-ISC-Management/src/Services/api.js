// import axios from 'axios';


const url = {
    baseURL : "https:8080//www.saigontech.edu.vn/restful-api",

}
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