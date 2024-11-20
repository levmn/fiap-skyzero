package br.com.skyzero.service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


public class CarbonApiService {
    private static final String API_URL = "https://api-calculators.carbonext.com.br/v2/calculators/distance";

    public double calcularEmissao(String tipoAviao, int distancia) {
        Client client = ClientBuilder.newClient();

        JsonObject jsonRequest = Json.createObjectBuilder()
                .add("distance", distancia)
                .add("type", tipoAviao)
                .build();

        System.out.println("JSON enviado para API externa: " + jsonRequest);

        Response response = client.target(API_URL)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(jsonRequest));

        try {
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new RuntimeException("Erro na API: " + response.getStatus() + " - " + response.getStatusInfo().getReasonPhrase());
            }

            JsonObject jsonResponse = response.readEntity(JsonObject.class);
            System.out.println("Resposta da API externa: " + jsonResponse);

            return jsonResponse.getJsonNumber("emission").doubleValue();

        } finally {
            response.close();
            client.close();
        }
    }
}
