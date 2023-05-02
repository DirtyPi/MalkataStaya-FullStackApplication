
import { useEffect, useState } from "react";
import PropertyCard from './PropertyCard';
import PropertyService from "../Logic/PropertyService";
import Select from "./CitySelect"
import NotFound from "../Pages/NotFound";

function PropertyCardRows(){
    const [properties, setProperties] = useState([])
    
    useEffect(() => {
        getProperties()
    }, [])

    const getProperties = () => {
        PropertyService.getProperties().then((response)=>{
            setProperties(response.data);
            console.log(response.data.properties);
            console.log(response.data);
        })
    }
    const setSearch = (data) => {
        if(data !== ""){
          PropertyService.getPropertyByCity(data).then((response) => {
            setProperties(response.data);
          });
        }else{
            getProperties();
        }
      }
    
      if (!properties) return <NotFound />;
    return (
       
        <div className="container">
             <Select setCity={setSearch} clear={true} text="Select a location" />
            {properties.map((item, index) => (
                <div>
                  
                    <PropertyCard property={item} key={index}></PropertyCard>
                </div>
            ))}
            
        </div>
    )
} export default PropertyCardRows
