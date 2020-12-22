import api from './api';

const getAll = () => {return api.get(api.url.listCompany).then(res => res.data);
}
const get = (id) => api.get(`${api.url.getOneCompany}?id=${id}`).then(res => res.data);

const companyService = {
    getAll, get
};

export default companyService;