package br.com.oracleone.ScreamMatch.service;

import br.com.oracleone.ScreamMatch.model.DadosSerie;

public interface IConverteDados {

     <T> T obterDados(String json, Class<T> classe);
}
