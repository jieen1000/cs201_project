import React, { useState } from "react";
import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import { useDispatch, useSelector } from "react-redux";
import { setProjects } from "redux/actions/projectsActions";
import ProjectForm from "../../data/ProjectForm";
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
 * @version 1.1
 * @since 2021-10-18
 */

function ProjectTable() {
  const projects = useState([]);
  const empty = {};

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

  const remove = (projectId) => {
    Auth.currentSession().then((res) => {
      fetch(`/api/project/${projectId}`, {
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + res.getIdToken().getJwtToken(),
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      }).then(() => {
        let updatedProjects = projects.filter((i) => i.projectId !== projectId);
        dispatch(setProjects(updatedProjects));
        window.location.reload();
      });
    });
  };

  const columns = [
    {
      name: "Project Id",
      selector: "projectId",
      sortable: true,
    },
    {
      name: "Project Name",
      selector: "ProjectName",
      sortable: true,
    },
    {
      name: "Budget",
      selector: "budget",
      sortable: true,
    },
    {
      name: "Start Date",
      selector: "startDate",
      sortable: true,
    },
    {
      name: "Completion Date",
      selector: "completionDate",
      sortable: true,
    },
    {
      name: "Progress",
      selector: "progress",
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
            <ProjectForm attr={record} />
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
          <SuiButton onClick={() => remove(record.workPermitNumber)}>
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
            <EmployeeImage employee={record.projectId} />
          </ImageModal>
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
            Projects
          </SuiTypography>
        </SuiBox>
        <SuiBox pt={1}>
          <SuiButton
            size="large"
            variant="text"
            buttonColor="success"
            onClick={handleClickOpen("paper")}
          >
            Add Project
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
              Add Project
            </SuiTypography>
          </DialogTitle>
          <DialogContent dividers={scroll === "paper"}>
            <DialogContentText
              id="scroll-dialog-description"
              ref={descriptionElementRef}
              tabIndex={-1}
            >
              {<ProjectForm attr={empty} />}
            </DialogContentText>
          </DialogContent>
        </Dialog>
      </div>
      <SuiBox p={3}>
        <DataTableExtensions exportHeaders columns={columns} data={projects}>
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
export default ProjectTable;
