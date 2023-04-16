package bg.softuni.hangman.web;

import bg.softuni.hangman.model.dto.DictionaryDto;
import bg.softuni.hangman.model.dto.GamePlayDto;
import bg.softuni.hangman.model.dto.PlayerRegisterDto;
import bg.softuni.hangman.service.GameService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GameController {

    private final GameService gameService;
    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/play")
    public String playGame(Model model) {
        this.gameService.gameSetup();
        model.addAttribute("hiddenWord", gameService.getHiddenWord());
        model.addAttribute("usedLetters", gameService.getUsedLetters());
        model.addAttribute("remainingGuesses", 6 - gameService.getWrongGuesses());
        return "game";
    }

    // TODO>: used letters




    @PostMapping("/play")
    public String playGame(@Valid @ModelAttribute(name = "gamePlayDto") GamePlayDto gamePlayDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("gamePlayDto", gamePlayDto)
                    .addFlashAttribute(BINDING_RESULT_PATH + "gamePlayDto", bindingResult);

            return "redirect:/play";
        }


        String outcome = this.gameService.playGame(gamePlayDto.getLetter().charAt(0));
        String message;

        // TODO

        switch (outcome) {
            case "used letter", "right", "wrong" -> {

                if (outcome.equals("used letter")){
                    message = "You already used this letter";
                }else if ((outcome.equals("right"))) {
                    message = String.format("Correct guess! %d attempts remaining!",  6 - gameService.getWrongGuesses());
                }else {
                    message = String.format("Wrong guess! %d attempts remaining!",  6 - gameService.getWrongGuesses());
                }

                redirectAttributes.addFlashAttribute("message", message);

                return "redirect:/play";
            }

            default -> {
                return "redirect:/outcome";
            }

        }
    }

    @ModelAttribute(name = "gamePlayDto")
    public GamePlayDto innitGamePlayDto() {
        return new GamePlayDto();
    }



}
