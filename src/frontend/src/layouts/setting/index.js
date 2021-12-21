import Grid from "@mui/material/Grid";
import Card from "@mui/material/Card";
import FacebookIcon from "@mui/icons-material/Facebook";
import TwitterIcon from "@mui/icons-material/Twitter";
import InstagramIcon from "@mui/icons-material/Instagram";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import Footer from "examples/Footer";
import ProfileInfoCard from "examples/Cards/InfoCards/ProfileInfoCard";
import ProfilesList from "examples/ProfilesList";
import DefaultProjectCard from "examples/Cards/ProjectCards/DefaultProjectCard";
import PlaceholderCard from "examples/Cards/PlaceholderCard";
import PlatformSettings from "layouts/setting/components/PlatformSettings";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";

/**
 * {@code Settings} creates the layout for the settings interface.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-22
 */

function Settings() {
  return (
    <DashboardLayout>
      <DashboardNavbar />
      <SuiBox mt={5} mb={3}>
        <Grid container spacing={3}>
          <Grid item xs={12} md={12} xl={12}>
            <PlatformSettings />
          </Grid>
        </Grid>
      </SuiBox>

      <Footer />
    </DashboardLayout>
  );
}

export default Settings;
