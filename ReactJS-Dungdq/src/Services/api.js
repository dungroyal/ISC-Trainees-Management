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
    paginationStudent: "/student/pagination",
    searchStudent: "/student/searchStudent",

    //Intake
    listIntake: "/intake/listIntake",
    addIntake: "/intake/newIntake",
    getOneIntake: "/intake/getIntake",
    updateIntake: "/intake/editIntake",
    deleteIntake: "/intake/deleteIntake",
    paginationIntake: "/intake/pagination",
    searchStudent: "/student/searchIntake",
  
    //Major
    listMajor: "/major/listMajor",
    addMajor: "/major/newMajor",
    getOneMajor: "/major/getMajor",
    updateMajor: "/major/editMajor",
    deleteMajor: "/major/deleteMajor",
    paginationMajor: "/major/pagination",
    searchMajor: "/major/searchMajor",

    //Student - Intake
    listStudentIntake: "/student-intake/allStudentIntake",
    getOneStudentIntake:"/student-intake/getIntakeOfStu",
    addStudentIntake:"/student-intake/postIntakeOfStu",
    updateIntakeOfStuArray:"/student-intake/updateIntakeOfStuArray",
    updateIntakeOfStu:"/student-intake/updateIntakeOfStu",
    deleteIntakeOfStu: "/student-intake/deleteIntakeOfStu",

    //university
    listUniversity: "/university/listUniversity",
    getUniversity: "/university/getUniversity",
    newUniversity: "/university/newUniversity",
    editUniversity: "/university/editUniversity",
    deleteUniversity: "/university/deleteUniversity",
    paginationUniversity: "/university/pagination",
    
    //company
    listCompany: "/company/listCompany",
    getCompany: "/company/getCompany",
    newCompany: "/company/newCompany",
    editCompany: "/company/editCompany",
    deleteCompany: "/company/deleteCompany",
    paginationCompany: "/company/pagination",

    //room
    listRoom: "/room/listRoom",
    getRoom: "/room/getRoom",
    newRoom: "/room/newRoom",
    editRoom: "/room/editRoom",
    deleteRoom: "/room/deleteRoom",
    paginationRoom: "/room/pagination",

    //clazz
    listClazz: "/clazz/listClazz",
    getClazz: "/clazz/getClazz",
    newClazz: "/clazz/newClazz",
    editClazz: "/clazz/editClazz",
    deleteClazz: "/clazz/deleteClazz",
    paginationClazz: "/clazz/pagination",

    //Subject
    paginationSubject : "/subject/pagination",
    searchSubject : "/subject/searchSubject",

    //jobTitle
    listJobTitle: "/jobTitle/listJobTitle",
    getJobTitle: "/jobTitle/getJobTitle",
    newJobTitle: "/jobTitle/newJobTitle",
    editJobTitle: "/jobTitle/editJobTitle",
    deleteJobTitle: "/jobTitle/deleteJobTitle",
    paginationJobTitle: "/jobTitle/pagination",

    //Lecturer
    paginationLecturer: "/lecturer/pagination",
    newLecturer: "/lecturer/newLecturer",
    getLecturer: "/lecturer/getLecturer",
    updateLecturerNotImage: "/lecturer/editLecturerNotImg", 
    updateLecturerHasImage: "/lecturer/editLecturerImg",
    deleteLecturer: "/lecturer/deleteLecturer",
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
    paginationStudent: url.paginationStudent,
    searchStudent: url.searchStudent,
    //Univer
    listUniversity: url.listUniversity,
    getOneUniversity: url.getOneUniversity,
    paginationUniversity: url.paginationUniversity,
    //Company
    listCompany: url.listCompany,
    getOneCompany: url.getOneCompany,
    //Intake
    listIntake: url.listIntake,
    getOneIntake: url.getOnIntake,
    addIntake: url.addIntake,
    //Stu_Company
    addStudentCompany: url.addStudentCompany,
    //Subject
    paginationSubject: url.paginationSubject,
    searchSubject: url.searchSubject,
    //Stu_Intake
    listStudentIntake: url.listStudentIntake,
    addStudentIntake: url.addStudentIntake,
    getOneStudentIntake: url.getOneStudentIntake,
    updateIntakeOfStuArray: url.updateIntakeOfStuArray,
    updateIntakeOfStu: url.updateIntakeOfStu,
    deleteIntakeOfStu: url.deleteIntakeOfStu,
    //Lecturer
    paginationLecturer: url.paginationLecturer,
    newLecturer: url.newLecturer,
    getLecturer: url.getLecturer,
    updateLecturerNotImage: url.updateLecturerNotImage,
    updateLecturerHasImage: url.updateLecturerHasImage,
    deleteLecturer: url.deleteLecturer,

    //Header
    headers: {
        "Content-Type" :"multipart/form-data",
        Accept: "application/json",
    }
});

// instance.interceptors.request.use((request) => {
//     const state = store.getState();
//     if (state.auth.token) {
//       request.headers.Authorization = `Bearer ${state.auth.token}`;
//     }
//     return request;
//   });

const api = {
    url,
    instance: instance,
    get: instance.get,
    post: instance.post,
    put: instance.put,

    delete: instance.delete
}
export default api;