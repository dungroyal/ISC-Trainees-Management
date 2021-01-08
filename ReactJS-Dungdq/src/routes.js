import DefaultLayout from "./Pages/DefaultLayout";
import Student from "./Pages/Student/student";
import Intake from './Pages/Intake/intake';
import Major from './Pages/Major/major';
import University from './Pages/University/university';
import Company from './Pages/Company/company';
import Room from './Pages/Room/room';
import Clazz from './Pages/Clazz/clazz';
import JobTitle from './Pages/JobtTitle/jobtitle';
import Subject from './Pages/Subject/subject';
const routes = [
    { path:"/",exact: true, component: DefaultLayout},
    { path:"/student",exact: false, component: Student},
    { path: "/intake", exact: false, component: Intake },
    { path: "/major", exact: false, component: Major },
    { path:"/company",exact: false, component: Company},
    { path:"/room",exact: false, component: Room},
    { path:"/university",exact: false, component: University},
    { path:"/clazz",exact: false, component: Clazz},
    { path:"/jobTitle",exact:false,component: JobTitle},
    { path:"/subject",exact:false,component: Subject}
];
export default routes;