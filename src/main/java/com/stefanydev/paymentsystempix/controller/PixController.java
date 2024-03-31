package com.stefanydev.paymentsystempix.controller;

import com.stefanydev.paymentsystempix.dto.PixChargeRequest;
import com.stefanydev.paymentsystempix.service.PixService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(value = "/api/v1/pix", produces = {"application/json"})
@Tag(name = "pix")
public class PixController {

    @Autowired
    private PixService pixService;

    // Endpoint para criar um Pix usando EVP (Equivalente Virtual de Pagamento)
    @GetMapping
    @Operation(summary = "Realiza a autenticação de usuário.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "criar um Pix usando EVP realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a criação de um Pix usando EVP"),
    })
    public ResponseEntity pixCreateEVP(){
        // Chama o serviço para criar um Pix usando EVP e recebe a resposta
        JSONObject response = this.pixService.pixCreateEVP();

        // Retorna uma resposta HTTP OK com o JSON da resposta do serviço
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toString());
    }

    // Endpoint para criar uma cobrança Pix
    @PostMapping
    @Operation(summary = "Realiza a autenticação de usuário.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "criar uma cobrança Pix realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a criação de uma cobrança Pix"),
    })
    public ResponseEntity pixCreateCharge(@RequestBody PixChargeRequest pixChargeRequest){
        // Chama o serviço para criar uma cobrança Pix e recebe a resposta
        JSONObject response = this.pixService.pixCreateCharge(pixChargeRequest);

        // Retorna uma resposta HTTP OK com o JSON da resposta do serviço
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toString());
    }
}
