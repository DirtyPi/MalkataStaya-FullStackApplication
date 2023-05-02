import React from 'react'
import { useHistory } from 'react-router';
import { useNavigate } from 'react-router-dom';
import AuthService from '../Logic/AuthService'

const LogOut = () => {
    const navigate = useNavigate();
    AuthService.logout();
    navigate("/sign-in");
    window.location.reload();


    return(
        <div></div>
    );

}
export default LogOut;