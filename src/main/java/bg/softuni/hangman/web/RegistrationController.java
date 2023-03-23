package bg.softuni.hangman.web;

import bg.softuni.hangman.model.dto.PlayerRegisterDto;
import bg.softuni.hangman.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class RegistrationController {

  private final PlayerService playerService;

  public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";

  public RegistrationController(PlayerService playerService) {
    this.playerService = playerService;
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

      return "redirect:registration-success";
    }
    //todo
    playerService.registerPlayer(playerRegisterDto);

    return "redirect:login";
  }

  @GetMapping("/activate?code={code}")
  public String accountActivation(@PathVariable String code){
    //TODO: Code switches user to Active;
    return "activation-success";
  }



  // Model attributes

  @ModelAttribute(name = "playerRegisterDto")
  public PlayerRegisterDto initUserRegisterFormDto() {
    return new PlayerRegisterDto();
  }

}
