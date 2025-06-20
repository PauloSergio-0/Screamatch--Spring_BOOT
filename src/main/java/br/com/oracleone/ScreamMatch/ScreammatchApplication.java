package br.com.oracleone.ScreamMatch;

import br.com.oracleone.ScreamMatch.Principal.Principal;
import br.com.oracleone.ScreamMatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;
import java.util.Scanner;

@SpringBootApplication
public class ScreammatchApplication implements CommandLineRunner {

	@Autowired
	private SerieRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(ScreammatchApplication.class, args);
	}

	@Override //metodo main comum
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio);
		principal.menu();


//		ArrayList<DadosTemporada> temporadas = new ArrayList<>();
//		for(int i = 1; i <= dadosSerie.totalTemporadas(); i++){
//
//			json = consumoApi.obterDados(url+"/?"+ "apikey="+ apiKey + "t="+ serie +"&season=" + i);
//			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
//			temporadas.add(dadosTemporada);
//		}
//
//		temporadas.forEach(System.out::println);
	}
}
