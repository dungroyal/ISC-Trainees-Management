import api from './api';

const add = (_stuId, _intakeId) => api.post(`${api.url.addStudentIntake}?studentId=${_stuId}&intakeId=${_intakeId}`).then(res => res.data);
const get = (_stuId2) => api.get(`${api.url.getOneStudentIntake}?studentId=${_stuId2}`).then(res => res.data);
const remove = (_studentId,_intakeId) => api.delete(`${api.url.deleteIntakeOfStu}?studentId=${_studentId}&intakeId=${_intakeId}`).then(res => res.data);
const updateIntakeOfStuArray = (_newIntakeId,_studentId,_intakeId) => api.put(`${api.url.updateIntakeOfStuArray}?newIntakeId=${_newIntakeId}&studentId=${_studentId}&intakeId=${_intakeId}`).then(res => res.data);
const studentIntakeService = {
   add, get, updateIntakeOfStuArray,remove
};

export default studentIntakeService;