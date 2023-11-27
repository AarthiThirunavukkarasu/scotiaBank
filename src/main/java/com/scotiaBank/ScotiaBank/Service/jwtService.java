package com.scotiaBank.ScotiaBank.Service;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Service
public class jwtService {
	private static final String SECRET_KEY = "your-secret-key";
    private static final long EXPIRATION_TIME = 864000000; // 10 days

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
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
		   public String extractUsername(String token) {
		        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		        return claims.getSubject();
		    }
}
