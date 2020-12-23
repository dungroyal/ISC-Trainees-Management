import React, { Fragment, Component } from "react";
import PropTypes from "prop-types";

class Input extends Component {
  render() {
    const {
      id,
      type,
      name,
      label,
      placeholder,
      lableSize,
      refInput,
      error,
      errorMessage,
      frmField,
      onChange,
    } = this.props;
    const size = lableSize ? lableSize : 0;
    const classLeft = `col-sm-${size} col-form-label ml-3`;
    const classRight = `col-sm-${12 - size} `;
    const inputClass = `form-control ${error ? "is-invalid" : ""}`;

    return (
      <Fragment>
        <div className="form-group row">
          <label htmlFor={id} className={classLeft}>
            {label}
          </label>
          <div className={classRight}>
            <input
              type={type}
              onChange={onChange}
              id={id}
              name={name}
              className={inputClass}
              {...frmField}
              placeholder={placeholder}
              ref={refInput}
            />
            {error ? (
              <div className="invalid-feedback"> {errorMessage}</div>
            ) : null}
          </div>
        </div>
      </Fragment>
    );
  }
}

export default Input;
