package bg.softuni.hangman.web;

import bg.softuni.hangman.model.dto.DictionaryDto;
import bg.softuni.hangman.model.dto.DictionaryToRemoveDto;
import bg.softuni.hangman.model.dto.EmailForPromotionDto;
import bg.softuni.hangman.model.dto.EmailForRemovingDto;
import bg.softuni.hangman.service.DictionaryService;
import bg.softuni.hangman.service.PlayerService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = {"ADMIN"})
public class AdminPanelControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private DictionaryService dictionaryService;

    @InjectMocks
    private AdminPanelController adminPanelController;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminPanelController).build();
    }

    @Test
    public void testAdminPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    public void testAddWordSuccess() throws Exception {
        DictionaryDto dictionaryDto = new DictionaryDto()
                .setWord("test")
                .setDefinition("a definition");

        mockMvc.perform(post("/admin/add-word").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("word", "test")
                        .param("definition", "a definition")
                        .sessionAttr("dictionaryDto", dictionaryDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    public void testRemoveWordWithValidInput() throws Exception {
        DictionaryToRemoveDto dictionaryToRemoveDto = new DictionaryToRemoveDto().setWord("testWord");

        mockMvc.perform(post("/admin/remove-word").with(csrf())
                        .flashAttr("dictionaryToRemoveDto", dictionaryToRemoveDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));

    }

    @Test
    public void testPromoteToAdmin_ValidEmail() throws Exception {
        EmailForPromotionDto emailForPromotionDto = new EmailForPromotionDto()
                .setEmail("test@example.com");

        mockMvc.perform(post("/admin/promote-admin").with(csrf())
                        .flashAttr("emailForPromotionDto", emailForPromotionDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    public void testRemoveAdmin_ValidEmail() throws Exception {
        EmailForRemovingDto emailForRemovingDto = new EmailForRemovingDto();
        emailForRemovingDto.setEmail("test@example.com");

        mockMvc.perform(post("/admin/remove-admin").with(csrf())
                        .flashAttr("emailForRemovingDto", emailForRemovingDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }
}
