package br.com.oracleone.ScreamMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(
        @JsonAlias("Season") Integer numeroTemp,
        @JsonAlias("Episodes") ArrayList<DadosEpisodio> episodios
    )
        {

}
