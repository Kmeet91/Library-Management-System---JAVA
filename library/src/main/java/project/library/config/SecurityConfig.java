package project.library.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Query to retrieve user by username (using user table)
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select username, password, true from user where username=?");

        // Query to retrieve authorities/roles by username (joining user and user_roles tables)
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select u.username, ur.roles from user u join user_roles ur on u.id = ur.user_id where u.username=?");

        return jdbcUserDetailsManager;
    }    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(configurer -> configurer
                // Public endpoints
                .requestMatchers(HttpMethod.GET, "/library/books").permitAll()
                .requestMatchers(HttpMethod.GET, "/library/book/**").permitAll()
                
                // Authenticated endpoints (any role)
                .requestMatchers(HttpMethod.POST, "/library/borrow").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/library/renew").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/library/return/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/library/borrowed/user/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/library/returned/user/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                
                // Manager and Admin endpoints
                .requestMatchers(HttpMethod.POST, "/library/add").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/library/update/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/library/borrowed").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/library/returned").hasAnyRole("MANAGER", "ADMIN")
                
                // Admin-only endpoints
                .requestMatchers(HttpMethod.DELETE, "/library/delete/**").hasRole("ADMIN")
                
                // User management endpoints
                .requestMatchers(HttpMethod.GET, "/auth/users").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.GET, "/auth/user/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.POST, "/auth/user").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PUT, "/auth/user/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/auth/user/**").hasRole("ADMIN")
                
                // Other endpoints (keep your existing configurations)
                .requestMatchers(HttpMethod.GET, "/library/borrowed").hasAnyRole("ADMIN", "MANAGER")
                
                // Any other request needs to be authenticated
                .anyRequest().authenticated()
            )
            .exceptionHandling(handling -> handling
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        System.out.println("ACCESS DENIED FOR: " + request.getRequestURI());
                        System.out.println("AUTHENTICATION: " + SecurityContextHolder.getContext().getAuthentication());
                        response.sendError(HttpStatus.FORBIDDEN.value());
                    })
             )
            .httpBasic(Customizer.withDefaults());
        	
            
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}