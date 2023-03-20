package bg.softuni.hangman.repository;

import bg.softuni.hangman.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role, Long> {
}
