import React, { useState } from "react";
import MultiSelect from "react-multi-select-component";

const MultiSelectCheckboxWorkingStatus = () => {
  const options = [
    { label: "Active", value: 0 },
    { label: "Inactive", value: 1 },
    // { label: "Watermelon 🍉", value: "watermelon" },
    // { label: "Pear 🍐", value: "pear" },
    // { label: "Apple 🍎", value: "apple" },
    // { label: "Tangerine 🍊", value: "tangerine" },
    // { label: "Pineapple 🍍", value: "pineapple" },
    // { label: "Peach 🍑", value: "peach" },
  ];
  const label = "Working Status:";
  const id = "";
  const labelSize = "";
  const size = labelSize ? labelSize : 3;
  const classLeft = `col-sm-${size} col-form-label text-center`;
  const classRight = `col-sm-${12 - size}`;
  //   options.forEach((e) => console.log(e));
  const [selected, setSelected] = useState([]);

  return (
    <div className="form-group row">
      <label htmlFor={id} className={classLeft}>
        {label}
      </label>
      {/* <h1>Select Fruits</h1> */}
      {/* <pre>{JSON.stringify(selected)}</pre> */}
      <div className={classRight}>
        <MultiSelect
          options={options}
          value={selected}
          onChange={setSelected}
          labelledBy={"Select"}
        />
      </div>
    </div>
  );
};

export default MultiSelectCheckboxWorkingStatus;
