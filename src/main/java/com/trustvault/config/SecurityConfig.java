package com.trustvault.config;

import com.trustvault.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    @Bean DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService service, PasswordEncoder encoder){
        DaoAuthenticationProvider p=new DaoAuthenticationProvider(service); p.setPasswordEncoder(encoder); return p;
    }
    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider provider) throws Exception {
        http.authenticationProvider(provider)
          .authorizeHttpRequests(auth->auth.requestMatchers("/login","/css/**","/error").permitAll()
            .requestMatchers("/admin/**","/employees/**").hasRole("ADMIN")
            .anyRequest().authenticated())
          .formLogin(form->form.loginPage("/login").usernameParameter("email").defaultSuccessUrl("/documents",true).permitAll())
          .logout(logout->logout.logoutSuccessUrl("/login?logout").invalidateHttpSession(true).deleteCookies("JSESSIONID"))
          .exceptionHandling(ex->ex.accessDeniedPage("/error/403"));
        return http.build();
    }
}
