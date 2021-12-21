import { createStore } from "redux";
import reducers from "./reducers/index";

/**
 * {@code store} creates a store from redux.
 *
 * @author Teo Keng Swee
 * @version 1.0
 * @since 2021-10-16
 */

const store = createStore(
  reducers,
  {},
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);

export default store;
