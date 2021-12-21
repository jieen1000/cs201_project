import React from "react";
import TextField from '@mui/material/TextField';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import DatePicker from '@mui/lab/DatePicker';
import { Controller } from "react-hook-form";

/**
 * {@code FormInputDate} creates a date picker component for the forms.
 *
 * @author Teo Keng Swee
 * @version 1.0
 * @since 2021-10-16
 */


export const FormInputDate = ({ name, control, label }) => {
  return (
    <LocalizationProvider dateAdapter={AdapterDateFns}>
      <Controller
        name={name}
        control={control}
        render={({ field : {onChange , value } }) => (
          <DatePicker
            onChange={onChange}
            value={value}
            label={label}
            renderInput={(params) => <TextField {...params} size="normal" />}
          />
        )}
      />
    </LocalizationProvider>
  );
};
