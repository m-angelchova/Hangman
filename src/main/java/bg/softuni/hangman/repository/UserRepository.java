package bg.softuni.hangman.repository;

import bg.softuni.hangman.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
}
