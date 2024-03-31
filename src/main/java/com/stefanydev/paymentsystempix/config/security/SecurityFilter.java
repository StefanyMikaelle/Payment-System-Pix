package com.stefanydev.paymentsystempix.config.security;

import com.stefanydev.paymentsystempix.repository.UserRepository;
import com.stefanydev.paymentsystempix.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    // Método para filtrar requisições
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        // Recupera o token da requisição
        var token = this.recoverToken(request);
        if(token != null){
            // Valida o token e obtém o assunto (subject)
            var subject = tokenService.validateToken(token);
            // Obtém os detalhes do usuário com base no email (subject)
            UserDetails userDetails = userRepository.findByEmail(subject);

            // Cria uma instância de autenticação com os detalhes do usuário
            var authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            // Define a autenticação no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // Continua o fluxo da cadeia de filtros
        filterChain.doFilter(request,response);
    }

    // Método para recuperar o token da requisição
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        } else {
            // Remove o prefixo "Bearer " do cabeçalho de autorização
            return authHeader.replace("Bearer ","");
        }
    }
}
