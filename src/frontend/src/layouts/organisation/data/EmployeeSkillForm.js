
import React from "react";
import { FormInputText } from "layouts/organisation/input/FormInputText";
import { FormInputDropdown } from "layouts/organisation/input/FormInputDropdown";
import DialogContent from "@mui/material/DialogContent";
import Grid from "@mui/material/Grid";
import SuiBox from "components/SuiBox";
import SuiButton from "components/SuiButton";
import { useForm } from "react-hook-form";
import styles from "layouts/organisation/styles";
import { Auth } from "aws-amplify";
import SuiTypography from "components/SuiTypography";
import { Divider } from "@mui/material";

/**
 * {@code EmployeeTable} creates the layout for the CRUD interface.
 *
 * @author Chong Zhan Han
 * @author Tan Jie En
 * @author Teo Keng Swee 
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-10-18
 */

export default function EmployeeSkillForm(props) {
  const classes = styles();
  const employeeSkill = props.attr || ["", "", "", "", ""];

  const defaultValues = {
    employeeId: employeeSkill.workPermitNumber,
    skillId: employeeSkill.skillId,
    cost: employeeSkill.cost,
    experience: employeeSkill.experience,
    rating: employeeSkill.rating,
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
    const response = Auth.currentSession().then((res) => {
      const queryString = objToQueryString({
        empId: data.employeeId,
        skillId: data.skillId,
      });
      const queryComp = objToQueryString({
        compId: res.getIdToken().payload['cognito:groups'][0]
      })
      var dataFormatted = {
        "id": {
          "employee": data.employeeId,
          "skill": data.skillId
        },
        "experience": data.experience,
        "rating": data.rating,
        "cost": data.cost
      }
      fetch("/api/employeeSkills" +
        (employeeSkill[0] ? `?${queryString}` : "") + (`?${queryComp}`), {
        method: employeeSkill[0] ? "PUT" : "POST",
        headers: {
          Authorization: "Bearer " + res.getIdToken().getJwtToken(),
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(dataFormatted),
      }).then((getResponse) => {
        console.log(getResponse);
        if (getResponse.status == 200 || getResponse.status == 201) {
          alert("Employee skill updated!"); 
          window.location.reload();
        } else {
          alert("Error occured. Please ensure that the fields are encountered correctly");
        }
     
    })

  })};

  return (
    <DialogContent>
      <SuiTypography
        variant="h6"
        textColor="info"
        fontWeight="bold"
        textGradient
      >
        Employee
      </SuiTypography>
      <SuiBox customClass={classes.tables_table} pt={1}>
        <FormInputText
          name="employeeId"
          control={control}
          label="Employee ID"
          min={9}
        />
      </SuiBox>

      <Divider />

      <SuiTypography
        variant="h6"
        textColor="info"
        fontWeight="bold"
        textGradient
      >
        Skill
      </SuiTypography>

      <SuiBox customClass={classes.tables_table} pt={1}>
        <Grid container spacing={1} direction={"row"}>
          <Grid item s={6}>
            <FormInputDropdown
              name="skillId"
              control={control}
              label="Skill"
            />
          </Grid>
          <Grid item s={6}>
            <FormInputText
              name="cost"
              control={control}
              label="Cost"
            />
          </Grid>
        </Grid>
      </SuiBox>
      <SuiBox customClass={classes.tables_table} pt={1} pb={3}>
        <Grid container spacing={1} direction={"row"}>
          <Grid item>
            <FormInputText
              name="experience"
              control={control}
              label="Experience"
            />
          </Grid>

          <Grid item>
            <FormInputText
              name="rating"
              control={control}
              label="Rating"
              min="0.00 - 5.00"
            />
          </Grid>
        </Grid>
      </SuiBox>
      <SuiBox>
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

