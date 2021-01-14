import React,{ Fragment,Component} from 'react';
import PropTypes from 'prop-types';

class Input extends Component {
    render(){
        const {id,type, name, label, placeholder, lableSize, refInput,error, errorMessage, frmField,onChange} = this.props;
        const size = lableSize ? lableSize: 3;
        const classLeft = `col-sm-${size} col-form-label`;
        const clasRight = `col-sm-${ 12 - size} `;
        const inputClass = `form-control ${error? "is-invalid": ""}`
        
        return (
            <Fragment>
                <div className="col-md-6">
                        <div className="form-group">
                        <label htmlFor={id} >{label}</label>
                        <div >
                            <input type={type} onChange={onChange} id={id} name={name} className={inputClass} {...frmField} placeholder={placeholder} ref={refInput}/>
                            {error?<div className="invalid-feedback" ></div>: null}
                        </div>
                    </div>
                </div>
            </Fragment>
        );
    }
    
}

export default Input;