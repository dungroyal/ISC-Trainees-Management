import React, { useState } from "react";
import MultiSelect from "react-multi-select-component";

const MultiSelectCheckboxTypeStu = (props) => {
  const options = [
    { label: "Studying", value: 0 },
    { label: "Graduate", value: 1 },
    { label: "Reserve ", value: 2 },
    // { label: "Watermelon 🍉", value: "watermelon" },
    // { label: "Pear 🍐", value: "pear" },
    // { label: "Apple 🍎", value: "apple" },
    // { label: "Tangerine 🍊", value: "tangerine" },
    // { label: "Pineapple 🍍", value: "pineapple" },
    // { label: "Peach 🍑", value: "peach" },
  ];
  const label = "Type Student";
  const id = "";
  const labelSize = "";
  const size = labelSize ? labelSize : 3;
  const classLeft = `col-sm-${size} col-form-label text-center`;
  const classRight = `col-sm-${12 - size}`;

  const [selected, setSelected] = useState([]);
  // handle onChange event of the dropdown
  const handleChange = (e) => {
    setSelected(Array.isArray(e) ? e.map((x) => x.value) : []);
  };
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
          value={options.filter((obj) => selected.includes(obj.value))} // set selected values
          onChange={handleChange}
          labelledBy={"Select"}
        />
        {/* {selected && (
          <div style={{ marginTop: 20, lineHeight: "25px" }}>
            <div>
              <b>Selected Value: </b> {JSON.stringify(selected, null, 2)}
            </div>
          </div>
        )} */}
      </div>
    </div>
  );
};

export default MultiSelectCheckboxTypeStu;
