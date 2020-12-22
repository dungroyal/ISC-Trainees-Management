import api from './api';

const getAll = () => {return api.get(api.url.listIntake).then(res => res.data);
}
const get = (id) => api.get(`${api.url.getOneIntake}?id=${id}`).then(res => res.data);

const intakeService = {
    getAll, get
};

export default intakeService;