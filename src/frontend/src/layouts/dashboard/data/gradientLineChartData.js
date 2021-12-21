/**
 * {@code Dashboard/data/gradientLineChartData} provides static data for the cashflow chart.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

export default {
  labels: ["Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
  datasets: [
    {
      label: "Accounts Receivable",
      color: "info",
      data: [50, 40, 300, 220, 500, 250, 400, 230, 500],
    },
    {
      label: "Accounts Payable",
      color: "dark",
      data: [30, 90, 40, 140, 290, 290, 340, 230, 400],
    },
  ],
};
