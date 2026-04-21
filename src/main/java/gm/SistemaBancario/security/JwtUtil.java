package gm.SistemaBancario.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "miclavesupersegurademinimo32caracteres";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String generarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(KEY)
                .compact();
    }

    //Metodo para generar clave
    private static SecretKey clave() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public static boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(clave())
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}