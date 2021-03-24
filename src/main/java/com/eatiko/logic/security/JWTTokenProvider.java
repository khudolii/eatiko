package com.eatiko.logic.security;

import com.eatiko.logic.model.ACLUser;
import com.eatiko.logic.utils.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {

    private static final Class<JWTTokenProvider> CLAZZ = JWTTokenProvider.class;
    private static final Logger logger = Logger.getLogger(CLAZZ);

    public String generateToken(Authentication authentication) {
        ACLUser user = (ACLUser) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + AppConstants.SECURITY_EXPIRATION_TIME);

        String userId = String.valueOf(user.getUserId());

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", userId);
        claimsMap.put("userName", user.getUsername());
        claimsMap.put("firstName", user.getFirstName());
        claimsMap.put("lastName", user.getLastName());

        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, AppConstants.SECURITY_SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(AppConstants.SECURITY_SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error("validateToken: " + e);
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(AppConstants.SECURITY_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong((String) claims.get("id"));
    }
}
