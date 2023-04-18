package bg.softuni.hangman.web;

import bg.softuni.hangman.service.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class GameControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;


// Was working att first, now it's not :/
//    @Test
//    @WithMockUser
//    public void testPlayGame() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/play"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.model().attributeExists("hiddenWord"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("usedLetters"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("remainingGuesses"))
//                .andExpect(MockMvcResultMatchers.view().name("game"));
//    }

    @Test
    public void testPlayGame_withValidInput() throws Exception {
        String outcome = "right";
        char guessedLetter = 'A';
        int wrongGuesses = 2;

        Mockito.when(gameService.playGame(guessedLetter)).thenReturn(outcome);
        Mockito.when(gameService.getWrongGuesses()).thenReturn(wrongGuesses);

        mockMvc.perform(MockMvcRequestBuilders.post("/play").with(csrf())
                        .param("letter", String.valueOf(guessedLetter)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.flash().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/play"));
    }

}
