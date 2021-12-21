import React from "react";
import {
  FormControlLabel,
  Radio,
  RadioGroup,
} from "@mui/material";
import { Controller } from "react-hook-form";

const options = [
  {
    label: "Vaccinated",
    value: "true",
  },
  {
    label: "Not Vaccinated",
    value: "false",
  },
];

export const FormInputRadio = ({ name, control, label }) => {
  const generateRadioOptions = () => {
    return options.map((singleOption) => (
      <FormControlLabel
        value={singleOption.value}
        label={singleOption.label}
        control={<Radio />}
      />
    ));
  };

  return (
    <Controller
      name={name}
      control={control}
      render={({ field: { onChange, value } }) => (
        <RadioGroup defaultValue="false" value={value} label={label} onChange={onChange} >
          {generateRadioOptions()}
        </RadioGroup>
      )}
    />
  );
};
