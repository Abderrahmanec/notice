import React, { useState, useEffect } from "react";
import { TextField, Button, IconButton, InputAdornment, Typography, Container, Paper, Grid, Snackbar } from "@mui/material";
import { styled } from "@mui/system";
import { FaEye, FaEyeSlash, FaLock, FaUser } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { NavLink } from "react-router-dom";
import { jwtDecode } from "jwt-decode";  
const StyledPaper = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(4),
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  maxWidth: "400px",
  margin: "auto",
  marginTop: theme.spacing(8),
  borderRadius: "12px",
  boxShadow: "0 3px 10px rgba(0, 0, 0, 0.2)",
}));

const Form = styled("form")(({ theme }) => ({
  width: "100%",
  marginTop: theme.spacing(1),
}));

function LoginComponent() {
  const [formData, setFormData] = useState({ username: "", password: "" });
  const [errors, setErrors] = useState({ username: "", password: "" });
  const [showPassword, setShowPassword] = useState(false);
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarType, setSnackbarType] = useState("success");
  const navigate = useNavigate();

  
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      try {
        const decodedToken = jwtDecode(token);
        console.log("Decoded token:", decodedToken);  
      } catch (error) {
        console.error("Error decoding token:", error);
      }
    }
  }, []);

  const handleSnackbarClose = () => {
    setOpenSnackbar(false);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));

    setErrors((prev) => ({
      ...prev,
      [name]: "", // Clear error message when user types
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const newErrors = {};

    // Validate username
    if (!formData.username) {
      newErrors.username = "Username is required";
    }

    // Validate password
    if (!formData.password) {
      newErrors.password = "Password is required";
    } else if (formData.password.length < 6) {
      newErrors.password = "Password must be at least 6 characters long";
    }

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    // Fetch request 192.168.178.84  /api/auth/login"
    fetch("http://192.168.178.186:8080/api/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(formData),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          setSnackbarMessage("Login successful!");
          setSnackbarType("success");
          setOpenSnackbar(true);

          // Store the token in localStorage
          console.log("Storing token in localStorage:", data.token);
          localStorage.setItem("token", data.token);

          // Verify token is stored
          console.log("Token in localStorage:", localStorage.getItem("token"));

          // Wait for Snackbar to close before navigating
          setTimeout(() => {
            navigate("/dash"); // Redirect to dashboard
          }, 1500); // Wait 1.5 seconds to allow Snackbar to disappear
        } else {
          setSnackbarMessage("Login failed! Invalid credentials.");
          setSnackbarType("error");
          setOpenSnackbar(true);
        }
      })
      .catch((error) => {
        console.error("Error during login:", error);
        setSnackbarMessage("Login failed! Please try again later.");
        setSnackbarType("error");
        setOpenSnackbar(true);
      });
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  return (
    <Container component="main" maxWidth="xs">
      <Grid container justifyContent="center" alignItems="center" height="100vh">
        <Grid item>
          <StyledPaper elevation={6}>
            <Typography component="h1" variant="h5" gutterBottom textAlign="center">
              Login
            </Typography>
            <Form onSubmit={handleSubmit} noValidate>
              <TextField
                margin="normal"
                required
                fullWidth
                id="username"
                label="Username"
                name="username"
                autoComplete="off"
                value={formData.username}
                onChange={handleChange}
                error={!!errors.username}
                helperText={errors.username}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <FaUser />
                    </InputAdornment>
                  ),
                }}
                type="text"
              />
              <TextField
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type={showPassword ? "text" : "password"}
                id="password"
                value={formData.password}
                onChange={handleChange}
                error={!!errors.password}
                helperText={errors.password}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <FaLock />
                    </InputAdornment>
                  ),
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        aria-label="toggle password visibility"
                        onClick={togglePasswordVisibility}
                        edge="end"
                      >
                        {showPassword ? <FaEyeSlash /> : <FaEye />}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{
                  mt: 3,
                  mb: 2,
                  height: "48px",
                  borderRadius: "8px",
                  textTransform: "none",
                  fontSize: "16px",
                }}
              >
                Login
              </Button>
              <Grid container justifyContent="center">
                <Grid item>
                  <NavLink to="/signup" style={{ textDecoration: "none" }}>
                    <Typography variant="body2" color="primary">
                      Don't have an account? Sign Up
                    </Typography>
                  </NavLink>
                </Grid>
              </Grid>
            </Form>
          </StyledPaper>
        </Grid>
      </Grid>

      {/* Snackbar component for feedback */}
      <Snackbar
        open={openSnackbar}
        autoHideDuration={6000}
        onClose={handleSnackbarClose}
        message={snackbarMessage}
        severity={snackbarType}
        anchorOrigin={{ vertical: 'top', horizontal: 'center' }}  // For more control over its position
      />
    </Container>
  );
}

export default LoginComponent;
