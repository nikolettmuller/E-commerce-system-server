package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service;


import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(UserDetails user);

    String extractUserName(String jwt);

    Boolean isTokenActive(String jwt);
}
