package br.com.alura.desafios.aula1;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        List<String> desafio = Arrays.asList("10", "abc", "20", "xyz", "30x");

        // refeito por moi

        List<Integer> resultado = desafio.stream()
                .map(x -> {
                    try {
                        return Optional.of(Integer.parseInt(x));
                    } catch (NumberFormatException e) {
                        return Optional.<Integer>empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        System.out.println(resultado);

    }

//    public static Optional<Integer> processaNumero(Optional<Integer> numero) {
//        if (numero.isPresent() && numero.get() > 0) {
//            return Optional.;
//        }
//    }
}
