package bg.softuni.hangman.service;

import bg.softuni.hangman.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreboardService {
    private final PlayerRepository playerRepository;

    public ScoreboardService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void getScoreboard(){
        this.playerRepository.

    }
}
