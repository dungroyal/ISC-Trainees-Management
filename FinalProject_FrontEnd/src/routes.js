import Company from "./Pages/Company/company";
import DefaultLayout from "./Pages/DefaultLayout";
import Intake from "./Pages/Intake/intake";
import Lecturer from "./Pages/Lecturer/lecturer";
import Major from "./Pages/Major/major";
import Room from "./Pages/Room/room";
import Student from "./Pages/Student/student";
import Subject from "./Pages/Subject/subject";
import University from "./Pages/University/university";
const routes = [
    {   path:"/",exact: true, component: DefaultLayout},
    {   path:"/company",exact: false, component: Company},
    {   path:"/intake",exact: false, component: Intake},
    {   path:"/lecturer",exact: false, component: Lecturer},
    {   path:"/major",exact: false, component: Major},
    {   path:"/room",exact: false, component: Room},
    {   path:"/student",exact: false, component: Student},
    {   path:"/subject",exact: false, component: Subject},
    {   path:"/university",exact: false, component: University},

];
export default routes;