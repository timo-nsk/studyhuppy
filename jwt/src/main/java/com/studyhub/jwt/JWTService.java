package com.studyhub.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JWTService {

	private String secretkey = "Lb4t5iJI3EOL2aEQupj+sppum/fvoq4A5/u2idDDst3rjbytIzzRHq2dyP2C+b/g8QpJiRP+tA4SO9qE+o5srg==";

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		long expirationTime = 7 * 24 * 60 * 60 * 1000;
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expirationTime))
				.and()
				.signWith(getKey())
				.compact();
	}

	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretkey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	/**
	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUsername(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	 **/

	public boolean validateToken(String token, String username) {
		final String userName = extractUsername(token);
		return (userName.equals(username) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public String extractTokenFromHeader(String header) {
		// Header-Format: "Authorization: Bearer <JWT-TOKEN>"
		return header.substring(7);
	}

	public String extractUsernameFromHeader(HttpServletRequest req) {
		String header = req.getHeader("Authorization");
		String token = extractTokenFromHeader(header);
		return extractUsername(token);
	}

	/**
	public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
		Claims claims = extractAllClaims(token);

		// Hole den "roles"-Claim aus den Claims
		String[] roles = claims.get("roles", String[].class);

		// Falls der roles-Claim null ist, gebe eine leere Liste zurück
		if (roles == null) {
			return Collections.emptyList();
		}

		// Konvertiere die Rollen in eine Liste von GrantedAuthority
		return Arrays.stream(roles)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}
	 **/
}