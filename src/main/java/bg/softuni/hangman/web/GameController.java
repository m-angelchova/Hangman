package bg.softuni.hangman.web;

import bg.softuni.hangman.model.dto.DictionaryDto;
import bg.softuni.hangman.service.GameService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GameController {
    // TODO - UI

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/game/play")
    public String playGame() {
        return "todo/game";
    }

    @PostMapping("/game/play")
    public String playGame(RedirectAttributes redirectAttributes,
                           @RequestParam char letter,
                           @AuthenticationPrincipal UserDetails user) {
        String outcome = this.gameService.playGame(letter);

        // TODO

        switch (outcome) {
            case "used letter" -> {
                return "redirect:/game/play";
            }
            case "Win", "Loss" -> {
                return "redirect:/game/end";
            }
            case "right" -> {

                String dosmth;
                return "redirect:/game/play";
            }
            default -> {
                //wrong
                String dosmthElse = "1";
                return "redirect:/game/play";

            }
        }
    }

    @GetMapping("/game/end")
    public String winLossScreen(@AuthenticationPrincipal UserDetails user,
                                Model model) {

        // TODO: shows word + score + totalscore

        gameService.saveGame(user.getUsername());

        Long score = gameService.calculateScore();
        Long totalScore = gameService.getTotalScore(user.getUsername());
        DictionaryDto dictionary = gameService.getDictionary();

        model.addAttribute("dictionary", dictionary);
        model.addAttribute("score", score);
        model.addAttribute("totalScore", totalScore);


        if (gameService.isWon()) {
            return "redirect:/todo/win";
        } else {
            return "redirect:/todo/loss";
        }
    }
}
