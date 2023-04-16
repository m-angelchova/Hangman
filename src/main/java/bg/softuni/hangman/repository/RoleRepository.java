package bg.softuni.hangman.repository;

import bg.softuni.hangman.model.enums.PlayerRoleEnum;
import bg.softuni.hangman.model.entity.PlayerRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository <PlayerRole, Long> {
    Optional<PlayerRole> findByRole(PlayerRoleEnum role);
}
