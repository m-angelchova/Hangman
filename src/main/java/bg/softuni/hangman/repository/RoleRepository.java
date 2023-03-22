package bg.softuni.hangman.repository;

import bg.softuni.hangman.model.entity.PlayerRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <PlayerRole, Long> {
}
