
import React from "react";
import { FormInputText } from "layouts/organisation/input/FormInputText";
import { FormInputDate } from "layouts/organisation/input/FormInputDate";
import { FormInputRadio } from "layouts/organisation/input/FormInputRadio";
import DialogContent from "@mui/material/DialogContent";
import Grid from "@mui/material/Grid";
import SuiBox from "components/SuiBox";
import SuiButton from "components/SuiButton";
import { useForm } from "react-hook-form";
import { Auth } from "aws-amplify";
import SuiTypography from "components/SuiTypography";
import { Divider } from "@mui/material";

/**
 * {@code EmployeeForm} creates the form component for the CRUD interface.
 *
 * @author Teo Keng Swee
 * @author Pang Jun Rong
 * @author Bryan Tan
 * @version 1.0
 * @since 2021-10-16
 */

export default function EmployeeForm(props) {
  const employee = props.attr;

  console.log(employee);

  const defaultValues = {
    workId: employee.workId || "",
    name: employee.name || "",
    employeeRole: employee.employeeRole || "",
    passportNumber: employee.passportNumber || "",
    workPermitNumber: employee.workPermitNumber || "",
    levy: employee.levy || "",
    workContactNumber: employee.workContactNumber || "",
    workSiteLocation: employee.workSiteLocation || "",
    singaporeAddress: employee.singaporeAddress || "",
    vaccStatus: employee.vaccStatus || "",
    description: employee.description || "",
    workPermitDateOfIssue: employee.workPermitDateOfIssue || new Date(),
    workPermitExpiryDate: employee.workPermitExpiryDate || new Date(),
  };

  const methods = useForm({ defaultValues: defaultValues });
  const { register, handleSubmit, control, reset, formState: { errors } } = methods;
  console.log(errors);

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
        compId: res.getIdToken().payload['cognito:groups'][0]
      });
      fetch(
        "/api/employees" +
        (employee.workPermitNumber ? "/" + employee.workPermitNumber : "") +
        `?${queryString}`,
        {
          method: employee.workPermitNumber ? "PUT" : "POST",
          headers: {
            Authorization: "Bearer " + res.getIdToken().getJwtToken(),
            Accept: "application/json",
            "Content-Type": "application/json",
          },
          body: JSON.stringify(data),
        }
      ).then((getResponse) => {
        console.log(getResponse);
        if (getResponse.status == 200 || getResponse.status == 201) {
          alert("Employee updated!"); 
          window.location.reload();
        } else {
          alert("Error occured. Please ensure that the fields are encountered correctly");
        }
      }) 
    })}; 
  // ref={register({
  //   required: true, minLength: 10
  return employee[12] ? (
    <DialogContent>
      <SuiTypography
        variant="h6"
        textColor="info"
        fontWeight="bold"
        textGradient
      >
        Personal Particulars
      </SuiTypography>
      <SuiBox  pt={1}>
        <FormInputText
          name="workId"
          control={control}
          label="Work ID"
          min={10}
        />

      </SuiBox>

      <SuiBox  pt={1} pb={3}>
        <Grid container spacing={1} direction={"row"}>
          <Grid item>
            <FormInputText
              name="name"
              control={control}
              label="Employee Name"
            />
          </Grid>
          <Grid item>
            <FormInputText
              name="passportNumber"
              control={control}
              label="Passport Number"
              min={10}
            />
          </Grid>
          <Grid item>
            <FormInputText
              name="workContactNumber"
              control={control}
              label="Contact Number"
              min={8}
            />
          </Grid>
        </Grid>
      </SuiBox>

      <Divider variant="middle" />
      <SuiTypography
        variant="h6"
        textColor="info"
        fontWeight="bold"
        textGradient
      >
        Job Details
      </SuiTypography>

      <SuiBox  pt={1} pb={3}>
        <Grid container spacing={1} direction={"row"}>
          <Grid item>
            <FormInputText
              name="employeeRole"
              control={control}
              label="Employee Role"
            />
          </Grid>
          <Grid item>
            <FormInputText
              name="workPermitNumber"
              control={control}
              label="Work Permit Number"
            />
          </Grid>
          <Grid item>
            <FormInputDate
              name="workPermitDateOfIssue"
              control={control}
              label="Work Permit Issue Date"
            />
          </Grid>
          <Grid item>
            <FormInputDate
              name="workPermitExpiryDate"
              control={control}
              label="Work Permit Expiry"
            />
          </Grid>
          <Grid item>
            <FormInputText
              name="workSiteLocation"
              control={control}
              label="Work Location"
              min={10}
            />
          </Grid>
          <Grid item>
            <FormInputText
              name="singaporeAddress"
              control={control}
              label="Residence Address"
              min={10}
            />
          </Grid>
          <Grid item>
            <FormInputText name="levy" control={control} label="Levy" />
          </Grid>
        </Grid>
      </SuiBox>

      <Divider variant="middle" />
      <SuiTypography
        variant="h6"
        textColor="info"
        fontWeight="bold"
        textGradient
      >
        Employee Description
      </SuiTypography>
      <SuiBox  pt={1} pb={3}>
        <Grid container spacing={1} direction={"row"}>
          <Grid item>
            <FormInputText
              name="description"
              control={control}
              label="Description"
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
  ) : (<DialogContent>
    <SuiTypography
      variant="h6"
      textColor="info"
      fontWeight="bold"
      textGradient
    >
      Personal Particulars
    </SuiTypography>
    <SuiBox  pt={1}>
      <FormInputText name="workId" control={control} label="Work ID" min={10}/>
    </SuiBox>

    <SuiBox  pt={1} pb={3}>
      <Grid container spacing={1} direction={"row"}>
        <Grid item>
          <FormInputText
            name="name"
            control={control}
            label="Name"
          />
        </Grid>
        <Grid item>
          <FormInputText
            name="passportNumber"
            control={control}
            label="Passport Number"
            min={10}
          />
        </Grid>
        <Grid item>
          <FormInputText
            name="workContactNumber"
            control={control}
            label="Contact Number"
            min={8}
          />
        </Grid>
      </Grid>
    </SuiBox>

    <Divider variant="middle" />
    <SuiTypography
      variant="h6"
      textColor="info"
      fontWeight="bold"
      textGradient
    >
      Job Details
    </SuiTypography>

    <SuiBox  pt={1} pb={3}>
      <Grid container spacing={1} direction={"row"}>
        <Grid item>
          <FormInputText
            name="employeeRole"
            control={control}
            label="Employee Role"
          />
        </Grid>
        <Grid item>
          <FormInputText
            name="workPermitNumber"
            control={control}
            label="Work Permit Number"
            min={9}
          />
        </Grid>
        <Grid item>
          <FormInputDate
            name="workPermitDateOfIssue"
            control={control}
            label="Work Permit Issue Date"
          />
        </Grid>
        <Grid item>
          <FormInputDate
            name="workPermitExpiryDate"
            control={control}
            label="Work Permit Expiry"
          />
        </Grid>
        <Grid item>
          <FormInputText
            name="workSiteLocation"
            control={control}
            label="Work Location"
            min={10}
          />
        </Grid>
        <Grid item>
          <FormInputText
            name="singaporeAddress"
            control={control}
            label="Residence Address"
            min={10}
          />
        </Grid>
        <Grid item>
          <FormInputText name="levy" control={control} label="Levy" />
        </Grid>
      </Grid>
    </SuiBox>

    <Divider variant="middle" />
    <SuiTypography
      variant="h6"
      textColor="info"
      fontWeight="bold"
      textGradient
    >
      Health Information
    </SuiTypography>
    <SuiBox  pt={1} pb={3}>
      <Grid container spacing={1} direction={"row"}>
        <Grid item>
          <FormInputRadio
            name="vaccStatus"
            control={control}
            label="Vaccination Status"
          />
        </Grid>
      </Grid>
    </SuiBox>


    <Divider variant="middle" />
    <SuiTypography
      variant="h6"
      textColor="info"
      fontWeight="bold"
      textGradient
    >
      Employee Description
    </SuiTypography>
    <SuiBox  pt={1} pb={3}>
      <Grid container spacing={1} direction={"row"}>
        <Grid item>
          <FormInputText
            name="description"
            control={control}
            label="Description"
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
  </DialogContent>);
}

