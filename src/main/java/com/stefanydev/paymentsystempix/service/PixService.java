package com.stefanydev.paymentsystempix.service;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.stefanydev.paymentsystempix.dto.PixChargeRequest;
import com.stefanydev.paymentsystempix.pix.Credentials;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
@Service
public class PixService {
    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    // Método para criar um Pix usando EVP (Equivalente Virtual de Pagamento)
    public JSONObject pixCreateEVP(){
        // Configuração das opções necessárias para a chamada da API
        JSONObject options = configuringJsonObject();

        try {
            // Criação de uma instância de EfiPay para interagir com a API
            EfiPay efi = new EfiPay(options);
            // Chama o método da API para criar um Pix EVP
            JSONObject response = efi.call("pixCreateEvp", new HashMap<String,String>(), new JSONObject());
            // Imprime a resposta da API
            System.out.println(response.toString());
            // Retorna a resposta da API
            return response;
        } catch (EfiPayException e) {
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Método para criar uma cobrança Pix
    public JSONObject pixCreateCharge(PixChargeRequest pixChargeRequest){
        // Configuração das opções necessárias para a chamada da API
        JSONObject options = configuringJsonObject();

        // Construção do corpo da requisição para criar uma cobrança Pix
        JSONObject body = new JSONObject();
        body.put("calendario", new JSONObject().put("expiracao", 3600));
        body.put("devedor", new JSONObject().put("cpf", "12345678909").put("nome", "Stefany Mikaelle"));
        body.put("valor", new JSONObject().put("original", pixChargeRequest.value()));
        body.put("chave", pixChargeRequest.key());

        JSONArray infoAdicionais = new JSONArray();
        infoAdicionais.put(new JSONObject().put("nome", "Campo 1").put("valor", "Informação Adicional1 do PSP-Recebedor"));
        infoAdicionais.put(new JSONObject().put("nome", "Campo 2").put("valor", "Informação Adicional2 do PSP-Recebedor"));
        body.put("infoAdicionais", infoAdicionais);

        try {
            // Criação de uma instância de EfiPay para interagir com a API
            EfiPay efi = new EfiPay(options);
            // Chama o método da API para criar uma cobrança Pix
            JSONObject response = efi.call("pixCreateImmediateCharge", new HashMap<String,String>(), body);

            // Obtém o ID da cobrança Pix da resposta da API
            int idFromJson= response.getJSONObject("loc").getInt("id");
            // Gera o QR Code para a cobrança Pix
            pixGenerateQRCode(String.valueOf(idFromJson));

            // Retorna a resposta da API
            return response;
        } catch (EfiPayException e) {
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Método para gerar um QR Code para uma cobrança Pix
    private void pixGenerateQRCode(String id){
        // Configuração das opções necessárias para a chamada da API
        JSONObject options = configuringJsonObject();

        // Parâmetros da requisição para gerar o QR Code
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        try {
            // Criação de uma instância de EfiPay para interagir com a API
            EfiPay efi = new EfiPay(options);
            // Chama o método da API para gerar o QR Code
            Map<String, Object> response = efi.call("pixGenerateQRCode", params, new HashMap<String, Object>());

            // Imprime a resposta da API
            System.out.println(response);

            // Salva a imagem do QR Code
            File outputfile = new File("qrCodeImage.png");
            ImageIO.write(ImageIO.read(new ByteArrayInputStream(javax.xml.bind.DatatypeConverter.parseBase64Binary(((String) response.get("imagemQrcode")).split(",")[1]))), "png", outputfile);
            // Abre a imagem do QR Code
            Desktop desktop = Desktop.getDesktop();
            desktop.open(outputfile);
        } catch (EfiPayException e) {
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Método para configurar as opções necessárias para as chamadas da API
    private JSONObject configuringJsonObject(){
        // Carrega as credenciais
        Credentials credentials = new Credentials();

        // Configura as opções para a chamada da API
        JSONObject options = new JSONObject();
        options.put("client_id", clientId);
        options.put("client_secret", clientSecret);
        options.put("certificate", credentials.getCertificate());
        options.put("sandbox", credentials.isSandbox());

        return options;
    }
}
