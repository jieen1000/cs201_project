import Grid from "@mui/material/Grid";
import Icon from "@mui/material/Icon";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import Footer from "examples/Footer";
import MiniStatisticsCard from "examples/Cards/StatisticsCards/MiniStatisticsCard";
import ReportsBarChart from "examples/Charts/BarCharts/ReportsBarChart";
import GradientLineChart from "examples/Charts/LineCharts/GradientLineChart";
import typography from "assets/theme/base/typography";
import Projects from "layouts/dashboard/components/Projects";
import ActivitiesOverview from "layouts/dashboard/components/ActivitiesOverview";
import reportsBarChartData from "layouts/dashboard/data/reportsBarChartData";
import gradientLineChartData from "layouts/dashboard/data/gradientLineChartData";
import GeneralNewsCard from "./components/GeneralNewsCard";
import AdvisoryCard from "./components/AdvisoryCard/index";

/**
 * {@code Dashboard} provides layout for the main dashboard.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

function Dashboard() {
  const { size } = typography;
  const { chart, items } = reportsBarChartData;
  
  return (
    <DashboardLayout>
      <DashboardNavbar />
      <SuiBox py={3}>
        <SuiBox mb={3}>
          <Grid container spacing={3}>
            <Grid item xs={12} sm={6} xl={3}>
              <MiniStatisticsCard
                title={{ text: "today's manpower" }}
                count="121"
                percentage={{ color: "success", text: "+8%" }}
                icon={{ color: "info", component: "person" }}
              />
            </Grid>
            <Grid item xs={12} sm={6} xl={3}>
              <MiniStatisticsCard
                title={{ text: "outstanding bill" }}
                count="$72,030.51"
                percentage={{ color: "success", text: "+3%" }}
                icon={{ color: "info", component: "paid" }}
              />
            </Grid>
            <Grid item xs={12} sm={6} xl={3}>
              <MiniStatisticsCard
                title={{ text: "expiring work permits" }}
                count="3"
                percentage={{ color: "error", text: "+50%" }}
                icon={{ color: "info", component: "assignment_ind" }}
              />
            </Grid>
            <Grid item xs={12} sm={6} xl={3}>
              <MiniStatisticsCard
                title={{ text: "Current Borrowed Manpower" }}
                count="17"
                percentage={{ color: "success", text: "+5%" }}
                icon={{
                  color: "info",
                  component: "share",
                }}
              />
            </Grid>
          </Grid>
        </SuiBox>
        <SuiBox mb={3}>
          <Grid container spacing={3}>
            <Grid item xs={12} lg={7}>
              <GeneralNewsCard />
            </Grid>
            <Grid item xs={12} lg={5}>
              <AdvisoryCard />
            </Grid>
          </Grid>
        </SuiBox>
        <SuiBox mb={3}>
          <Grid container spacing={3}>
            <Grid item xs={12} lg={5}>
              <ReportsBarChart
                title="ART Schedule"
                description={
                  <>
                    (<strong>+23%</strong>) than last week
                  </>
                }
                chart={chart}
                items={items}
              />
            </Grid>
            <Grid item xs={12} lg={7}>
              <GradientLineChart
                title="Cashflow Overview"
                description={
                  <SuiBox display="flex" alignItems="center">
                    <SuiBox
                      fontSize={size.lg}
                      color="success"
                      mb={0.3}
                      mr={0.5}
                      lineHeight={0}
                    >
                      <Icon className="font-bold">arrow_upward</Icon>
                    </SuiBox>
                    <SuiTypography
                      variant="button"
                      textColor="text"
                      fontWeight="medium"
                    >
                      4% more{" "}
                      <SuiTypography
                        variant="button"
                        textColor="text"
                        fontWeight="regular"
                      >
                        in 2021
                      </SuiTypography>
                    </SuiTypography>
                  </SuiBox>
                }
                height="20.25rem"
                chart={gradientLineChartData}
              />
            </Grid>
          </Grid>
        </SuiBox>
        <Grid container spacing={3}>
          <Grid item xs={12} md={6} lg={8}>
            <Projects />
          </Grid>
          <Grid item xs={12} md={6} lg={4}>
            <ActivitiesOverview />
          </Grid>
        </Grid>
      </SuiBox>
      <Footer />
    </DashboardLayout>
  );
}

export default Dashboard;
