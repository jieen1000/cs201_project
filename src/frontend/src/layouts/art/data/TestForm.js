
import React, {useState} from "react";
import { FormInputText } from "layouts/organisation/input/FormInputText";
import { FormInputDate } from "layouts/organisation/input/FormInputDate";
import DialogContent from "@mui/material/DialogContent";
import Grid from "@mui/material/Grid";
import SuiBox from "components/SuiBox";
import SuiButton from "components/SuiButton";
import { useForm } from "react-hook-form";
import styles from "layouts/organisation/styles";
import { Auth } from "aws-amplify";
import SuiTypography from "components/SuiTypography";
import { Divider } from "@mui/material";
import { FormInputRadio } from "../input/FormInputRadio";


/**
 * {@code TestForm} creates the form component for the CRUD interface.
 *
 * @author Teo Keng Swee
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

export default function TestForm() {
  const classes = styles();

  const defaultValues = {
    employeeWP: "",
    result: "",
    dateOfTest: new Date(),
  };

  const methods = useForm({ defaultValues: defaultValues });
  const { handleSubmit, control, reset } = methods;


  function objToQueryString(obj) {
    const keyValuePairs = [];
    for (const key in obj) {
      keyValuePairs.push(
        encodeURIComponent(key) + "=" + encodeURIComponent(obj[key])
      );
    }
    return keyValuePairs.join("&");
  }

  const onSubmit = (data) => {


    console.log(data);
    const response = Auth.currentSession().then((res) => {
      const queryString = objToQueryString({
        empId: data.employeeWP,
        compId: res.getIdToken().payload['cognito:groups'][0],
      });
      fetch("/api/covidTest" + `?${queryString}`, {
        method: "POST",
        headers: {
          Authorization: "Bearer " + res.getIdToken().getJwtToken(),
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      }).then((getResponse) => {
        console.log(getResponse);
        if (getResponse.status == 200 || getResponse.status == 201) {
          alert("ART test updated!"); 
          window.location.reload();
        } else {
          alert("Error occured. Please ensure that the fields are encountered correctly");
        }
     
    })
        
    });
    
  };

  return (
    <DialogContent>
     
      <SuiTypography
        variant="h6"
        textColor="info"
        fontWeight="bold"
        textGradient
      >
        Personal Particulars
      </SuiTypography>
      <SuiBox customClass={classes.tables_table} pt={1}>
        <FormInputText
          name="employeeWP"
          control={control}
          label="Work Permit Number" /> 
      </SuiBox>

      <SuiBox customClass={classes.tables_table} pt={1} pb={3}>
        <Grid container spacing={1} direction={"row"}>
          <Grid item>
            <FormInputRadio
              name="result"
              control={control}
              label="Test Result"
            />
          </Grid>
          <Grid item>
            <FormInputDate
              name="dateOfTest"
              control={control}
              label="Test Date"
            />
          </Grid>
        </Grid>
      </SuiBox>
      <SuiBox py={3}>
        <Grid container direction="row" spacing={3}>
          <Grid item>
            <SuiButton
              size="medium"
              onClick={handleSubmit(onSubmit)}
              variant={"contained"}
              buttonColor="success"
              circular
            >
              Submit
            </SuiButton>
          </Grid>
          <Grid item>
            <SuiButton
              size="medium"
              variant={"contained"}
              buttonColor="error"
              circular
              onClick={() => reset()}
            >
              Reset
            </SuiButton>
          </Grid>
        </Grid>
      </SuiBox>
    </DialogContent>
  );
}
