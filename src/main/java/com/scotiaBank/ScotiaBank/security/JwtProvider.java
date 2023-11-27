package com.scotiaBank.ScotiaBank.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.scotiaBank.ScotiaBank.Entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtProvider {

    private static final String SECRET_KEY = "yoursecretkeyqgdhcbjNsdjkduisjcnsaldxcnhhbdxckjdncdmjdknckjnk";
    private static final long EXPIRATION_TIME = 864000000; // 10 days

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateTokenUser(Object user) {
        UserDetails userDetails = (UserDetails) user;
        return generateToken(new HashMap<>(), userDetails);
    }
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
          .builder()
          .setClaims(extraClaims)
          .setSubject(userDetails.getUsername())
          .setIssuedAt(new Date(System.currentTimeMillis()))
          .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
          .signWith(getSigningKey(), SignatureAlgorithm.HS256)
          .compact();
    }
	/*
	 * Date now = new Date(); Date expiryDate = new Date(now.getTime() +
	 * EXPIRATION_TIME);
	 * 
	 * return Jwts.builder()
	 */
				/*
				 * .setSubject(userDetails.getUsername()) .setIssuedAt(new Date())
				 * .setExpiration(expiryDate) .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				 * .compact();
				 
   */
  

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


	public boolean validateToken(String jwt,UserDetails userDetails) {
		try {
			String username = extractUsername(jwt);
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt);
			if(username.equalsIgnoreCase(userDetails.getUsername())) {
				return true;
			}
			else {
				return false;
			}
            
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            // Handle exception
            return false;
        }
	}
	  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
	      final Claims claims = extractAllClaims(token);
	      return claimsResolvers.apply(claims);
	  }
	  private Claims extractAllClaims(String token) {
	      return Jwts
	        .parserBuilder()
	        .setSigningKey(getSigningKey())
	        .build()
	        .parseClaimsJws(token)
	        .getBody();
	  }

	  private Key getSigningKey() {
	      byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
	      return Keys.hmacShaKeyFor(keyBytes);
	  }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
