package com.stefanydev.paymentsystempix.service;

import com.stefanydev.paymentsystempix.dto.UserResponse;
import com.stefanydev.paymentsystempix.entity.User;
import com.stefanydev.paymentsystempix.repository.UserRepository;
import com.stefanydev.paymentsystempix.util.RandomString;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    // Método para registrar um novo usuário
    public UserResponse registerUser(User user) throws MessagingException, UnsupportedEncodingException {
        // Verifica se o email do usuário já está em uso
        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new RuntimeException("This email already exists!");
        } else {
            // Codifica a senha do usuário antes de salvar no banco de dados
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            // Gera um código de verificação aleatório e desabilita o usuário
            String randomCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomCode);
            user.setEnabled(false);

            // Salva o usuário no banco de dados
            User saveUser = userRepository.save(user);

            // Cria uma resposta com os detalhes do usuário
            UserResponse userResponse = new UserResponse(
                    saveUser.getId(),
                    saveUser.getName(),
                    saveUser.getEmail(),
                    saveUser.getPassword());

            // Envia um email de verificação para o usuário
            mailService.sendVerificationEmail(user);

            return  userResponse;
        }
    }

    // Método para verificar e ativar um usuário com base no código de verificação
    public boolean verify(String verificationCode){
        // Busca o usuário pelo código de verificação
        User user = userRepository.findByVerificationCode(verificationCode);

        // Verifica se o usuário existe e não está ativado
        if(user == null || user.isEnabled()){
            return  false; // Retorna falso se o usuário não for encontrado ou já estiver ativado
        } else {
            // Remove o código de verificação e ativa o usuário
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true; // Retorna verdadeiro se a verificação for bem-sucedida
        }
    }
}
