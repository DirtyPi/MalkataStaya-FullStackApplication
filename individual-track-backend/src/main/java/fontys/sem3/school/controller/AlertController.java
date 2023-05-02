package fontys.sem3.school.controller;


import fontys.sem3.school.DTO.WS.Greeting;
import fontys.sem3.school.DTO.WS.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class AlertController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting(HtmlUtils.htmlEscape(message.getName()));
    }
}
