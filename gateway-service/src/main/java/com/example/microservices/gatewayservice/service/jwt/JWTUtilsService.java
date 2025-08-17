package com.example.microservices.gatewayservice.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;

@Service
public class JWTUtilsService {

    private final JwtParser parser;

    @Autowired
    public JWTUtilsService(@Value("${jwt.secret}") final String jwtSecret) {
        final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        this.parser = Jwts.parser()
                .verifyWith(key)
                .build();
    }

    public ClaimsData getClaims(final String token) {
        try {
            final Claims claims = parser
                    .parseSignedClaims(token)
                    .getPayload();

            final ClaimsData claimsData = new ClaimsData();

            claimsData.setUserId(claims.getSubject());
            claimsData.setUserName(claims.get("userName", String.class));

            return claimsData;
        } catch (final Exception e) {
            System.out.println(e.getMessage() + " => " + e);
            return null;
        }
    }

    public boolean isTokenValid(final String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        // This will throw an exception in case the token is invalid
        parser.parseSignedClaims(token);

        return true;
    }
}
