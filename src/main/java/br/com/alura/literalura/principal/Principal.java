package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.*;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    ***************************************************
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - Sair
                    ***************************************************
                    """;

            System.out.println(menu);
            try {
                opcao = leitura.nextInt();
                leitura.nextLine();

                switch (opcao) {
                    case 1 -> buscarLivroWeb();
                    case 2 -> listarLivrosRegistrados();
                    case 3 -> listarAutoresRegistrados();
                    case 4 -> listarAutoresVivosNoAno();
                    case 5 -> listarLivrosPorIdioma();
                    case 0 -> System.out.println("Encerrando aplicação...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                leitura.nextLine();
            }
        }
    }

    private void buscarLivroWeb() {
        System.out.println("Digite o título do livro:");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
        DadosResultados dados = conversor.obterDados(json, DadosResultados.class);

        if (dados.resultados().isEmpty()) {
            System.out.println("Livro não encontrado.");
        } else {
            DadosLivro dadosLivro = dados.resultados().get(0);
            
           
            if (livroRepository.findByTituloContainingIgnoreCase(dadosLivro.titulo()).isPresent()) {
                System.out.println("Este livro já está salvo no banco de dados!");
                return;
            }

            Livro livro = new Livro(dadosLivro);
            
            
            if (!dadosLivro.autores().isEmpty()) {
                DadosAutor dadosAutor = dadosLivro.autores().get(0);
               
                Optional<Autor> autorExistente = autorRepository.findByNomeContainingIgnoreCase(dadosAutor.nome());
                
                if (autorExistente.isPresent()) {
                    livro.setAutor(autorExistente.get());
                } else {
                    Autor autorNovo = new Autor(dadosAutor);
                    autorRepository.save(autorNovo);
                    livro.setAutor(autorNovo);
                }
            }
            
            livroRepository.save(livro);
            System.out.println("\nLivro salvo com sucesso: " + livro.getTitulo());
        }
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();
        livros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivosNoAno() {
        System.out.println("Digite o ano que deseja pesquisar:");
        var ano = leitura.nextInt();
       
        List<Autor> autores = autorRepository.findAutoresVivosNoAno(ano);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado vivo nesse ano.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Digite o idioma para busca:
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
        var idioma = leitura.nextLine();
        List<Livro> livros = livroRepository.findByIdioma(idioma);
        livros.forEach(System.out::println);
    }
}