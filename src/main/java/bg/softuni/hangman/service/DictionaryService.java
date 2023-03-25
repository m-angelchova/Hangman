package bg.softuni.hangman.service;

import bg.softuni.hangman.model.entity.Dictionary;
import bg.softuni.hangman.repository.DictionaryRepository;
import org.springframework.stereotype.Service;

@Service
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    public void addWord(String wordToAdd, String description){
        Dictionary dictionary = new Dictionary()
                .setWord(wordToAdd)
                .setDefinition(description);

        this.dictionaryRepository.save(dictionary);
    }
}
