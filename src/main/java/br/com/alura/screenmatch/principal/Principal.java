package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.core.env.Environment;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private SerieRepository serieRepository;
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final Environment env;

    private String apiKey = System.getenv("OMDB_APIKEY");


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
        var listaSeries = serieRepository.findAll();

        System.out.println("-- Séries pesquisadas --");
        listaSeries.stream().forEach(System.out::println);

        voltarAoMenu();
    }

    private void buscarSerieWeb() {
        Serie serie  = new Serie(getDadosSerie());
        System.out.println(serie);
        serieRepository.save(serie);
        voltarAoMenu();
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") +"&apikey=" + apiKey);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = consumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + env.getProperty("app.api.key"));
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
        voltarAoMenu();
    }



}