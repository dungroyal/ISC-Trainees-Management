import React, { useState, useEffect } from "react";
import MultiSelect from "react-multi-select-component";
import univerService from "src/services/universityService";

const MultiSelectCheckboxUniversity = () => {
  const options = [];
  const label = "University:";
  const id = "";
  const labelSize = "";
  const size = labelSize ? labelSize : 3;
  const classLeft = `col-sm-${size} col-form-label text-center`;
  const classRight = `col-sm-${12 - size}`;

  const [selected, setSelected] = useState([]);
  const [univers, setuniver] = useState([]);
  // Load Data
  const loadData = () => {
    univerService.getAll().then((res) => {
      if (res.status === 0) {
        setuniver(res.data);
      }
    });
  };

  //Didmount load data Univer
  useEffect(() => {
    loadData();
  }, []);

  return (
    <div className="form-group row">
      <label htmlFor={id} className={classLeft}>
        {label}
      </label>
      {/* <h1>Select Fruits</h1> */}
      {/* <pre>{JSON.stringify(selected)}</pre> */}
      <div className={classRight}>
        <MultiSelect
          options={univers.map((e) => ({
            label: e.nameUni,
            value: e.id,
          }))}
          value={selected}
          onChange={setSelected}
          labelledBy={"Select"}
        />
      </div>
    </div>
  );
};

export default MultiSelectCheckboxUniversity;
