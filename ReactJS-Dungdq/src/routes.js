import DefaultLayout from "./Pages/DefaultLayout";
import Student from "./Pages/Student/student";
import Intake from './Pages/Intake/intake';
import Major from './Pages/Major/major';
const routes = [
    { path:"/",exact: true, component: DefaultLayout},
    { path:"/student",exact: false, component: Student},
    { path: "/intake", exact: false, component: Intake },
    { path: "/major", exact: false, component: Major },
];
export default routes;