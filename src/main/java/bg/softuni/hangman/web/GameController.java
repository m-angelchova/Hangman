package bg.softuni.hangman.web;

import bg.softuni.hangman.service.GameService;
import org.springframework.stereotype.Controller;
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
    public String playGame(){
        return "todo/game";
    }

    @PostMapping("/game/play")
    public String playGame(RedirectAttributes redirectAttributes,
                            @RequestParam char letter){
        String outcome = this.gameService.playGame(letter);

        // TODO

        switch (outcome){
            case "used letter" -> {
                return "redirect:/game/play";
            }
            case "Win" -> {
                return "redirect:/game/win";
            }
            case "Loss" -> {
                return "redirect:/game/loss";
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

    @GetMapping("/game/win")
    public String winScreen(){

        // TODO: shows word + score + totalscore

        return "todo/win";
    }

    @GetMapping("/game/loss")
    public String lossScreen(){

        // TODO: shows word + score + totalscore

        return "todo/loss";
    }
}
