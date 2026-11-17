package br.com.evrcar.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey key;
    private final long expirationMinutes;

    public JwtService(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.expiration-minutes:720}") long expirationMinutes) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }

    public String generate(String username) {
        Instant now = Instant.now();
        return Jwts.builder().subject(username).issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(expirationMinutes * 60)))
            .signWith(key).compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
            .getPayload().getSubject();
    }

    public boolean isValid(String token, String username) {
        try { return username.equals(extractUsername(token)); }
        catch (RuntimeException ex) { return false; }
    }
}
