package bg.softuni.hangman.web;

import bg.softuni.hangman.service.DictionaryService;
import bg.softuni.hangman.service.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminPanelController {
    // TODO: Page for admin options - make user admins + add words + optional: ban users
    private final PlayerService playerService;
    private final DictionaryService dictionaryService;

    public AdminPanelController(PlayerService playerService, DictionaryService dictionaryService) {
        this.playerService = playerService;
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "todo/admin";
    }

    @PostMapping("/admin")
    public String addWord(@RequestParam String wordToAdd,
                          @RequestParam String description){
        this.dictionaryService.addWord(wordToAdd, description);
        //success message?
        return "redirect:/admin";
    }

    @PostMapping("/admin")
    public String promoteUser(@RequestParam String email){
        this.playerService.promoteToAdmin(email);
        //success message?
        return "redirect:/admin";
    }
}
