import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import EmployeeForm from "../../data/EmployeeForm";
import React from "react";
import SuiBadge from "components/SuiBadge";
import SuiAvatar from "components/SuiAvatar";
import { useDispatch, useSelector } from "react-redux";
import { setEmployees } from "redux/actions/employeesActions";
import DataTable from "react-data-table-component";
import DataTableExtensions from "react-data-table-component-extensions";
import "react-data-table-component-extensions/dist/index.css";
import SuiButton from "components/SuiButton";
import Modal from "components/Custom/Modal";
import EmployeeImage from "../../data/EmployeeImage";
import ImageModal from "components/Custom/ImageModal";
import { Auth } from "aws-amplify";

/**
 * {@code EmployeeTable} creates the layout for the CRUD interface.
 *
 * @author Chong Zhan Han
 * @author Tan Jie En
 * @author Teo Keng Swee
 * @author Pang Jun Rong
 * @version 1.2
 * @since 2021-10-18
 */


function Labour({ image, name, company }) {
  return (
    <SuiBox display="flex" alignItems="center" px={1} py={0.5} sx={{ width: 200 }}>
      <SuiBox mr={2}>
        <SuiAvatar src={image} alt={name} size="sm" variant="rounded" />
      </SuiBox>
      <SuiBox display="flex" flexDirection="column">
        <SuiTypography variant="button" fontWeight="medium">
          {name}
        </SuiTypography>
        <SuiTypography variant="caption" textColor="secondary">
          {company}
        </SuiTypography>
      </SuiBox>
    </SuiBox>
  );
}

function EmployeeTable() {

  const results = useSelector((state) => state.allEmployees.employees);
  const empty = {};
  const dispatch = useDispatch();
  const [open, setOpen] = React.useState(false);
  const [scroll, setScroll] = React.useState("paper");

  const handleClickOpen = (scrollType) => () => {
    setOpen(true);
    setScroll(scrollType);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const descriptionElementRef = React.useRef(null);
  React.useEffect(() => {
    if (open) {
      const { current: descriptionElement } = descriptionElementRef;
      if (descriptionElement !== null) {
        descriptionElement.focus();
      }
    }
  }, [open]);

  const remove = (workId) => {
    Auth.currentSession().then((res) => {
      fetch(`/api/employees/${workId}`, {
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + res.getIdToken().getJwtToken(),
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      }).then(() => {
        let updatedEmployees = results.filter((i) => i.workId !== workId);
        dispatch(setEmployees(updatedEmployees));
       
      });

      window.location.reload();
    });
    
  };


  const columns = [
    {
      name: "Employee",
      selector: "name",
      sortable: true,
      width: "25vh",
      cell: (record) => {
        return (
          <Labour
            image={record.profileURL}
            name={record.name}
            company={record.company}
          />
        )
      }
    },
    {
      name: "Work Permit NUmber",
      selector: "workPermitNumber",
      sortable: true
    },
    {
      name: "Work Contact Number",
      selector: "workContactNumber",
      sortable: true
    },
    {
      name: "Employee Role",
      selector: "employeeRole",
      sortable: true
    },
    {
      name: "Work Site Location",
      selector: "workSiteLocation",
      sortable: true
    },
    {
      name: "Work Permit Date Of Issue",
      selector: "workPermitDateOfIssue",
      sortable: true
    },
    {
      name: "Work Permit Expiry Date",
      selector: "workPermitExpiryDate",
      sortable: true
    },
    {
      name: "Vaccination Status",
      selector: "vaccStatus",
      sortable: true,
      cell: (record) => {
        return record.vaccStatus ? (
          <SuiBadge variant="gradient" badgeContent="Vaccinated" color="success" size="extra-small" />
        ) : (
          <SuiBadge variant="gradient" badgeContent="Not Vaccinated" color="error" size="extra-small" />

        );
      },
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
            <EmployeeForm attr={record} />
          </Modal>
        )
      }
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
          <SuiButton
            onClick={() => remove(record.workPermitNumber)}
          >
            Delete
          </SuiButton>
        );
      },
    },
    {
      key: "upload",
      text: "Upload",
      className: "action",
      width: 100,
      align: "left",
      sortable: false,
      cell: (record) => {
        return (
          <ImageModal>
            <EmployeeImage employee={record.workPermitNumber} />
          </ImageModal>
        );
      },
    }

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
            Employee
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
              Add Employee
            </SuiTypography>
          </DialogTitle>
          <DialogContent dividers={scroll === "paper"}>
            <DialogContentText
              id="scroll-dialog-description"
              //ref={descriptionElementRef}
              tabIndex={-1}
            >
              {<EmployeeForm attr={empty} />}
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

} export default EmployeeTable;