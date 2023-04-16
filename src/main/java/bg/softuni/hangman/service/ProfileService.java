package bg.softuni.hangman.service;


import bg.softuni.hangman.model.AppUserDetails;
import bg.softuni.hangman.model.dto.GameProfileDto;
import bg.softuni.hangman.model.dto.PlayerProfileDto;
import bg.softuni.hangman.model.entity.Game;
import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.repository.GameRepository;
import bg.softuni.hangman.repository.PlayerRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;


    public ProfileService(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    public PlayerProfileDto getPlayer(String email) throws NoSuchElementException {
        Player player = this.playerRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);

        return new PlayerProfileDto().setFullName(player.getFirstName() + " " + player.getLastName())
                .setScore(player.getScore())
                .setGamesPlayed(getGamesPlayed(player));
    }

    public List<GameProfileDto> getGamesPlayed(Player player) throws NoSuchElementException {

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

    public void changeEmail(UserDetails user, String newEmail) throws NoSuchElementException {
        Player player = this.playerRepository.findByEmail(user.getUsername()).orElseThrow(NoSuchElementException::new);


        player.setEmail(newEmail);
        playerRepository.save(player);
    }
}
