package br.com.oracleone.ScreamMatch;

import br.com.oracleone.ScreamMatch.model.DadosEpisodio;
import br.com.oracleone.ScreamMatch.model.DadosSerie;
import br.com.oracleone.ScreamMatch.service.ConsumoApi;
import br.com.oracleone.ScreamMatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreammatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreammatchApplication.class, args);
	}

	@Override //metodo main comum
	public void run(String... args) throws Exception {

		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?apikey=308478da&t=peaky_blinders");


		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

		json = consumoApi.obterDados("http://www.omdbapi.com/?apikey=308478da&t=peaky_blinders&season=1&episode=1");

		DadosEpisodio dadosEpisodio = conversor.obterDados(json,DadosEpisodio.class);
		System.out.println(dadosEpisodio);

	}
}
