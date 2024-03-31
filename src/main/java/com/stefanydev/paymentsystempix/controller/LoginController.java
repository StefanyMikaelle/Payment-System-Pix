package com.stefanydev.paymentsystempix.controller;

import com.stefanydev.paymentsystempix.dto.AuthenticationRequest;
import com.stefanydev.paymentsystempix.dto.AuthenticationResponse;
import com.stefanydev.paymentsystempix.entity.User;
import com.stefanydev.paymentsystempix.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth", produces = {"application/json"})
@Tag(name = "auth")
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Endpoint para autenticação de usuário
    @PostMapping("/login")
    @Operation(summary = "Realiza a autenticação de usuário.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação de usuário realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a autenticação de usuário"),
    })
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest){
        // Cria um token de autenticação com base no email e senha fornecidos
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(), authenticationRequest.password()
        );
        // Realiza a autenticação
        var auth = authenticationManager.authenticate(usernamePassword);

        // Gera um token JWT com base no usuário autenticado
        var token = tokenService.generateToken( (User) auth.getPrincipal());

        // Retorna uma resposta de sucesso contendo o token JWT
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
