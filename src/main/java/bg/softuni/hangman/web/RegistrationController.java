package bg.softuni.hangman.web;

import bg.softuni.hangman.model.dto.PlayerRegisterDto;
import bg.softuni.hangman.service.PlayerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class RegistrationController {

  private final PlayerService playerService;
  private final SecurityContextRepository contextRepository;

  public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";


  public RegistrationController(PlayerService playerService, SecurityContextRepository contextRepository) {
    this.playerService = playerService;
    this.contextRepository = contextRepository;
  }


  @GetMapping("/register") // post method localhost:8080/users/register
  public String getRegister(Model model) {
    return "auth-register";
  }

  @PostMapping("/register")
  public String postRegister(@Valid @ModelAttribute(name = "playerRegisterDto") PlayerRegisterDto playerRegisterDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("playerRegisterDto", playerRegisterDto)
          .addFlashAttribute(BINDING_RESULT_PATH + "playerRegisterDto", bindingResult);

      return "redirect:/register";
    }

    playerService.registerPlayer(playerRegisterDto);


    return "redirect:/login";
  }



  // Model attributes

  @ModelAttribute(name = "playerRegisterDto")
  public PlayerRegisterDto initUserRegisterFormDto() {
    return new PlayerRegisterDto();
  }

}
