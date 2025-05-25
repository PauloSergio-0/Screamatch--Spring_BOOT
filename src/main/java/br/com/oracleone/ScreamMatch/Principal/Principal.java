package br.com.oracleone.ScreamMatch.Principal;

import br.com.oracleone.ScreamMatch.model.DadosEpisodio;
import br.com.oracleone.ScreamMatch.model.DadosSerie;
import br.com.oracleone.ScreamMatch.model.DadosTemporada;
import br.com.oracleone.ScreamMatch.model.Episodio;
import br.com.oracleone.ScreamMatch.service.ConsumoApi;
import br.com.oracleone.ScreamMatch.service.ConverteDados;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t ->t.episodios().stream())
                .toList();

        System.out.println("Top 5 eps");
        dadosEpisodios.stream()
                .filter(e->!"N/A".equals(e.avaliacao()))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);


        List<Episodio> episodios = temporadas.stream()
                .flatMap(t ->t.episodios().stream()
                .map(d-> new Episodio(t.numeroTemp(),d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de qual data deseja mostrar os eps: ");
        var ano = scanner.nextInt();
        scanner.nextLine();

        LocalDate dataBusca = LocalDate.of(ano,1,1);

        DateTimeFormatter dataFormatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e-> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: "+ e.getTemporada()+
                        " Titulo:"+ e.getTitulo()+
                        " Episodio: "+ e.getNumeroEp()+
                        " Avaliação: "+ e.getAvaliacao()+
                        " Data de Lançamento: " + e.getDataLancamento().format(dataFormatador)
                        )
                );


        //toList = lista imutavel
        //.collect(Collectors.toList()); = lista mutavel

//        List<String> nomes = Arrays.asList("Paulo", "Ana", "Tentoi", "Bonout", "Zuloi");
//
//        nomes.stream()
//                .sorted()
//                .limit(3)
//                .filter(n -> n.startsWith("P"))
//                .map(n -> n.toUpperCase())
//                .forEach(System.out::println);
    }
}
