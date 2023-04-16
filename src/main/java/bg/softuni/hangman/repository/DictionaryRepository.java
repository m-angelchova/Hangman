package bg.softuni.hangman.repository;

import bg.softuni.hangman.model.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DictionaryRepository extends JpaRepository <Dictionary, Long> {
    Optional<Dictionary> findByWord(String word);
    void deleteByWord (String word);
}
