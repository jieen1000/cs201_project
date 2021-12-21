
import React, { useEffect, useCallback, useMemo } from "react";
import { useDispatch, useSelector } from "react-redux";
import { setEmployees } from "redux/actions/employeesActions";
import { Auth } from 'aws-amplify';

/**
 * {@code EmployeeListing} creates the listing component for the CRUD interface.
 *
 * @author Teo Keng Swee
 * @version 1.0
 * @since 2021-10-16
 */

const EmployeePage = () => {
  const employees = useSelector((state) => state.allEmployees.employees);
  const dispatch = useDispatch();

  function objToQueryString(obj) {
    const keyValuePairs = [];
    for (const key in obj) {
      keyValuePairs.push(
        encodeURIComponent(key) + "=" + encodeURIComponent(obj[key])
      );
    }
    return keyValuePairs.join("&");
  }

  const fetchEmployees = async () => {
    const response = await Auth.currentSession().then(res => {
      const queryString = objToQueryString({
        compId: res.getIdToken().payload['cognito:groups'][0],
      });
       fetch('/api/employees' + `?${queryString}`, {
          headers: {
              'Authorization': 'Bearer ' + res.getIdToken().getJwtToken()
          }
      }).catch((err) => {
              console.log("Err: ", err);
      }).then(response => response.json())
      .then(data => dispatch(setEmployees(data)))
          
  })
  };

  useEffect(() => {
    fetchEmployees();
  }, []);

  Auth.currentSession().then(res =>{console.log(res.getIdToken().payload['cognito:groups'][0]); } );
  
  return(
    <></>
  )
};

export default EmployeePage;

