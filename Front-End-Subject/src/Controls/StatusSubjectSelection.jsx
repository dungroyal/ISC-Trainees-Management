import React, { Component } from 'react';
import { Fragment } from 'react';
import Select from 'react-select';

const options = [
    {value:'Active',label:'Active'},
    {value:'Inactive',label:'Inactive'}
]

class StatusSubjectSelection extends Component {

    render() {
        const {id,label,lableSize, ...others} = this.props;
        const size = lableSize ? lableSize: 2;
        const classLeft = `col-sm-${size} col-form-label`;
        const classRight = `col-sm-${ 12 - size} `;   
        return ( 
            <Fragment>
                    <div className="col-md-6">
                        <div className="form-group row">
                        <label htmlFor={id} >{label}</label>
                        <div className={classRight} >
                            <Select 
                            defaultValue={options[0]}
                            options={options} />
                        </div>
                        </div>
                    </div>
                {/* <div className="col-md-6">
                    <div className="form-group row">
                    <label className={classLeft} htmlFor={id}>{label}</label>
                    <div className={classRight}>
                        <Select 
                        defaultValue={options[0]}
                        options={options} />
                    </div>
                    </div>
                </div> */}
            </Fragment>
            
        );
    }
}
 
export default StatusSubjectSelection;