import React, { useState, useEffect } from "react";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import DataTable from "react-data-table-component";
import DataTableExtensions from "react-data-table-component-extensions";
import "react-data-table-component-extensions/dist/index.css";
import { Auth } from "aws-amplify";

/**
 * {@code EmployeeTable} creates the layout for the CRUD interface.
 *
 * @author Chong Zhan Han
 * @author Tan Jie En
 * @author Teo Keng Swee
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-10-18
 */

function IncomingTable() {
  const [transactions, setTransactions] = useState([]);

  function objToQueryString(obj) {
    const keyValuePairs = [];
    for (const key in obj) {
      keyValuePairs.push(
        encodeURIComponent(key) + "=" + encodeURIComponent(obj[key])
      );
    }
    return keyValuePairs.join("&");
  }

  const FetchData = async () => {
    await Auth.currentSession().then((res) => {
      const queryString = objToQueryString({
        compId: res.getIdToken().payload["cognito:groups"][0],
      });
      fetch("/api/transactions/outgoing" + `?${queryString}`, {
        headers: {
          Authorization: "Bearer " + res.getIdToken().getJwtToken(),
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      })
        .then((response) => response.json())
        .then((data) => setTransactions(data))
        .then((data) => console.log(data));
    });
  };

  useEffect(() => {
    FetchData();
  }, []);

  const columns = [
    {
      name: "Employee Id",
      selector: "employeeId",
      sortable: true,
    },
    {
      name: "Loan Company UEN",
      selector: "loanCompanyId",
      sortable: true,
    },
    {
      name: "Borrowing Company UEN",
      selector: "borrowingCompanyId",
      sortable: true,
    },
    {
      name: "Start Date",
      selector: "startDate",
      sortable: true,
    },
    {
      name: "End Date",
      selector: "endDate",
      sortable: true,
    },
    {
      name: "Total Cost",
      selector: "totalCost",
      sortable: true,
    },
    {
      name: "Status",
      selector: "status",
      sortable: true,
    },
  ];

  return (
    <div className="main">
      <div style={{ padding: "1em" }}>
        <SuiBox py={3}>
          <SuiTypography
            variant="h4"
            textColor="info"
            fontWeight="bold"
            textGradient
          >
            Outgoing Transactions
          </SuiTypography>
        </SuiBox>
      </div>
      <SuiBox p={3}>
      <DataTableExtensions exportHeaders columns={columns} data={transactions}>
        <DataTable
          noHeader
          defaultSortField="id"
          defaultSortAsc={false}
          pagination
          highlightOnHover
        />
      </DataTableExtensions>
      </SuiBox>
    </div>
  );
}
export default IncomingTable;
