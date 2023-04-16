package bg.softuni.hangman.service;

import bg.softuni.hangman.model.enums.PlayerRoleEnum;
import bg.softuni.hangman.model.entity.Dictionary;
import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.model.entity.PlayerRole;
import bg.softuni.hangman.repository.DictionaryRepository;
import bg.softuni.hangman.repository.PlayerRepository;
import bg.softuni.hangman.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DatabaseInnitService {
    private final RoleRepository roleRepository;
    private final PlayerRepository playerRepository;
    private final DictionaryRepository dictionaryRepository;
    private final PasswordEncoder passwordEncoder;
    private final String DEFAULT_PASSWORD = "defaultPass";
    private final String DEFAULT_DESCRIPTION = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia, molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium optio";
    private final PlayerService playerService;

    @Autowired
    public DatabaseInnitService(RoleRepository roleRepository,
                                PlayerRepository playerRepository,
                                DictionaryRepository dictionaryRepository,
                                PasswordEncoder passwordEncoder, PlayerService playerService) {
        this.roleRepository = roleRepository;
        this.playerRepository = playerRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.passwordEncoder = passwordEncoder;
        this.playerService = playerService;
    }

    public void fillDatabase() {
        fillRoleRepository();
        fillPlayerRepository();
        fillDictionaryRepository();
    }

    public void fillRoleRepository() {
        if (roleRepository.count() == 0){
            List<PlayerRole> roles = Arrays.stream(PlayerRoleEnum.values())
                    .map(role -> new PlayerRole().setRole(role)).toList();

            this.roleRepository.saveAll(roles);
        }
    }

    public void fillPlayerRepository() {
        if (playerRepository.count() == 0){
            Player admin = new Player().setEmail("admin@example.com")
                    .setFirstName("Admin").setLastName("Adminov")
                    .setPassword(passwordEncoder.encode(DEFAULT_PASSWORD))
                    .setScore(400L)
                    .setRoles(getAdminRoles());

            Player userOne = new Player().setEmail("userOne@example.com")
                    .setFirstName("One").setLastName("Player")
                    .setPassword(passwordEncoder.encode(DEFAULT_PASSWORD))
                    .setScore(900L)
                    .setRoles(playerService.defaultPlayerRoles());

            Player userTwo = new Player().setEmail("userTwo@example.com")
                    .setFirstName("Two").setLastName("Player")
                    .setPassword(passwordEncoder.encode(DEFAULT_PASSWORD))
                    .setScore(150L)
                    .setRoles(playerService.defaultPlayerRoles());

            Player userThree = new Player().setEmail("userThree@example.com")
                    .setFirstName("Three").setLastName("Player")
                    .setPassword(passwordEncoder.encode(DEFAULT_PASSWORD))
                    .setScore(550L)
                    .setRoles(playerService.defaultPlayerRoles());

            this.playerRepository.save(admin);
            this.playerRepository.save(userOne);
            this.playerRepository.save(userTwo);
            this.playerRepository.save(userThree);
        }
    }

    public void fillDictionaryRepository() {
        if (this.dictionaryRepository.count() == 0){
            Dictionary wordOne = new Dictionary().setWord("баница").setDefinition(DEFAULT_DESCRIPTION);
            Dictionary wordTwo = new Dictionary().setWord("слушалки").setDefinition(DEFAULT_DESCRIPTION);
            Dictionary wordThree = new Dictionary().setWord("панталон").setDefinition(DEFAULT_DESCRIPTION);
            Dictionary wordFour = new Dictionary().setWord("ландшафт").setDefinition(DEFAULT_DESCRIPTION);

            this.dictionaryRepository.save(wordOne);
            this.dictionaryRepository.save(wordTwo);
            this.dictionaryRepository.save(wordThree);
            this.dictionaryRepository.save(wordFour);
        }

    }

    public List<PlayerRole> getAdminRoles(){
        return List.of
                (this.roleRepository.findByRole(PlayerRoleEnum.ADMIN).orElseThrow(NoSuchElementException::new),
                 this.roleRepository.findByRole(PlayerRoleEnum.USER).orElseThrow(NoSuchElementException::new));
    }
}
