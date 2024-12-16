import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './Components/Andere/Home';
import LoginComponent from './Components/Authencomponents/LoginComponent';
import RegistrationComponent from './Components/Authencomponents/RegistrationComponent';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<LoginComponent />} />
        <Route path="/signup" element={<RegistrationComponent />} />
      </Routes>
    </Router>
  );
}

export default App;
