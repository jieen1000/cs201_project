import Card from "@mui/material/Card";
import Icon from "@mui/material/Icon";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import styles from "layouts/labour-sharing/components/MarketplaceBanner/styles";

/**
 * {@code MarketplaceBanner} creates the component for labour sharing marketplace banner.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

function MarketplaceBanner() {
  const classes = styles();

  return (
    <Card className="h-100">
      <SuiBox position="relative" height="30vh">
        <SuiBox customClass={classes.marketplaceBanner_content}>
          <SuiBox mb={3} pt={1}>
            <SuiTypography variant="h1" textColor="white" fontWeight="bold">
              Labour Sharing 
            </SuiTypography>
            <SuiTypography variant="h1" textColor="info" fontWeight="bold" textGradient>
              Marketplace
            </SuiTypography>
          </SuiBox>
          <SuiBox mb={2}>
            <SuiTypography variant="body" textColor="white">
              Improving Manpower Efficiency & Skilled Labour Access
            </SuiTypography>
          </SuiBox>
          <SuiTypography
            component="a"
            href="/#/labour-discovery"
            variant="h6"
            textColor="white"
            fontWeight="medium"
            customClass={classes.marketplaceBanner_button}
          >
            Discover Now
            <Icon className="font-bold">search</Icon>
          </SuiTypography>
        </SuiBox>
      </SuiBox>
    </Card>
  );
}

export default MarketplaceBanner;
