package com.example.blog.server.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt_secret}")
    private String secret;
    private final String subject = "Account details";
    private final String issuer = "MyBackend";
    private final String audience = "MyClient";
    private final String claimAccountId = "accountId";
    private final String claimRole = "role";

    public String generateToken(int accountId, String role) {
        return JWT.create()
                .withSubject(subject)
                .withClaim(claimAccountId, accountId)
                .withClaim(claimRole, role)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withAudience(audience)
                .withExpiresAt(Date.from(ZonedDateTime.now().plusDays(30).toInstant()))
                .sign(Algorithm.HMAC256(secret));
    }

    public int validateTokenAndRetrieveClaimAccountId(String token) throws JWTVerificationException {
        var verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(subject)
                .withIssuer(issuer)
                .withAudience(audience)
                .build();

        var decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim(claimAccountId).asInt();
    }

    public String validateTokenAndRetrieveClaimRole(String token) throws JWTVerificationException {
        var verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(subject)
                .withIssuer(issuer)
                .withAudience(audience)
                .build();

        var decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim(claimRole).asString();
    }
}
