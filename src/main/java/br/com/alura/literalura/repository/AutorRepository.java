package br.com.alura.literalura.repository;

import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import br.com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Autor> findAutoresVivosNoAno(Integer ano);

    Optional<Autor> findByNomeContainingIgnoreCase(String nome);
}