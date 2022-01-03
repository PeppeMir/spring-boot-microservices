package com.example.microservices.gatewayservice.service.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTUtilsService {

	@Value("${jwt.secret}")
	private String jwtSecret;

	public ClaimsData getClaims(final String token) {
		try {
			final Claims claims = Jwts	.parser()
						.setSigningKey(jwtSecret)
						.parseClaimsJws(token)
						.getBody();
			
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
		Jwts.parser()
			.setSigningKey(jwtSecret)
			.parseClaimsJws(token);
		
		return true;
	}
}
