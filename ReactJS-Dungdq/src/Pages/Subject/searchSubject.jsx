import React, { useState, useRef } from "react";
import PropTypes from 'prop-types';

SearchSubject.propTypes = {
  onSubmit: PropTypes.func,
};

SearchSubject.defaultProps = {
  onSubmit: null,
}

function SearchSubject(props) {
  const {onSubmit} = props;
  const [searchTerm, setSearchTerm] = useState("");
  const typingTimeoutRef = useRef(null);
  function handleSearchTermChange(e) {
    const value = e.target.value;
    setSearchTerm(value);
    
    if(!onSubmit) return;

    if(typingTimeoutRef.current){
      clearTimeout(typingTimeoutRef.current);
    };

    typingTimeoutRef.current = setTimeout(()=>{
      const formValues = {
        searchTerm: value,
      };
      onSubmit(formValues);
    }, 500);
  }
  return(
      <form>
        <input 
        type="search" 
        value={searchTerm}
        $$={console.log("search",searchTerm)} 
        onChange={handleSearchTermChange} 
        className="form-control" 
        placeholder="Search..." />
      </form>
  )
}

export default SearchSubject;