package bg.softuni.hangman.service;

import bg.softuni.hangman.model.constant.PlayerRoleEnum;
import bg.softuni.hangman.model.dto.PlayerRegisterDto;
import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.model.entity.PlayerRole;
import bg.softuni.hangman.repository.PlayerRepository;
import bg.softuni.hangman.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultAdminPass;
    private final EmailService emailService;


    public PlayerService(PlayerRepository playerRepository,
                         RoleRepository roleRepository,
                         PasswordEncoder passwordEncoder,
                         @Value("${hangman.admin.defaultpass}") String defaultAdminPass, EmailService emailService) {
        this.playerRepository = playerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultAdminPass = defaultAdminPass;
        this.emailService = emailService;
    }



    public void registerPlayer(PlayerRegisterDto registrationDTO) {
        Player player = new Player().
                setFirstName(registrationDTO.getFirstName()).
                setLastName(registrationDTO.getLastName()).
                setEmail(registrationDTO.getEmail()).
                setPassword(passwordEncoder.encode(registrationDTO.getPassword())).
                setRoles(defaultPlayerRoles()).
                setAccountActive(false);
        //roles?

        playerRepository.save(player);

        emailService.sendRegistrationEmail(player.getEmail(),
                player.getFirstName() + " " + player.getLastName());
    }


    public List<PlayerRole> defaultPlayerRoles(){
        return List.of(this.roleRepository.findByRole(PlayerRoleEnum.USER).orElseThrow(NoSuchElementException::new));
    }

    public PlayerRole getAdminRole(){
        return this.roleRepository.findByRole(PlayerRoleEnum.ADMIN).orElseThrow(NoSuchElementException::new);
    }

    public void promoteToAdmin(String playerEmail){
        Player player = this.playerRepository.findByEmail(playerEmail).orElseThrow(NoSuchElementException::new);
        player.getRoles().add(getAdminRole());
    }
}
