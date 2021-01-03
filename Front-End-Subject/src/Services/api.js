import axios from 'axios';


const url = {
    baseURL : "http://localhost:8080/api",
    subject : "/subject",
    paginationSubject : "/subject/pagination",
    searchSubject : "/subject/searchSubject"
    
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
    instance,
    get: instance.get,
    post: instance.post,
    put: instance.put,
    delete: instance.delete
}
export  default api;