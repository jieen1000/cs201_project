import React from "react";
import { useState, useEffect , memo} from "react";
import { Route, Switch, Redirect, useLocation } from "react-router-dom";
import { ThemeProvider, StyledEngineProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import Icon from "@mui/material/Icon";
import SuiBox from "components/SuiBox";
import Sidenav from "examples/Sidenav";
import theme from "assets/theme";
import routes from "routes";
import { useSoftUIController } from "context";
import Amplify from "aws-amplify";
import { AmplifyAuthenticator } from "@aws-amplify/ui-react";
import { AuthState, onAuthUIStateChange } from "@aws-amplify/ui-components";
import LabourDetails from "layouts/labour-details";
import Categories from "layouts/categories";
import awsconfig from "./aws-exports";
import SignInStyles from "./SignIn.css";
import EmployeePage from "layouts/organisation/data/EmployeeListing";
import KommunicateChat from "chat";

/**
 * {@code App} is the main frontend injection w/ cognito integrated authentication
 *
 * @author Pang Jun Rong
 * @author Chong Zhan Han
 * @version 1.0
 * @since 2021-10-16
 */

Amplify.configure(awsconfig);


const App = () => {
  const [controller, dispatch] = useSoftUIController();
  const { direction, layout, openConfigurator } = controller;
  const { pathname } = useLocation();
  const [authState, setAuthState] = useState();
  const [user, setUser] = useState();

  useEffect(() => {
    return onAuthUIStateChange((nextAuthState, authData) => {
      setAuthState(nextAuthState);
      setUser(authData);
    });
  }, []);


  // Change the openConfigurator state
  const handleConfiguratorOpen = () => {
    dispatch({ type: "OPEN_CONFIGURATOR", value: !openConfigurator });
  };

  // Setting the dir attribute for the body element
  useEffect(() => {
    document.body.setAttribute("dir", direction);
  }, [direction]);

  // Setting page scroll to 0 when changing the route
  useEffect(() => {
    document.documentElement.scrollTop = 0;
    document.scrollingElement.scrollTop = 0;
  }, [pathname]);

  const getRoutes = (allRoutes) =>
    allRoutes.map((route) => {
      if (route.collapse) {
        return getRoutes(route.collapse);
      }

      if (route.route) {
        return (
          <Route
            exact
            path={route.route}
            component={route.component}
            key={route.key}
          />
        );
      }

      return null;
    });

  const signUpConfig = {
    signUpFields: [
      {
        label: "Company UEN",
        key: "custom:CompanyUEN",
        placeholder: "Company UEN",
        required: true,
        displayOrder: 5,
      },
    ],
  };

  const configsButton = (
    <SuiBox
      display="flex"
      justifyContent="center"
      alignItems="center"
      width="3.5rem"
      height="3.5rem"
      backgroundColor="white"
      boxShadow="sm"
      borderRadius="50%"
      position="fixed"
      right="2rem"
      bottom="2rem"
      zIndex={99}
      customClass="cursor-pointer"
      onClick={handleConfiguratorOpen}
    >
      <Icon className=" text-dark" fontSize="default">
        settings
      </Icon>
    </SuiBox>
  );

  return authState === AuthState.SignedIn && user ? (
    <div className="App">
      <StyledEngineProvider injectFirst>
        <ThemeProvider theme={theme}>
          <CssBaseline />
          {layout === "dashboard" && (
            <>
              <Sidenav routes={routes} />
              <EmployeePage />
              {/* {configsButton} */}
              <KommunicateChat/>
            </>
          )}
          <Switch>
            {getRoutes(routes)}
            <Route path='/labour-details/:id' component={LabourDetails}/> 
            <Route path='/categories/:name' component={Categories}/> 
            <Redirect from="*" to="/dashboard" />
          </Switch>
        </ThemeProvider>
      </StyledEngineProvider>
    </div>
  ) : (
    <div className="bg">
      <AmplifyAuthenticator
        styles={{ SignInStyles }}
        signUpConfig={signUpConfig}
      />
    </div>
  );
};

export default App;
