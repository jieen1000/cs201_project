import Card from "@mui/material/Card";
import Icon from "@mui/material/Icon";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import Table from "examples/Table";
import styles from "layouts/dashboard/components/Projects/styles";
import data from "layouts/dashboard/components/Projects/data";

/**
 * {@code Projects} creates a component to display projects completed in a table.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

function Projects() {
  const { columns, rows } = data();
  const classes = styles();

  return (
    <Card>
      <SuiBox display="flex" justifyContent="space-between" alignItems="center" p={3}>
        <SuiBox>
          <SuiTypography variant="h6" gutterBottom>
            Projects
          </SuiTypography>
          <SuiBox display="flex" alignItems="center" lineHeight={0}>
            <Icon className="font-bold text-info">done</Icon>
            <SuiTypography variant="button" fontWeight="regular" textColor="text">
              &nbsp;<strong>2 done</strong> this year
            </SuiTypography>
          </SuiBox>
        </SuiBox>
      </SuiBox>
      <SuiBox customClass={classes.projects_table}>
        <Table columns={columns} rows={rows} />
      </SuiBox>
    </Card>
  );
}

export default Projects;
