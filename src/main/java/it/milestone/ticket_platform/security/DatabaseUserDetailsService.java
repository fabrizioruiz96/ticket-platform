package it.milestone.ticket_platform.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import it.milestone.ticket_platform.model.User;
import it.milestone.ticket_platform.model.UserState;
import it.milestone.ticket_platform.repository.UserRepository;

public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    public DatabaseUserDetailsService() {}

    public DatabaseUserDetailsService(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepo.findByUsername(username);
        if(optUser.isPresent()) {
            return new DatabaseUserDetails(optUser.get());
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    public boolean isOperator(DatabaseUserDetails user) {
        boolean isOperator = false;
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("OPERATOR")) {
                isOperator = true;
            }
        }
        return isOperator;
    }

    public List<User> findByState(UserState state) {
        return userRepo.findByState(state);
    }

    public User findById(Integer id) {
        return userRepo.findById(id).get();
    }

    public User save(User user) {
        return userRepo.save(user);
    }

}
