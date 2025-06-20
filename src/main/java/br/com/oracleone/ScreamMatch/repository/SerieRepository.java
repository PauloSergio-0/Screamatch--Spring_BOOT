package br.com.oracleone.ScreamMatch.repository;

import br.com.oracleone.ScreamMatch.model.Categoria;
import br.com.oracleone.ScreamMatch.model.Episodio;
import br.com.oracleone.ScreamMatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTitulosContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, double avalicao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria genero);

//    @Query(value="select * from series s where s.total_temporadas > :temp and s.avaliacao_serie >= :ava", nativeQuery = true)
    @Query("select s from Serie s where s.totalTemporadas > :totalTemp and s.avaliacao >= :ava")
    List<Serie> seriePorTempAva(Integer totalTemp, double ava);

    @Query("select e from Serie s join s.episodios e where e.titulo ILIKE %:epBusca%")
    List<Episodio> epsiodioPorTrecho(String epBusca);
}
