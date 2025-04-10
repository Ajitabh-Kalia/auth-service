package com.expenseTracker.auth.util;

import org.springframework.stereotype.Component;

import com.expenseTracker.auth.model.User;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private final long expiration = 60 * 60 * 1000;
	
	public String generateToken(User user) {
		return Jwts.builder()
				.setSubject(user.getUsername())
				.claim("role", user.getRole().name())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(key)
				.compact();
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build()
				.parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		}catch (JwtException e) {
			return false;
		}
		
	}
	

}
