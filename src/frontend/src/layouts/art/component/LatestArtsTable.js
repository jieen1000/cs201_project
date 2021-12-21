import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import SuiBox from "components/SuiBox";
import SuiBadge from "components/SuiBadge";
import SuiTypography from "components/SuiTypography";
import TestForm from "../data/TestForm";
import React, { useState, useEffect } from "react";
import DataTable from "react-data-table-component";
import DataTableExtensions from "react-data-table-component-extensions";
import "react-data-table-component-extensions/dist/index.css";
import SuiButton from "components/SuiButton";
import { Auth } from "aws-amplify";


function LatestArtsTable() {

    const [results, setResults] = useState([]);
    const [open, setOpen] = React.useState(false);
    const [scroll, setScroll] = React.useState("paper");

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

    const FetchData = async () => {
        await Auth.currentSession().then(res => {
            const queryString = objToQueryString({
                compId: res.getIdToken().payload['cognito:groups'][0],
            });
            fetch('/api/covidTest/latest' + `?${queryString}`, {
                headers: {
                    'Authorization': 'Bearer ' + res.getIdToken().getJwtToken()
                }
            })
                .then(response => response.json())
                .then(data => setResults(data))
        })
    }

    useEffect(() => {
        FetchData()
    }, []);

    const remove = (testid) => {
        Auth.currentSession().then((res) => {
            fetch(`/api/covidTest/${testid}`, {
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
            name: "Employee Work Permit Number",
            selector: "employeeWP",
            sortable: true
        },
        {
            name: "Employee Name",
            selector: "employeeName",
            sortable: true
        },
        {
            name: "Date Of Test",
            selector: "dateOfTest",
            sortable: true
        },
        {
            name: "Test Expiry Date",
            selector: "expiryDate",
            sortable: true
        },
        {
            name: "Result",
            selector: "result",
            sortable: true,
            cell: (record) => {
                return record.result ? (
                    <SuiBadge variant="gradient" badgeContent="Postive" color="error" size="extra-small" />
                ) : (
                    <SuiBadge variant="gradient" badgeContent="Negative" color="success" size="extra-small" />

                );
            },
        },
        {
            key: "action",
            text: "Action",
            className: "action",
            width: 100,
            align: "left",
            sortable: false,
            cell: (record) => {
                return (
                    <SuiButton onClick={() => remove(record.id)}>
                        Delete
                    </SuiButton>
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
                        Latest ART Result Database
                    </SuiTypography>
                </SuiBox>
                <SuiBox pt={1}>
                    <SuiButton
                        size="large"
                        variant="text"
                        buttonColor="success"
                        onClick={handleClickOpen("paper")}
                    >
                        Add Result
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
                            Add Result
                        </SuiTypography>
                    </DialogTitle>
                    <DialogContent dividers={scroll === "paper"}>
                        <DialogContentText
                            id="scroll-dialog-description"
                            tabIndex={-1}
                        >
                            {<TestForm />}
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

} export default LatestArtsTable;