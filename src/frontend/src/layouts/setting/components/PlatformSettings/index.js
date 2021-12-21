import { useState } from "react";

/**
 * {@code EmployeeTable} creates the layout for the CRUD interface.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-22
 */

// @mui material components
import Card from "@mui/material/Card";
import Switch from "@mui/material/Switch";

// Soft UI Dashboard PRO React components
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";

function PlatformSettings() {
  const [followsMe, setFollowsMe] = useState(true);
  const [answersPost, setAnswersPost] = useState(false);
  const [mentionsMe, setMentionsMe] = useState(true);
  const [newLaunches, setNewLaunches] = useState(false);
  const [productUpdate, setProductUpdate] = useState(true);
  const [newsletter, setNewsletter] = useState(true);

  return (
    <Card>
      <SuiBox pt={2} px={2}>
        <SuiTypography variant="h6" fontWeight="medium" textTransform="capitalize">
          platform settings
        </SuiTypography>
      </SuiBox>
      <SuiBox pt={1.5} pb={2} px={2} lineHeight={1.25}>
        <SuiTypography
          variant="caption"
          fontWeight="bold"
          textColor="text"
          textTransform="uppercase"
        >
          account
        </SuiTypography>
        <SuiBox display="flex" py={1} mb={0.25}>
          <SuiBox mt={0.25}>
            <Switch checked={followsMe} onChange={() => setFollowsMe(!followsMe)} />
          </SuiBox>
          <SuiBox width="80%" ml={2}>
            <SuiTypography variant="button" fontWeight="regular" textColor="text">
              Email me when work permits are expiring
            </SuiTypography>
          </SuiBox>
        </SuiBox>
        <SuiBox display="flex" py={1} mb={0.25}>
          <SuiBox mt={0.25}>
            <Switch checked={answersPost} onChange={() => setAnswersPost(!answersPost)} />
          </SuiBox>
          <SuiBox width="80%" ml={2}>
            <SuiTypography variant="button" fontWeight="regular" textColor="text">
              Email me about daily ART results
            </SuiTypography>
          </SuiBox>
        </SuiBox>
        <SuiBox display="flex" py={1} mb={0.25}>
          <SuiBox mt={0.25}>
            <Switch checked={mentionsMe} onChange={() => setMentionsMe(!mentionsMe)} />
          </SuiBox>
          <SuiBox width="80%" ml={2}>
            <SuiTypography variant="button" fontWeight="regular" textColor="text">
              Email me when changes are made to the database
            </SuiTypography>
          </SuiBox>
        </SuiBox>
        <SuiBox mt={3}>
          <SuiTypography
            variant="caption"
            fontWeight="bold"
            textColor="text"
            textTransform="uppercase"
          >
            labour sharing
          </SuiTypography>
        </SuiBox>
        <SuiBox display="flex" py={1} mb={0.25}>
          <SuiBox mt={0.25}>
            <Switch checked={newLaunches} onChange={() => setNewLaunches(!newLaunches)} />
          </SuiBox>
          <SuiBox width="80%" ml={2}>
            <SuiTypography variant="button" fontWeight="regular" textColor="text">
              Payment Invoices
            </SuiTypography>
          </SuiBox>
        </SuiBox>
        <SuiBox display="flex" py={1} mb={0.25}>
          <SuiBox mt={0.25}>
            <Switch checked={productUpdate} onChange={() => setProductUpdate(!productUpdate)} />
          </SuiBox>
          <SuiBox width="80%" ml={2}>
            <SuiTypography variant="button" fontWeight="regular" textColor="text">
              Recommendations
            </SuiTypography>
          </SuiBox>
        </SuiBox>
        <SuiBox display="flex" py={1} mb={0.25}>
          <SuiBox mt={0.25}>
            <Switch checked={newsletter} onChange={() => setNewsletter(!newsletter)} />
          </SuiBox>
          <SuiBox width="80%" ml={2}>
            <SuiTypography variant="button" fontWeight="regular" textColor="text">
              Labour-Hire Requests
            </SuiTypography>
          </SuiBox>
        </SuiBox>
      </SuiBox>
    </Card>
  );
}

export default PlatformSettings;
