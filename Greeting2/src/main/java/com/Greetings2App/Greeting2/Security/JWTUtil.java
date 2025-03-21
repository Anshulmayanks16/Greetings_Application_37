package com.Greetings2App.Greeting2.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
@Component
public class JWTUtil {
    private static final String SECRET_KEY = "your-secret-key-your-secret-key-your-secret-key"; // Should be at least 256 bits

    private static Key getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }// Use a strongkey

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) //1 hour expiry
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
