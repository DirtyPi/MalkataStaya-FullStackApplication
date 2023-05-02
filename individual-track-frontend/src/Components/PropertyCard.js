import React, { useEffect, useState } from "react";
import {Link} from "react-router-dom";
import PropertyService from "../Logic/PropertyService";


function PropertyCard(props) {

    const [properties, setProperties] = useState([])

    const deleteProperty = (propertyID) =>{
        PropertyService.deleteProperties(propertyID).then((response) =>{
        }).catch(error => {
            console.log(error);
        })
    }
    useEffect(() => {
        PropertyService.getPropertyById(props.property.id)
    },[])

    


    return (
        <Link to={'/properties/' + props.property.id}>
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
          <h2>{props.property.available}</h2>
            {/* <a href="" class="btn btn-primary" onClick={() => deleteProperty(props.property.id)}>Delete</a> */}
        </div>
        </div>
        </div>
        </Link>
       
        
    )
} export default PropertyCard

