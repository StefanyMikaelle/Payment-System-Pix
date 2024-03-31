package com.stefanydev.paymentsystempix.controller;

import com.stefanydev.paymentsystempix.dto.AuthenticationRequest;
import com.stefanydev.paymentsystempix.dto.AuthenticationResponse;
import com.stefanydev.paymentsystempix.dto.UserRequest;
import com.stefanydev.paymentsystempix.dto.UserResponse;
import com.stefanydev.paymentsystempix.entity.User;
import com.stefanydev.paymentsystempix.service.TokenService;
import com.stefanydev.paymentsystempix.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
@RestController
@RequestMapping(value = "/api/v1/user", produces = {"application/json"})
@Tag(name = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Endpoint para registrar um novo usuário
    @PostMapping("/register")
    @Operation(summary = "Endpoint para registrar um novo usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "registro de um novo usuário com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o registro de um novo usuário"),
    })
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userCreateRequest) throws MessagingException, UnsupportedEncodingException {
        // Converte a solicitação de criação de usuário para um objeto do modelo de domínio
        User user = userCreateRequest.toModel();
        // Registra o usuário e obtém a resposta com os detalhes do usuário registrado
        UserResponse userSaved = userService.registerUser(user);
        // Retorna uma resposta HTTP OK com os detalhes do usuário registrado
        return ResponseEntity.ok().body(userSaved);
    }

    // Endpoint para verificar um usuário usando um código de verificação
    @GetMapping("/verify")
    @Operation(summary = "Endpoint para verificar um usuário usando um código de verificação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "verificar um usuário usando um código de verificação com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao  verificar um usuário usando um código de verificação"),
    })
    public String verifyUser(@RequestParam("code") String code){
        // Verifica se o código de verificação é válido
        if(userService.verify(code)){
            return "verify_success"; // Retorna uma mensagem de sucesso se a verificação for bem-sucedida
        } else {
            return "verify_fail"; // Retorna uma mensagem de falha se a verificação falhar
        }
    }

    // Endpoint de teste para verificar se o usuário está logado
    @GetMapping("/test")
    public String test(){
        return "You are logged!"; // Retorna uma mensagem indicando que o usuário está logado
    }

}

