package com.co.planeador.security.aspect;

import com.co.planeador.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class InterceptorAspect {

    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final String BEARER_KEY = "Bearer ";

    @Pointcut("@annotation(com.co.planeador.security.annotation.SessionRequired)")
    public void sessionRequiredMethods() {}

    @Pointcut("@annotation(com.co.planeador.security.annotation.DirectorRequired)")
    public void directorRequiredMethods() {}

    @Pointcut("@annotation(com.co.planeador.security.annotation.TeacherRequired)")
    public void teacherRequiredMethods() {}

    @Around("sessionRequiredMethods()")
    public Object checkSession(ProceedingJoinPoint pjp) throws Throwable{
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String authHeader = request.getHeader(AUTHORIZATION_KEY);
        if (authHeader == null || !authHeader.startsWith(BEARER_KEY)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay token presente");
        }

        String token = authHeader.substring(7);
        Claims claims = JwtUtil.validateToken(token);
        if (claims == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
        }

        return pjp.proceed();
    }

    @Around("directorRequiredMethods()")
    public Object checkDirectorRole(ProceedingJoinPoint pjp) throws Throwable {
        // Revisamos si el token indica rol = DIRECTOR
        HttpServletRequest request =
                ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        String authHeader = request.getHeader(AUTHORIZATION_KEY);
        if (authHeader == null || !authHeader.startsWith(BEARER_KEY)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta token");
        }

        String token = authHeader.substring(7);
        Claims claims = JwtUtil.validateToken(token); // Tu método de validación
        if (claims == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        String role = (String) claims.get("role");

        if (!"DIRECTOR".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos de Director");
        }

        // Rol correcto -> proceder al método
        return pjp.proceed();
    }

    @Around("teacherRequiredMethods()")
    public Object checkTeacherRole(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        String authHeader = request.getHeader(AUTHORIZATION_KEY);
        if (authHeader == null || !authHeader.startsWith(BEARER_KEY)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falta token");
        }

        String token = authHeader.substring(7);
        Claims claims = JwtUtil.validateToken(token);
        if (claims == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        String role = (String) claims.get("role");

        if (!"TEACHER".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos de Docente");
        }

        return pjp.proceed();
    }

}
