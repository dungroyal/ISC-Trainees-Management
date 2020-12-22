import DefaultLayout from "./Pages/DefaultLayout";
import Student from "./Pages/Student/student";
const routes = [
    {   path:"/",exact: true, component: DefaultLayout},
    {   path:"/student",exact: false, component: Student},
];
export default routes;