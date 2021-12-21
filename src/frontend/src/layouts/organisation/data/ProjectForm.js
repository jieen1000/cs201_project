import React from "react";
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

/**
 * {@code ProjectForm} creates the form component for the CRUD interface.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

export default function ProjectForm(props) {
    const classes = styles();
    const project = props.attr;

    console.log("testing" + project);

    const defaultValues = {
        projectId: project[0] || "",
        projectName: project[5] || "",
        budget: project[2] || "",
        startDate: project[6] || new Date(),
        completionDate: project[3] || new Date(),
        progress: project[4] || "",
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
                compId: "0123456789",
            });
            fetch(
                "/api/project" +
                (project[1] ? "/" + project[1] : "") +
                `?${queryString}`,
                {
                    method: project[1] ? "PUT" : "POST",
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
                  alert("Project updated!"); 
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
                Project Information
            </SuiTypography>
            <SuiBox customClass={classes.tables_table} pt={1}>
                <FormInputText name="projectId" control={control} label="Project ID" />
            </SuiBox>

            <SuiBox customClass={classes.tables_table} pt={1} pb={3}>
                <Grid container spacing={1} direction={"row"}>
                    <Grid item>
                        <FormInputText
                            name="name"
                            control={control}
                            label="Project Name"
                        />
                    </Grid>
                    <Grid item>
                        <FormInputText name="budget" control={control} label="Budget" />
                    </Grid>
                    <Grid item>
                        <FormInputDate
                            name="startDate"
                            control={control}
                            label="Start Date"
                        />
                    </Grid>
                    <Grid item>
                        <FormInputDate
                            name="completionDate"
                            control={control}
                            label="Completion Date"
                        />
                    </Grid>
                    <Grid item>
                        <FormInputText
                            name="progress"
                            control={control}
                            label="Progress (%)"
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
