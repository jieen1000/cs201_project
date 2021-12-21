import * as React from 'react';
import SuiTypography from 'components/SuiTypography';
import SuiBox from 'components/SuiBox';
import SuiButton from 'components/SuiButton';
import SuiAvatar from 'components/SuiAvatar';
import { Rating } from "@mui/material";
import { Auth } from "aws-amplify";
import { useState, useEffect } from "react";
import { Link } from 'react-router-dom';
import DataTable from "react-data-table-component";
import DataTableExtensions from "react-data-table-component-extensions";


/**
 * {@code CategoriesTable} provides a table component for the categories page.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
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


export default function CategoriesTable(props) {


  const [results, setResults] = useState([]);

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
        skillId: props.attr,
        compId: res.getIdToken().payload['cognito:groups'][0],
      });
      fetch('/api/employeeSkills' + `?${queryString}`, {
        headers: {
          'Authorization': 'Bearer ' + res.getIdToken().getJwtToken()
        }
      })
        .then(response => response.json())
        .then(data => setResults(data))
        .then(data => console.log(data));
    })
  }

  useEffect(() => {
    FetchData()
  }, []);

  const columns = [
    {
      name: "Profile",
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
      name: "Skill",
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
  );
}

