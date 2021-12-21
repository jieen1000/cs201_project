
import SuiBox from "components/SuiBox";
import Footer from "examples/Footer";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import SkillMarketTable from "layouts/discover/components/SkillMarketTable";
import Card from "@mui/material/Card";

/**
 * {@code discover} creates the layout for the discovery panel page.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

function LabourSharing() {

  return (
    <DashboardLayout>
      <DashboardNavbar />
      <SuiBox py={3}>
        <Card>
          <SkillMarketTable />
        </Card>
      </SuiBox>
      <Footer />
    </DashboardLayout>
  );
}

export default LabourSharing;
