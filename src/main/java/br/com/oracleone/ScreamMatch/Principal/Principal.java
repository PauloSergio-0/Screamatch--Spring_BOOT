package br.com.oracleone.ScreamMatch.Principal;

import br.com.oracleone.ScreamMatch.model.DadosEpisodio;
import br.com.oracleone.ScreamMatch.model.DadosSerie;
import br.com.oracleone.ScreamMatch.model.DadosTemporada;
import br.com.oracleone.ScreamMatch.service.ConsumoApi;
import br.com.oracleone.ScreamMatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Principal {
    private final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    private final String apiKey = System.getenv("API_OMDB");
    private final String url = "http://www.omdbapi.com";
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();

    public void menu(){

        System.out.println("Digite uma serie: ");
        String serie = scanner.nextLine().trim().toLowerCase().replace(" ","_");

        var json = consumoApi.obterDados(url+"/?"+ "apikey="+ apiKey + "&t="+ serie);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        json = consumoApi.obterDados(url+"/?"+ "apikey="+ apiKey + "&t="+ serie +"&season=" + 1 +"&episode=1");
        DadosEpisodio dadosEpisodio = conversor.obterDados(json,DadosEpisodio.class);
        System.out.println(dadosEpisodio);


        ArrayList<DadosTemporada> temporadas = new ArrayList<>();
		for(int i = 1; i <= dadosSerie.totalTemporadas(); i++){

			json = consumoApi.obterDados(url+"/?"+ "apikey="+ apiKey + "&t="+ serie +"&season=" + i);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);

//        for(int i = 0; i < dadosSerie.totalTemporadas(); i++){
//            ArrayList<DadosEpisodio> episodiosTemp = temporadas.get(i).episodios();
//
//            for(DadosEpisodio episodio: episodiosTemp){
//                System.out.println(episodio.titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        temporadas.forEach(System.out::println);
    }
}
