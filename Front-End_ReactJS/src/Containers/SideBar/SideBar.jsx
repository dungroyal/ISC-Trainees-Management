import {React, Fragment} from 'react';
import { Link } from "react-router-dom";
import { ProSidebar, Menu, MenuItem, SubMenu } from 'react-pro-sidebar';
import { FaTachometerAlt, FaGem, FaList , FaRegLaughWink, FaHeart } from 'react-icons/fa';

import './SideBar.css';

const SideBar = ({collapsed, toggled, handleToggleSidebar }) => {
  return (
    <Fragment>
    <div className="vertical-menu">
        <div data-simplebar className="h-100">
          <ProSidebar
            collapsed={collapsed}
            toggled={toggled}
            onToggle={handleToggleSidebar}
          >
          <Menu iconShape="circle">
            <MenuItem icon={<FaTachometerAlt />}>Dashboard <Link to="/dashboard"/></MenuItem>
            <SubMenu title="Student manage" icon={<FaRegLaughWink />}>
              <MenuItem>Student <Link to="/student" /> </MenuItem>
              <MenuItem>University <Link to="/university" /></MenuItem>
              <MenuItem>Companies <Link to="/company" /></MenuItem>
            </SubMenu>
            <SubMenu title="Training manage" icon={<FaList />}>
              <MenuItem>Intakes <Link to="/intake"/></MenuItem>
              <MenuItem>Majors <Link to="/major"/></MenuItem>
              <MenuItem>Subjects <Link to="/subject" /></MenuItem>
              <MenuItem>Lecturers <Link to="/lecturer" /></MenuItem>
              <MenuItem>Rooms <Link to="/room" /></MenuItem>
            </SubMenu>
            <SubMenu title="Account manage" icon={<FaGem />}>
              <MenuItem>Account</MenuItem>
            </SubMenu>
          </Menu>
          </ProSidebar>
        </div>
      </div>
    </Fragment>
  );
};

export default SideBar;