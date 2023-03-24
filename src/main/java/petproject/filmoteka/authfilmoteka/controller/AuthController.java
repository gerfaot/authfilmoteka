package petproject.filmoteka.authfilmoteka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petproject.filmoteka.authfilmoteka.exception.LoginException;
import petproject.filmoteka.authfilmoteka.exception.RegistrationException;
import petproject.filmoteka.authfilmoteka.model.ErrorResponse;
import petproject.filmoteka.authfilmoteka.model.TokenResponse;
import petproject.filmoteka.authfilmoteka.model.User;
import petproject.filmoteka.authfilmoteka.service.ClientService;
import petproject.filmoteka.authfilmoteka.service.TokenService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ClientService clientService;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody User user) {
        clientService.register(user.getClientId(), user.getClientSecret());
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/token")
    public TokenResponse getToken(@RequestBody User user) {
        clientService.checkCredentials(user.getClientId(), user.getClientSecret());
        return new TokenResponse(tokenService.generateToken(user.getClientId()));
    }

    @ExceptionHandler({RegistrationException.class, LoginException.class})
    public ResponseEntity<ErrorResponse> handleUserRegistrationException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(ex.getMessage()));
    }
}
