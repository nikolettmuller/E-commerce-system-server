package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.impl;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.config.JwtServiceProperties;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtServiceProperties jwtServiceProperties;

    @Override
    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtServiceProperties.getExpirationTime()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String extractUserName(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    @Override
    public Boolean isTokenActive(String jwt) {
        Date expirationDate = extractAllClaims(jwt).getExpiration();
        return expirationDate.after(new Date());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtServiceProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

}
