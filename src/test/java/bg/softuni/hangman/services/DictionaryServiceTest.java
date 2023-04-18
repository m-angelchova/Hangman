package bg.softuni.hangman.services;

import bg.softuni.hangman.model.dto.DictionaryDto;
import bg.softuni.hangman.model.dto.DictionaryToRemoveDto;
import bg.softuni.hangman.model.entity.Dictionary;
import bg.softuni.hangman.repository.DictionaryRepository;
import bg.softuni.hangman.service.DictionaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DictionaryServiceTest {
    @Mock
    private DictionaryRepository mockDictionaryRepository;


    @Captor
    private ArgumentCaptor<Dictionary> dictionaryArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> wordArgumentCaptor;

    private DictionaryService toTest;

    @BeforeEach
    void setUp() {
        toTest = new DictionaryService(mockDictionaryRepository);
    }


    @Test
    void testAddWord() {
        String word = "word";
        String definition = "definition";

        DictionaryDto testDto = new DictionaryDto()
                .setWord(word)
                .setDefinition(definition);


        when(mockDictionaryRepository.findByWord(testDto.getWord()))
                .thenReturn(Optional.empty());

        toTest.addWord(testDto);


        //ASSERT
        verify(mockDictionaryRepository).save(dictionaryArgumentCaptor.capture());

        Dictionary actualSavedDictionary = dictionaryArgumentCaptor.getValue();
        assertEquals(testDto.getWord(), actualSavedDictionary.getWord());
        assertEquals(testDto.getDefinition(), actualSavedDictionary.getDefinition());
    }

    @Test
    void testRemoveWord() {
        String word = "word";
        String definition = "definition";

        DictionaryToRemoveDto testDto = new DictionaryToRemoveDto().setWord(word);
        Dictionary testDictionary = new Dictionary().setWord(word).setDefinition(definition);

        when(mockDictionaryRepository.findByWord(testDto.getWord()))
                .thenReturn(Optional.of(testDictionary));

        toTest.removeWord(testDto);


        // Capture the argument before calling verify
        verify(mockDictionaryRepository).deleteByWord(wordArgumentCaptor.capture());

        //ASSERT
        String actualDeletedWord = wordArgumentCaptor.getValue();
        assertEquals(testDto.getWord(), actualDeletedWord);
    }

}
