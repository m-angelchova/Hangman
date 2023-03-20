package bg.softuni.hangman.repository;

import bg.softuni.hangman.model.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionaryRepository extends JpaRepository <Dictionary, Long> {
}
