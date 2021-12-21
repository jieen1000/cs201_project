import React from "react";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import Footer from "examples/Footer";
import Paper from '@mui/material/Paper';
import EmployeeTable from "./components/EmployeeTable/EmployeeTable";
import EmployeeSkillTable from "./components/EmployeeSkillTable/EmployeeSkillTable";
import ProjectTable from "./components/ProjectTable/ProjectTable";
import Header from "./components/Header";
import Card from "@mui/material/Card";


/**
 * {@code organisation} creates the layout for the CRUD interface.
 *
 * @author Teo Keng Swee
 * @author Chong Zhan Han
 * @author Tan Jie En
 * @author Pang Jun Rong
 * @version 1.2
 * @since 2021-10-16
 */

function Organisation() {

  return (
    <DashboardLayout>
      <DashboardNavbar />
      <Header />
      <div style={{ padding: "1em" }}>
        <Card>
          <Paper sx={{ elevation: 16 }}>
            <EmployeeTable />
          </Paper>
        </Card>
      </div>
      <div style={{ padding: "1em" }}>
        <Card>
          <Paper sx={{ elevation: 16 }}>
            <EmployeeSkillTable />
          </Paper>
        </Card>
      </div>
      <div style={{ padding: "1em" }}>
        <Card>
          <Paper sx={{ elevation: 16 }}>
            <ProjectTable />
          </Paper>
        </Card>
      </div>
      <Footer />
    </DashboardLayout>



  );
}

export default Organisation;
