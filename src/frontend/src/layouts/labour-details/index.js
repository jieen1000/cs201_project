import Card from "@mui/material/Card";
import SuiBox from "components/SuiBox";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import Footer from "examples/Footer";
import LabourCard from "./components/LabourDetails/LabourCard"
import { useState, useEffect } from "react";
import { Auth } from "aws-amplify";
import Recommendation from "./components/Recommendation";

/**
 * {@code labour-details} creates the layout for the labour details page.
 *
 * @author Pang Jun Rong
 * @author Chong Zhan Han
 * @version 2.0
 * @since 2021-10-27
 */

function LabourDetails(props) {

    const [employeeSkillDTO, setEmployeeSkillDTO] = useState(null);
    const [recommendations, setRecommendations] = useState([]);
    const empty = {};

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

            fetch('/api/employeeSkills/all' + `?${queryString}`, {
                headers: {
                    'Authorization': 'Bearer ' + res.getIdToken().getJwtToken()
                }
            })
                .then(response => response.json())
                .then(data => setRecommendations(data))
        })
    }

    //Recommendation will be refreshed the first time the page is loaded
    useEffect(() => {
        FetchData();    
    }, []);

    //First item in recommendation will be set as the displayed employee skill
    useEffect(() => {
        setEmployeeSkillDTO(recommendations[0]);
    }, [recommendations]);

    //if we select an employee detail in the recommendation section
    const findEmployeeSkillDTO = (employeeName) => {
        recommendations.filter((e) => e.name === employeeName).map(
            e => setEmployeeSkillDTO(e)
        )
    }

    console.log(employeeSkillDTO);

    const currEmployeeSkill = props.location.state || employeeSkillDTO;


    return (
        <DashboardLayout>
            <DashboardNavbar />
            <SuiBox py={3}>
                <Card>
                    <SuiBox display="flex" flexDirection="column" height="100%">
                        <LabourCard employeeSkillDTO = {currEmployeeSkill} />
                        <Recommendation attr={recommendations}/>
                    </SuiBox>
                </Card>
                
            </SuiBox>
            <Footer />
        </DashboardLayout>
    );
}

export default LabourDetails