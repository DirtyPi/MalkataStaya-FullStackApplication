
import './App.css';
//import UnapPropertiesCardRows from "../adminComponents/UnapPropertiesCardRows";
import SendMessagePage from "./Pages/sendMessagePage"
import UnapPropertiesCardRows from "./Pages/UnapprovedProperties";
import PropertyPage from "./Pages/PropertyPage"
import CreateOffer from "./Pages/CreateProperty";
import RegisterUser from './Components/RegisterUser';
import SignOut from './Pages/signout' 
import MainPage from "./Pages/MainPage";
import LogOut from './Pages/signout';
import NotFound from './Pages/NotFound';
import UserPage from "./Pages/MyProfilePage";
import MyProperties from "./Pages/MyProperties";
import {Navigate ,Route, Routes, BrowserRouter as Router } from "react-router-dom";
import AuthService from './Logic/AuthService';
import Navbar from './Components/Navbar';
import jwtDecode from 'jwt-decode';
import Login from "./Pages/Login";
import Body from "./Components/Body";
import React, { useRef } from "react";
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import Link from '@material-ui/core/Link';

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <Link color="inherit" href="https://mui.com/">
        Malkata Staya
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}
function App() {

  const videoRef = useRef(null);

  React.useEffect(() => {
    if (videoRef.current) {
      videoRef.current.play();
    }
  }, []);
  function requireAuth() {
    
    if (AuthService.getCurrentUser()!==null ) {
      const token = JSON.parse(localStorage.getItem("user"))&& JSON.parse(localStorage.getItem("user"))["access_token"];
      if (jwtDecode(token).exp > Date.now() / 1000) {
        return true;
      }
    }
    else return false;
  }

  function requireAdminAuth() {
    if (AuthService.getCurrentUser()!==null ) {
      const token = JSON.parse(localStorage.getItem("user"))&& JSON.parse(localStorage.getItem("user"))["access_token"];
      if (jwtDecode(token).exp > Date.now() / 1000 && AuthService.getCurrentUser().roles.includes("ROLE_ADMIN")) {
        return true;
      }
    }
    else return false;
  }


  function LogOrNotFound(){
    if (AuthService.getCurrentUser()!==null){
      return <LogOut/>
    }
    return <NotFound/>
  }
  return ( 
    
    <div className="App">
  

       <Router>
         <Navbar />
         <Body />
         <div  className="container mt-5">
        
          <Routes>
              <Route path="/my-profile" element={(requireAuth() ? (<UserPage/>) : (LogOrNotFound()))} />
              <Route path="/my-properties" element={(requireAuth() ? (<MyProperties/>) : (LogOrNotFound()))} />
              <Route path="/create-property" element={(requireAuth() ? (<CreateOffer/>) : (LogOrNotFound()))} />
              <Route path="/Unapproved-properties" element={(requireAdminAuth() ? (<UnapPropertiesCardRows/>) : (LogOrNotFound()))} />
              <Route path="/send-message" element={(requireAdminAuth() ? (<SendMessagePage/>) : (LogOrNotFound()))} />
              <Route path="/MainPage" element={<MainPage/>} />
              <Route path="/sign-up" element={<RegisterUser/>} />
              <Route path="/properties/:id" element={(requireAuth() ? (<PropertyPage/>) : (LogOrNotFound()))} />
              <Route path="/sign-in"element={(requireAuth() ? (<Navigate  to="/MainPage"/>) : (<Login/>))} />
              <Route path="/sign-out" element={<SignOut/>} />
              <Route component={() => <NotFound/>}/>

          </Routes>
        
         </div>  
        {/* </Body> */}
        </Router>
        <Box mt={8} style={{position:"fixed",bottom: 0,left:"0%", backgroundColor: "#FFDEAD"}}>
        <Copyright />
      </Box>
 
    </div>
   
    
  );
}

export default App;
