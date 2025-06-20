package br.com.oracleone.ScreamMatch.Principal;

import br.com.oracleone.ScreamMatch.model.*;
import br.com.oracleone.ScreamMatch.repository.SerieRepository;
import br.com.oracleone.ScreamMatch.service.ConsumoApi;
import br.com.oracleone.ScreamMatch.service.ConverteDados;
import jakarta.persistence.criteria.CriteriaBuilder;

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
    private String json;
    private List<DadosSerie> dadosSerie = new ArrayList<>();
    private String serie;
    private ArrayList<DadosTemporada> temporadas = new ArrayList<>();
    private List<Episodio> episodios;
    private List<Serie> series = new ArrayList<>();
    private SerieRepository repositorio;


    public Principal(SerieRepository repositorio){
        this.repositorio = repositorio;
    }

    public void menu() {

        while (true){
            System.out.println("""
                    1. Buscar Série
                    2. Buscar Episodio
                    3. Listar series
                    4. Buscar Serie por titulo
                    5. Buscar Serie por ator
                    6. Top 5 Series
                    7. Buscar Serie por catecoria
                    8. Listar Series por temporada e avaliação
                    9. Buscar Episodio portrecho
                    0. Sair
                    """);

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 0){
                System.out.println("Saindo......");
                break;
            } else if (opcao == 1) {
                buscarSerie();
            } else if (opcao == 2) {
                detalhesEps();
            } else if (opcao == 3) {
                listarSeries();
            } else if (opcao == 4){
                buscarSeriePorTitulo();
            } else if (opcao == 5){
                buscarSeriePorAtor();
            } else if (opcao == 6){
                buscarTop5Series();
            } else if (opcao == 7){
                buscarCategoria();
            } else if (opcao == 8){
                listarSeriesPorTemp();
            }else if (opcao == 9){
                buscarEpsodioporTrecho();

            } else {
                System.out.println("Nenhua opção encontrada!");
            }

        }





//        topNSerie(temporadas);
//        buscarTituloEps(episodios);
//        avaliacoesTemporadas(episodios);
//        filtroData(episodios);
//        ordenarNomes();

        //temporadas.forEach(System.out::println);

//        for(int i = 0; i < dadosSerie.totalTemporadas(); i++){
//            ArrayList<DadosEpisodio> episodiosTemp = temporadas.get(i).episodios();
//
//            for(DadosEpisodio episodio: episodiosTemp){
//                System.out.println(episodio.titulo());
//            }
//        }

//        temporadas.forEach(t -> t.episodios().
//                forEach(e -> System.out.println(e.titulo())));

        //temporadas.forEach(System.out::println);

    }

    private void buscarEpsodioporTrecho() {

        System.out.println("Digite o nome do episodio para busca: ");
        String epBusca = scanner.nextLine().trim();
        List<Episodio> epsEncontrados = repositorio.epsiodioPorTrecho(epBusca);

        epsEncontrados.forEach(e ->
                System.out.printf("Serie: %s | Temporada: %d | NumEP: %d | Episodio: %s\n",
                e.getSerie().getTitulos(),
                e.getTemporada(),
                e.getNumeroEp(),
                e.getTitulo()
            )
                );
    }

    private void listarSeriesPorTemp() {
        System.out.println("Qual o minino de tempotaradas: ");
        Integer temp = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Qual o minimo de avaliacao");
        double ava = scanner.nextDouble();
        scanner.nextLine();


        List<Serie> series = repositorio.seriePorTempAva(temp,ava);
        System.out.println("Series");
        series.forEach(s -> System.out.println("Nome serie: "+s.getTitulos() + " | Avaliação: "+ s.getAvaliacao()));

    }

    private void buscarCategoria() {
        System.out.println("Escolha um genero");
        String genero =scanner.nextLine();

        Categoria categoria =Categoria.fromPortugues(genero);
        List<Serie> categoriaBuscada = repositorio.findByGenero(categoria);
        System.out.println("Séries da categoria: "+ categoriaBuscada);

        categoriaBuscada.forEach(s -> System.out.println("Nome serie: "+s.getTitulos() + " | Avaliação: "+ s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Serie> top5series = repositorio.findTop5ByOrderByAvaliacaoDesc();
        System.out.println("Top 5 Series");
        top5series.forEach(s -> System.out.println("Nome serie: "+s.getTitulos() + " | Avaliação: "+ s.getAvaliacao()));
    }

    private void buscarSeriePorAtor() {
        System.out.println("Digite o nome do ator");
        String nomeAtor = scanner.nextLine();

        System.out.println("Avalicao minima");
        double avalicao = scanner.nextDouble();
        List<Serie> seriesEncontrdas = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avalicao);
        System.out.println("O ator "+nomeAtor+" trabalhou na(s) serie(s).");
        seriesEncontrdas.forEach(s -> System.out.println("Nome serie: "+s.getTitulos() + " | Avaliação: "+ s.getAvaliacao()));


    }

    private void buscarSeriePorTitulo() {
        System.out.println("Digite uma serie: ");
        String serieBusca = scanner.nextLine().trim();

        Optional<Serie> serieBuscada = repositorio.findByTitulosContainingIgnoreCase(serieBusca);

        if (serieBuscada.isPresent()){
            System.out.println("Serie encontrada");
            System.out.println(serieBuscada);
        }else {
            System.out.println("Serie não encontrada");
        }

    }

    private void buscarSerie(){
        System.out.println("Digite uma serie: ");
        String serieBusca = scanner.nextLine().trim().toLowerCase().replace(" ", "_");

        json = consumoApi.obterDados(url + "/?" + "apikey=" + apiKey + "&t=" + serieBusca);
        DadosSerie serieData = conversor.obterDados(json, DadosSerie.class);
        Serie serie =new Serie(serieData);
        repositorio.save(serie);
        System.out.println(serieData);
        dadosSerie.add(serieData);

//        json = consumoApi.obterDados(url + "/?" + "apikey=" + apiKey + "&t=" + serie + "&season=" + 1 + "&episode=1");
//        DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
//        System.out.println(dadosEpisodio);
    }

    private void detalhesEps(){
        listarSeries();
        System.out.println("Digite uma serie: ");
        String serieBusca = scanner.nextLine().trim();

        Optional<Serie> seriesFiltrada = series.stream()
                .filter(s ->serieBusca.equalsIgnoreCase(s.getTitulos()))
                .findFirst();

        if(seriesFiltrada.isPresent()){

            var serieEncontrada = seriesFiltrada.get();

            temporadas = new ArrayList<>();
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {

                json = consumoApi.obterDados(url + "/?" + "apikey=" + apiKey + "&t=" + serieEncontrada.getTitulos().trim().toLowerCase().replace(" ", "_") + "&season=" + i);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(d -> new Episodio(t.numeroTemp(), d)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);

            System.out.println("Série: " + serieEncontrada.getTitulos());
            episodios.forEach(System.out::println);
        } else {
            System.out.println("Não existe série");
        }
    }

    private void listarSeries(){
       // List<Serie> series = new ArrayList<>();
//        series = dadosSerie.stream()
//                        .map(ds -> new Serie(ds))
//                        .toList();

        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }


    private void topNSerie(ArrayList<DadosTemporada> temporadas) {

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .toList();

        System.out.println("Top 5 eps");
        dadosEpisodios.stream()
                .filter(e->!"N/A".equals(e.avaliacao()))
                .peek(e -> System.out.println("Primeiro filtro(N/A) "+e ))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenação"+ e))
                .limit(10)
                .peek(e -> System.out.println("limite "+ e))
                .map(e-> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Colocando em caixa alta e mapeando" + e))
                .forEach(System.out::println);
                }

    private void buscarTituloEps(List<Episodio> episodios){


        episodios.forEach(System.out::println);

        System.out.println("Buscar Titulo : ");
        String buscarTitulo = scanner.nextLine().trim();

        Optional<Episodio> episodioBusca = episodios.stream()
                .filter(e -> e.getTitulo().toLowerCase().contains(buscarTitulo.toLowerCase()))
                .findFirst();
        if(episodioBusca.isPresent()) {
            System.out.println("Episodio encontrado");
            System.out.println("Temporada: "+episodioBusca.get().getTemporada() + "\nEpisodio: "+ episodioBusca.get().getNumeroEp());
        } else {
            System.out.println("Episodio não encontrado");
        }
    }

     private void avaliacoesTemporadas(List<Episodio> episodios){
        Map<Integer, Double> avaliacoesPorTemp = episodios.stream()
                .filter(e -> !(e.getAvaliacao() == 0))
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemp);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> !(e.getAvaliacao() == 0))
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println(
                "Média: " + est.getAverage()
                        + "\nMelhor Avaliação: " + est.getMax()
                        + "\nPior Avaliação: " + est.getMin()
                        + "\nTotal de eps: " + est.getCount()
        );

    }
    private void filtroData(List<Episodio> episodios){
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


//        toList = lista imutavel
//        .collect(Collectors.toList()); = lista mutavel
    }

    private void ordenarNomes() {
        List<String> nomes = Arrays.asList("Paulo", "Ana", "Tentoi", "Bonout", "Zuloi");

        nomes.stream()
                .sorted()
                .limit(3)
                .filter(n -> n.startsWith("P"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);
    }

}
