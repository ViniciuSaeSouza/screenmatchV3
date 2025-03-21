package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.core.env.Environment;


import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private SerieRepository serieRepository;
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final Environment env;
    private String apiKey = System.getenv("OMDB_APIKEY");
    private List<Serie> listaSeries = new ArrayList<>();


    public Principal(SerieRepository repository, Environment env) {
        this.serieRepository = repository;
        this.env = env;
    }

    public void exibeMenu() {
        var menu = """
                1 - Buscar séries
                2 - Buscar episódios
                3 - Listar séries pesquisadas
                0 - Sair
                                
                Escolha: """;
        int opcao = -1;

        while (!(opcao == 0)) {
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();
            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesPesquisadas();
                    voltarAoMenu();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void voltarAoMenu() {
        System.out.print("\nAperte qualquer tecla para voltar ao menu: ");
        leitura.nextLine();
        exibeMenu();
    }

    private void listarSeriesPesquisadas() {
        listaSeries = serieRepository.findAll();
        listaSeries.stream()
                .sorted(Comparator.comparing(Serie::getCategoria))
                .forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        Serie serie = new Serie(getDadosSerie());
        System.out.println(serie);
        serieRepository.save(serie);
        voltarAoMenu();
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        try {
            var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&apikey=" + apiKey);
            DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
            return dados;
        } catch (RuntimeException ex) {
            throw new RuntimeException("Falha em getDadosSerie(): " + ex);
        }
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesPesquisadas();
        System.out.println("Digite o nome da série: ");
        String nomeSerie = leitura.nextLine();
        Optional<Serie> dadosSerie = listaSeries.stream().filter(s -> s.getTitulo().equalsIgnoreCase(nomeSerie)).findFirst();
        if (dadosSerie.isPresent()) {
            var serieEncontrada = dadosSerie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + "&apikey=" + apiKey);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(e -> new Episodio(t.numero(), e))
                    ).toList();
            serieEncontrada.setEpisodios(episodios);
            serieRepository.save(serieEncontrada);
        } else {
            System.out.println("Série '" + nomeSerie + "' não encontrada");
        }
        voltarAoMenu();
    }


}