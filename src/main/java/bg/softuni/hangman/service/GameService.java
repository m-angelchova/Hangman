package bg.softuni.hangman.service;

import bg.softuni.hangman.model.constant.GameOutcomeEnum;
import bg.softuni.hangman.model.dto.DictionaryDto;
import bg.softuni.hangman.model.entity.Dictionary;
import bg.softuni.hangman.model.entity.Game;
import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.repository.DictionaryRepository;
import bg.softuni.hangman.repository.GameRepository;
import bg.softuni.hangman.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

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
    private Boolean isGameSet;
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

    public String playGame(char letter){
        if (!isGameSet) {
            gameSetup();
        }

        if (usedLetters.contains(letter)) {
            return "used letter";
        }

        makeGuess(letter);

        if (isWon()) {
            return "Win";
        } else if (isLost()){
            return "Loss";
        } else {
            return currentOutcome;
        }
    }


    public void gameSetup() {
        // Select a word
        Random random = new Random();
        Long randomNumber = random.nextLong(1,5);
        //todo bonus: make sure word doesn't repeat

        dictionary = this.dictionaryRepository.findById(randomNumber).orElseThrow(NoSuchElementException::new);
        word = dictionary.getWord();
        hiddenWord = "-".repeat(word.length());
        wrongGuesses = 0;
        usedLetters = new HashSet<>();
        isGameSet = true;

        LOGGER.info("The random word is: " + word);
    }

    public boolean makeGuess(char letter) {
        usedLetters.add(letter);

        if (word.indexOf(letter) == -1) {
            currentOutcome = "wrong";
            wrongGuesses++;
            return false;
        }
        currentOutcome = "right";;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                hiddenWord = hiddenWord.substring(0, i) + letter + hiddenWord.substring(i + 1);
            }
        }
        return true;
    }

    public boolean isWon() {
        return !hiddenWord.contains("-");
    }

    public boolean isLost() {
        return wrongGuesses >= MAX_WRONG_GUESSES;
    }

    public DictionaryDto getDictionary(){
        return this.modelMapper.map(dictionary, DictionaryDto.class);
    }

    public void saveGame(String email){

        Long score = isWon() ? calculateScore() : 0L;

        Game game = new Game().setDictionary(dictionary)
                .setOutcome(isWon() ? GameOutcomeEnum.WIN : GameOutcomeEnum.LOSS)
                .setScore(score);

        this.gameRepository.save(game);
        Player loggedUser = this.playerRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
        loggedUser.getGamesPlayed().add(game);
        loggedUser.setScore(loggedUser.getScore() + score);
        this.playerRepository.save(loggedUser);
    }

    public Long calculateScore(){
        Long score;

        switch (wrongGuesses){
            case 0 -> score = 200L;
            case 1 -> score = 150L;
            case 2 -> score = 100L;
            case 3 -> score = 80L;
            case 4 -> score = 50L;
            default -> score = 20L;
        }

        return score;
    }

    public Long getTotalScore(String email){

        Player loggedUser = this.playerRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);

        return loggedUser.getScore();
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public Set<Character> getUsedLetters() {
        return usedLetters;
    }
}
