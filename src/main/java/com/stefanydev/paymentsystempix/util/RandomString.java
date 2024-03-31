package com.stefanydev.paymentsystempix.util;

import java.security.SecureRandom;

public class RandomString {
    // Caracteres que serão utilizados na geração da string aleatória
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // Método para gerar uma string aleatória de tamanho específico
    public static String generateRandomString(int length){
        SecureRandom secureRandom = new SecureRandom(); // Objeto para geração de números aleatórios seguros
        StringBuilder stringBuilder = new StringBuilder(); // StringBuilder para construir a string aleatória
        for (int i = 0 ; i < length ; i++) {
            int index = secureRandom.nextInt(CHARACTERS.length()); // Gera um índice aleatório com base no tamanho da lista de caracteres
            stringBuilder.append(CHARACTERS.charAt(index)); // Adiciona o caractere correspondente ao índice à string
        }
        return stringBuilder.toString(); // Retorna a string aleatória gerada
    }
}
