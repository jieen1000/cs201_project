import { ActionTypes } from "../constants/action-types";

/**
 * {@code employeesReducer} creates a proxy from the frontend to the backend for employee removal.
 *
 * @author Teo Keng Swee
 * @version 1.0
 * @since 2021-10-16
 */

const intialState = {
  employees: [],
};

export const employeesReducer = (state = intialState, { type, payload }) => {
  switch (type) {
    case ActionTypes.SET_EMPLOYEES:
      return { ...state, employees: payload };
    default:
      return state;
  }
};

export const selectedEmployeesReducer = (state = {}, { type, payload }) => {
  console.log(type);
  switch (type) {
    case ActionTypes.SELECTED_EMPLOYEE:
      return { ...state, ...payload };
    case ActionTypes.REMOVE_SELECTED_EMPLOYEE:
      return {};
    default:
      return state;
  }
};
