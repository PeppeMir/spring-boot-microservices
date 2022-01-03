package com.example.microservices.authservice.service.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtilsService {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.token.validity}")
	private long tokenValidity;

	public String generateToken(final String userId, final String userName) {
		final long nowMillis = System.currentTimeMillis();

		final Claims claims = Jwts	.claims()
									.setSubject(userId);
		claims.put("userName", userName);

		return Jwts	.builder()
					.setClaims(claims)
					.setIssuedAt(new Date(nowMillis))
					.setExpiration(new Date(nowMillis + tokenValidity))
					.signWith(SignatureAlgorithm.HS512, jwtSecret)
					.compact();
	}
}
