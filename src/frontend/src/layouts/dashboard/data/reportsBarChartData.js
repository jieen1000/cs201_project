/**
 * {@code Dashboard/data/reportsBarChartData} provides static data for the ART scheduling.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

export default {
  chart: {
    labels: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"],
    datasets: { label: "ART Tests", data: [3, 37, 40, 21, 11, 10, 8] },
  },
  items: [
    {
      icon: { color: "primary", component: "person" },
      label: "eligible",
      progress: { content: "110", percentage: 90 },
    },
    {
      icon: { color: "info", component: "today" },
      label: "today",
      progress: { content: "40", percentage: 30 },
    },
    {
      icon: { color: "warning", component: "next_plan" },
      label: "tomorrow",
      progress: { content: "21", percentage: 15 },
    },
    {
      icon: { color: "error", component: "flag" },
      label: "flagged",
      progress: { content: "2", percentage: 10 },
    },
  ],
};
