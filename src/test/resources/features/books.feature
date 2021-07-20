# language: en

Feature: Resource de livros

  Background: Uma request
    And com header da request "Accept" com valor "application/json;charset=UTF-8"
    And com header da request "User-Agent" com valor "Cucumber"
    And sequences do banco com valores:
      | sq_book_idt | 1 |
    And com as tabelas vazias:
      | book |

  Scenario: Buscar todos os livros
    Given livros inseridos no SGBD atraves do arquivo "busca_livros.sql"
    When fizer um GET para o resource "/books/"
    Then deve retornar status 200
    And retornar a lista de livros buscado:
      | name   |
      | livro1 |
      | livro2 |

  Scenario: Buscar todos os livros quando nao ha registros
    When fizer um GET para o resource "/books/"
    Then deve retornar status 200
    And retornar a lista de livros vazia

  Scenario: Buscar um livro especifico
    Given livros inseridos no SGBD atraves do arquivo "busca_livros.sql"
    When fizer um GET para o resource "/books/livro1"
    Then deve retornar status 200
    And retornar o livro buscado:
      | name   |
      | livro1 |

  Scenario: Buscar um livro especifico que nao existe na base
    Given livros inseridos no SGBD atraves do arquivo "busca_livros.sql"
    When fizer um GET para o resource "/books/livro3"
    Then deve retornar status 404

  Scenario Outline: Buscar um livro especifico com nome fora do intervalo 5 e 10 caracteres
    Given livros inseridos no SGBD atraves do arquivo "busca_livros.sql"
    When fizer um GET para o resource "/books/<identificadores>"
    Then deve retornar status 400
    Examples:
      | identificadores |
      | livr            |
      | l               |
      | livro123456     |

  Scenario: Criar um novo livro
    Given livros inseridos no SGBD atraves do arquivo "busca_livros.sql"
    And sequences do banco com valores:
      | sq_book_idt | 3 |
    When fizer um POST para o resource "/books/" com o body:
      | name   |
      | livro3 |
    Then deve retornar status 201

  Scenario Outline: Criar um livro que ja existe
    Given livros inseridos no SGBD atraves do arquivo "busca_livros.sql"
    And sequences do banco com valores:
      | sq_book_idt | 3 |
    When fizer um POST para o resource "/books/" com o body:
      | name       |
      | <bookName> |
    Then deve retornar status <status>
    Examples:
      | bookName    | status |
      | livro1      | 409    |
      | livro2      | 409    |
      | livr        | 400    |
      | livro123456 | 400    |