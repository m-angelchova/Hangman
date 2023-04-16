package bg.softuni.hangman.web;

import bg.softuni.hangman.service.ScoreboardService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//TODO

@Controller
public class ScoreboardController {
    private final ScoreboardService scoreboardService;

    public ScoreboardController(ScoreboardService scoreboardService) {
        this.scoreboardService = scoreboardService;
    }


    @GetMapping("/scoreboard")
    public String getAllOffers(Model model,
                               @PageableDefault(
                                       sort = "score", direction = Sort.Direction.DESC
                               ) Pageable pageable) {

        var playerScores = scoreboardService.getPlayerScores(pageable);

        model.addAttribute("players", playerScores);

        return "scoreboard";
    }
}
