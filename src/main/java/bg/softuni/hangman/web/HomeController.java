package bg.softuni.hangman.web;

import bg.softuni.hangman.model.AppUserDetails;
import bg.softuni.hangman.model.entity.Dictionary;
import bg.softuni.hangman.service.DictionaryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // TODO

    private final DictionaryService dictionaryService;

    public HomeController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/")
    public String getHomeForNotLogged() {
        return "index";
    }

    @GetMapping("/home")
    public String getHomeForLogged(@AuthenticationPrincipal AppUserDetails appUserDetails, Model model) {
        model.addAttribute("firstName", appUserDetails.getFirstName());
        model.addAttribute("score", appUserDetails.getScore());

        Dictionary wordOfTheDay = dictionaryService.getWordOfTheDay();

        model.addAttribute("word", wordOfTheDay.getWord().toUpperCase());
        model.addAttribute("definition", wordOfTheDay.getDefinition());
        return "home";
    }


}
