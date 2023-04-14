package bg.softuni.hangman.service;

import bg.softuni.hangman.model.dto.DictionaryDto;
import bg.softuni.hangman.model.entity.Dictionary;
import bg.softuni.hangman.repository.DictionaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;
    private Dictionary wordOfTheDay;
    private Long wordId;


    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
        wordOfTheDay = new Dictionary();
        wordId = 0L;
    }

    public void addWord(DictionaryDto dto){
        Dictionary dictionary = new Dictionary()
                .setWord(dto.getWord())
                .setDefinition(dto.getDefinition());

        this.dictionaryRepository.save(dictionary);
    }

   // @Scheduled(cron = "0 0 0 * * *")
    public void selectWordOfTheDay(){
        Random random = new Random();
        Long randomNumber = random.nextLong(1, dictionaryRepository.count() + 1);

        if (wordId.equals(randomNumber)){
            randomNumber = random.nextLong(1, dictionaryRepository.count() + 1);
        }
        wordOfTheDay = this.dictionaryRepository.findById(randomNumber).orElseThrow(NoSuchElementException::new);
        wordId = randomNumber;

    }

    public Dictionary getWordOfTheDay() {
        selectWordOfTheDay();
        return wordOfTheDay;
    }
}
