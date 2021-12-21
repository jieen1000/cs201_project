import Tooltip from "@mui/material/Tooltip";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import SuiAvatar from "components/SuiAvatar";
import SuiProgress from "components/SuiProgress";
import styles from "layouts/dashboard/components/Projects/styles";
import logoGeneric from "assets/images/small-logos/logo-building.svg"
import team1 from "assets/images/team-1.jpg";
import team2 from "assets/images/team-2.jpg";
import team3 from "assets/images/team-3.jpg";
import team4 from "assets/images/team-4.jpg";

/**
 * {@code Projects/data} provides static data from the projects table.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

export default function data() {
  const classes = styles();

  const avatars = (supervisors) =>
    supervisors.map(([image, name]) => (
      <Tooltip key={name} title={name} placeholder="bottom">
        <SuiAvatar src={image} alt="name" size="xs" customClass={classes.projects_tableAvatar} />
      </Tooltip>
    ));

  return {
    columns: [
      { name: "companies", align: "left" },
      { name: "supervisors", align: "left" },
      { name: "budget", align: "center" },
      { name: "completion", align: "center" },
    ],

    rows: [
      {
        companies: [logoGeneric, "3A Namly Ave"],
        supervisors: (
          <SuiBox display="flex" py={1}>
            {avatars([
              [team1, "Ryan Tompson"],
              [team2, "Romina Hadid"],
              [team3, "Alexander Smith"],
              [team4, "Jessica Doe"],
            ])}
          </SuiBox>
        ),
        budget: (
          <SuiTypography variant="caption" textColor="text" fontWeight="medium">
            $3.8M
          </SuiTypography>
        ),
        completion: (
          <SuiBox width="8rem" textAlign="left">
            <SuiProgress value={60} color="info" gradient />
          </SuiBox>
        ),
      },
      {
        companies: [logoGeneric, "24 Sixth Ave"],
        supervisors: (
          <SuiBox display="flex" py={1}>
            {avatars([
              [team2, "Romina Hadid"],
              [team4, "Jessica Doe"],
            ])}
          </SuiBox>
        ),
        budget: (
          <SuiTypography variant="caption" textColor="text" fontWeight="medium">
            $8.1M
          </SuiTypography>
        ),
        completion: (
          <SuiBox width="8rem" textAlign="left">
            <SuiProgress value={10} color="info" gradient />
          </SuiBox>
        ),
      },
      {
        companies: [logoGeneric, "1 Marina Bay Dr"],
        supervisors: (
          <SuiBox display="flex" py={1}>
            {avatars([
              [team1, "Ryan Tompson"],
              [team3, "Alexander Smith"],
            ])}
          </SuiBox>
        ),
        budget: (
          <SuiTypography variant="caption" textColor="text" fontWeight="medium">
            $10.1M
          </SuiTypography>
        ),
        completion: (
          <SuiBox width="8rem" textAlign="left">
            <SuiProgress value={100} color="success" gradient />
          </SuiBox>
        ),
      },
      {
        companies: [logoGeneric, "76 Sembawang Rd"],
        supervisors: (
          <SuiBox display="flex" py={1}>
            {avatars([
              [team4, "Jessica Doe"],
              [team3, "Alexander Smith"],
              [team2, "Romina Hadid"],
              [team1, "Ryan Tompson"],
            ])}
          </SuiBox>
        ),
        budget: (
          <SuiTypography variant="caption" textColor="text" fontWeight="medium">
            $1.8M
          </SuiTypography>
        ),
        completion: (
          <SuiBox width="8rem" textAlign="left">
            <SuiProgress value={90} color="info" gradient />
          </SuiBox>
        ),
      },
      {
        companies: [logoGeneric, "53 Jurong West Ave 7"],
        supervisors: (
          <SuiBox display="flex" py={1}>
            {avatars([[team4, "Jessica Doe"]])}
          </SuiBox>
        ),
        budget: (
          <SuiTypography variant="caption" textColor="text" fontWeight="medium">
            $720K
          </SuiTypography>
        ),
        completion: (
          <SuiBox width="8rem" textAlign="left">
            <SuiProgress value={25} color="info" gradient />
          </SuiBox>
        ),
      },
    ],
  };
}
