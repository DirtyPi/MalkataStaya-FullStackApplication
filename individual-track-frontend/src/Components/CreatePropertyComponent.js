import React, {  useState } from "react";
import PropertyService from "../Logic/PropertyService";
//import * as PropertyService from '../Logic/PropertyService';

import { useNavigate } from 'react-router-dom';
import Select from "./CitySelect"


 function CreateProperty(){

    const price = React.useRef();
    const description = React.useRef();
    const[location, setLocation] = useState("");
    const streetName = React.useRef();
    const houseNumber = React.useRef();
    const propertySize = React.useRef();
    const [msg, setMsg] = React.useState(null);
    const navigate = useNavigate();

   
    const RegisterProperty = e => {
      e.preventDefault();
      const property = {
        price: price.current.value,
        description: description.current.value,
        location: location,
        streetName: streetName.current.value,
        houseNumber: houseNumber.current.value,
        propertySize: propertySize.current.value,
      };
      PropertyService.postProperty(property);
      alert("Your property has been listed and waiting to be approved by admin!");
      window.location.assign("http://localhost:3000/MainPage");

      
    }
    
    const selectCity = (data) =>{
      setLocation(data);
  }


return(
    <div style={{borderRadius: "20px",boxShadow: "5px 5px 5px black" , backgroundColor: "#FFDEAD", padding: "20px", margin: "30%" ,width:"600px"}}>
    
   <div> </div>
    <h1>Create a Property</h1>
  <form method="post" id="crateOffer-form" onSubmit={RegisterProperty} >
  <h3>{msg}</h3>
  <div class="mb-3">
  <label for="" class="form-label">Price:</label>
  <input type="text" class="form-control" id="" required ref={price}></input>
  </div>
  <div class="mb-3">
  <label for="" class="form-label">Location:</label>
  {/* <input type="text" class="form-control" id="" required ref={location}></input> */}
  <Select  setCity={selectCity} text="Select a location"  />
  </div>
  <div class="mb-3">
  <label for="" class="form-label">Street Name:</label>
  <input type="text" class="form-control" id="" required ref={streetName}></input>
  </div>
  <div class="mb-3">
  <label for="" class="form-label">House Number:</label>
  <input type="text" class="form-control" id="" required ref={houseNumber}></input>
  </div>
  <div class="mb-3">
  <label for="" class="form-label">Property Size:</label>
  <input type="text" class="form-control" id="" required ref={propertySize}></input>
  </div>
  <div class="mb-3">
    <label for="" class="form-label">Description:</label>
    <textarea type="text" class="form-control" id="" required ref={description}></textarea>
  </div>
  <button type="submit" class="btn btn-primary" >Submit</button>
  
  </form>
  </div>


);
} export default CreateProperty