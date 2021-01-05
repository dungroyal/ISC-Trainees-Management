import { Fragment, useState } from "react";
import Footer from "../Containers/Footer";
import Header from "../Containers/Header";
import Body from "../Containers/Body";
import SideBar from '../Containers/SideBar/SideBar';

const DefaultLayout = () => {
  const [collapsed, setCollapsed] = useState(false);
  const [image, setImage] = useState(true);
  const [toggled, setToggled] = useState(false);
  const handleToggleSidebar = (value) => {
    setToggled(value);
  };
    return ( 
        <Fragment>
            <Header/>
            <SideBar
                image={image}
                collapsed={collapsed}
                toggled={toggled}
                handleToggleSidebar={handleToggleSidebar}
            />
            <Body/>
            {/* <Footer/> */}
        </Fragment>
     );
}
 
export default DefaultLayout;