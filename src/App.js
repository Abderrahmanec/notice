import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './Components/Andere/Home';
import LoginComponent from './Components/Authencomponents/LoginComponent';
import RegistrationComponent from './Components/Authencomponents/RegistrationComponent';
import Dash from './Components/Andere/Dash';
import AddNote from './Components/Andere/AddNote';
import NotesDisplay from './Components/Andere/NotesDisplay';
import FilterNotes from './Components/Andere/FilterNotes';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<LoginComponent />} />
        <Route path="/signup" element={<RegistrationComponent />} />
        <Route path="/dash" element={<Dash />} />
        <Route path="/addnote" element={<AddNote />} />
        <Route path='/display' element={<NotesDisplay />} />
        <Route path='/fi' element={<FilterNotes />} />
      </Routes>
    </Router>
  );
}

export default App;
