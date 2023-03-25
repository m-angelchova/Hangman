package bg.softuni.hangman.web;

import org.springframework.web.bind.annotation.GetMapping;

public class PlayerProfileController {
    //TODO
    @GetMapping("/profile")
    public String getProfile(){
        return "todo/profile";
    }
}
