package com.stefanydev.paymentsystempix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
@Table(name = "users")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // Gera automaticamente valores para a chave primária
    private Long id;

    private String name;
    private String email;
    private String password;
    private String verificationCode;
    private boolean enabled;
    private String role;

    // Construtor para inicializar um usuário com nome, email, senha e papel
    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Método necessário para o Spring Security - retorna as autoridades do usuário (papel/role)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // No momento, não estamos lidando com papéis/roles específicos
    }

    // Método necessário para o Spring Security - retorna o nome de usuário (email)
    @Override
    public String getUsername() {
        return email;
    }

    // Métodos necessários para o Spring Security - indicam se a conta do usuário está expirada, bloqueada ou as credenciais estão expiradas
    @Override
    public boolean isAccountNonExpired() {
        return true; // Por padrão, não estamos lidando com expiração de conta
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Por padrão, não estamos lidando com contas bloqueadas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Por padrão, não estamos lidando com credenciais expiradas
    }

    // Método necessário para o Spring Security - indica se a conta do usuário está habilitada
    @Override
    public boolean isEnabled() {
        return this.enabled; // Retorna o status de habilitação do usuário
    }

}
