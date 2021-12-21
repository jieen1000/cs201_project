import React, { Component } from "react";
import { Auth } from "aws-amplify";
import { Button } from "@mui/material";
import SuiTypography from "components/SuiTypography";
import DialogContent from "@mui/material/DialogContent";
import { Divider } from "@mui/material";


import SuiBox from "components/SuiBox";


/**
 * {@code EmployeeTable} creates the layout for the CRUD interface.
 *
 * @author Tan Jie En
 * @author Bryan Tan
 * @version 1.0
 * @since 2021-10-20
 */

class EmployeeImage extends Component {
  // employees = state => ({employees: state.allEmployees.employees});

  constructor(props) {
    super(props);
    this.state = {
      // Initially, no file is selected
      selectedFile: null,
      isUploaded: false,
    };
  }

  // On file select (from the pop up)
  onFileChange = (event) => {
    // Update the state
    this.setState({ selectedFile: event.target.files[0] });
  };

  // On file upload (click the upload button)
  onFileUpload = () => {
    if (!this.state.selectedFile) {
      alert("Please upload a file first.")
      return;
    }
    // Create an object of formData
    const formData = new FormData();
    // Update the formData object
    formData.append(
      "file",
      this.state.selectedFile,
      this.state.selectedFile.name
    );

    // Details of the uploaded file
    console.log(this.state.selectedFile);

    this.setState({ isUploaded: true });


    function objToQueryString(obj) {
      const keyValuePairs = [];
      for (const key in obj) {
        keyValuePairs.push(
          encodeURIComponent(key) + "=" + encodeURIComponent(obj[key])
        );
      }
      return keyValuePairs.join("&");
    }

    // Request made to the backend api
    // Send formData object
    const response = Auth.currentSession().then((res) => {
      const queryString = objToQueryString({
        empId: this.props.employee,
      });
      fetch(
        "/api/employees/image"
        + `?${queryString}`,
        {
          method: "POST",
          headers: {
            Authorization: "Bearer " + res.getIdToken().getJwtToken(),
            Accept: "multipart/form-data"
          },
          body: formData,
        }
      );
      console.log(formData);
    });
  };

  // File content to be displayed after
  // file upload is complete
  fileData = () => {
    if (this.state.isUploaded) {
      window.location.reload();

    }
    if (this.state.selectedFile) {
      return (
        <SuiBox mb={3}>
          <div>
          <SuiTypography
            variant="h6"
            textColor="info"
            fontWeight="bold"
            textGradient>
            File Details
          </SuiTypography>
          </div>
          <SuiTypography
          variant="h7"
          textGradient>
            <p>File Name: {this.state.selectedFile.name}</p>
            <p>File Type: {this.state.selectedFile.type}</p>
            <p> Last Modified:{" "}
                  {this.state.selectedFile.lastModifiedDate.toDateString()} </p>
          </SuiTypography>
          
        </SuiBox>
      );
    } else {
      return (
        <SuiTypography
          variant="h7"
          
          textGradient>
          Choose a photo to upload
        </SuiTypography>
        
      );
    }
  };

  render() {
    return (
      <DialogContent>
        {this.fileData()}
        <Divider variant="middle" />

        <Button
          variant="contained"
          component="label"
          style={{
            margin: "20px",
          }}
        >
          Select Image
          <input type="file" onChange={this.onFileChange} hidden />
        </Button>
        <Button onClick={this.onFileUpload}>Upload</Button>
      </DialogContent>

    );
  }
}

export default EmployeeImage;
