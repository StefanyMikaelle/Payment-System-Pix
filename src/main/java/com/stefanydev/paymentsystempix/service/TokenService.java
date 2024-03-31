package com.stefanydev.paymentsystempix.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.stefanydev.paymentsystempix.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {


    @Value("${jwt.secret}")
    private String secret;

    // Método para gerar um token JWT com base no usuário
    public String generateToken(User user){
        try{
            // Cria um algoritmo de assinatura HMAC com a chave secreta
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // Gera o token JWT com o emissor, assunto e data de expiração
            String token = JWT.create()
                    .withIssuer("auth")
                    .withSubject(user.getEmail())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception){
            // Captura exceção se houver erro na criação do token
            throw new RuntimeException("ERROR: Token was not generated", exception);
        }
    }

    // Método para validar um token JWT e obter o email do usuário
    public String validateToken(String token){
        try{
            // Cria um algoritmo de verificação HMAC com a chave secreta
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // Valida o token JWT e retorna o email do usuário
            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception){
            // Captura exceção se o token for inválido
            throw new RuntimeException("Invalid Token!", exception);
        }
    }

    // Método privado para obter a data de expiração do token (1 minuto a partir de agora)
    private Instant expirationDate() {
        return LocalDateTime.now().plusMinutes(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
