/*package fr.homingpigeon.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;

public class JwtConfig {
    @Value( "${application.jwt.secretKey}" )//ne marche pas idem pour @ConfigurationProperties wtf
    private static String key = "Deu8BCxg2KXbkH9cgQfdSvm8drmygQT49nq8SsbnYs7m8HTzujrpjaQUz9N9uwPKTTrQWLFkSSg5evgsRU9RZmHN4FYz2wpqPZeDbvrMCBVUGhHXU5QwLcabsEtGzUJe";

    public JwtConfig() {

    }

    public static SecretKey secretKey() {
        return Keys.hmacShaKeyFor(
                key.getBytes());
    }

    public static String getUsernameFromHeader(String header) {
        String token = header.replace("Bearer ", "");
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                                    .setSigningKey(JwtConfig.secretKey())
                                    .build()
                                    .parseClaimsJws(token);

        return claimsJws.getBody().getSubject();
    }
}
*/