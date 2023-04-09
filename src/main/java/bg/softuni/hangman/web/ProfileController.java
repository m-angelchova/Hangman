package bg.softuni.hangman.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping
    public String getProfile(){
        return "profile";
    }
}
