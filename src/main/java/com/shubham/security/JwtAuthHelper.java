package com.shubham.security;

import com.shubham.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtAuthHelper {

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimResolver) {
        Claims claims = Jwts.parser().setSigningKey(Constants.SECRET).parseClaimsJws(token).getBody();
        return claimResolver.apply(claims);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String usernameFromToken = getUsernameFromToken(token);
        return (usernameFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expirationTimeFromtToken = getExpirationTimeFromToken(token);
        return expirationTimeFromtToken.before(new Date());
    }
    private Date getExpirationTimeFromToken(String token){
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Map<String, Object> claim = new HashMap<>();
        claim.put("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return generateJwtToken(username, claim);
    }

    private String generateJwtToken(String subject, Map<String, Object> claim) {
        return Jwts.builder().setClaims(claim).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1*60*60*1000))
                .signWith(SignatureAlgorithm.HS256, Constants.SECRET).compact();
    }
}
