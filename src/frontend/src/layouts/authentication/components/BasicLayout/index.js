import PropTypes from "prop-types";
import Grid from "@mui/material/Grid";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import DefaultNavbar from "examples/Navbars/DefaultNavbar";
import PageLayout from "examples/LayoutContainers/PageLayout";
import Footer from "layouts/authentication/components/Footer";
import styles from "layouts/authentication/components/BasicLayout/styles";
import routes from "routes";

/**
 * {@code BasicLayout} provides the basic layout for authentication page.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

function BasicLayout({ title, description, image, children }) {
  const classes = styles({ image });

  return (
    <PageLayout>
      <DefaultNavbar
        routes={routes}
        action={{
          type: "external",
          route: "https://www.creative-tim.com/product/soft-ui-dashboard-material-ui",
          label: "free download",
        }}
        transparent
        light
      />
      <SuiBox customClass={classes.basicLayout}>
        <Grid container spacing={3} justifyContent="center" className="text-center">
          <Grid item xs={10} lg={4}>
            <SuiBox mt={6} mb={1}>
              <SuiTypography variant="h1" textColor="white" fontWeight="bold">
                {title}
              </SuiTypography>
            </SuiBox>
            <SuiBox mb={2}>
              <SuiTypography variant="body2" textColor="white" fontWeight="regular">
                {description}
              </SuiTypography>
            </SuiBox>
          </Grid>
        </Grid>
      </SuiBox>
      <SuiBox mt={{ xs: -26, lg: -24 }} px={1} width="calc(100% - 2rem)" mx="auto">
        <Grid container spacing={1} justifyContent="center">
          <Grid item xs={11} sm={9} md={5} lg={4} xl={3}>
            {children}
          </Grid>
        </Grid>
      </SuiBox>
      <Footer />
    </PageLayout>
  );
}

// Setting default values for the props of BasicLayout
BasicLayout.defaultProps = {
  title: "",
  description: "",
};

// Typechecking props for the BasicLayout
BasicLayout.propTypes = {
  title: PropTypes.string,
  description: PropTypes.string,
  image: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
};

export default BasicLayout;
