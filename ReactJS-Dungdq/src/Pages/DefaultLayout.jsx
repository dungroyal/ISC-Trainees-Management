import { Fragment } from "react";
import Footer from "../Containers/Footer";
import Header from "../Containers/Header";
import Sidebar from "../Containers/Sidebar";
import Body from "../Containers/Body";

const DefaultLayout = () => {
    return ( 
        <Fragment>
            <Header/>
            <Sidebar/>
            <Body/>
            <Footer/>
        </Fragment>
     );
}
 
export default DefaultLayout;