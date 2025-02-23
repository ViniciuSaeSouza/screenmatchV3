package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.OptionalDouble;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private Categoria categoria;
    private String poster;
    private String sinopse;
    private String elenco;

    public Serie(DadosSerie dadosSerie) {
        titulo = dadosSerie.titulo();
        totalTemporadas = dadosSerie.totalTemporadas();
        avaliacao = OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0);
        categoria = Categoria.fromString(dadosSerie.categoria().split(",")[0].trim());
        elenco = dadosSerie.elenco();
        poster = dadosSerie.poster();
        sinopse = dadosSerie.sinopse();
    }
}
