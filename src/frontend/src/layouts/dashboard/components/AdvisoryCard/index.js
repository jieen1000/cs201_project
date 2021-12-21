import Card from "@mui/material/Card";
import Icon from "@mui/material/Icon";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import styles from "layouts/dashboard/components/AdvisoryCard/styles";
import { Auth } from 'aws-amplify';
import { useState, useEffect } from "react";
import Carousel from 'react-material-ui-carousel';

/**
 * {@code AdvisoryCard} creates a component to display industry-related media releases.
 *
 * @author Pang Jun Rong
 * @author Bryan Tan Zhi Yong
 * @version 1.0
 * @since 2021-10-16
 */

function AdvisoryCard() { 
  const[boxes, setboxes] = useState([]);

  useEffect(() => {
    Auth.currentSession().then(res => {
      fetch('/api/dashboard', {
          headers: {
          }})
          .then(response => response.json())
          .then(data => setboxes(Array.from(data)))
    })
  }, []);
  
  const classes = styles();
  let headers = boxes.map(box =>
    <SuiTypography variant="body2" textColor="white">
      <SuiTypography key={box.id} textColor="white" variant="body2" fontWeight="bold">
        {box.date}
      </SuiTypography>
      <SuiTypography style={{height:190}} key={box.id} textColor="white" variant="body2">
        {box.header} 
      </SuiTypography>
      <SuiTypography key = {box.id}
      component="a"
      href={box.url}
      variant="button"
      textColor="white"
      fontWeight="medium"
      customClass={classes.advisoryCard_button}
    > Read More
      <Icon className="font-bold">arrow_forward</Icon>
    </SuiTypography>
    </SuiTypography>
    
    );
    

  return (
    <Card style={{height: "40vh"}}>
      <SuiBox position="relative" height="100%">
        <SuiBox customClass={classes.advisoryCard_content}>
          <SuiBox mb={3} pt={1}>
            <SuiTypography variant="h5" textColor="white" fontWeight="bold">
              Latest Updates from BCA
            </SuiTypography>
          </SuiBox>
          <SuiBox mb={2}>
            <SuiTypography variant="body2" textColor="white">
            <Carousel animation="slide" indicators={false} interval="5000">
                {headers}
            </Carousel>
            </SuiTypography>
          </SuiBox>
        </SuiBox>
      </SuiBox>
    </Card>
  );
}

export default AdvisoryCard;    