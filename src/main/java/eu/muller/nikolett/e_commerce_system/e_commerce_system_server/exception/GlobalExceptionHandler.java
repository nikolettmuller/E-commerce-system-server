package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<String> handleDuplicatedEmailException() {
        return new ResponseEntity<>("The given is email already used", HttpStatus.BAD_REQUEST);
    }
}
