import Card from "@mui/material/Card";
import Icon from "@mui/material/Icon";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import Table from "examples/Table";
import styles from "layouts/labour-sharing/components/CategoryTable/styles";
import data from "layouts/labour-sharing/components/CategoryTable/data";



/**
 * {@code CategoryTable} creates the component table for labour sharing categories.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */
function CategoryTable() {


  const { columns, rows } = data();
  const classes = styles();

  return (
    <Card>
      <SuiBox display="flex" justifyContent="space-between" alignItems="center" p={3}>
        <SuiBox>
          <SuiTypography variant="h3" gutterBottom>
            Skill Categories
          </SuiTypography>
          <SuiBox display="flex" alignItems="center" lineHeight={0}>
            <Icon className="font-bold text-info">category</Icon>
            <SuiTypography variant="button" fontWeight="regular" textColor="text">
              &nbsp;select skilled labour from over <strong>10 specializations</strong>.
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

export default CategoryTable;
