import React,{ useEffect, useState } from "react";
import SuiAvatar from "components/SuiAvatar";
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import DataTable from "react-data-table-component";
import DataTableExtensions from "react-data-table-component-extensions";
import "react-data-table-component-extensions/dist/index.css";
import SuiButton from "components/SuiButton";
import { Auth } from "aws-amplify";
import { Link } from 'react-router-dom';

/**
 * {@code EmployeeTable} creates the layout for the CRUD interface.
 *
 * @author Chong Zhan Han
 * @author Tan Jie En
 * @author Teo Keng Swee
 * @author Pang Jun Rong
 * @version 1.2
 * @since 2021-10-18
 */


 function Labour({ image, name, company }) {
  return (
    <SuiBox display="flex" alignItems="center" px={1} py={0.5}>
      <SuiBox mr={2}>
        <SuiAvatar src={image} alt={name} size="sm" variant="rounded" />
      </SuiBox>
      <SuiBox display="flex" flexDirection="column">
        <SuiTypography variant="button" fontWeight="medium">
          {name}
        </SuiTypography>
        <SuiTypography variant="caption" textColor="secondary">
          {company}
        </SuiTypography>
      </SuiBox>
    </SuiBox>
  );
}

function Function({ category, experience }) {
  return (
    <SuiBox display="flex" flexDirection="column">
      <SuiTypography variant="caption" fontWeight="medium" textColor="text">
        {category}
      </SuiTypography>
      <SuiTypography variant="caption" textColor="secondary">
        {experience}
      </SuiTypography>
    </SuiBox>
  );
}

function SkillMarketTable() {

  const [employeeSkills, setEmployeeSkills] = useState([]);
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
    await Auth.currentSession().then((res) => {
      const queryString = objToQueryString({
        compId: res.getIdToken().payload['cognito:groups'][0],
      });
      fetch("/api/employeeSkills/all"  + `?${queryString}`, {
        headers: {
          Authorization: "Bearer " + res.getIdToken().getJwtToken(),
        },
      })
        .then((response) => response.json())
        .then((data) => setEmployeeSkills(data))
    });
  };

  useEffect(() => {
    FetchData();
    console.log(employeeSkills);
  }, []);

  const columns = [
    {
      name: "Employee",
      selector: "name",
      sortable: true,
      width:300,
      cell: (record) => {
        return (
          <Labour
          image={record.imageURL}
          name={record.name}
          company={record.company}
        />
        )
      }
    },
    {
      name: "Skill & Experience",
      selector: "name",
      sortable: true,
      width:300,
      cell: (record) => {
        return (
          <Function
              category={record.skillName}
              experience={record.experience}
            />
        )
      }
    },
    {
      name: "Employee",
      selector: "name",
      sortable: true,
      width:300,
      cell: (record) => {
        return (
          <Link  to={{  pathname: "/labour-details/" + record.workPermitNumber, state: record }}>
          <SuiButton > View </SuiButton>
        </Link>
        )
      }
    },
    
  ];

  return (
    <div className="main">
      <DataTableExtensions exportHeaders columns={columns} data={employeeSkills}>
        <DataTable
          noHeader
          defaultSortField="id"
          defaultSortAsc={false}
          pagination
          highlightOnHover
        />
      </DataTableExtensions>
    </div>
  );
}
export default SkillMarketTable;
