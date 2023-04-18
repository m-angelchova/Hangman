package bg.softuni.hangman.web;

import bg.softuni.hangman.model.AppUserDetails;
import bg.softuni.hangman.model.dto.EmailForSettingsDto;
import bg.softuni.hangman.model.dto.PlayerProfileDto;
import bg.softuni.hangman.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {
    private final ProfileService profileService;

    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";

    public ProfileController(ProfileService profileService ) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public String getProfile(Model model,
                             @AuthenticationPrincipal UserDetails user) {
        PlayerProfileDto player = profileService.getPlayer(user.getUsername());
        model.addAttribute("player", player);
        return "profile";
    }


    @PostMapping("/profile")
    public String changeEmail(@Valid @ModelAttribute(name = "emailForSettingsDto") EmailForSettingsDto emailForSettingsDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal AppUserDetails user) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("emailForSettingsDto", emailForSettingsDto)
                    .addFlashAttribute(BINDING_RESULT_PATH + "emailForSettingsDto", bindingResult);

            return "redirect:/profile";
        }


        profileService.changeEmail(user.getUsername(), emailForSettingsDto.getEmail());
        user.setUsername(emailForSettingsDto.getEmail());
        return "redirect:/profile";
    }

    @ModelAttribute(name = "emailForSettingsDto")
    public EmailForSettingsDto innitEmailForSettingsDto() {
        return new EmailForSettingsDto();
    }

}
