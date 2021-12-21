import { Grid, Rating } from "@mui/material";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
// import styles from "./styles";
import SuiBadge from "components/SuiBadge";
import Carousel from 'react-material-ui-carousel';
import HireDialog from "../HireDialog/";

/**
 * {@code LabourCard} creates the card for labour details.
 * @author Pang Jun Rong
 * @author Chong Zhan Han
 * @version 1.1
 * @since 2021-10-27
 */


function LabourCard(props) {

    const employeeSkill = props.employeeSkillDTO || {};

    return (
        <SuiBox p={3} mb={0.5}>
            <SuiTypography variant="h4" textColor="dark" fontWeight="medium" pl={3}>
                Labour Profile
            </SuiTypography>
            <Grid container spacing={3} p={3}>
                <Grid item xs={12} lg={4}>
                    <Carousel
                        indicators={false}
                        interval="5000"
                    >
                        <SuiBox
                            borderRadius="10%"
                            style={{
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                                overflow: "hidden"
                            }}>
                            <SuiBox
                                component="img"
                                src={employeeSkill.imageURL}
                                alt="profile-image"
                                style={{
                                    flexShrink: "0",
                                    minWidth: "100%",
                                    minHeight: "100%",
                                    height: "40vh"
                                }}
                            />
                        </SuiBox>
                    </Carousel>
                    <SuiBox pt={3}>
                        <Grid container justifyContent="center">
                            <Grid item>
                                <SuiBadge variant="gradient" badgeContent="CLEAN" color="info" size="large" />
                            </Grid>
                            <Grid item>
                                <SuiBadge variant="gradient" badgeContent="TEAMPLAYER" color="info" size="large" />
                            </Grid>
                            <Grid item>
                                <SuiBadge variant="gradient" badgeContent="PUNCTUAL" color="info" size="large" />
                            </Grid>
                        </Grid>
                    </SuiBox>
                </Grid>
                <Grid item xs={12} lg={8}>
                    <SuiBox pb={8} pl={8} pr={8} pl={1}>
                        <SuiTypography variant="h3" textColor="dark" fontWeight="bold">
                            {employeeSkill.name}
                        </SuiTypography>
                        <SuiTypography variant="h6" textColor="text" fontWeight="medium">
                            {employeeSkill.skillName} | {employeeSkill.experience} YEARS
                        </SuiTypography>
                        <Rating name="readOnly" value={employeeSkill.rating || 0} precision={0.1} readOnly />
                        <SuiBox py={3} pl={1}>
                            <SuiTypography variant="h5" textColor="dark" fontWeight="medium">
                                Price
                            </SuiTypography>
                            <SuiTypography variant="h5" textColor="dark" fontWeight="medium">
                                ${employeeSkill.cost}/Week
                            </SuiTypography>
                        </SuiBox>
                        <SuiBox pb={6} pl={1}>
                            <SuiTypography variant="h6" textColor="dark" fontWeight="medium">
                                Description
                            </SuiTypography>
                            <SuiTypography variant="h6" textColor="text" fontWeight="medium">
                                {employeeSkill.description}
                            </SuiTypography>
                        </SuiBox>
                        <SuiBox pl={1}>
                            <SuiTypography variant="h5" textColor="dark" fontWeight="medium">
                                Interested in Hiring {employeeSkill.name}?
                            </SuiTypography>
                        </SuiBox>
                        <SuiBox p={3}>
                            <HireDialog employeeSkillDTO={employeeSkill}/>
                        </SuiBox>
                    </SuiBox>
                </Grid>
            </Grid>
        </SuiBox>
    );
}

export default LabourCard;