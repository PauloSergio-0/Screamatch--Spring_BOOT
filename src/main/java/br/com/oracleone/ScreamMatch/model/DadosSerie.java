package br.com.oracleone.ScreamMatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonAlias;

@JsonIgnoreProperties(ignoreUnknown = true) // ignore oq não encontrar
public record DadosSerie(
         @JsonAlias({"Title", "titulo"}) String titulos,
         @JsonAlias("totalSeasons") Integer totalTemporadas,
         @JsonAlias("imdbRating") String avaliacao,
         @JsonAlias("Genre") String genero,
         @JsonAlias("Actors") String atores,
         @JsonAlias("Plot") String sinopse,
         @JsonAlias("Poster") String poster
)
{

    //@JsonProperty serve para escrever e ler json mantendo o nome em ""
   // @JsonAlias apenas para ler um json
}
