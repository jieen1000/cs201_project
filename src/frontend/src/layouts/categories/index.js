import SuiBox from "components/SuiBox";
import Footer from "examples/Footer";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import CategoriesTable from "layouts/categories/components/CategoriesTable";
import Card from "@mui/material/Card";

/**
 * {@code categories} provides a layout for the categories page.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

function Categories(props) {


  return (
    <DashboardLayout>
      <DashboardNavbar />
      <SuiBox py={3}>
        <Card>
          <CategoriesTable attr={props.location.state}/>
        </Card>
      </SuiBox>
      <Footer />
    </DashboardLayout>
  );
}

export default Categories;
