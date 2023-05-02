
// import { useEffect, useState } from "react";
// import PropertyCard from './MyPropertyCard';
// import PropertyService from "../Logic/PropertyService";
// import NotFound from "../Pages/NotFound";

// function PropertyCardRows(){
//     const [properties, setProperties] = useState([]);
//     const [selectedOption, setSelectedOption] = useState(null);
    
//     useEffect(() => {
//         getProperties()
//     }, [])


   
  

//     const getProperties = () => {
//         PropertyService.getAllPropertiesByUser().then((response)=>{
//             setProperties(response.data);
//             console.log(response.data)
//         })
//     }
   
    
//       if (!properties) return <NotFound />;
//     return (
       
//         <div className="container">
            
//             {properties.map((item, index) => (
//                 <div>
                  
//                     <PropertyCard property={item} key={index}></PropertyCard>
//                 </div>
//             ))}
            
//         </div>
//     )
// } export default PropertyCardRows



import { useEffect, useState } from 'react';
import PropertyCard from './MyPropertyCard';
import PropertyService from '../Logic/PropertyService';
import NotFound from '../Pages/NotFound';

function PropertyCardRows() {
  const [properties, setProperties] = useState([]);
  const [selectedOption, setSelectedOption] = useState(null);

  useEffect(() => {
    let cancel = false;

    async function fetchData() {
      if (selectedOption === 'approved') {
        const result = await PropertyService.getApprovedPropertyByUser();
        if (!cancel) {
          setProperties(result.data);
        }
      } else if (selectedOption === 'unapproved') {
        const result = await PropertyService.getUnapprovedPropertyByUser();
        if (!cancel) {
          setProperties(result.data);
        }
      } else {
        const result = await PropertyService.getAllPropertiesByUser();
        if (!cancel) {
          setProperties(result.data);
        }
      }
    }

    if (selectedOption) {
      fetchData();
    }

    return () => {
      cancel = true;
    };
  }, [selectedOption]);

  function handleOptionChange(event) {
    setSelectedOption(event.target.value);
  }

  return (
    <div>
        <div style={{borderRadius: "20px",boxShadow: "5px 5px 5px black" , backgroundColor: "#FFDEAD", padding: "20px", margin: "28%" ,width:"600px"}}>
            <h3>View your properties</h3>
    <select onChange={handleOptionChange}>
    <option value="all">Select list</option>
        <option value="all">All</option>
        <option value="approved">Approved</option>
        <option value="unapproved">Unapproved</option>
    </select>
        </div>
     
      {!properties && <NotFound />}
      <div className="container">
      {properties.map((property, index) => (
<div key={index}>
<PropertyCard key={property.id} property={property} />
</div>
        ))}
      </div>
    </div>
  );
}

export default PropertyCardRows;