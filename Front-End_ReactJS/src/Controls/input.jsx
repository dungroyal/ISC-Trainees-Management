import React, { Component, Fragment} from "react";

class CustomInput extends Component {
  state = {};
  render() {
    const {
      typeInput, //1: Vertical(Dọc), 2: Horizontal(Ngan)
      onChange,
      value,
      type,
      id,
      label,
      placeHolder,
      labelSize,
      column, // Thêm cột Column BootStrap VD: column="6"->col-sm-6
      inputRef,
      frmField,
      err,
      errMessage,
      ...others
    } = this.props;

    const typeinput = typeInput ? typeInput : "1";
    const size = labelSize ? labelSize : 3;
    const classLeft = `col-sm-${size} col-form-label text-center`;
    const ColumnClass = `col-sm-${column}`;
    const classRight = `col-sm-${12 - size}`;
    const inputClass = `form-control  ${err ? "is-invalid" : ""}`;

    return (
      <Fragment>
            {(() => {
                switch(typeinput) {
                  case "1":
                    return(
                      <div className={ColumnClass}>
                        <div className="form-group">
                          <label htmlFor="formrow-email-input">{label}</label>
                          {others["rows"] == 1 ? (
                            <input
                              onChange={onChange}
                              value={value}
                              ref={inputRef}
                              type={type}
                              className={inputClass}
                              id={id}
                              placeholder={placeHolder}
                              {...frmField}
                              {...others}
                            />
                          ) : (
                            <textarea
                              ref={inputRef}
                              className={inputClass}
                              id={id}
                              type={type}
                              placeholder={placeHolder}
                              {...frmField}
                              {...others}
                            ></textarea>
                          )}
                          {err ? <div className="invalid-feedback">{errMessage}</div> : null}
                        </div>
                      </div>
                    )
                  break;

                  case "2":
                    return(
                      <div className={ColumnClass}>
                        <div className="form-group row">
                          <label htmlFor={id} className={classLeft}>
                            {label}
                          </label>
                          <div className={classRight}>
                            {others["rows"] == 1 ? (
                              <input
                                onChange={onChange}
                                value={value}
                                ref={inputRef}
                                type={type}
                                className={inputClass}
                                id={id}
                                placeholder={placeHolder}
                                {...frmField}
                                {...others}
                              />
                            ) : (
                              <textarea
                                ref={inputRef}
                                className={inputClass}
                                id={id}
                                type={type}
                                placeholder={placeHolder}
                                {...frmField}
                                {...others}
                              ></textarea>
                            )}
                            {err ? <div className="invalid-feedback">{errMessage}</div> : null}
                          </div>
                        </div>
                      </div>
                    )
                  break;
                }
            })()}
        </Fragment>
    );
  }
}
export default CustomInput;
