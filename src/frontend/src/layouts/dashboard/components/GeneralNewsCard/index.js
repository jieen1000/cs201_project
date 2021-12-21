import Card from "@mui/material/Card";
import Grid from "@mui/material/Grid";
import Icon from "@mui/material/Icon";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import styles from "layouts/dashboard/components/GeneralNewsCard/styles";
import { Auth } from "aws-amplify";
import { useState, useEffect } from "react";

import Carousel from "react-material-ui-carousel";

/**
 * {@code GeneralNewsCard} creates a component to display general COVID-19 related media releases.
 *
 * @author Pang Jun Rong
 * @author Bryan Tan Zhi Yong
 * @version 1.0
 * @since 2021-10-16
 */

function GeneralNewsCard() {
  const classes = styles();
  const [boxes, setboxes] = useState([]);

  useEffect(() => {
    Auth.currentSession().then((res) => {
      fetch("/api/dashboard/news", {
        headers: {},
      })
        .then((response) => response.json())
        .then((data) => setboxes(Array.from(data)));
    });
  }, []);

  let headers = boxes.map((box) => (
    <Grid container spacing={3}>
      <Grid item item xs={12} lg={7}>
        <SuiBox textColor="white" flexDirection="column">
          <SuiBox mb={3}>
            <SuiBox>
              <SuiTypography
                key={box.id}
                variant="h5"
                fontWeight="bold"
                gutterBottom
              >
                {box.header}
              </SuiTypography>
            </SuiBox>
          </SuiBox>
          <SuiTypography>
            <SuiTypography mb={3} variant="body2">
              {box.excerpt}
            </SuiTypography>
            <SuiTypography
              key={box.id}
              component="a"
              href={box.url}
              variant="button"
              textColor="text"
              fontWeight="medium"
              customClass={classes.generalNewsCard_button}
            >
              {" "}
              Read More
              <Icon className="font-bold">arrow_forward</Icon>
            </SuiTypography>
          </SuiTypography>
        </SuiBox>
      </Grid>
      <Grid item xs={12} lg={5}>
        <SuiBox
          p={3}
          class="img"
          justifyContent="center"
          alignItems="center"
          style={{ height: "20vh" }}
          customClass={classes.remove_img}
        >
          <img
            height="100%"
            width="100%"
            style={{ borderRadius: "10%" }}
            src={box.imageUrl}
          />
        </SuiBox>
      </Grid>
    </Grid>
  ));

  return (
    <Card style={{ height: "40vh" }}>
      <SuiBox p={2} position="relative" height="100%">
        <SuiBox>
          <SuiBox mb={3} pt={1}>
            <SuiTypography variant="h5" textColor="text" fontWeight="medium">
              General COVID-19 News
            </SuiTypography>
          </SuiBox>
        </SuiBox>
        <Grid container spacing={3}>
          <Grid item xs={20} lg={40}>
            <SuiBox display="flex" flexDirection="column" mb={2} pt={1}>
              <Carousel animation="slide" indicators={false} interval="5000">
                {headers}
              </Carousel>
            </SuiBox>
          </Grid>
        </Grid>
      </SuiBox>
    </Card>
  );
}

export default GeneralNewsCard;
