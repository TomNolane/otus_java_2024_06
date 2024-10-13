package tomnolane.otus.dao;

import tomnolane.otus.model.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> findByLogin(String login);
}
