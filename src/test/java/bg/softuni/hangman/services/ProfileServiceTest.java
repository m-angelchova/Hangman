package bg.softuni.hangman.services;

import bg.softuni.hangman.model.dto.GameProfileDto;
import bg.softuni.hangman.model.dto.PlayerProfileDto;
import bg.softuni.hangman.model.entity.Dictionary;
import bg.softuni.hangman.model.entity.Game;
import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.model.enums.GameOutcomeEnum;
import bg.softuni.hangman.repository.PlayerRepository;
import bg.softuni.hangman.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    private ProfileService toTest;

    @Mock
    private PlayerRepository mockPlayerRepository;

    @Captor
    private ArgumentCaptor<Player> playerCaptor;


    @BeforeEach
    public void setUp() {
        toTest = new ProfileService(mockPlayerRepository);
    }

    @Test
    void testGetPlayer() {
        String email = "test@example.com";
        String firstName = "Test";
        String lastName = "Testov";
        Long score = 100L;
        List<Game> gamesPlayed = new ArrayList<>();
        List<GameProfileDto> expectedGamesToShow = new ArrayList<>();

        Player player = new Player()
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName).setScore(score)
                .setGamesPlayed(gamesPlayed);

        PlayerProfileDto expectedPlayerProfileDto = new PlayerProfileDto().setFullName(firstName + " " + lastName)
                .setScore(score)
                .setGamesPlayed(expectedGamesToShow);

        when(mockPlayerRepository.findByEmail(email)).thenReturn(Optional.of(player));

        PlayerProfileDto actualPlayerProfileDto = toTest.getPlayer(email);


        assertEquals(expectedPlayerProfileDto.getFullName(), actualPlayerProfileDto.getFullName());
        assertEquals(expectedPlayerProfileDto.getScore(), actualPlayerProfileDto.getScore());
        assertEquals(expectedPlayerProfileDto.getGamesPlayed(), actualPlayerProfileDto.getGamesPlayed());
    }

    @Test
    void testGetGamesPlayedWithEmptyGames() {
        Player player = new Player().setGamesPlayed(new ArrayList<>());
        List<GameProfileDto> expectedGamesToShow = new ArrayList<>();

        List<GameProfileDto> actualGamesToShow = toTest.getGamesPlayed(player);

        assertEquals(expectedGamesToShow, actualGamesToShow);
    }

    @Test
    void testGetGamesPlayedWithLessThanFiveGames() {
        String dictionaryWord = "hello";
        GameOutcomeEnum outcome = GameOutcomeEnum.WIN;
        Long score = 10L;
        Game game = new Game().setDictionary(new Dictionary().setWord(dictionaryWord)).setOutcome(GameOutcomeEnum.WIN).setScore(score);

        List<Game> gamesPlayed = new LinkedList<>();
        gamesPlayed.add(game);

        Player player = new Player().setGamesPlayed(gamesPlayed);


        List<GameProfileDto> expectedGamesToShow = new ArrayList<>();
        GameProfileDto expectedGame = new GameProfileDto().setDictionary(dictionaryWord).setOutcome(outcome.toString()).setScore(score);
        expectedGamesToShow.add(expectedGame);

        List<GameProfileDto> actualGamesToShow = toTest.getGamesPlayed(player);

        assertEquals(expectedGamesToShow.size(), actualGamesToShow.size());
        assertEquals(expectedGamesToShow.get(0).getOutcome(), actualGamesToShow.get(0).getOutcome());
        assertTrue(expectedGamesToShow.contains(expectedGame));

    }


    @Test
    void testChangeEmail() {
        // Set up test data
        String oldEmail = "old@email.bg";
        String newEmail = "new@email.bg";

        Player player = new Player().setEmail(oldEmail);

        when(mockPlayerRepository.findByEmail(oldEmail)).thenReturn(Optional.of(player));

        toTest.changeEmail(oldEmail, newEmail);

        verify(mockPlayerRepository).save(playerCaptor.capture());
        Player actualPlayer = playerCaptor.getValue();

        assertEquals(newEmail, actualPlayer.getEmail());
        assertNotEquals(oldEmail, actualPlayer.getEmail());
    }
}
