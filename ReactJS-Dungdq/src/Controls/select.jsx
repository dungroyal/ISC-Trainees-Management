import React, { Component, Fragment } from "react";
class select extends Component {
  state = {};
  render() {
    const {
      type,
      id,
      name,
      label,
      labelSize,
      placeHolder,
      readOnly,
      column,
      err,
      autofocus,
      inputRef,
      errMessage,
      frmField,
      ...others
    } = this.props;
    const typee = type ? type : "1";
    const size = labelSize ? labelSize : 4;
    const classLeft = `col-sm-${size} col-form-label text-center`;
    const ColumnClass = `col-sm-${column}`;
    const classRight = `col-sm-${12 - size} `;
    //const numRows = rows ? rows : 1;
    const selectClass = `form-control ${err ? "is-invalid" : ""}`;
    return (
      <Fragment>
        {(() => {
          switch (typee) {
            case "1":
              return (
                <div className={ColumnClass}>
                  <div className="form-group">
                    <label htmlFor="formrow-email-input">{label}</label>
                    {others["rows"] == 1 ? (
                      <textarea
                        ref={inputRef}
                        type={type}
                        className={selectClass}
                        {...others}
                        {...frmField}
                        //id={id}
                        //name={name}
                        //placeholder={placeHolder}
                        //readOnly={readOnly}
                      />
                    ) : (
                      <select
                        ref={inputRef}
                        type={type}
                        className={selectClass}
                        {...others}
                        {...frmField}
                      ></select>
                    )}
                    {err ? (
                      <div className="invalid-feedback">{errMessage}</div>
                    ) : null}
                  </div>
                </div>
              );
              break;

            case "2":
              return (
                <div className={ColumnClass}>
                  <div className="form-group row">
                    <label htmlFor={id} className={classLeft}>
                      {label}
                    </label>
                    <div className={classRight}>
                      {others["rows"] == 1 ? (
                        <textarea
                          ref={inputRef}
                          type={type}
                          className={selectClass}
                          {...others}
                          {...frmField}
                          //id={id}
                          //name={name}
                          //placeholder={placeHolder}
                          //readOnly={readOnly}
                        />
                      ) : (
                        <select
                          ref={inputRef}
                          type={type}
                          className={selectClass}
                          {...others}
                          {...frmField}
                        ></select>
                      )}
                      {err ? (
                        <div className="invalid-feedback">{errMessage}</div>
                      ) : null}
                    </div>
                  </div>
                </div>
              );
              break;
          }
        })()}
      </Fragment>
    );
  }
}

export default select;
