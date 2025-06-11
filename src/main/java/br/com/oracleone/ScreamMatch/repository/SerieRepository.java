package br.com.oracleone.ScreamMatch.repository;

import br.com.oracleone.ScreamMatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
