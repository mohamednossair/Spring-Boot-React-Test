import React from "react";

const Input = (props) => {
  let InputClassName = "form-control";

  if (props.hasError !== undefined) {
    InputClassName += props.hasError ? " is-invalid" : " is-valid";
  }

  return (
    <div>
      {props.label && <label>{props.label}</label>}
      <input
        className={InputClassName}
        type={props.type || "text"}
        placeholder={props.placeholder}
        value={props.value}
        onChange={props.onChange}
      />
      {props.hasError && (
        <span className="invalid-feedback">{props.error}</span>
      )}
    </div>
  );
};
Input.defaultProps = {
  onChange: () => {},
};

export default Input;
