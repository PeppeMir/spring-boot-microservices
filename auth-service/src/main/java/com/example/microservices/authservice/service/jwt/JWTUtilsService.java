package com.example.microservices.authservice.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTUtilsService {

    private final long tokenValidity;
    private final SecretKey key;

    @Autowired
    public JWTUtilsService(
            @Value("${jwt.secret}") final String jwtSecret,
            @Value("${jwt.token.validity}") final long tokenValidity) {
        this.tokenValidity = tokenValidity;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateToken(final String userId, final String userName) {
        final long nowMillis = System.currentTimeMillis();

        final Claims claims = Jwts.claims()
                .subject(userId)
                .add("userName", userName)
                .build();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(nowMillis))
                .expiration(new Date(nowMillis + tokenValidity))
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }
}
