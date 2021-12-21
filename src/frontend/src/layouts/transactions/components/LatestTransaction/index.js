import Card from "@mui/material/Card";
import Icon from "@mui/material/Icon";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import Transaction from "layouts/transactions/components/Transaction";

/**
 * {@code LatestTransaction} creates a component which shows the latest transactions.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-25
 */

function Transactions() {
  return (
    <Card className="h-100">
      <SuiBox display="flex" justifyContent="space-between" alignItems="center" pt={3} px={2}>
        <SuiTypography variant="h6" fontWeight="medium" textTransform="capitalize">
          Latest Transactions
        </SuiTypography>
        <SuiBox display="flex" alignItems="flex-start">
          <SuiBox color="text" mr={0.5} lineHeight={0}>
            <Icon color="inherit" fontSize="small">
              date_range
            </Icon>
          </SuiBox>
          <SuiTypography variant="button" textColor="text" fontWeight="regular">
            Past 7 Days
          </SuiTypography>
        </SuiBox>
      </SuiBox>
      <SuiBox pt={3} pb={2} px={2}>
        <SuiBox
          component="ul"
          display="flex"
          flexDirection="column"
          p={0}
          m={0}
          customClass="no-list-style"
        >
          <Transaction
            color="success"
            icon="arrow_backward"
            name="Satish Manoj"
            description="Yesterday"
            value="$ 2,500"
          />
          <Transaction
            color="warning"
            icon="arrow_forward"
            name="Pho Vienna"
            description="3 Days Ago"
            value="- $ 2,500"
          />
        </SuiBox>
      </SuiBox>
    </Card>
  );
}

export default Transactions;
