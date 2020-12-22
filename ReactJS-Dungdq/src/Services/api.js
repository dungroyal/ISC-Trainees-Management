import axios from 'axios';

const url = {
    image: "http://localhost:8080/api/file/downloadFile/",

    baseURL : "http://localhost:8080/api",
    //Student
    listStudent: "/student/allStudent",
    addStudent: "/student/newStudent",
    getOneStudent: "/student/oneStudent",
    updateStuImg: "/student/updateStudentImg",
}
const instance = axios.create({
    baseURL : url.baseURL,
    //Student
    listStudent: url.listStudent,
    addStudent: url.addStudent,
    getOneStudent: url.getOneStudent,
    image: url.image,
    updateStuImg: url.updateStuImg,
    //Univer
    listUniversity: url.listUniversity,
    getOneUniversity: url.getOneUniversity,
    //Company
    listCompany: url.listCompany,
    getOneCompany: url.getOneCompany,
    //Intake
    listIntake: url.listIntake,
    getOneIntake: url.getOnIntake,
    //Stu_Company
    addStudentCompany: url.addStudentCompany,
    //Stu_Intake
    addStudentIntake: url.addStudentIntake,
    getOneStudentIntake: url.getOneStudentIntake,
    headers: {
        "Content-Type" :"multipart/form-data",
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
export default api;