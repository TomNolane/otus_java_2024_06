package tomnolane.otus.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
