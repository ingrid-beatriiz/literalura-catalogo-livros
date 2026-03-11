package br.com.alura.literalura.repository;

import java.util.List;
import java.util.Optional;
import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByIdioma(String idioma);
    Optional<Livro> findByTituloContainingIgnoreCase(String titulo);
}