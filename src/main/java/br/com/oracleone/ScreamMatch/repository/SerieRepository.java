package br.com.oracleone.ScreamMatch.repository;

import br.com.oracleone.ScreamMatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTitulosContainingIgnoreCase(String nomeSerie);
}
