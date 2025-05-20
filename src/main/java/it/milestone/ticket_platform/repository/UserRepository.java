package it.milestone.ticket_platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.milestone.ticket_platform.model.User;
import it.milestone.ticket_platform.model.UserState;

public interface  UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);
    public List<User> findByState(UserState state);
}
