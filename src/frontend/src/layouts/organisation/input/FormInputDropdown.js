import React from "react";
import { FormControl, FormHelperText, InputLabel, MenuItem, Select } from "@mui/material";
import { Controller } from "react-hook-form";


const options = [
  {
    label: "Welding",
    value: "welding",
  },
  {
    label: "Electrical Wiring",
    value: "wiring",
  },
  {
    label: "Gas Pipefitting",
    value: "pipefitting",
  },
  {
    label: "Waterproofing",
    value: "waterproofing",
  },
  {
    label: "Tiling",
    value: "tiling",
  },
  {
    label: "Thermal Insulation",
    value: "insulation",
  },
  {
    label: "Tower Crane Operation",
    value: "towercrane",
  },
  {
    label: "Plastering",
    value: "plastering",
  },
  {
    label: "Lift Installation",
    value: "lift",
  },
  {
    label: "Mobile Crane Operation",
    value: "mobilecrane",
  },

];

export const FormInputDropdown = ({
  name,
  control,
  label,
}) => {
  const generateSingleOptions = () => {
    return options.map((option) => {
      return (
        <MenuItem key={option.value} value={option.value}>
          {option.label}
        </MenuItem>
      );
    });
  };

  return (
    <FormControl>
      <Controller
        render={({ field: { onChange, value } }) => (
          <div style={{width: "19vh"}}>
          <Select onChange={onChange} value={value}>
            {generateSingleOptions()}
          </Select>
          <FormHelperText>{label}</FormHelperText>
          </div>
        )}
        control={control}
        name={name}
      />
    </FormControl>
  );
};