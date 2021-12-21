import Card from "@mui/material/Card";
import Icon from "@mui/material/Icon";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import TimelineItem from "examples/Timeline/TimelineItem";

/**
 * {@code OrderOverview} creates a component to display contextual information on actions and/or alerts.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

function OrdersOverview() {
  return (
    <Card className="h-100">
      <SuiBox pt={3} px={3}>
        <SuiTypography variant="h6" fontWeight="medium">
          Activities Overview
        </SuiTypography>
        <SuiBox mt={1} mb={2}>
          <SuiTypography variant="button" textColor="text" fontWeight="regular">
            <SuiTypography display="inline" variant="body2" verticalAlign="middle">
              <Icon className="font-bold text-success">arrow_upward</Icon>
            </SuiTypography>
            &nbsp;
            <SuiTypography variant="button" textColor="text" fontWeight="medium">
              24%
            </SuiTypography>{" "}
            this month
          </SuiTypography>
        </SuiBox>
      </SuiBox>
      <SuiBox p={2}>
        <TimelineItem
          color="success"
          icon="share"
          title="4 Incoming Shared Labour"
          dateTime="22 DEC 7:20 PM"
        />
        <TimelineItem
          color="error"
          icon="local_hospital"
          title="2 Suspected Positive ART Results"
          dateTime="21 DEC 11 PM"
        />
        <TimelineItem
          color="error"
          icon="announcement"
          title="New BCA Advisory Release"
          dateTime="21 DEC 9:34 PM"
        />
        <TimelineItem
          color="info"
          icon="article"
          title="Daily General COVID-19 News"
          dateTime="20 DEC 2:20 AM"
        />
        <TimelineItem
          color="warning"
          icon="exit_to_app"
          title="2 Employees Shared & Outgoing"
          dateTime="18 DEC 4:54 AM"
        />
        <TimelineItem
          color="success"
          icon="paid"
          title="Monthly Financials for Employee Sharing"
          dateTime="17 DEC"
        />
      </SuiBox>
    </Card>
  );
}

export default OrdersOverview;
