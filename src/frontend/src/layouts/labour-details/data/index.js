import SuiBox from "components/SuiBox";
import SuiButton from "components/SuiButton";
import SuiTypography from "components/SuiTypography";
import { Rating } from "@mui/material";
import { Link } from 'react-router-dom';


/**
 * {@code labour-details/data} provides static data for the labour details page.
 *
 * @author Pang Jun Rong
 * @author Chong Zhan Han
 * @version 2.0
 * @since 2021-10-27
 */


//takes in an array of employee skills to throw out the correct data
export default function data(prop) {


  const rows = prop.map((details) => {

    // const setRow = () => {
    //   findEmployeeSkillDTO(details.name);
    // }
    //console.log(details);
    return {
      name: [details.imageURL, details.name],
      rating: (
        <Rating name="readOnly" value={details.rating} precision={0.1} readOnly />
      ),
      category: (
        <SuiTypography variant="caption" textColor="text" fontWeight="medium">
          {details.skillName}
        </SuiTypography>
      ),
      action: (
        <SuiBox width="8rem" textAlign="center">
          <Link to={{ pathname: "/labour-details/" + details.workPermitNumber, state: details }}>
            <SuiButton
              size="large"
              variant="text"
              buttonColor="info"
            >
              View
            </SuiButton>
          </Link>
        </SuiBox>
      ),
    } || [];
  });


  return {
    columns: [
      { name: "name", align: "left" },
      { name: "category", align: "center" },
      { name: "rating", align: "left" },
      { name: "action", align: "center" },
    ],

    rows: rows
  };
}

