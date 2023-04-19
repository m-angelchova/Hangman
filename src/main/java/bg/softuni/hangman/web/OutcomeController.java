package bg.softuni.hangman.web;

import bg.softuni.hangman.model.dto.DictionaryDto;
import bg.softuni.hangman.service.GameService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OutcomeController {

    private final GameService gameService;

    public OutcomeController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/outcome")
    public String winLossScreen(@AuthenticationPrincipal UserDetails user,
                                Model model) {

        String result;
        if (gameService.isWon()) {
            result = "WIN";
        } else {
            result = "LOSS";
        }

        Long score = gameService.calculateScore();
        DictionaryDto dictionary = gameService.getDictionary();
        gameService.saveGame(user.getUsername());
        Long totalScore = gameService.getTotalScore(user.getUsername());


        model.addAttribute("result", result);
        model.addAttribute("dictionary", dictionary);
        model.addAttribute("score", score.toString());
        model.addAttribute("totalScore", totalScore.toString());

        gameService.gameRestart();
        return "outcome";
    }

}
