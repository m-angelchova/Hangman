package bg.softuni.hangman.web;

import bg.softuni.hangman.model.dto.PlayerRegisterDto;
import bg.softuni.hangman.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Test
    public void testGetRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("auth-register"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("playerRegisterDto"));
    }

    @Test
    public void testPostRegisterWithValidData() throws Exception {
        PlayerRegisterDto playerRegisterDto = new PlayerRegisterDto()
                .setFirstName("test")
                .setLastName("testov")
                .setEmail("test@user")
                .setPassword("password")
                .setConfirmPassword("password");


        mockMvc.perform(MockMvcRequestBuilders.post("/register").with(csrf())
                        .flashAttr("playerRegisterDto", playerRegisterDto))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));

        verify(playerService, times(1)).registerPlayer(playerRegisterDto);
    }

    @Test
    public void testPostRegisterWithInvalidData() throws Exception {
        PlayerRegisterDto playerRegisterDto = new PlayerRegisterDto()
                .setFirstName("test")
                .setLastName("testov")
                .setEmail("test@user")
                .setPassword("password")
                .setConfirmPassword("invalid");

        mockMvc.perform(MockMvcRequestBuilders.post("/register").with(csrf())
                        .flashAttr("playerRegisterDto", playerRegisterDto))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("playerRegisterDto"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("org.springframework.validation.BindingResult.playerRegisterDto"));
    }

}
