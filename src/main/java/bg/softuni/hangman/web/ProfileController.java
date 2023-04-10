package bg.softuni.hangman.web;

import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.service.PlayerService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {
    private final PlayerService playerService;

    public ProfileController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/profile")
    public String getProfile(Model model,
                             @AuthenticationPrincipal UserDetails user){

        //TODO: Convert to DTO
        Player player = playerService.getPlayer(user.getUsername());
        model.addAttribute("player", player);

        return "profile";
    }


    @PostMapping ("/profile")
    public String changeEmail(@RequestParam String newEmail,
                              @AuthenticationPrincipal UserDetails user){
        playerService.changeEmail(user.getUsername(), newEmail);
        return "redirect:/profile";
    }
}
