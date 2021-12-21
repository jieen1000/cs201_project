import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import SuiButton from "components/SuiButton";
import logoGeneric from "assets/images/small-logos/logo-category.svg"
import { Auth } from "aws-amplify";
import { useState, useEffect } from "react";
import { Link, withRouter } from 'react-router-dom';


/**
 * {@code CategoryTable/data} provides the static data for category table.
 *
 * @author Pang Jun Rong
 * @author Chong Zhan Han
 * @version 1.2
 * @since 2021-10-28
 */

export default function data() {

  const [data, setData] = useState([]);

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

      fetch('/api/employeeSkills/collate' + `?${queryString}`, {
        headers: {
          'Authorization': 'Bearer ' + res.getIdToken().getJwtToken()
        }
      })
        .then(response => response.json())
        .then(data => setData(data))
        .then(data => console.log("Testing labour sharing " + data));
    })
  }

  //Recommendation will be refreshed the first time the page is loaded
  useEffect(() => {
    FetchData();
    console.log(data);
  }, []);

  const rows = data.map((detail) => {
    //console.log(details);
    return {
      category: [logoGeneric, detail.name],
      availability: (
        <SuiTypography variant="caption" textColor="text" fontWeight="medium">
          {detail.pax}
        </SuiTypography>
      ),
      cost: (
        <SuiTypography variant="caption" textColor="text" fontWeight="medium">
          From ${detail.min}/Week
        </SuiTypography>
      ),
      action: (
        <SuiBox width="8rem" textAlign="center">
          <Link to={{ pathname: "/categories/" + detail.name, state: detail.name }}>
            <SuiButton
              size="large"
              variant="text"
              buttonColor="info"
            > Select </SuiButton>
          </Link>
        </SuiBox>
      )
    } || [];
  });



  return {
    columns: [
      { name: "category", align: "left" },
      { name: "availability", align: "left" },
      { name: "cost", align: "center" },
      { name: "action", align: "center" },
    ],

    rows: rows
  };
}
