import React, { Component } from "react";

class Input extends Component {
  state = {};
  render() {
    const {
      onChange,
      value,
      type,
      id,
      label,
      placeHolder,
      labelSize,
      inputRef,
      frmField,
      err,
      errMessage,
      ...others
    } = this.props;
    const size = labelSize ? labelSize : 3;
    const classLeft = `col-sm-${size} col-form-label text-center`;
    const classRight = `col-sm-${12 - size}`;
    // const numRows = rows ? rows : 1;
    const inputClass = `form-control ${err ? "is-invalid" : ""}`;
    return (
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
    );
  }
}

export default Input;
