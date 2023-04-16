package bg.softuni.hangman.service;

import bg.softuni.hangman.model.enums.PlayerRoleEnum;
import bg.softuni.hangman.model.dto.PlayerRegisterDto;
import bg.softuni.hangman.model.entity.Player;
import bg.softuni.hangman.model.entity.PlayerRole;
import bg.softuni.hangman.repository.PlayerRepository;
import bg.softuni.hangman.repository.RoleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;


    public PlayerService(PlayerRepository playerRepository,
                         RoleRepository roleRepository,
                         PasswordEncoder passwordEncoder,
                         EmailService emailService) {
        this.playerRepository = playerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    @Transactional
    public void registerPlayer(@Valid PlayerRegisterDto registrationDTO) {
        Player player = new Player().
                setFirstName(registrationDTO.getFirstName()).
                setLastName(registrationDTO.getLastName()).
                setEmail(registrationDTO.getEmail()).
                setPassword(passwordEncoder.encode(registrationDTO.getPassword())).
                setRoles(defaultPlayerRoles());

        playerRepository.save(player);

        emailService.sendRegistrationEmail(player.getEmail(),
                player.getFirstName() + " " + player.getLastName());
    }


    public List<PlayerRole> defaultPlayerRoles() throws NoSuchElementException {
        return List.of(this.roleRepository.findByRole(PlayerRoleEnum.USER).orElseThrow(NoSuchElementException::new));
    }

    public PlayerRole getAdminRole() throws NoSuchElementException {
        return this.roleRepository.findByRole(PlayerRoleEnum.ADMIN).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void promoteToAdmin(String playerEmail) throws NoSuchElementException {
        Player player = this.playerRepository.findByEmail(playerEmail).orElseThrow(NoSuchElementException::new);
        player.getRoles().add(getAdminRole());
        playerRepository.save(player);
    }

    @Transactional
    public void removeAdmin(String playerEmail) throws NoSuchElementException {
        Player player = this.playerRepository.findByEmail(playerEmail).orElseThrow(NoSuchElementException::new);
        player.getRoles().remove(getAdminRole());
        playerRepository.save(player);
    }


}
