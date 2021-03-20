package fr.homingpigeon.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class UsefullFunctions {

    public static String getUsernameFromHeader(String header, SecretKey jwtSecret) {
        String token = header.replace("Bearer ", "");
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                                    .setSigningKey(jwtSecret)
                                    .build()
                                    .parseClaimsJws(token);

        return claimsJws.getBody().getSubject();
    }
}
