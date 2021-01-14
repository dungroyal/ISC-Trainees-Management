import React, { Fragment } from "react";

function Pagination(props) {
  const { pagination, onPageChange } = props;
  const { pageP, sizeP, totalElementsP } = pagination;
  const totalPage = Math.ceil(totalElementsP / sizeP);
  const handlePagination = (newPage) => {
    onPageChange(newPage);
  };
  const showPage = (numberPage) => {
    var result = [];
    for (var i = 0; i < numberPage; i++) {
      result.push(i + 1);
    }
    return result;
  };
  return (
    <ul className="pagination pagination-rounded justify-content-end mb-2">
        {pageP < 1 ?(
          <li className="page-item disabled">
          <a className="page-link" href="javascript: void(0);" onClick={() => handlePagination(pageP - 1)}>
            <i class="fas fa-chevron-right fa-flip-horizontal"></i>
          </a>
        </li>
        ):(
          <li className="page-item ">
          <a className="page-link" href="javascript: void(0);" onClick={() => handlePagination(pageP - 1)}>
            <i class="fas fa-chevron-right fa-flip-horizontal"></i>
          </a>
        </li>
        )}

         {showPage(totalPage).map((item, index) => {
            return (
              <Fragment>
                {pageP == index ? (
                  <li className="page-item active" onClick={() => handlePagination(index)}>
                    <a className="page-link" href="javascript: void(0);">{item}</a>
                  </li>
                ):(
                  <li className="page-item" onClick={() => handlePagination(index)}>
                    <a className="page-link" href="javascript: void(0);">{item}</a>
                  </li>
                )}
              </Fragment>
            );
          })}

        {pageP >= totalPage - 1 ?(
          <li className="page-item disabled">
            <a className="page-link" href="javascript: void(0);" onClick={() => handlePagination(pageP + 1)}>
              <i class="fas fa-chevron-right"></i>
            </a>
          </li>
        ):(
          <li className="page-item">
            <a className="page-link" href="javascript: void(0);" onClick={() => handlePagination(pageP + 1)}>
              <i class="fas fa-chevron-right"></i>
            </a>
          </li>
        )}
    </ul>
  );
}

export default Pagination;
