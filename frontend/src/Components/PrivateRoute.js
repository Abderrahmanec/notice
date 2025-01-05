import React from "react";
import { Route, Redirect } from "react-router-dom";

// PrivateRoute component checks if the user is authenticated
const PrivateRoute = ({ component: Component, ...rest }) => {
  const token = localStorage.getItem("jwt_token"); // Check if the token exists

  return (
    <Route
      {...rest}
      render={(props) =>
        token ? ( // If token exists, render the protected component
          <Component {...props} />
        ) : (
          <Redirect to="/login" /> // If no token, redirect to login page
        )
      }
    />
  );
};

export default PrivateRoute;
