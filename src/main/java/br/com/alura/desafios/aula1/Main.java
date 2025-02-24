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
//        System.out.println(desafio3("  João Carlos Silva   ")); // Saída: "João Silva"
//        System.out.println(desafio3("Maria Fifi Da Silva Sauro  ")); // Saída: "Maria Sauro"
//        System.out.println(desafio3("  Anderson")); // Saída: "Anderson"

        // 4 - Implemente um método que verifica se uma frase é um palíndromo.
        // Um palíndromo é uma palavra/frase que, quando lida de trás pra frente, é igual à leitura normal.
//        System.out.println(desafio4("socorram me subi no onibus em marrocos")); // Saída: true
//        System.out.println(desafio4("Java")); // Saída: false

        //5 - Implemente um método que recebe uma lista de e-mails (String)
        // e retorna uma nova lista onde cada e-mail está convertido para letras minúsculas.
        List<String> emails = Arrays.asList("TESTE@EXEMPLO.COM", "exemplo@Java.com ", "Usuario@teste.Com");
        System.out.println(normalizeEmails(emails)); // Saída: ["teste@exemplo.com", "exemplo@java.com", "usuario@teste.com"]


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

    public static boolean desafio4(String frase) {
        frase = frase.replace(" ", "");
        var fraseInvertida = new StringBuilder(frase).reverse().toString();
        return frase.equalsIgnoreCase(fraseInvertida);
    }

}
