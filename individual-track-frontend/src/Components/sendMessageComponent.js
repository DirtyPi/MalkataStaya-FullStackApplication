import React, { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import Button from 'react-bootstrap/Button'
import AuthService from '../Logic/AuthService';


// Set the backend location
const ENDPOINT = "http://localhost:8080/ws";

function Alert() {

    AuthService.checkTokenAdmin();

    const [stompClient, setStompClient] = useState(null);
    const [msgToSend, setSendMessage] = useState("Enter your message here!");

    function handle(){
        var r = window.confirm("Are you sure you want to send this alert?");
        if (r === true) {
            stompClient.send("/app/hello", {}, JSON.stringify({ 'name': msgToSend }));
        }
    }

    useEffect(() => {
        // use SockJS as the websocket client
        const socket = SockJS(ENDPOINT);
        // Set stomp to use websockets
        const stompClient = Stomp.over(socket);

        setStompClient(stompClient);
    }, []);
    return (
        
        <div className="">
             
            <div className="">
                <div className="form-group">
                    <label>Message:</label>
                    <textarea name="details" className="form-control" cols="25" rows="15" data-cy="text" minlength="25" required onChange={(event) => setSendMessage(event.target.value)}></textarea>
                </div>
                <br></br>
                <center><Button onClick={handle}>Send Message</Button></center>
            </div>
        </div>

    );

}

export default Alert;