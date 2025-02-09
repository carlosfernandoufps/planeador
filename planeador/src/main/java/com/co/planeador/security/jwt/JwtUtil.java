package com.co.planeador.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private JwtUtil(){}

    // Clave secreta para firmar el token (mínimo 256 bits si usas HS256/HS512)
    // Por ejemplo "MYSECRETKEYMYSECRETKEYMYSECRETKEY12" de 32 chars para HS256
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 1 día de expiración (por ejemplo)
    private static final long EXPIRATION_TIME = 1000L * 24 * 60 * 60;

    /**
     * Genera un JWT con el email (subject) y rol como claim.
     * Se firma usando SECRET_KEY y expira tras EXPIRATION_TIME.
     */
    public static String generateToken(Integer userId, String role) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida el token. Si es correcto, devuelve las Claims;
     * si es inválido o expiró, retorna null.
     */
    public static Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            // Firma incorrecta, token expirado, mal formado, etc.
            return null;
        }
    }

    /**
     * Extrae el rol (Docente/Director) del token, o null si es inválido.
     */
    public static String getRoleFromToken(String token) {
        Claims claims = validateToken(token);
        return (claims != null) ? claims.get("role", String.class) : null;
    }

    /**
     * Extrae el email (subject) del token, o null si es inválido.
     */
    public static Integer getIdFromToken(String token) {
        Claims claims = validateToken(token);
        return (claims != null) ? Integer.parseInt(claims.getSubject()) : null;
    }


}
