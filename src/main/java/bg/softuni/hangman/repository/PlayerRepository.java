package bg.softuni.hangman.repository;

import bg.softuni.hangman.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository <Player, Long> {
    Optional<Player> findByEmail(String email);
}
