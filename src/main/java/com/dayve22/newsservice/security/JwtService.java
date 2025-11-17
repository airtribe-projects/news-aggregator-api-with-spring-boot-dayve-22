package com.dayve22.newsservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dayve22.newsservice.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecretKey);

        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId()) // Add custom claim for user ID
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        // Verifier will throw an exception if the token is invalid
        return verifier.verify(token);
    }

    public String getUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    public Long getUserId(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("userId").asLong();
    }
}
