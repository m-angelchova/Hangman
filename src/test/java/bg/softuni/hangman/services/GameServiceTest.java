package bg.softuni.hangman.services;

import bg.softuni.hangman.model.entity.Dictionary;
import bg.softuni.hangman.model.entity.Game;
import bg.softuni.hangman.model.enums.PlayerRoleEnum;
import bg.softuni.hangman.repository.DictionaryRepository;
import bg.softuni.hangman.repository.GameRepository;
import bg.softuni.hangman.repository.PlayerRepository;
import bg.softuni.hangman.service.GameService;
import bg.softuni.hangman.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class GameServiceTest {
    @Mock
    private DictionaryRepository dictionaryRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private ModelMapper modelMapper;


    private GameService toTest;

    @BeforeEach
    void setUp() {
        toTest = new GameService(dictionaryRepository, gameRepository, playerRepository, modelMapper);

        Dictionary dictionary = new Dictionary().setWord("test").setDefinition("definition");
        when(dictionaryRepository.count()).thenReturn(1L);
        when(dictionaryRepository.findById(1L)).thenReturn(Optional.of(dictionary));
        toTest.gameSetup();
    }

    @Test
    public void testPlayGameCorrectGuess() {
        String expectedOutcome = "right";
        char letter = 't';
        String actualOutcome = toTest.playGame(letter);
        assertEquals(expectedOutcome, actualOutcome);
    }

    @Test
    public void testPlayGameIncorrectGuess() {

        String expectedOutcome = "wrong";
        char letter = 'Z';
        String actualOutcome = toTest.playGame(letter);
        assertEquals(expectedOutcome, actualOutcome);
    }

    @Test
    public void testCalculateScore() {


        toTest.playGame('Z');
        toTest.playGame('t');
        toTest.playGame('a');

        Long expectedOutcome = 100L;
        Long actualOutcome = toTest.calculateScore();
        assertEquals(expectedOutcome, actualOutcome);

    }
}
