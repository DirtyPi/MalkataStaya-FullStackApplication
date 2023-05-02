import React, { useEffect, useState } from "react";
import {Link} from "react-router-dom";
import PropertyService from "../Logic/PropertyService";


function UnapprovedProperty(props) {

    const [properties, setProperties] = useState([])
    const [proprty2, setProprty2] = useState();
    const location = React.useRef();
    const description = React.useRef();
    const streetName = React.useRef();
    const houseNumber = React.useRef();
    const propertySize = React.useRef();
    const approved = React.useRef();
    const landLord = React.useRef();
    const available = React.useRef();

    const ApproveProperty = (propertyID) =>{
        console.log(propertyID);
        const proprty2 = {
            propid: propertyID,
            location: location.current.value,
            description: description.current.value,
            streetName: streetName.current.value,
            houseNumber: houseNumber.current.value,
            propertySize: propertySize.current.value,
            approved: approved.current.value,
            landLord: landLord.current.value,
            available: available.current.value,

        };
       // property = PropertyService.getPropertyById(propertyID);
      
        PropertyService.ApproveProperty(proprty2).then((response) =>{
        }).catch(error => {
            console.log(error);
        })
    }
 

    return (
        <div  style={{display: 'grid', placeItems: 'center'}}>
        <div  class="w-50 p-3" >
        <div class="card" >
            <img src="https://th.bing.com/th/id/R.4a752b72769f0e6bd542291867758a12?rik=bYUqhqajXznV%2fQ&riu=http%3a%2f%2fwww.dunboynecastlehotel.com%2fupload%2flarge_gallery_images%2frooms-10.jpg&ehk=Hi8P12nSsyL090XlWviuFaEXhW%2b5%2beUkTIMw0goOeak%3d&risl=&pid=ImgRaw&r=0" class="card-img-top" alt="Fissure in Sandstone"/>
            <div class="card-body">
            <h2>Location: {props.property.location}</h2>
            </div>
            <p class="card-text">  <h2> {props.property.description}</h2></p>
            <h2>Price: {props.property.price}</h2>
            <h2>Property Size(m²): {props.property.propertySize}</h2>
            
            <a href="" class="btn btn-primary" onClick={ApproveProperty}>Approve</a>
        </div>
        </div>
        </div>
    )
} export default UnapprovedProperty