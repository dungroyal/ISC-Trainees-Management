import api from './api';

const getAll = () => {
    return api.get(api.url.listUniversity).then(res => res.data);
}
const get = (id) => api.get(`${api.url.getOneUniversity}?id=${id}`).then(res => res.data);
const univerService = {
    getAll, get
};

export default univerService;