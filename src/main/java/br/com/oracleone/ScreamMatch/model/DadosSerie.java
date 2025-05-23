package br.com.oracleone.ScreamMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // ignore oq não encontrar
public record DadosSerie(@JsonAlias({"title", "titulo"}) String titulos,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliação) {

    //@JsonProperty serve para escrever e ler json mantendo o nome em ""
   // @JsonAlias apenas para ler um json
}
