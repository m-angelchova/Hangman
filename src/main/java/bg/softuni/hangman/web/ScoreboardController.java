package bg.softuni.hangman.web;

import org.springframework.web.bind.annotation.GetMapping;

public class ScoreboardController {
    // TODO - Show scoreboard

    @GetMapping("/scoreboard")
    public String getHomeForNotLogged() {
        return "todo/scoreboard";
    }
}
