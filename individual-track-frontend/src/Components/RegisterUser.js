import React, { useEffect, useState } from "react";
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css'
import moment from 'moment';
import { useNavigate } from 'react-router-dom';
import UserService from "../Logic/UserService";

 function CreateUser(){
  const [startDate, setStartDate] = useState(new Date());
  
    const email = React.useRef();
    const phoneNumber = React.useRef();
    const firstName = React.useRef();
    const lastName = React.useRef();
    const dob = React.useRef();
    const gender = React.useRef();
    const password = React.useRef();
    const CheckPassword = React.useRef();
    const navigate = useNavigate();
    const [errorMessage,setErrorMessage]  = React.useState(null);

    const saveUser = e => {
        e.preventDefault(); 
       
        if(password.current.value === CheckPassword.current.value){
       
           UserService.register(email.current.value,firstName.current.value,lastName.current.value,moment(startDate).format("DD-MM-YYYY")
           ,gender.current.value,phoneNumber.current.value,password.current.value)
           .then(() => {
            navigate("/index");
            window.location.reload();
            }).catch(err=>{setErrorMessage("Error, email exists? Or check input fields.");})
      // console.log(user);
        }else{
          setErrorMessage("Error: Passwords do not match");
        }
       
    }
//onClick={(e) => saveUser(e)}
return(
    
    <div className="App" >
   <div> </div>
   
  <form method="post" className="container mt-5" id="regiserUser-form" onSubmit={saveUser} style={{borderRadius: "20px",boxShadow: "5px 5px 5px black" , backgroundColor: "#FFDEAD", padding: "20px", margin: "30%" ,width:"600px"}}>
  <h1>Create an User</h1>
    <h3>{errorMessage}</h3>
  <div class="mb-3">
  <label for="" class="form-label">Email:</label>
  <input type="email" class="form-control" id="" required ref={email} aria-describedby="" ></input>
  </div>
  <div class="mb-3">
  <label for="" class="form-label">Phone Number:</label>
  <input type="text" class="form-control" id="" required ref={phoneNumber} ></input>
  </div>
  <div class="mb-3">
  <label for="" class="form-label">First Name:</label>
  <input type="text" class="form-control" id="" required ref={firstName} ></input>
  </div>
  <div class="mb-3">
  <label for="" class="form-label">Last Name:</label>
  <input type="text" class="form-control" id="" required ref={lastName} ></input>
  </div>
  <div class="mb-3">
  <label for="" class="form-label">Date of birth:</label>
  <DatePicker className="form-control" closeOnScroll={true} selected={startDate} onChange={(date) => setStartDate(date)} maxDate={new Date()} showYearDropdown value={dob}/>
  
  </div>
  <div class="mb-3">
    <label for="" class="form-label">Gender:</label>
    <select className="auth-wrapper form-control" required ref={gender}>
                    <option hidden >Selection</option>
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                    <option value="other">Other</option>
                </select>  </div>
  <div class="mb-3">
    <label for="" class="form-label">Password:</label>
    <input type="password" class="form-control" minlength="6" maxLength="15" id="" required ref={password} ></input>
  </div>
  <div class="mb-3">
    <label for="" class="form-label">Repeat Password:</label>
    <input type="password" class="form-control" minlength="6" maxLength="15" id="" required ref={CheckPassword} ></input>
  </div>
  <button type="submit" class="btn btn-primary" >Register</button>
  
  </form>
  
  </div> 
);
} export default CreateUser