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
            <MenuItem icon={<FaTachometerAlt />}>Dashboard</MenuItem>
            <SubMenu title="Student manage" icon={<FaRegLaughWink />}>
              <MenuItem>Student <Link to="/student" /> </MenuItem>
              <MenuItem>University</MenuItem>
              <MenuItem>Companies</MenuItem>
            </SubMenu>
            <SubMenu title="Training manage" icon={<FaList />}>
              <MenuItem>Intakes <Link to="/intake"/></MenuItem>
              <MenuItem>Majors <Link to="/major"/></MenuItem>
              <MenuItem>Subjects</MenuItem>
              <MenuItem>Lecturers</MenuItem>
              <MenuItem>Rooms</MenuItem>
            </SubMenu>
            <SubMenu title="Account manage" icon={<FaGem />}>
              <MenuItem>Account Admin</MenuItem>
              <MenuItem>Account Lecturers</MenuItem>
            </SubMenu>
          </Menu>
          </ProSidebar>
        </div>
      </div>
    </Fragment>
  );
};

export default SideBar;