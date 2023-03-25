package bg.softuni.hangman.service;

import bg.softuni.hangman.model.dto.ScoreboardPlayersDto;
import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.repository.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ScoreboardService {
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    public ScoreboardService(PlayerRepository playerRepository, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
    }

    public List<ScoreboardPlayersDto> getScoreboard(){

        List<ScoreboardPlayersDto> sbPlayers = getScoreboardPlayers();
        sbPlayers.sort(compareByScore());
        // TODO: test if mapper is working correctly
        // TODO: test if comparator is working correctly
        return sbPlayers;
    }

    public Comparator<ScoreboardPlayersDto> compareByScore(){
        return new Comparator<ScoreboardPlayersDto>() {
            @Override
            public int compare(ScoreboardPlayersDto p1, ScoreboardPlayersDto p2) {
                return p1.getScore().compareTo(p2.getScore());
            }
        };
    }

    public TypeMap<Player, ScoreboardPlayersDto> getPropertyMapper(){
        return this.modelMapper.createTypeMap(Player.class, ScoreboardPlayersDto.class)
                .addMappings(modelMapper -> modelMapper.map(p -> p.getFirstName() + " " + p.getLastName(), ScoreboardPlayersDto::setFullName))
                .addMappings(modelMapper -> modelMapper.map(p -> p.getGamesPlayed().size(), ScoreboardPlayersDto::setGamesPlayed));
    }

    public List<ScoreboardPlayersDto> getScoreboardPlayers(){
        TypeMap<Player, ScoreboardPlayersDto> propertyMapper = getPropertyMapper();
        List<Player> players = this.playerRepository.findAll().stream().toList();
        List<ScoreboardPlayersDto> sbPlayers = new ArrayList<>();
        for (Player player : players) {
            ScoreboardPlayersDto sbPlayer = this.modelMapper.map(player, ScoreboardPlayersDto.class);
            sbPlayers.add(sbPlayer);
        }

        return sbPlayers;
    }
}
