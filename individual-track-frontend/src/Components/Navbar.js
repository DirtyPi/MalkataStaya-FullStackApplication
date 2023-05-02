
import { Navbar, Nav, Container } from 'react-bootstrap';
import AuthService from "../Logic/AuthService"
import React,{ Fragment } from 'react';
import { Store } from 'react-notifications-component';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import '../../node_modules/react-notifications-component/dist/theme.css'


var isLoggedIn = 0;
if (AuthService.getCurrentUser() !== null && AuthService.getCurrentUser().roles.includes("ROLE_ADMIN")) {
    isLoggedIn = 1;
} else if (AuthService.getCurrentUser() !== null) {
    isLoggedIn = 2;
}
const ENDPOINT = "http://localhost:8080/ws";
const Navb = () => {

    const [stompClient, setStompClient] = React.useState(null);
    
    React.useEffect(() => {
        // use SockJS as the websocket client
        const socket = SockJS(ENDPOINT);
        
        // Set stomp to use websockets
        const stompClient = Stomp.over(socket);
        // connect to the backend
        stompClient.connect({}, () => {
            stompClient.subscribe('/topic/greetings', (data) => {
                onMessageReceived(data);
            });
        });
        // maintain the client for sending and receiving
        setStompClient(stompClient);
    }, []);

    function onMessageReceived(data) {
        const result = JSON.parse(data.body);
        Store.addNotification({
            title: "Message from the admin:",
            message: result.content,
            type: "info",
            insert: "top",
            container: "top-left",
            animationIn: ["animate__animated", "animate__fadeIn"],
            animationOut: ["animate__animated", "animate__fadeOut"],
            dismiss: {
                duration: 5000,
                onScreen: true
            }
        });

    };


    let menu = '';
    if(isLoggedIn === 0){
        menu = (
            <Fragment>
            <Nav.Link href='/sign-in'>Login</Nav.Link>
            <Nav.Link href='/sign-up'>Sign Up</Nav.Link>
        </Fragment>
           
        )
    }else if(isLoggedIn === 1) {
        menu = (
            <Fragment>
                <h5>Admin</h5>
                <Nav.Link href='/Unapproved-properties'>Unapproved Properties</Nav.Link>
                <Nav.Link href='/send-message'>Send Message</Nav.Link>
                <Nav.Link onClick={()=>{stompClient.disconnect();}} href='/sign-out'>Sign Out</Nav.Link>
            </Fragment>
        )
    }else{
        menu = (
            <Fragment>
            <Nav.Link href='/my-profile'>My Profile</Nav.Link>
            <Nav.Link href='/my-properties'>My Properties</Nav.Link>
            <Nav.Link href='/create-property'>Create Property</Nav.Link>
            <Nav.Link onClick={()=>{stompClient.disconnect();}} href='/sign-out'>Sign Out</Nav.Link>
            </Fragment>
        )
    }

   
   
return(
    <>
            <Navbar collapseOnSelect expand="sm" bg="light" variant="light" >
                <Container>
                    <Navbar.Toggle aria-controls='responsive-navbar-nav' />
                    <Navbar.Collapse id='responsive-navbar-nav'>
                        <Nav className="me-auto my-2 my-lg-0">
                            <Navbar.Brand href='/'>
                               <h3>Malkata Staya</h3>
                            </Navbar.Brand>
                            <Nav.Link href='/MainPage'>View Properties</Nav.Link>
                        </Nav>
                        <Nav >
                            {menu}
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
)
}
export default Navb;