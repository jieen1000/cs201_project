import Grid from "@mui/material/Grid";
import SuiBox from "components/SuiBox";
import DefaultInfoCard from "examples/Cards/InfoCards/DefaultInfoCard";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import Transactions from "layouts/transactions/components/LatestTransaction";
import IncomingTable from "layouts/transactions/components/IncomingTable";
import OutgoingTable from "layouts/transactions/components/OutgoingTable";
import Footer from "examples/Footer";
import Paper from '@mui/material/Paper';
import Card from "@mui/material/Card";

/**
 * {@code transactions} creates the layout for the CRUD interface.
 *
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-10-25
 */

function TransactionHistory() {
  return (
    <DashboardLayout>
      <DashboardNavbar />
      <SuiBox mt={4}>
        <SuiBox mb={1.5}>
          <Grid container spacing={3}>
            <Grid item xs={12} lg={12}>
              <Grid container spacing={3}>
                <Grid item xs={12} md={6} xl={3}>
                  <DefaultInfoCard
                    icon="account_balance"
                    title="Revenue"
                    description="Sharing Remunerations (30d)"
                    value="$23,400"
                  />
                </Grid>
                <Grid item xs={12} md={6} xl={3}>
                  <DefaultInfoCard
                    icon="money_off"
                    title="Expenses"
                    description="Sharing Costs (30d)"
                    value="$17,800"
                  />
                </Grid>
                <Grid item xs={12} md={6} xl={6}>
                  <SuiBox style={{ height: "23.5vh" }}>
                    <Transactions />
                  </SuiBox>
                </Grid>
              </Grid>
            </Grid>
          </Grid>
        </SuiBox>
        <SuiBox pb={3}>
        <Card>
          <Paper sx={{ elevation: 16 }}>
            <IncomingTable />
          </Paper>
        </Card>
        </SuiBox>
        <Card>
          <Paper sx={{ elevation: 16 }}>
            <OutgoingTable />
          </Paper>
        </Card>
      </SuiBox>
      <Footer />
    </DashboardLayout>
  );
}

export default TransactionHistory;
