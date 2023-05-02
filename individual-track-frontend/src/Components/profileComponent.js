import React, { useState,useEffect } from 'react';
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css'
import { useNavigate } from "react-router";
import UserService from '../Logic/UserService';
import moment from 'moment';
 import Person from '@material-ui/icons/Person';
import AuthService from "../Logic/AuthService";
import NotFound from "../Pages/NotFound";

const Profile = () => {

    AuthService.checkToken();

    const [user, setUser] = useState(null);
    const [startDate, setStartDate] = useState(new Date());
    
    useEffect(() => {
        UserService.getCurrentinfo().then((response) => {
            setUser(response.data);
            console.log(response.data)
    })}, [])
   
    
    const firstName = React.useRef();
    const lastName = React.useRef();
    const email = React.useRef();
    const phoneNumber = React.useRef();
    const dob = React.useRef();
    const gender = React.useRef();

    const navigate = useNavigate();
    const [errorMessage,setErrorMessage]  = React.useState(null);

   const handleUpdateProfile = e => {
        e.preventDefault();
        if(email.current.value !== "",firstName.current.value !== "",lastName.current.value !== "",moment(startDate).format("DD-MM-YYYY") !== "",gender.current.value !== "",phoneNumber.current.value !== ""){
            UserService.updateUser(email.current.value,firstName.current.value,lastName.current.value,moment(startDate).format("DD-MM-YYYY"),gender.current.value,phoneNumber.current.value)
            .then(() => {
                navigate("/MainPage");
            })
        }else{

        }
       
    }
    if(!user){return <NotFound/>}
    return(
            <div style={{borderRadius: "20px",boxShadow: "5px 5px 5px black" , backgroundColor: "#FFDEAD", padding: "20px", margin: "28%" ,width:"600px"}}>
                 <h3>{errorMessage}</h3>
                 <form onSubmit={handleUpdateProfile}>
            <center><Person size="big"/></center>
                <center><h3>My profile</h3></center>
                {/* <center><p>(No ability to change email)</p></center> */}

                <div className="form-group">
                    <label>First name</label>
                    <input type="text" className="form-control" defaultValue={user.firstName} placeholder="First name" required ref={firstName}/>
                </div>

                <div className="form-group">
                    <label>Last name</label>
                    <input type="text" className="form-control" defaultValue={user.lastName} placeholder="Last name" required ref={lastName}/>
                </div>

                <div className="form-group">
                    <label>Email address</label>
                    <input type="email" className="form-control" defaultValue={user.email} placeholder="Enter email" required ref={email}/>
                </div>

                <div className="form-group">
                    <label>Date of Birth</label>
                    <DatePicker className="form-control" closeOnScroll={true} defaultValue={user.dateOfBirth} selected={startDate} onChange={(date) => setStartDate(date)} maxDate={new Date()} showYearDropdown required ref={dob}/>
                </div>

                
                <div className="form-group">
                <label>Gender</label>
                <select className="auth-wrapper form-control" defaultValue={user.gender} required ref={gender}>
                    <option value="male" >Male</option>
                    <option value="female" >Female</option>
                    <option value="other">Other</option>
                </select>
                </div>

                <div className="form-group">
                    <label>Phone number</label>
                    <input type="text" className="form-control" placeholder="Enter number" defaultValue={user.phoneNumber} required ref={phoneNumber}/>
                </div>
                   <span style={{padding: "10px"}}></span>
                    <center><button type="submit" className="btn btn-primary btn-block" >Update personal details</button></center>
               
               
            </form>
            </div>
    );

}
export default Profile;