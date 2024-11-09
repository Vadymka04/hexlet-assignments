package exercise.controller;

import exercise.daytime.Day;
import exercise.daytime.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class WelcomeController {

    private Daytime daytime;

    public WelcomeController(Daytime daytime) {
        this.daytime = daytime;
    }

    @GetMapping(path = "/welcome")
    public String index(){
        return "It is " +daytime.getName()+ " now! Welcome to Spring!";
    }
}