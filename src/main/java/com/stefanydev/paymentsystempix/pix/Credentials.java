package com.stefanydev.paymentsystempix.pix;

import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Value;

public class Credentials {

    private String clientId;
    private String clientSecret;
    private String certificate;
    private boolean sandbox;
    private boolean debug;

    // Construtor para carregar as credenciais do arquivo JSON
    public Credentials() {
        // Obtém o carregador de classes atual para acessar o arquivo JSON de credenciais
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // Obtém o fluxo de entrada do arquivo de credenciais
        InputStream credentialsFile = classLoader.getResourceAsStream("credentials.json");
        // Cria um tokener JSON para analisar o arquivo de credenciais
        JSONTokener tokener = new JSONTokener(credentialsFile);
        // Cria um objeto JSON para armazenar as credenciais
        JSONObject credentials = new JSONObject(tokener);
        try {
            // Fecha o fluxo de entrada do arquivo de credenciais
            credentialsFile.close();
        } catch (IOException e) {
            // Manipula uma possível exceção ao fechar o arquivo de credenciais
            System.out.println("Impossible to close file credentials.json");
        }

        // Inicializa os campos da classe com base nas credenciais do arquivo JSON
        this.clientId = credentials.getString("clientId");
        this.clientSecret = credentials.getString("clientSecret");
        this.certificate = credentials.getString("certificate");
        this.sandbox = credentials.getBoolean("sandbox");
        this.debug = credentials.getBoolean("debug");
    }

    // Getters para os campos das credenciais
    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getCertificate() {
        return certificate;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public boolean isDebug() {
        return debug;
    }
}
