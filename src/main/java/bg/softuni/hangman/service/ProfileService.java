package bg.softuni.hangman.service;


import bg.softuni.hangman.model.dto.GameProfileDto;
import bg.softuni.hangman.model.dto.PlayerProfileDto;
import bg.softuni.hangman.model.entity.Game;
import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.repository.PlayerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProfileService {
    private final PlayerRepository playerRepository;



    public ProfileService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;

    }

    public PlayerProfileDto getPlayer(String email) {
        Player player = this.playerRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);

        return new PlayerProfileDto().setFullName(player.getFirstName() + " " + player.getLastName())
                .setScore(player.getScore())
                .setGamesPlayed(getGamesPlayed(player));
    }

    public List<GameProfileDto> getGamesPlayed(Player player) {

        List<Game> gamesPlayed = player.getGamesPlayed();

        if(gamesPlayed.isEmpty()){
            return new ArrayList<>();
        }

        List<GameProfileDto> gamesToShow = new LinkedList<>();
        for (int i = gamesPlayed.size() - 1; i > gamesPlayed.size() - 6; i--) {
            if (i == 0) {
                break;
            }
            gamesToShow.add(map(gamesPlayed.get(i)));
        }

        return gamesToShow;
    }


    private GameProfileDto map(Game game) {
        return new GameProfileDto()
                .setDictionary(game.getDictionary().getWord())
                .setOutcome(game.getOutcome().toString())
                .setScore(game.getScore());
    }

    public void changeEmail(UserDetails user, String newEmail) {
        Player player = this.playerRepository.findByEmail(user.getUsername()).orElseThrow(NoSuchElementException::new);


        player.setEmail(newEmail);
        playerRepository.save(player);
    }
}
