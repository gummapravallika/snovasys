package com.trustvault.user;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AppUserRepository users;
    public CustomUserDetailsService(AppUserRepository users){this.users=users;}
    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser u=users.findByEmailIgnoreCase(username).orElseThrow(()->new UsernameNotFoundException("Invalid email or password"));
        return User.withUsername(u.getEmail()).password(u.getPasswordHash()).roles(u.getRole().name()).disabled(!u.isEnabled()).build();
    }
}
