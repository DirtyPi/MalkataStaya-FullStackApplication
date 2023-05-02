import React, { useEffect, useState } from "react";
import {useParams} from "react-router-dom";
import {Link} from "react-router-dom";
import PropertyService from "../Logic/PropertyService";

function PropertyPageComonent(){

const [property, setProperty] = useState({});
let { id } = useParams()

useEffect(() => {
PropertyService.getPropertyById(id).then(res => {setProperty(res.data);})
},[])

return(
    <div>
          <img src="https://cdn.pixabay.com/photo/2016/11/18/17/20/living-room-1835923__480.jpg" class="card-img-top" alt="Fissure in Sandstone"/>
 <h2>Location: {property.location}</h2>
    <p class="card-text">  <h2> {property.description}</h2></p>
    <h2>Price: {property.price}</h2>
    <h2>Property Size(m²): {property.propertySize}</h2>
  <h2>{property.available}</h2>
    </div>
)   


} export default PropertyPageComonent

