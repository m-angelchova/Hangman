package bg.softuni.hangman.web;

import bg.softuni.hangman.model.AppUserDetails;
import bg.softuni.hangman.model.dto.*;
import bg.softuni.hangman.service.DictionaryService;
import bg.softuni.hangman.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminPanelController {
    //TODO: Page for admin options - make user admins + add words + optional: ban users
    private final PlayerService playerService;
    private final DictionaryService dictionaryService;
    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";

    public AdminPanelController(PlayerService playerService, DictionaryService dictionaryService) {
        this.playerService = playerService;
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "admin";
    }

    @PostMapping("/admin/add-word")
    public String addWord(@Valid @ModelAttribute(name = "dictionaryDto") DictionaryDto dictionaryDto,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("dictionaryDto", dictionaryDto)
                    .addFlashAttribute(BINDING_RESULT_PATH + "dictionaryDto", bindingResult);

            return "redirect:/admin";
        }


        this.dictionaryService.addWord(dictionaryDto);
        redirectAttributes.addFlashAttribute("message", "Success!");

        //success message?
        return "redirect:/admin";
    }


    @PostMapping("/admin/remove-word")
    public String removeWord(@Valid @ModelAttribute(name = "dictionaryToRemoveDto") DictionaryToRemoveDto dictionaryToRemoveDto,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("dictionaryToRemoveDto", dictionaryToRemoveDto)
                    .addFlashAttribute(BINDING_RESULT_PATH + "dictionaryToRemoveDto", bindingResult);

            return "redirect:/admin";
        }


        this.dictionaryService.removeWord(dictionaryToRemoveDto);
        redirectAttributes.addFlashAttribute("message", "Success!");

        //success message?
        return "redirect:/admin";
    }



    @PostMapping("/admin/promote-admin")
    public String promoteToAdmin(@Valid @ModelAttribute(name = "emailForPromotionDto") EmailForPromotionDto emailForPromotionDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("emailForPromotionDto", emailForPromotionDto)
                    .addFlashAttribute(BINDING_RESULT_PATH + "emailForPromotionDto", bindingResult);

            return "redirect:/admin";
        }


        playerService.promoteToAdmin(emailForPromotionDto.getEmail());
        return "redirect:/admin";
    }

    @PostMapping("/admin/remove-admin")
    public String removeAdmin(@Valid @ModelAttribute(name = "emailForRemovingDto") EmailForRemovingDto emailForRemovingDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("emailForRemovingDto", emailForRemovingDto)
                    .addFlashAttribute(BINDING_RESULT_PATH + "emailForRemovingDto", bindingResult);

            return "redirect:/admin";
        }


        playerService.removeAdmin(emailForRemovingDto.getEmail());
        return "redirect:/admin";
    }


    @ModelAttribute(name = "dictionaryDto")
    public DictionaryDto initDictionaryDto() {
        return new DictionaryDto();
    }

    @ModelAttribute(name = "dictionaryToRemoveDto")
    public DictionaryToRemoveDto initDictionaryToRemoveDto() {
        return new DictionaryToRemoveDto();
    }

    @ModelAttribute(name = "emailForPromotionDto")
    public EmailForPromotionDto innitEmailForPromotionDto() {
        return new EmailForPromotionDto();
    }

    @ModelAttribute(name = "emailForRemovingDto")
    public EmailForRemovingDto innitEmailForRemovingDto() {
        return new EmailForRemovingDto();
    }
}
