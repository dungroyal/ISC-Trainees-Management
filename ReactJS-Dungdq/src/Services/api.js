import axios from 'axios';

const url = {
    image: "http://localhost:8080/api/file/downloadFile/",

    baseURL : "http://localhost:8080/api",
    //Student
    listStudent: "/student/allStudent",
    addStudent: "/student/newStudent",
    getOneStudent: "/student/oneStudent",
    updateStudentHasImg: "/student/updateStudentImg",
    updateStudentNoImg: "/student/updateStudentNotImg",
    deleteStudent: "/student/deleteStudent",
    //University
    listUniversity: "/university/listUniversity",
    getOneUniversity: "/university/getUniversity",
    //Company
    listCompany: "/company/listCompany",
    getOneCompany: "/company/getCompany",
    //Intake
    listIntake: "/intake/listIntake",
    getOnIntake:"/intake/getIntake",

     //Student - Intake
     listStudentIntake: "/student-intake/allStudentIntake",
     getOneStudentIntake:"/student-intake/getIntakeOfStu",
     addStudentIntake:"/student-intake/postIntakeOfStu",
     updateIntakeOfStuArray:"/student-intake/updateIntakeOfStuArray",
     updateIntakeOfStu:"/student-intake/updateIntakeOfStu",
     deleteIntakeOfStu: "/student-intake/deleteIntakeOfStu"
}
const instance = axios.create({
    baseURL : url.baseURL,
    //Student
    listStudent: url.listStudent,
    addStudent: url.addStudent,
    getOneStudent: url.getOneStudent,
    image: url.image,
    updateStuImg: url.updateStuImg,
    deleteStudent: url.deleteStudent,
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
    updateIntakeOfStuArray: url.updateIntakeOfStuArray,
    updateIntakeOfStu: url.updateIntakeOfStu,
    deleteIntakeOfStu: url.deleteIntakeOfStu,
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