package bg.softuni.hangman.config;

import bg.softuni.hangman.model.enums.PlayerRoleEnum;
import bg.softuni.hangman.repository.PlayerRepository;
import bg.softuni.hangman.service.ApplicationUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http,
      SecurityContextRepository securityContextRepository) throws Exception {
    http.
        // defines which pages will be authorized
            authorizeHttpRequests().
        // allow access to all static files (images, CSS, js)
            requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
        // the URL-s below are available for all users - logged in and anonymous
            requestMatchers("/", "/login", "/register", "/login-error").anonymous().
            // only for admins
            requestMatchers("/admin").hasRole(PlayerRoleEnum.ADMIN.name()).
        anyRequest().authenticated().
        and().
        // configure login with HTML form
            formLogin().
        loginPage("/login").
        // the names of the username, password input fields in the custom login form
            usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
            passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
        // where do we go after login
            defaultSuccessUrl("/home").//use true argument if you always want to go there, otherwise go to previous page
        failureForwardUrl("/login-error").
        and().logout().//configure logout
        logoutUrl("/logout").
        logoutSuccessUrl("/").//go to homepage after logout
        invalidateHttpSession(true).
        and().
        securityContext().
        securityContextRepository(securityContextRepository);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(PlayerRepository playerRepository) {
    return new ApplicationUserDetailsService(playerRepository);
  }

  @Bean
  public SecurityContextRepository securityContextRepository() {
    return new DelegatingSecurityContextRepository(
        new RequestAttributeSecurityContextRepository(),
        new HttpSessionSecurityContextRepository()
    );
  }

}
