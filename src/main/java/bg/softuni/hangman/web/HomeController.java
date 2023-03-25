package bg.softuni.hangman.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    // TODO

    @GetMapping("/")
    public String getHomeForNotLogged() {
        return "index";
    }

    @GetMapping("/home")
    public String getHomeForLogged() {
        return "index";
    }


}
