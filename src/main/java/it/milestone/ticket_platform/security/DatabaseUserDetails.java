package it.milestone.ticket_platform.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.milestone.ticket_platform.model.Role;
import it.milestone.ticket_platform.model.User;
import it.milestone.ticket_platform.model.UserState;

public class DatabaseUserDetails implements UserDetails {

    private final Integer id;
    private final String username;
    private final String email;
    private final String password;
    private final UserState state;
    private final List<GrantedAuthority> authorities;

    public DatabaseUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.state = user.getState();
        this.authorities = new ArrayList<>();
        for(Role role : user.getRoles()) {
            this.authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Integer getId() {
        return this.id;
    }

    public UserState getState() {
        return this.state;
    }

}
