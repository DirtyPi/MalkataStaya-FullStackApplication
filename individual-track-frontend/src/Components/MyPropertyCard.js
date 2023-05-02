import React, { useEffect, useState } from "react";
import {Link} from "react-router-dom";
import PropertyService from "../Logic/PropertyService";
import { useNavigate } from "react-router";


function PropertyCard(props) {

    const [properties, setProperties] = useState([])
    const navigate = useNavigate();
   
    const deleteProperty = (id) => {
        PropertyService.deleteProperty(id)
        .then(res => {
            console.log('Delete successful')
            navigate("/MainPage")
            window.location.reload()
        })
       

    }
    
  
    useEffect(() => {
        PropertyService.getPropertyById(props.property.id)
    },[])

    


    return (
        <div key={props.property.id}  style={{borderRadius: "20px",boxShadow: "5px 5px 5px black" , backgroundColor: "#FFDEAD", padding: "20px", margin: "28%" ,width:"600px"}}>
        <Link to={'/properties/' + props.property.id}>
            <div style={{display: 'grid', placeItems: 'center'}}>
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
         
        </div>
        </div>
        </div>
        </Link>
        <span style={{padding: "20px"}}></span>
           <a href="" class="btn btn-primary" onClick={() => deleteProperty(props.property.id)}>Delete</a>
           </div>
        
    )
} export default PropertyCard