package bg.softuni.hangman.repository;

import bg.softuni.hangman.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository <Game, Long> {
}
