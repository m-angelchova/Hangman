package bg.softuni.hangman.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.NoSuchElementException;

@ControllerAdvice
public class NoSuchElementAdvice {

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoSuchElementException.class)
  public String onNoElementFound() {
    return "error";
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(UsernameNotFoundException.class)
  public String onNoUsernameFound() {
    return "error";
  }

}
