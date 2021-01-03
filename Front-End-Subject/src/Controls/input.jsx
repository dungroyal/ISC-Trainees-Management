import React,{ Fragment,Component} from 'react';
import PropTypes from 'prop-types';

class Input extends Component {
    render(){
        const {id,type, name, label, placeholder, lableSize, error, errorMessage, frmField,onChange,...others} = this.props;
        const size = lableSize ? lableSize: 3;
        const classLeft = `col-sm-${size} col-form-label`;
        const classRight = `col-sm-${ 12 - size} `;
        const inputClass = `form-control ${error? "is-invalid": ""}`
        
        return (
            <Fragment>
                <div className="col-md-12">
                        <div className="form-group row">
                        <label htmlFor={id} >{label}</label>
                            {others["rows"] > 1 ?(
                                <textarea id={id} {...others} className={inputClass} {...frmField}></textarea>
                            ):(
                                <input type={type} onChange={onChange} id={id} name={name} className={inputClass} {...frmField} placeholder={placeholder} />  
                            )}
                            {error?<div className="invalid-feedback" >{errorMessage}</div>: null}
                    </div>
                </div>
            </Fragment>
        );
    }
    
}

export default Input;