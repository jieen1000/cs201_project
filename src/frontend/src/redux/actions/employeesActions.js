import { ActionTypes } from "../constants/action-types";

/**
 * {@code employeesActions} creates a proxy from the frontend to the backend.
 *
 * @author Teo Keng Swee
 * @version 1.0
 * @since 2021-10-16
 */

export const setEmployees = (employees) => {
  return {
    type: ActionTypes.SET_EMPLOYEES,
    payload: employees,
  };
};

export const selectedEmployee = (employee) => {
  return {
    type: ActionTypes.SELECTED_EMPLOYEE,
    payload: employee,
  };
};
export const removeSelectedEmployee = () => {
  return {
    type: ActionTypes.REMOVE_SELECTED_EMPLOYEE,
  };
};
