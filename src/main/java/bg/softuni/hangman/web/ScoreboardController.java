package bg.softuni.hangman.web;

import bg.softuni.hangman.model.dto.ScoreboardPlayersDto;
import bg.softuni.hangman.service.ScoreboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//TODO

@Controller
public class ScoreboardController {
    private final ScoreboardService scoreboardService;

    public ScoreboardController(ScoreboardService scoreboardService) {
        this.scoreboardService = scoreboardService;
    }

    @GetMapping("/scoreboard")
    public String getScoreboard(Model model) {
        List<ScoreboardPlayersDto> scoreboard = scoreboardService.getScoreboard();

        model.addAttribute("scoreboard",scoreboard);

        return "todo/scoreboard";
    }
}
