import PropTypes from "prop-types";
import Grid from "@mui/material/Grid";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import PageLayout from "examples/LayoutContainers/PageLayout";
import Footer from "layouts/authentication/components/Footer";
import styles from "layouts/authentication/components/CoverLayout/styles";
import kaizenlogo from "assets/images/kaizen-logo.png";

/**
 * {@code CoverLayout} provides the cover layout for authentication page.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

function CoverLayout({ color, header, title, description, image, top, children }) {
  const classes = styles({ image });

  return (
    <PageLayout background="white">
      <Grid container justifyContent="center" className={classes.coverLayout}>
        <Grid item xs={11} sm={8} md={5} xl={3}>
          <SuiBox mt={top}>
            <img src={kaizenlogo} alt="Kaizen Logo" className="logo" />
            <SuiBox pt={3} px={3}>
              {!header ? (
                <>
                  <SuiBox mb={1}>
                    <SuiTypography variant="h3" fontWeight="bold" textColor={color} textGradient>
                      {title}
                    </SuiTypography>
                  </SuiBox>
                  <SuiTypography variant="body2" fontWeight="regular" textColor="text">
                    {description}
                  </SuiTypography>
                </>
              ) : (
                header
              )}
            </SuiBox>
            <SuiBox p={3}>{children}</SuiBox>
          </SuiBox>
        </Grid>
        <Grid item xs={12} md={5}>
          <SuiBox
            display={{ xs: "none", md: "block" }}
            position="relative"
            right={{ md: "-12rem", xl: "-16rem" }}
            customClass={classes.coverLayout_imageBox}
          >
            <SuiBox customClass={classes.coverLayout_image} />
          </SuiBox>
        </Grid>
      </Grid>
      <Footer />
    </PageLayout>
  );
}

CoverLayout.defaultProps = {
  header: "",
  title: "",
  description: "",
  color: "info",
  top: 5,
};

CoverLayout.propTypes = {
  color: PropTypes.oneOf([
    "primary",
    "secondary",
    "info",
    "success",
    "warning",
    "error",
    "dark",
    "light",
  ]),
  header: PropTypes.node,
  title: PropTypes.string,
  description: PropTypes.string,
  image: PropTypes.string.isRequired,
  top: PropTypes.number,
  children: PropTypes.node.isRequired,
};

export default CoverLayout;
