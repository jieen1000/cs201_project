import { combineReducers } from "redux";
import { employeesReducer, selectedEmployeesReducer } from "./employeesReducer";

/**
 * {@code reducers} creates a proxy from the frontend to the backend for employee removal.
 *
 * @author Teo Keng Swee
 * @version 1.0
 * @since 2021-10-16
 */

const reducers = combineReducers({
  allEmployees: employeesReducer,
  employees: selectedEmployeesReducer,
});
export default reducers;
