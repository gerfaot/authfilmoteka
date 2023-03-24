package petproject.filmoteka.authfilmoteka.service;

public interface TokenService {
    String generateToken(String clientId);
}
