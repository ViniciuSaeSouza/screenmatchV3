package br.com.alura.desafios.aula1;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        //1 - Imagine que você tem uma lista de strings.
        // Algumas das strings são números, mas outras não. Queremos converter a lista de strings para uma lista de números.
        List<Integer>resultado = desafio1();
        System.out.println(resultado);

        //2 - Implemente um método que recebe um número inteiro dentro de um Optional.
        // Se o número estiver presente e for positivo, calcule seu quadrado. Caso contrário, retorne Optional.empty.
//        System.out.println(processaNumero(Optional.of(5))); // Saída: Optional[25]
//        System.out.println(processaNumero(Optional.of(-3))); // Saída: Optional.empty
//        System.out.println(processaNumero(Optional.empty()));


        // 3 - 3 - Implemente um método que recebe uma String representando um nome completo separado por espaços.
        // O método deve retornar o primeiro e o último nome após remover os espaços desnecessários.
        System.out.println(desafio3("  João Carlos Silva   ")); // Saída: "João Silva"
        System.out.println(desafio3("Maria Fifi Da Silva Sauro  ")); // Saída: "Maria Sauro"
        System.out.println(desafio3("  Anderson")); // Saída: "Anderson"

    }

    public static List<Integer> desafio1() {
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
        return resultado;
    }

    public static Optional<Integer> processaNumero(Optional<Integer> numero) {
        if (numero.isPresent() && numero.get() > 0) {
            return Optional.of((int)Math.pow(numero.get(), 2));
        }
        return Optional.empty();
    }

    public static String desafio3(String nome) {
        var listNome = nome.trim().split(" ");

        if (listNome.length < 1) {
            return "";
        }

        return listNome.length == 1 ? listNome[0] : listNome[0] + " " + listNome[listNome.length - 1];
    }
}
