import { useState, useEffect } from "react";
import Card from "@mui/material/Card";
import Grid from "@mui/material/Grid";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import SuiAvatar from "components/SuiAvatar";
import breakpoints from "assets/theme/base/breakpoints";
import styles from "./styles";

/**
 * {@code Header} creates the header for the CRUD interface.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-28
 */

function Header() {
  const [tabsOrientation, setTabsOrientation] = useState("horizontal");
  const [tabValue, setTabValue] = useState(0);
  const classes = styles();

  useEffect(() => {
    function handleTabsOrientation() {
      return window.innerWidth < breakpoints.values.sm
        ? setTabsOrientation("vertical")
        : setTabsOrientation("horizontal");
    }

    window.addEventListener("resize", handleTabsOrientation);

    handleTabsOrientation();

    return () => window.removeEventListener("resize", handleTabsOrientation);
  }, [tabsOrientation]);

  const handleSetTabValue = (event, newValue) => setTabValue(newValue);

  return (
    <SuiBox position="relative" py={3}>
      <SuiBox customClass={classes.profileHeader_background} />
      <Card className={classes.profileHeader_profile}>
        <Grid container spacing={3} alignItems="center">
          <Grid item>
            <SuiAvatar
              src="https://images.unsplash.com/photo-1619454016518-697bc231e7cb?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1480&q=80"
              alt="header-image"
              variant="rounded"
              size="xl"
              customClass="shadow-sm"
            />
          </Grid>
          <Grid item>
            <SuiBox height="100%" mt={0.5} lineHeight={1}>
              <SuiTypography variant="h5" fontWeight="medium">
                Welcome
              </SuiTypography>
              <SuiTypography variant="button" textColor="text" fontWeight="medium">
                Blackrock Industries Pte. Ltd
              </SuiTypography>
            </SuiBox>
          </Grid>
        </Grid>
      </Card>
    </SuiBox>
  );
}

export default Header;
