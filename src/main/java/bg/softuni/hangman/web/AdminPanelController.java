package bg.softuni.hangman.web;

import bg.softuni.hangman.model.dto.DictionaryDto;
import bg.softuni.hangman.service.DictionaryService;
import bg.softuni.hangman.service.PlayerService;
import jakarta.validation.Valid;
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
        return "todo/admin";
    }

//    @PostMapping("/admin")
//    public String addWord(@Valid @ModelAttribute(name = "dictionaryDto") DictionaryDto dictionaryDto,
//                          BindingResult bindingResult,
//                          RedirectAttributes redirectAttributes){
//
//        //TODO: DTO + check if exists -> attributes + message;
//
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("dictionaryDto", dictionaryDto)
//                    .addFlashAttribute(BINDING_RESULT_PATH + "dictionaryDto", bindingResult);
//
//            return "redirect:/admin";
//        }
//
//
//        this.dictionaryService.addWord(dictionaryDto);
//        redirectAttributes.addFlashAttribute("message", "Success!");
//
//        //success message?
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/admin")
//    public String promoteUser(@RequestParam String email,
//                              RedirectAttributes redirectAttributes){
//        this.playerService.promoteToAdmin(email);
//        //success message?
//        return "redirect:/admin";
//    }
//
//    @ModelAttribute(name = "dictionaryDto")
//    public DictionaryDto initDictionaryDto() {
//        return new DictionaryDto();
//    }
}
