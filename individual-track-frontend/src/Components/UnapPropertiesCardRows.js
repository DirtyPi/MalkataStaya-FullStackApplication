import axios from "axios";
import { useEffect, useState } from "react";
import PropertyCard from '../Components/UnapprovedProperty';
import PropertyService from "../Logic/PropertyService";

function UnapPropertiesCardRows(){
    const [properties, setProperties] = useState([])
    // useEffect(() => {
    //     PropertyService.getProperties().then(response => {
    //         setProperties(response.data.properties)
    //         console.log(response.data.properties);
    //     }).catch(error =>{
    //         console.log(error);
    //     })
    // },[])


    useEffect(() => {
        getProperties()
    }, [])

    const getProperties = () => {
        
        PropertyService.getUnapprovedProperties().then((response)=>{
            setProperties(response.data);
        })
    }

    return (
        
        <div className="container">
            {properties.map((item, index) => (
                <div>
                  
                    <PropertyCard property={item} key={index}></PropertyCard>
                </div>
            ))}
            
        </div>
    )
} export default UnapPropertiesCardRows