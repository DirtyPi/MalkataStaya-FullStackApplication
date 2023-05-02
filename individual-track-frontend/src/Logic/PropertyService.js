import axios from 'axios';
import authHeader from './AuthHeader';
const Property_BASE_REST_API = `http://localhost:8080/properties`;

class PropertyService{
    getAllPropertiesByUser(){
        return axios.get(Property_BASE_REST_API+"/AllProperties/user" ,{headers: authHeader()});
    }
    getApprovedPropertyByUser(){
        return axios.get(Property_BASE_REST_API+"/Approved/user" ,{headers: authHeader()});
    }
    getUnapprovedPropertyByUser(){
        return axios.get(Property_BASE_REST_API+"/Unapproved/user" ,{headers: authHeader()});
    }
   getPropertyByCity(city){
    return axios.get(Property_BASE_REST_API+"/Approved/"+city);
    }
    // getUnapprovedPropertiesCount(){
    //     return axios.get(Property_BASE_REST_API + "/Unapproved/count" ,{headers: authHeader()});
    // }
    getUnapprovedProperties(){
          return axios.get(Property_BASE_REST_API + "/Unapproved" ,{headers: authHeader()});
    }
    getProperties(){
        return axios.get(Property_BASE_REST_API ,{headers: authHeader()});
    }
    // createProperty(Property){
    //     return axios.post(Property_BASE_REST_API,Property);
    // }
    postProperty(Property){
        axios.post(Property_BASE_REST_API,Property,{headers: authHeader()});
    }
    getPropertyById(propertyId){
        return axios.get(Property_BASE_REST_API + '/' + propertyId);
    }
    deleteProperty(propertyId){
        return axios.post(Property_BASE_REST_API + '/delete' , {propertyId} ,{headers: authHeader()});
    }
    ApproveProperty(Property){
        return axios.put(Property_BASE_REST_API + "/update" ,Property,{headers: authHeader()});
    }
}
export default new PropertyService();