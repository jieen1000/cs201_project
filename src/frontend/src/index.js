import React from "react";
import ReactDOM from "react-dom";
import { HashRouter } from "react-router-dom";

import App from "App";
import { SoftUIControllerProvider } from "context";
import store from "./redux/store";
import { Provider } from "react-redux";
import Kommunicate from '@kommunicate/kommunicate-chatbot-plugin';


/**
 * {@code index} is a react component for the injection of react components into root.
 *
 * @author React
 * @version 1.0
 * @since 2021-10-16
 */

//Comment out when debugging
// window.console.log = window.console.debug = window.console.info = window.console.error = function () {
//   return false;
// }

Kommunicate.init("1326488dc77f91039518354a26d80239f");

ReactDOM.render(
  <>
    <HashRouter>
      <SoftUIControllerProvider>
        <Provider store={store}>
          <App />
        </Provider>
      </SoftUIControllerProvider>
    </HashRouter>
  </>,
  document.getElementById("root")
);
