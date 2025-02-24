package br.com.alura.screenmatch.service.traducao;

import br.com.alura.screenmatch.model.DadosSerie;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public record DadosTraducao(@JsonAlias("responseData") String textoTraduzido) {
    public String obterTextoTraduzido() {
        return textoTraduzido;
    }
}
