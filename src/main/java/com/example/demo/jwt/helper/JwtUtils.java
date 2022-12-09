package com.example.demo.jwt.helper;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${demo.app.jwtSecret}")
    private String jwtSecret;

    @Value("${demo.app.jwtExpirationMs}")
    private Long jwtExpirationMs;
    //actually business implement here to add anything in token,username,secret key,expiration time,
    // and algorithm to convert all information in single token
    public  String doGenerateToken(Map<String,Object> claims,String subject){
        return  Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512,jwtSecret).compact();
    }
    //generate token for user
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }
    public  boolean isTokenExpired(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
    }catch (SignatureException e){
            System.out.println("Invalid JWT signature:" +e.getMessage());
        }
        catch (MalformedJwtException e){
            System.out.println("Invalid JWT token:" +e.getMessage());
        }
        catch (ExpiredJwtException  e){
            System.out.println("JWT token is expired:" +e.getMessage());
        }
        catch (UnsupportedJwtException e){
            System.out.println("JWT token is unsupported:" +e.getMessage());
        }
        catch (IllegalArgumentException  e){
            System.out.println("JWT claims string is empty:" +e.getMessage());
        }
    return false;
        }
    public boolean isValidToken(String token,UserDetails userDetails){
        final String username=getUsernameFromToken(token);
        return username.equals(userDetails.getUsername())&& !isTokenExpired(token);
    }
    //for retrieveing any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
   public  <T> T getClaimFromToken(String token, Function<Claims,T> claimResolver){
        final  Claims claims=getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
     }
     // retrieving username from token
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token,Claims::getSubject);
    }
    // retrieving Expiration time from token
    public Date getExpirationDateTimeMsFromToken(String token){
        return getClaimFromToken(token,Claims::getExpiration);
    }
    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
