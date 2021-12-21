/**
=========================================================
* Soft UI Dashboard React - v2.0.0
=========================================================

* Product Page: https://www.creative-tim.com/product/soft-ui-dashboard-material-ui
* Copyright 2021 Creative Tim (https://www.creative-tim.com)

Coded by www.creative-tim.com

 =========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*/

// @mui material components
import Grid from "@mui/material/Grid";

// @mui icons
import FacebookIcon from "@mui/icons-material/Facebook";
import InstagramIcon from "@mui/icons-material/Instagram";
import LinkedInIcon from "@mui/icons-material/LinkedIn";

// Soft UI Dashboard React components
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";

function Footer() {
  return (
    <SuiBox component="footer" py={6}>
      <Grid container justifyContent="center">
        <Grid item xs={10} lg={8}>
          <SuiBox display="flex" justifyContent="center" flexWrap="wrap" mb={3}>
            <SuiBox mr={{ xs: 2, lg: 3, xl: 6 }}>
              <SuiTypography component="a" href="#" variant="body2" textColor="secondary">
                About Us
              </SuiTypography>
            </SuiBox>
            <SuiBox mr={{ xs: 2, lg: 3, xl: 6 }}>
              <SuiTypography component="a" href="#" variant="body2" textColor="secondary">
                Demo
              </SuiTypography>
            </SuiBox>
            <SuiBox mr={{ xs: 2, lg: 3, xl: 6 }}>
              <SuiTypography component="a" href="#" variant="body2" textColor="secondary">
                Pricing
              </SuiTypography>
            </SuiBox>
          </SuiBox>
        </Grid>
        <Grid item xs={12} lg={8}>
          <SuiBox display="flex" justifyContent="center" mt={1} mb={3}>
            <SuiBox mr={3} color="secondary">
              <FacebookIcon fontSize="small" />
            </SuiBox>
            <SuiBox mr={3} color="secondary">
              <InstagramIcon fontSize="small" />
            </SuiBox>
            <SuiBox color="secondary">
              <LinkedInIcon fontSize="small" />
            </SuiBox>
          </SuiBox>
        </Grid>
        <Grid item xs={12} lg={8} className="text-center">
          <SuiTypography variant="body2" textColor="secondary">
            Copyright &copy; 2021 Nori Furikake.
          </SuiTypography>
        </Grid>
      </Grid>
    </SuiBox>
  );
}

export default Footer;
