import logolight from "./../plugin/images/logo-light.png";

const Header = () => {
    return ( 
        <header id="page-topbar">
      <div className="navbar-header">
        <div className="d-flex">
          {/* LOGO */}
          <div className="navbar-brand-box">
            <a href="index.html" className="logo logo-light">
              <span className="logo-sm">
                <img src="assets/images/isc_logo_icon.png" alt="ISC Quang Trung" height={22} />
              </span>
              <span className="logo-lg">
                <img src={logolight} alt="ISC Quang Trung" height={22} />
              </span>
            </a>
          </div>
          <button type="button" className="btn btn-sm px-3 font-size-16 header-item waves-effect" id="vertical-menu-btn">
            <i className="bx bxs-dock-left font-weight-bold" />
          </button>
          {/* App Search*/}
          <form className="app-search d-none d-lg-block">
            <div className="position-relative">
              <input type="text" className="form-control" placeholder="Tìm kiếm..." />
              <span className="bx bx-search-alt" />
            </div>
          </form>
        </div>
        <div className="d-flex">
          <div className="dropdown d-inline-block d-lg-none ml-2">
            <button type="button" className="btn header-item noti-icon waves-effect" id="page-header-search-dropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <i className="mdi mdi-magnify" />
            </button>
            <div className="dropdown-menu dropdown-menu-lg dropdown-menu-right p-0" aria-labelledby="page-header-search-dropdown">
              <form className="p-3">
                <div className="form-group m-0">
                  <div className="input-group">
                    <input type="text" className="form-control" placeholder="Search ..." aria-label="Recipient's username" />
                    <div className="input-group-append">
                      <button className="btn btn-primary" type="submit"><i className="mdi mdi-magnify" /></button>
                    </div>
                  </div>
                </div>
              </form>
            </div>
          </div>
          <div className="dropdown d-none d-lg-inline-block ml-1">
            <button type="button" className="btn header-item noti-icon waves-effect" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <i className="bx bx-customize" />
            </button>
            <div className="dropdown-menu dropdown-menu-lg dropdown-menu-right">
              <div className="px-lg-2">
                <div className="row no-gutters">
                  <div className="col">
                    <a className="dropdown-icon-item" href="#">
                      <img src="assets\images\house-icon.png" alt="Trang chủ" />
                      <span>Trang chủ</span>
                    </a>
                  </div>
                  <div className="col">
                    <a className="dropdown-icon-item" href="#">
                      <img src="assets\images\recruitment-icon.png" alt="Tuyển dụng" />
                      <span>Tìm ứng viên</span>
                    </a>
                  </div>
                  <div className="col">
                    <a className="dropdown-icon-item" href="#">
                      <img src="assets\images\orders.png" alt="Hỗ trợ" />
                      <span>Bảng giá</span>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="dropdown d-none d-lg-inline-block ml-1">
            <button type="button" className="btn header-item noti-icon waves-effect" data-toggle="fullscreen">
              <i className="bx bx-fullscreen" />
            </button>
          </div>
          <div className="dropdown d-inline-block">
            <button type="button" className="btn header-item noti-icon waves-effect" id="page-header-notifications-dropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <i className="bx bx-bell bx-tada" />
              <span className="badge badge-danger badge-pill">2</span>
            </button>
            <div className="dropdown-menu dropdown-menu-lg dropdown-menu-right p-0" aria-labelledby="page-header-notifications-dropdown">
              <div className="p-3">
                <div className="row align-items-center">
                  <div className="col">
                    <h6 className="m-0"> Thông báo </h6>
                  </div>
                  <div className="col-auto">
                    <a href="#" className="small"> Xem tất cả</a>
                  </div>
                </div>
              </div>
              <div data-simplebar style={{maxHeight: 230}}>
                <a className="text-reset notification-item">
                  <div className="media">
                    <div className="avatar-xs mr-3">
                      <span className="avatar-title bg-primary rounded-circle font-size-16">
                        <i className="bx bx-cart" />
                      </span>
                    </div>
                    <div className="media-body">
                      <h6 className="mt-0 mb-1">Your order is placed</h6>
                      <div className="font-size-12 text-muted">
                        <p className="mb-1">If several languages coalesce the grammar</p>
                        <p className="mb-0"><i className="mdi mdi-clock-outline" /> 3 min ago</p>
                      </div>
                    </div>
                  </div>
                </a>
                <a  className="text-reset notification-item">
                  <div className="media">
                    <img src="assets\images\users\avatar-3.jpg" className="mr-3 rounded-circle avatar-xs" alt="user-pic" />
                    <div className="media-body">
                      <h6 className="mt-0 mb-1">James Lemire</h6>
                      <div className="font-size-12 text-muted">
                        <p className="mb-1">It will seem like simplified English.</p>
                        <p className="mb-0"><i className="mdi mdi-clock-outline" /> 1 hours ago</p>
                      </div>
                    </div>
                  </div>
                </a>
                <a  className="text-reset notification-item">
                  <div className="media">
                    <div className="avatar-xs mr-3">
                      <span className="avatar-title bg-success rounded-circle font-size-16">
                        <i className="bx bx-badge-check" />
                      </span>
                    </div>
                    <div className="media-body">
                      <h6 className="mt-0 mb-1">Your item is shipped</h6>
                      <div className="font-size-12 text-muted">
                        <p className="mb-1">If several languages coalesce the grammar</p>
                        <p className="mb-0"><i className="mdi mdi-clock-outline" /> 3 min ago</p>
                      </div>
                    </div>
                  </div>
                </a>
                <a  className="text-reset notification-item">
                  <div className="media">
                    <img src="assets\images\users\avatar-4.jpg" className="mr-3 rounded-circle avatar-xs" alt="user-pic" />
                    <div className="media-body">
                      <h6 className="mt-0 mb-1">Salena Layfield</h6>
                      <div className="font-size-12 text-muted">
                        <p className="mb-1">As a skeptical Cambridge friend of mine occidental.</p>
                        <p className="mb-0"><i className="mdi mdi-clock-outline" /> 1 hours ago</p>
                      </div>
                    </div>
                  </div>
                </a>
              </div>
              <div className="p-2 border-top">
                <a className="btn btn-sm btn-link font-size-14 btn-block text-center" href="#">
                  <i className="mdi mdi-arrow-right-circle mr-1" /> Xem thêm..
                </a>
              </div>
            </div>
          </div>
          <div className="dropdown d-inline-block">
            <button type="button" className="btn header-item waves-effect" id="page-header-user-dropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <img className="rounded-circle header-profile-user" src="https://ui-avatars.com/api/?background=0D8ABC&color=fff&bold=true&name=Đoàn Quốc Dũng" alt="Đoàn Quốc Dũng" />
              <span className="d-none d-xl-inline-block ml-1">Đoàn Quốc Dũng</span>
              <i className="mdi mdi-chevron-down d-none d-xl-inline-block" />
            </button>
            <div className="dropdown-menu dropdown-menu-right">
              {/* item*/}
              <a className="dropdown-item" href="#"><i className="bx bx-user font-size-16 align-middle mr-1" /> Hồ sơ công ty</a>
              <a className="dropdown-item d-block" href="#"><span className="badge badge-success float-right">5</span><i className="bx bx-wrench font-size-16 align-middle mr-1" /> Cài đặt</a>
              <a className="dropdown-item" href="#"><i className="bx bx-lock-open font-size-16 align-middle mr-1" /> Khóa màn hình</a>
              <div className="dropdown-divider" />
              <a className="dropdown-item text-danger" href="#"><i className="bx bx-power-off font-size-16 align-middle mr-1 text-danger" /> Đăng xuất</a>
            </div>
          </div>
        </div>
      </div>
    </header>

     );
}
 
export default Header;