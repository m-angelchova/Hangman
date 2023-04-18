package bg.softuni.hangman.service;

import bg.softuni.hangman.model.dto.ScoreboardPlayersDto;
import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ScoreboardService {
    private final PlayerRepository playerRepository;

    public ScoreboardService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;

    }

    public Page<ScoreboardPlayersDto> getPlayerScores(Pageable pageable) {
        return
                playerRepository.
                        findAll(pageable).
                        map(this::map);
    }

    private ScoreboardPlayersDto map(Player player) {
        return new ScoreboardPlayersDto()
                .setFullName(player.getFirstName() + " " + player.getLastName())
                .setGamesPlayed((long) player.getGamesPlayed().size())
                .setScore(player.getScore());
    }

}
