import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import EmployeeSkillForm from "../../data/EmployeeSkillForm";
import React, { useState, useEffect } from "react";
import DataTable from "react-data-table-component";
import DataTableExtensions from "react-data-table-component-extensions";
import "react-data-table-component-extensions/dist/index.css";
import SuiButton from "components/SuiButton";
import Modal from "components/Custom/Modal";
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

function EmployeeSkillTable() {
  const [results, setResults] = useState([]);
  const [open, setOpen] = React.useState(false);
  const [scroll, setScroll] = React.useState("paper");

  const FetchData = async () => {
    await Auth.currentSession().then((res) => {
      const queryString = objToQueryString({
        compId: res.getIdToken().payload["cognito:groups"][0],
      });
      fetch("/api/employeeSkills" + `?${queryString}`, {
        headers: {
          Authorization: "Bearer " + res.getIdToken().getJwtToken(),
        },
      })
        .then((response) => response.json())
        .then((data) => setResults(data));
    });
  };

  useEffect(() => {
    FetchData();
  }, []);

  const handleClickOpen = (scrollType) => () => {
    setOpen(true);
    setScroll(scrollType);
  };

  const handleClose = () => {
    setOpen(false);
  };

  function objToQueryString(obj) {
    const keyValuePairs = [];
    for (const key in obj) {
      keyValuePairs.push(
        encodeURIComponent(key) + "=" + encodeURIComponent(obj[key])
      );
    }
    return keyValuePairs.join("&");
  }

  const remove = (empId, skillId) => {
    console.log(empId + " " + skillId);

    Auth.currentSession().then((res) => {
      const queryString = objToQueryString({
        empId: empId,
        skillId: skillId,
      });

      fetch(`/api/employeeSkills?${queryString}`, {
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + res.getIdToken().getJwtToken(),
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      });
      window.location.reload();
    });
  };

  const columns = [
    {
      name: "Employee Name",
      selector: "name",
      sortable: true,
    },
    {
      name: "Skill",
      selector: "skillName",
      sortable: true,
    },
    {
      name: "Cost",
      selector: "cost",
      sortable: true,
    },
    {
      name: "Experience",
      selector: "experience",
      sortable: true,
    },
    {
      name: "Rating",
      selector: "rating",
      sortable: true,
    },
    {
      key: "edit",
      text: "Edit",
      className: "action",
      width: 100,
      align: "left",
      sortable: false,
      cell: (record) => {
        return (
          <Modal>
            <EmployeeSkillForm attr={record} />
          </Modal>
        );
      },
    },
    {
      key: "delete",
      text: "Delete",
      className: "action",
      width: 100,
      align: "left",
      sortable: false,
      cell: (record) => {
        return (
          <SuiButton onClick={() => remove(record.workPermitNumber, record.skillName)}>
            Delete
          </SuiButton>
        );
      },
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
            Employee Skills
          </SuiTypography>
        </SuiBox>
        <SuiBox pt={1}>
          <SuiButton
            size="large"
            variant="text"
            buttonColor="success"
            onClick={handleClickOpen("paper")}
          >
            Add Entry
          </SuiButton>
        </SuiBox>
        <Dialog
          open={open}
          onClose={handleClose}
          scroll={scroll}
          aria-labelledby="scroll-dialog-title"
          aria-describedby="scroll-dialog-description"
        >
          <DialogTitle id="scroll-dialog-title">
            <SuiTypography
              variant="h5"
              textColor="info"
              fontWeight="bold"
              textGradient
            >
              Add Employee Skill
            </SuiTypography>
          </DialogTitle>
          <DialogContent dividers={scroll === "paper"}>
            <DialogContentText
              id="scroll-dialog-description"
              //ref={descriptionElementRef}
              tabIndex={-1}
            >
              {<EmployeeSkillForm />}
            </DialogContentText>
          </DialogContent>
        </Dialog>
      </div>
      <SuiBox p={3}>
        <DataTableExtensions exportHeaders columns={columns} data={results}>
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
export default EmployeeSkillTable;
