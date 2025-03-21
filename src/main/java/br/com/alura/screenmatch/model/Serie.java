package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.traducao.ConsultaMyMemoryAPI;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")

public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private String poster;
    private String sinopse;
    private String elenco;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<Episodio> episodios = new ArrayList<>();
    public Serie() {
    }

    public Serie(DadosSerie dadosSerie) {
        titulo = dadosSerie.titulo();
        totalTemporadas = dadosSerie.totalTemporadas();
        avaliacao = OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0);
        categoria = Categoria.fromString(dadosSerie.categoria().split(",")[0].trim());
        elenco = dadosSerie.elenco();
        poster = dadosSerie.poster();
        sinopse = ConsultaMyMemoryAPI.obterTraducao(dadosSerie.sinopse());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getElenco() {
        return elenco;
    }

    public void setElenco(String elenco) {
        this.elenco = elenco;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        this.episodios = episodios;
    }

    @Override
    public String toString() {
        return "Serie: " +
                "titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacao=" + avaliacao +
                ", categoria=" + categoria +
                ", poster='" + poster + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", elenco='" + elenco + '\'' +
                '}';
    }
}
