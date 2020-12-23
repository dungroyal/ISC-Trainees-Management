import { Link } from "react-router-dom";

const Sidebar = () => {
    return ( 
       <div className="vertical-menu">
  <div data-simplebar className="h-100">
    <div id="sidebar-menu">
      <ul className="metismenu list-unstyled" id="side-menu">
        <li className="menu-title text-center text-white-50">Trang quản lý</li>
        <li>
          <Link to="#" className="waves-effect">
            <i className="bx bxs-dashboard" />
            <span>Quản lý chung</span>
          </Link>
        </li>
        <li>
          <Link to="#" className="has-arrow waves-effect">
            <i className="bx bx-briefcase" />
            <span>Quản lý học viên</span>
          </Link>
          <ul className="sub-menu" aria-expanded="false">
            <li><Link to="/student">Học viên</Link></li>
            <li><Link to="/university">Trường</Link></li>
            <li><Link to="/company">Công ty</Link></li>
          </ul>
        </li>
        <li>
          <Link to="#" className="has-arrow waves-effect">
            <i className="bx bx-user-pin" />
            <span>Quản lý đào tạo</span>
          </Link>
          <ul className="sub-menu" aria-expanded="false">
            <li><Link to="/intake">Khóa Học</Link></li>
            <li><Link to="/major">Ngành học</Link></li>
            <li><Link to="/subject">Môn học</Link></li>
            <li><Link to="/lecturer">Giảng viên</Link></li>
            <li><Link to="/room">Phòng học</Link></li>
          </ul>
        </li>
        <li>
          <Link to="#" className="has-arrow waves-effect">
            <i className="bx bx-info-circle" />
            <span>Quản lý tài khoản</span>
          </Link>
          <ul className="sub-menu" aria-expanded="false">
            <li><Link to="#">Cập nhật thông tin</Link></li>
            <li><Link to="#">Đổi mật khẩu</Link></li>
          </ul>
        </li>
      </ul>
    </div>
    {/* Sidebar */}
  </div>
</div>

     );
}
 
export default Sidebar;