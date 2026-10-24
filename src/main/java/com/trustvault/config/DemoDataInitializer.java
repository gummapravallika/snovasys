package com.trustvault.config;

import com.trustvault.user.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DemoDataInitializer {
    @Bean CommandLineRunner seedUsers(AppUserRepository users, PasswordEncoder encoder) {
        return args -> {
            seed(users,encoder,"TrustVault Administrator","admin@trustvault.local","${DEMO_ADMIN_PASSWORD}",Role.ADMIN,"IT");
            seed(users,encoder,"Employee Owner","owner@trustvault.local","${DEMO_OWNER_PASSWORD}",Role.OWNER,"Human Resources");
            seed(users,encoder,"Employee Viewer","viewer@trustvault.local","${DEMO_VIEWER_PASSWORD}",Role.VIEWER,"Finance");
        };
    }
    private void seed(AppUserRepository users, PasswordEncoder encoder, String name, String email, String placeholder, Role role, String dept) {
        if(users.existsByEmailIgnoreCase(email)) return;
        String envName = placeholder.substring(2, placeholder.length()-1);
        String password=System.getenv(envName);
        if(password==null || password.isBlank()) password="ChangeMe-"+role.name()+"-123!";
        users.save(new AppUser(name,email,encoder.encode(password),role,dept));
        if(password.startsWith("ChangeMe-")) System.err.println("WARNING: demo account " + email + " uses a development-only password. Set " + envName + " before deployment.");
    }
}
