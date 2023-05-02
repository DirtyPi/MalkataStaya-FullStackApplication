import { useNavigate } from 'react-router-dom';
import AuthService from '../Logic/AuthService'
const Index = () => {
    const navigate = useNavigate();
    AuthService.logout();
    window.location.assign("http://localhost:3000/sign-in");
    // navigate("/sign-in");
    // window.location.reload();


    return(
        <div></div>
    );
    
}
export default Index;