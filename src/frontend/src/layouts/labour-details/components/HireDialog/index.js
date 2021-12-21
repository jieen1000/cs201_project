import * as React from 'react';
import PropTypes from 'prop-types';
import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import Availability from "../Availability"
import SuiButton from "components/SuiButton";
import SuiBox from 'components/SuiBox';
import { Grid } from '@mui/material';
import Checkout from '../Checkout';
import { useState, useEffect } from "react";
import { DateRangePicker } from 'react-date-range';


/**
 * {@code HireDialog} creates the dialog component for both availability and checkout sub-components.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

const emails = ['username@gmail.com', 'user02@gmail.com'];

function SimpleDialog(props) {
    const { employeeSkill, onClose, selectedValue, open } = props;

    const handleClose = () => {
        onClose(selectedValue);
    };

    const handleListItemClick = (value) => {
        onClose(value);
    };

    const [selection, setSelection] = useState({startDate: new Date(), endDate: new Date()});
    const onChangeDateHandler = (selection) => {
        setSelection(selection);
    }

    return (
        <SuiBox>
            <Dialog onClose={handleClose} open={open}>
                <SuiBox p={1}>
                    <DialogTitle>Select Hire Sharing Duration</DialogTitle>
                    <SuiBox pr={3}>
                        <Availability onChangeDate={onChangeDateHandler}/>
                    </SuiBox>
                    <SuiBox px={3} color="white">
                        
                    </SuiBox>
                </SuiBox>
                <SuiBox p={3}>
                    <Grid container alignItems="center" justifyContent="center">
                        <Grid item p={3}>
                            <Checkout 
                                employeeSkill={employeeSkill}
                                selectedValue={selectedValue}
                                open={open}
                                onClose={handleClose}
                                selection={selection}/>
                        </Grid>
                    </Grid>
                </SuiBox>
            </Dialog>
        </SuiBox>
    );
}

SimpleDialog.propTypes = {
    onClose: PropTypes.func.isRequired,
    open: PropTypes.bool.isRequired,
    selectedValue: PropTypes.string.isRequired,
};

export default function HireDialog(props) {
    const [open, setOpen] = React.useState(false);
    const [selectedValue, setSelectedValue] = React.useState(emails[1]);
    const employeeSkill = props.employeeSkillDTO || {};

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = (value) => {
        setOpen(false);
        setSelectedValue(value);
    };

    return (
        <div>
            <SuiButton size="large" variant="gradient" buttonColor="success" onClick={handleClickOpen} circular>
                <SuiBox px={3} color="white">
                Check Availability 
                </SuiBox>
            </SuiButton>
            <SimpleDialog 
                employeeSkill={employeeSkill}
                selectedValue={selectedValue}
                open={open}
                onClose={handleClose}
            />
        </div>
    );
}