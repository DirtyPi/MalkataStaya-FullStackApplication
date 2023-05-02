import axios from 'axios';
import authHeader from './AuthHeader';
const User_BASE_REST_API = `http://localhost:8080/user`;

class UserService{
   getUser(){
        return axios.get(User_BASE_REST_API);
    }
    getCurrentinfo(){
        return axios.get(User_BASE_REST_API + "/myDetails",{headers: authHeader()});
    }
    createUser(User){
        return axios.post(User_BASE_REST_API,User);
    }
    register(email,firstName,lastName,dateOfBirth,gender,phoneNumber,password){
        return axios.post(User_BASE_REST_API + "/register", {
            email,
            firstName,
            lastName,
            dateOfBirth,
            gender,
            phoneNumber,
            password
        });
    }
    updateUser(
        firstName,
        lastName,
        email,
        phoneNumber,
        dateOfBirth,
        gender){
        return axios.put(User_BASE_REST_API,{
            firstName,
        lastName,
        email,
        phoneNumber,
        dateOfBirth,
        gender
        },
            {headers: authHeader()});
    }
    getUserById(userId){
        return axios.get(User_BASE_REST_API + '/' + userId);
    }
    deleteUser(userId){
        return axios.delete(User_BASE_REST_API + '/' + userId);
    }
}
export default new UserService();