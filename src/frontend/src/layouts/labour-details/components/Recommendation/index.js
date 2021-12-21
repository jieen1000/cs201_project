import {Icon} from "@mui/material";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import Table from "examples/Table";
import data from "layouts/labour-details/data";




function Recommendation(props) {

    const { columns, rows } = data(props.attr);
    

    return (
        <div><SuiBox display="flex" justifyContent="space-between" alignItems="center" p={3}>
            <SuiBox>
                <SuiTypography variant="h3" gutterBottom>
                    Recommendations
                </SuiTypography>
                <SuiBox display="flex" alignItems="center" lineHeight={0}>
                    <Icon className="font-bold text-info">recommend</Icon>
                    <SuiTypography variant="button" fontWeight="regular" textColor="text">
                        &nbsp;based on your <strong>past interactions</strong>.
                    </SuiTypography>
                </SuiBox>
            </SuiBox>
        </SuiBox>
            <SuiBox>
                <Table columns={columns} rows={rows} />
            </SuiBox>
            </div>
    );

} export default Recommendation;