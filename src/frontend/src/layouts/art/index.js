import React from "react";
import SuiBox from "components/SuiBox";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import Footer from "examples/Footer";
import Paper from '@mui/material/Paper';
import AllArtsTable from "./component/AllArtsTable";
import LatestArtsTable from "./component/LatestArtsTable";
import Card from "@mui/material/Card";

/**
 * {@code ART} creates the layout for the ART interface.
 *
 * @author Teo Keng Swee
 * @author Pang Jun Rong
 * @version 1.2
 * @since 2021-10-16
 */

function ART() {

  return (
    <DashboardLayout>
      <DashboardNavbar />
      <SuiBox py={3}>
        <SuiBox mb={3}>
          <Card>
            <Paper sx={{ elevation: 16 }}>
              <LatestArtsTable />
            </Paper>
          </Card>
        </SuiBox>
      </SuiBox>
      <SuiBox>
        <SuiBox mb={3}>
          <Card>
            <Paper sx={{ elevation: 16 }}>
              <AllArtsTable />
            </Paper>
          </Card>
        </SuiBox>
      </SuiBox>
      <Footer />
    </DashboardLayout>
  );
}

export default ART;
