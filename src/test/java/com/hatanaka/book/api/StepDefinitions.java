package com.hatanaka.book.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatanaka.book.api.dto.BookRequestTest;
import com.hatanaka.book.api.dto.BookResponseTest;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class StepDefinitions extends ApiApplicationTests {

    private final TestWebClient testWebClient;
    private final Map<String, String> mapHeaders = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JdbcTemplate jdbcTemplate;

    private ResponseEntity<?> response;

    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer
    @DefaultDataTableCellTransformer
    public Object transformer(final Object fromValue, final Type toValueType) {
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }

    @Then("deve retornar status {int}")
    public void deveRetornarStatus(final int httpStatus) {
        log.info("m=deveRetornarStatus, httpStatus={}", httpStatus);
        Assert.assertEquals("Status code", httpStatus, response.getStatusCode().value());
    }

    @When("fizer um GET para o resource {string}")
    public void fizerUmGETParaOResource(final String url) {
        log.info("m=fizerUmGETParaOResource, url={}", url);
        response = testWebClient.doGet(mapHeaders, url);
    }

    @And("com header da request {string} com valor {string}")
    public void comHeaderDaRequestComValor(final String nome, final String valor) {
        mapHeaders.put(nome, valor);
    }

    @And("retornar o livro buscado:")
    public void retornarOLivroBuscado(final BookResponseTest expectedBookResponse) throws JsonProcessingException {
        final BookResponseTest bookResponse = objectMapper.readValue(Objects.requireNonNull(response.getBody()).toString(), BookResponseTest.class);
        Assert.assertEquals(expectedBookResponse, bookResponse);
    }

    @Given("livros inseridos no SGBD atraves do arquivo {string}")
    public void livrosInseridosNoSGBDAtravesDoArquivo(final String sqlPathFile) throws IOException {
        log.info("m=livrosInseridosNoSGBDAtravesDoArquivo");
        final InputStream sqlInputStream = new ClassPathResource("files/" + sqlPathFile).getInputStream();
        final Reader sqlReader = new InputStreamReader(sqlInputStream, StandardCharsets.UTF_8);
        jdbcTemplate.execute(FileCopyUtils.copyToString(sqlReader));
    }

    @And("retornar a lista de livros buscado:")
    public void retornarAListaDeLivrosBuscado(final List<BookResponseTest> expectedBookResponseList) throws JsonProcessingException {
        final List<Object> bookResponseList = Arrays.asList(objectMapper.readValue(Objects.requireNonNull(response.getBody()).toString(), BookResponseTest[].class));
        Assert.assertEquals(expectedBookResponseList, bookResponseList);
    }

    @And("com as tabelas vazias:")
    public void comAsTabelasVazias(final List<String> tables) {
        log.info("m=comAsTabelasVazias, tables={}", tables);
        tables.forEach(table -> JdbcTestUtils.deleteFromTables(jdbcTemplate, table));
    }

    @And("retornar a lista de livros vazia")
    public void retornarAListaDeLivrosVazia() throws JsonProcessingException {
        retornarAListaDeLivrosBuscado(Collections.emptyList());
    }

    @When("fizer um POST para o resource {string} com o body:")
    public void fizerUmPOSTParaOResourceComOBody(final String url, final BookRequestTest bookRequest) throws JsonProcessingException {
        log.info("m=fizerUmPOSTParaOResourceComOBody, url={}, bookRequest={}", url, bookRequest);
        response = testWebClient.doPost(mapHeaders, url, objectMapper.writeValueAsString(bookRequest));
    }

    @And("sequences do banco com valores:")
    public void sequencesDoBancoComValores(final Map<String, Long> sequences) {
        log.info("m=sequencesDoBancoComValores, sequences={}", sequences);
        sequences.forEach((sequence, value) -> jdbcTemplate.execute(String.format("alter sequence if exists %s restart with %d;", sequence, value)));
    }
}
