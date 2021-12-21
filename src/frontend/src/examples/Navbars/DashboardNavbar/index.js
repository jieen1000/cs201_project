/**
=========================================================
* Soft UI Dashboard React - v2.0.0
=========================================================
* Product Page: https://www.creative-tim.com/product/soft-ui-dashboard-material-ui
* Copyright 2021 Creative Tim (https://www.creative-tim.com)
Coded by www.creative-tim.com
 =========================================================
* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*/

import { useState, useEffect } from "react";

// react-router components
import { useLocation, Link } from "react-router-dom";

// prop-types is a library for typechecking of props.
import PropTypes from "prop-types";

// @mui material components
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import Menu from "@mui/material/Menu";
import Icon from "@mui/material/Icon";

// Soft UI Dashboard React components
import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import SuiInput from "components/SuiInput";

// Soft UI Dashboard React example components
import Breadcrumbs from "examples/Breadcrumbs";
import NotificationItem from "examples/NotificationItem";

// Custom styles for DashboardNavbar
import styles from "examples/Navbars/DashboardNavbar/styles";

// Soft UI Dashboard React context
import { useSoftUIController } from "context";


import { Auth, Hub } from 'aws-amplify';

function DashboardNavbar({ absolute, light, isMini }) {
  const [navbarType, setNavbarType] = useState();
  const [controller, dispatch] = useSoftUIController();
  const { miniSidenav, transparentNavbar, fixedNavbar, openConfigurator } = controller;
  const [openMenu, setOpenMenu] = useState(false);
  const classes = styles({ transparentNavbar, absolute, light, isMini }, {index: 1, withTheme:true});
  const route = useLocation().pathname.split("/").slice(1);

  const handleSignOutButtonClick = async () => {
    try {
        await Auth.signOut();
        Hub.dispatch('UI Auth', {   // channel must be 'UI Auth'
            event: 'AuthStateChange',    // event must be 'AuthStateChange'
            message: 'signedout'    // message must be 'signedout'
        });
    } catch (error) {
        console.log('error signing out: ', error);
    }
};  

  useEffect(() => {
    // Setting the navbar type
    if (fixedNavbar) {
      setNavbarType("sticky");
    } else {
      setNavbarType("static");
    }

    // A function that sets the transparent state of the navbar.
    function handleTransparentNavbar() {
      dispatch({
        type: "TRANSPARENT_NAVBAR",
        value: (fixedNavbar && window.scrollY === 0) || !fixedNavbar,
      });
    }

    /** 
     The event listener that's calling the handleTransparentNavbar function when 
     scrolling the window.
    */
    window.addEventListener("scroll", handleTransparentNavbar);

    // Call the handleTransparentNavbar function to set the state with the initial value.
    handleTransparentNavbar();

    // Remove event listener on cleanup
    return () => window.removeEventListener("scroll", handleTransparentNavbar);
  }, [dispatch, fixedNavbar]);

  const handleMiniSidenav = () => dispatch({ type: "MINI_SIDENAV", value: !miniSidenav });
  const handleConfiguratorOpen = () =>
    dispatch({ type: "OPEN_CONFIGURATOR", value: !openConfigurator });
  const handleOpenMenu = (event) => setOpenMenu(event.currentTarget);
  const handleCloseMenu = () => setOpenMenu(false);

  // Render the notifications menu
  const renderMenu = () => (
    <Menu
      anchorEl={openMenu}
      getContentAnchorEl={null}
      anchorOrigin={{
        vertical: "bottom",
        horizontal: "left",
      }}
      open={Boolean(openMenu)}
      onClose={handleCloseMenu}
      style={{ marginTop: "1rem" }}
    >
      <NotificationItem
        color="info"
        image={
          <Icon fontSize="small" className="material-icon-round text-white">
            timelapse
          </Icon>
        }
        title={["", "3 Work Permits Expiring in 14 Days"]}
        date="30 minutes"
        onClick={handleCloseMenu}
      />
      <NotificationItem
        color="error"
        image={
          <Icon fontSize="small" className="material-icon-round text-white">
            announcement
          </Icon>
        }
        title={["", "New BCA Advisory – Updated SMMs"]}
        date="7 hours"
        onClick={handleCloseMenu}
      />
      <NotificationItem
        color="success"
        image={
          <Icon fontSize="small" className="material-icon-round text-white">
            payment
          </Icon>
        }
        title={["", "Labour Sharing Transaction Success"]}
        date="2 days"
        onClick={handleCloseMenu}
      />
    </Menu>
  );


  return (
    <AppBar
      position={absolute ? "absolute" : navbarType}
      color="inherit"
      className={classes.navbar}
    >
      <Toolbar className={classes.navbar_container}>
        <SuiBox customClass={classes.navbar_row} color="inherit" mb={{ xs: 1, md: 0 }}>
          <Breadcrumbs icon="home" title={route[route.length - 1]} route={route} light={light} />
        </SuiBox>
        {isMini ? null : (
          <SuiBox customClass={classes.navbar_row}>
            <SuiBox pr={1}>
              <SuiInput
                placeholder="Type here..."
                withIcon={{ icon: "search", direction: "left" }}
                customClass={classes.navbar_input}
              />
            </SuiBox>
            <SuiBox
              color={light ? "white" : "inherit"}
              customClass={classes.navbar_section_desktop}
            >
              <IconButton
                size="small"
                color="inherit"
                className={classes.navbar_mobile_menu}
                onClick={handleMiniSidenav}
              >
                <Icon>{miniSidenav ? "menu_open" : "menu"}</Icon>
              </IconButton>
              <IconButton
                color="inherit"
                className={classes.navbar_icon_button}
                aria-controls="notification-menu"
                aria-haspopup="true"
                variant="contained"
                onClick={handleOpenMenu}
              >
                <Icon>notifications</Icon>
              </IconButton>
              {renderMenu()}
              <IconButton className={classes.navbar_icon_button} onClick = {handleSignOutButtonClick}>
                <Link to="/authentication/sign-in/">
                  <Icon className={light?"text-white": "text-dark"}>exit_to_app</Icon>
                  <SuiTypography
                  variant="button"
                  fontWeight="medium"
                  textColor={light?"white": "dark"}
                  >
                  Sign Out
                  </SuiTypography>
                </Link>
              </IconButton>
          </SuiBox>
          </SuiBox>
        )}
    </Toolbar>
    </AppBar >
  );
}

// Setting default values for the props of DashboardNavbar
DashboardNavbar.defaultProps = {
  absolute: false,
  light: false,
  isMini: false,
};

// Typechecking props for the DashboardNavbar
DashboardNavbar.propTypes = {
  absolute: PropTypes.bool,
  light: PropTypes.bool,
  isMini: PropTypes.bool,
};

export default DashboardNavbar;