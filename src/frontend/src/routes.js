import Dashboard from "layouts/dashboard";
import Organisation from "layouts/organisation";
import ART from "layouts/art";
import Setting from "layouts/setting";
import LabourSharing from "layouts/labour-sharing";
import LabourDetails from "layouts/labour-details";
import Discovery from "layouts/discover";
import Categories from "layouts/categories";
import Transactions from "layouts/transactions";

import Shop from "examples/Icons/Shop";
import Office from "examples/Icons/Office";
import Settings from "examples/Icons/Settings";
import CustomerSupport from "examples/Icons/CustomerSupport";
import Basket from "examples/Icons/Basket";
import Document from "examples/Icons/Document";
import Cube from "examples/Icons/Cube";
import CreditCard from "examples/Icons/CreditCard";

/**
 * {@code routes} lists all routes in the frontend, which will be displayed on the sidenav
 *
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-10-16
 */

const routes = [
  {
    type: "collapse",
    name: "Dashboard",
    key: "dashboard",
    route: "/dashboard",
    icon: <Shop size="12px" />,
    component: Dashboard,
    noCollapse: true,
  },
  { type: "title", title: "Employee Management", key: "employee-pages" },
  {
    type: "collapse",
    name: "Organisation",
    key: "organisation",
    route: "/organisation",
    icon: <Office size="12px" />,
    component: Organisation,
    noCollapse: true,
  },
  {
    type: "collapse",
    name: "ART (Covid Testing)",
    key: "ART",
    route: "/art",
    icon: <Document size="12px" />,
    component: ART,
    noCollapse: true,
  },
  { type: "title", title: "Labour Marketplace", key: "labour-pages" },
  {
    type: "collapse",
    name: "Labour Sharing",
    key: "labour-sharing",
    route: "/labour-sharing",
    icon: <Basket size="12px" />,
    component: LabourSharing,
    noCollapse: true,
  },
  // {
  //   type: "collapse",
  //   name: "Categories",
  //   key: "categories",
  //   route: "/categories",
  //   icon: <Cube size="12px" />,
  //   component: Categories,
  //   noCollapse: true,
  // },
  {
    type: "collapse",
    name: "Labour Details",
    key: "labour-details",
    route: "/labour-details",
    icon: <CustomerSupport size="12px" />,
    component: LabourDetails,
    noCollapse: true,
  },
  {
    type: "collapse",
    name: "Labour Discovery",
    key: "labour-discovery",
    route: "/labour-discovery",
    icon: <Shop size="12px" />,
    component: Discovery,
    noCollapse: true,
  },
  {
    type: "collapse",
    name: "Transaction History",
    key: "transaction-history",
    route: "/transaction-history",
    icon: <CreditCard size="12px" />,
    component: Transactions,
    noCollapse: true,
  },
  { type: "title", title: "Configuration", key: "configuration-pages" },
  {
    type: "collapse",
    name: "Settings",
    key: "settings",
    route: "/settings",
    icon: <Settings size="12px" />,
    component: Setting,
    noCollapse: true,
  },
  
];

export default routes;
