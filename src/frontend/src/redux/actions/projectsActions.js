import { ActionTypes } from "../constants/action-types";

/**
 * {@code ProjectsActions} creates a proxy from the frontend to the backend.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-22
 */

export const setProjects = (projects) => {
  return {
    type: ActionTypes.SET_PROJECTS,
    payload: projects,
  };
};

export const selectedProject = (project) => {
  return {
    type: ActionTypes.SELECTED_PROJECT,
    payload: project,
  };
};
export const removeSelectedProject = () => {
  return {
    type: ActionTypes.REMOVE_SELECTED_PROJECT,
  };
};
