import React from "react";
import PropTypes from "prop-types";

Pagination.propTypes = {
  pagination: PropTypes.object.isRequired,
  onPageChange: PropTypes.func,
};

Pagination.defaultProps = {
  onPageChange: null,
};

function Pagination(props) {
  const { pagination, onPageChange } = props;
  const { page, size, totalRows } = pagination;
  const totalPage = Math.ceil(totalRows / size);

  function handlePageChange(newPage) {
    if (onPageChange) {
      onPageChange(newPage);
    }
  }
  return (
    <div>
      <ul className="pagination pagination-rounded justify-content-end mb-2">
        <div className="mt-1 mr-2 mr-auto">
          Total <strong>{totalRows}</strong> Major
        </div>
        {page == 0 ? (
          <li className="page-item disabled">
            <a
              className="page-link"
              href="javascript: void(0);"
              onClick={() => handlePageChange(page - 1)}
            >
              <i class="fas fa-chevron-right fa-flip-horizontal"></i>
            </a>
          </li>
        ) : (
          <li className="page-item ">
            <a
              className="page-link"
              href="javascript: void(0);"
              onClick={() => handlePageChange(page - 1)}
            >
              <i class="fas fa-chevron-right fa-flip-horizontal"></i>
            </a>
          </li>
        )}

        <li className="page-item py-1 px-2 active text-dark">
          Trang {page + 1}/{totalPage}
        </li>

        {page == totalPage - 1 ? (
          <li className="page-item disabled">
            <a
              className="page-link"
              href="javascript: void(0);"
              onClick={() => handlePageChange(page + 1)}
            >
              <i class="fas fa-chevron-right"></i>
            </a>
          </li>
        ) : (
          <li className="page-item">
            <a
              className="page-link"
              href="javascript: void(0);"
              onClick={() => handlePageChange(page + 1)}
            >
              <i class="fas fa-chevron-right"></i>
            </a>
          </li>
        )}
      </ul>
    </div>
  );
}

export default Pagination;
