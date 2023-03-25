package bg.softuni.hangman.service;

import bg.softuni.hangman.model.entity.Dictionary;
import bg.softuni.hangman.repository.DictionaryRepository;
import bg.softuni.hangman.repository.GameRepository;
import bg.softuni.hangman.repository.PlayerRepository;
import org.apache.el.stream.StreamELResolverImpl;
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

    private static final int MAX_WRONG_GUESSES = 6;
    private String word;
    private String hiddenWord;
    private int wrongGuesses;
    private Set<Character> usedLetters;
    private Dictionary wordClass;
    private Boolean isGameSet;

    public GameService(DictionaryRepository dictionaryRepository,
                       GameRepository gameRepository,
                       PlayerRepository playerRepository) {
        this.dictionaryRepository = dictionaryRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
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
            //you won + word.class
        } else if (isLost()){
            //you lost + word.class
        } else {
            //continue + hidden word
        }
    }


    public void gameSetup() {
        // Select a word
        Random random = new Random();
        Long randomNumber = random.nextLong(1,5);
        wordClass = this.dictionaryRepository.findById(randomNumber).orElseThrow(NoSuchElementException::new);
        word = wordClass.getWord();
        hiddenWord = "-".repeat(word.length());
        wrongGuesses = 0;
        usedLetters = new HashSet<>();
        isGameSet = true;
    }

    public boolean makeGuess(char letter) {

        usedLetters.add(letter);
        if (word.indexOf(letter) == -1) {
            System.out.println("Wrong guess! The letter " + letter + " is not in the word.");
            wrongGuesses++;
            return false;
        }
        System.out.println("Correct guess! The letter " + letter + " is in the word.");
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

}
