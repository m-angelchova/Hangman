package bg.softuni.hangman.service;

import bg.softuni.hangman.model.enums.GameOutcomeEnum;
import bg.softuni.hangman.model.dto.DictionaryDto;
import bg.softuni.hangman.model.entity.Dictionary;
import bg.softuni.hangman.model.entity.Game;
import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.repository.DictionaryRepository;
import bg.softuni.hangman.repository.GameRepository;
import bg.softuni.hangman.repository.PlayerRepository;
import org.apache.catalina.valves.rewrite.InternalRewriteMap;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    private final DictionaryRepository dictionaryRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    private static final int MAX_WRONG_GUESSES = 6;
    private String word;
    private String hiddenWord;
    private int wrongGuesses;
    private Set<Character> usedLetters;
    private Dictionary dictionary;
    private String currentOutcome;
    private final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    public GameService(DictionaryRepository dictionaryRepository,
                       GameRepository gameRepository,
                       PlayerRepository playerRepository, ModelMapper modelMapper) {
        this.dictionaryRepository = dictionaryRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
    }

    public String playGame(char letter) {
        letter = Character.toUpperCase(letter);
        if (usedLetters.contains(letter)) {
            return "used letter";
        }

        makeGuess(letter);

        if (isWon()) {
            return "Win";
        } else if (isLost()) {
            return "Loss";
        } else {
            return currentOutcome;
        }
    }


    public void gameSetup(){

        if (hiddenWord == null) {

            // Select a word
            Random random = new Random();
            Long randomNumber = random.nextLong(1, dictionaryRepository.count() + 1);

            dictionary = this.dictionaryRepository.findById(randomNumber).orElseThrow(NoSuchElementException::new);
            word = dictionary.getWord().toUpperCase();
            hiddenWord = "-".repeat(word.length());
            wrongGuesses = 0;
            usedLetters = new LinkedHashSet<>();

            LOGGER.info("The random word is: " + word);
        }
    }

    public String makeGuess(char letter) {
        usedLetters.add(letter);

        if (word.indexOf(letter) == -1) {
            currentOutcome = "wrong";
            wrongGuesses++;
            return currentOutcome;
        }
        currentOutcome = "right";
        ;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                hiddenWord = hiddenWord.substring(0, i) + letter + hiddenWord.substring(i + 1);
            }
        }

        return currentOutcome;
    }

    public boolean isWon() {
        return !hiddenWord.contains("-");
    }

    public boolean isLost() {
        return wrongGuesses >= MAX_WRONG_GUESSES;
    }

    public DictionaryDto getDictionary() {
        return this.modelMapper.map(dictionary, DictionaryDto.class);
    }

    public void saveGame(String email) {

        Long score = calculateScore();

        Game game = new Game().setDictionary(dictionary)
                .setOutcome(isWon() ? GameOutcomeEnum.WIN : GameOutcomeEnum.LOSS)
                .setScore(score);

        this.gameRepository.save(game);

        Player loggedUser = this.playerRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
        List<Game> gamesPlayed = loggedUser.getGamesPlayed();
        gamesPlayed.add(game);
        loggedUser.setGamesPlayed(gamesPlayed);
        loggedUser.setScore(loggedUser.getScore() + score);
        this.playerRepository.save(loggedUser);
    }

    public Long calculateScore() {
        Long score;

        switch (wrongGuesses) {
            case 0 -> score = 200L;
            case 1 -> score = 150L;
            case 2 -> score = 100L;
            case 3 -> score = 80L;
            case 4 -> score = 50L;
            case 5 -> score = 20L;
            default -> score = 0L;
        }

        return score;
    }

    public Long getTotalScore(String email)  {

        Player loggedUser = this.playerRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
        gameRestart();
        return loggedUser.getScore();
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public String getUsedLetters() {

        if (usedLetters.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        usedLetters.forEach(l -> sb.append(l + " "));

        return sb.toString().trim();
    }

    public int getWrongGuesses() {
        return wrongGuesses;
    }



    private void gameRestart() {

        dictionary = null;
        word = null;
        hiddenWord = null;
        wrongGuesses = 0;
        usedLetters = null;

    }
}

