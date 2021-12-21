import * as React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

import Modal from '@mui/material/Modal';
import SuiButton from "../../components/SuiButton";

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
  overflow:'scroll',
  height: '70%',
  width : '50%'
};

export default function BasicModal(props) {
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  return (
    <div>
      <SuiButton
            component="a"
            variant="caption"
            textColor="secondary"
            fontWeight="medium"
            onClick={handleOpen} > Edit </SuiButton>
      
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        
        <Box sx={style}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
      Edit Employee
    </Typography>
          {props.children}
        </Box>
      </Modal>
    </div>
  );
}
