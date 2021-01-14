import api from './api';

const add = (_stuId, _companyId = []) => api.post(`${api.url.addStudentCompany}?studentId=${_stuId}&companyId=${_companyId}`).then(res => res.data);

const studentCompanyService = {
   add
};

export default studentCompanyService;