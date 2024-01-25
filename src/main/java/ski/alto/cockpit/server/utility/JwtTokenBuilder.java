package ski.alto.cockpit.server.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ski.alto.cockpit.server.model.UserDTO;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

public class JwtTokenBuilder {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String SECRET_KEY_BASE = "SECRET_KEY_BASE";

    public String build(UserDTO user) {

        String jwtToken = null;
        try {
            String secret = System.getenv(SECRET_KEY_BASE);
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            Instant now = Instant.now();
            jwtToken = Jwts.builder()
                    .claim("type", user.getRole().replace(' ', '_'))
                    .setSubject(user.getId().toString())
                    .setId(UUID.randomUUID().toString())
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plus(30, ChronoUnit.DAYS)))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("Could not generate token", e);
        }

        return jwtToken;
    }
}
