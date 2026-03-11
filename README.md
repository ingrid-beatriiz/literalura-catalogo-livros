# 📚 LiterAlura - Catálogo de Livros

O LiterAlura é um catálogo interativo de livros via terminal. O sistema consome a API Gutendex para buscar informações detalhadas sobre obras e autores, e utiliza o poder do Spring Data JPA para persistir esses dados em um banco de dados relacional PostgreSQL. 

Projeto prático desenvolvido para o desafio da especialização Back-end do programa Oracle Next Education (ONE).

## 📸 Demonstração
![Demonstração do LiterAlura no Terminal](![alt text](image.png))

## ⚙️ Funcionalidades
Através de um menu interativo no console, o usuário pode:
1. **Buscar livro pelo título:** Faz uma requisição à API Gutendex, traz os dados do livro e do autor, e salva automaticamente no banco de dados (evitando duplicidades).
2. **Listar livros registrados:** Retorna todos os livros que já foram salvos no banco de dados local.
3. **Listar autores registrados:** Retorna todos os autores salvos no banco.
4. **Listar autores vivos em um determinado ano:** Utiliza *Derived Queries* do Spring para filtrar autores baseados no ano de nascimento e falecimento.
5. **Listar livros em um determinado idioma:** Filtra os livros salvos no banco por idioma (ex: inglês, português).

## 🧠 Aprendizados e Competências Desenvolvidas
* **Spring Boot & Spring Data JPA:** Configuração e uso do ecossistema Spring para mapeamento objeto-relacional (ORM) e injeção de dependências (`@Autowired`).
* **Modelagem de Dados:** Criação de Entidades (`@Entity`) e mapeamento de relacionamentos entre tabelas como `@OneToMany` e `@ManyToOne`.
* **Consultas ao Banco de Dados:** Criação de repositórios (`JpaRepository`) e uso de *Derived Queries* e consultas JPQL personalizadas.
* **Consumo de APIs e JSON:** Uso da biblioteca **Jackson** (`ObjectMapper`, `@JsonAlias`, `@JsonIgnoreProperties`) para desserializar objetos JSON aninhados em *Records* do Java.
* **Banco de Dados Relacional:** Configuração e integração com **PostgreSQL**.

## 🛠️ Tecnologias Utilizadas
* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA**
* **PostgreSQL**
* **Maven**
* **Jackson (Manipulação de JSON)**

## 🚀 Como Executar
1. Clone este repositório.
2. Certifique-se de ter o PostgreSQL rodando na sua máquina (ou via Docker) com um banco de dados chamado `literalura`.
3. Atualize as credenciais do banco no arquivo `application.properties`, se necessário.
4. No terminal, compile e rode o projeto usando o comando: `mvn clean spring-boot:run`
