package com.hatanaka.book.api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class StepDefinitions extends ApiApplicationTests {

    private final TestWebClient testWebClient;
    private final Map<String, String> mapHeaders = new HashMap<>();
    private ResponseEntity<?> response;

    @Then("deve retornar status {int}")
    public void deveRetornarStatus(int httpStatus) {
        Assert.assertEquals("Status code", httpStatus, response.getStatusCode().value());
    }

    @When("fizer um GET para o resource {string}")
    public void fizerUmGETParaOResource(String url) {
        response = testWebClient.doGet(mapHeaders, url);
    }

    @And("com header da request {string} com valor {string}")
    public void comHeaderDaRequestComValor(String nome, String valor) {
        mapHeaders.put(nome, valor);
    }
}
