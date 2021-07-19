# language: en

Feature: Listagem de livros

  Background: Uma request
    And com header da request "Accept" com valor "application/json;charset=UTF-8"
    And com header da request "User-Agent" com valor "Cucumber"

  Scenario: Buscar todos os livros
    When fizer um GET para o resource "/books/"
    Then deve retornar status 200

  Scenario: Buscar todos os livros
    When fizer um GET para o resource "/books/xpto"
    Then deve retornar status 200
