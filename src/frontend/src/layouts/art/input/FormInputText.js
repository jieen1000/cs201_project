import TextField from "@mui/material/TextField";
import { Controller } from "react-hook-form";
import React from "react";

/**
 * {@code FormInputText} creates the text input component for the CRUD interface.
 *
 * @author Teo Keng Swee
 * @version 1.0
 * @since 2021-10-16
 */

export const FormInputText = ({ name, control, label }) => {
  return (
    <Controller
        styles = {{border : "1px solid rgba(0, 0, 0, 0.05)", padding : "auto" }}
        name = { name } 
        control={control}
        render={({ field: { onChange, value } }) => (
          <TextField onChange={onChange} value={value} label={label} />
        )}
      />
  );
};
