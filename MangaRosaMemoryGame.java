import java.util.*;

public class MangaRosaMemoryGame {
    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Jogo da Memória Manga Rosa!!");
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("1 - Iniciar o jogo");
            System.out.println("2 - Ver pontuações");
            System.out.println("3 - Regras do jogo");
            System.out.println("4 - Sair");
            System.out.println("Digite a sua opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            String jogador1 = "PARTICIPANTE1";
            String jogador2 = "PARTICIPANTE2";
            String corJogador1 = "R";
            String corJogador2 = "B";
            int pontosJogador1 = 0;
            int pontosJogador2 = 0;

            switch (opcao) {
                case 1:
                    iniciarJogo(scanner);
                    break;
                case 2:
                    exibirPontuacoes(jogador1, jogador2, corJogador1, corJogador2, pontosJogador1, pontosJogador2);
                    break;
                case 3:
                    exibirRegras();
                    break;
            }
        } while (opcao != 4);
        System.out.println("Obrigado por jogar, volte sempre!!");
    }

    public static void escolherTamanhoTabuleiro(Scanner scanner) {
        int escolha;
        int tamanho = 0;
        do {
            System.out.println("Escolha o tamanho do tabuleiro: ");
            System.out.println("1 - 4x4\n2 - 6x6\n3 - 8x8\n4 - 10x10\n0 - Voltar");
            escolha = scanner.nextInt();
            switch (escolha) {
                case 1: tamanho = 4; break;
                case 2: tamanho = 6; break;
                case 3: tamanho = 8; break;
                case 4: tamanho = 10; break;
                case 0: return;
                default: System.out.println("Opção inválida. Escolha novamente.");
            }
        } while (tamanho == 0);
        int[] guardaTamanho = {tamanho};
        scanner.nextLine();
        iniciarJogo(scanner, guardaTamanho);
    }

    public static void iniciarJogo(Scanner scanner) {
        escolherTamanhoTabuleiro(scanner);
    }

    public static void iniciarJogo(Scanner scanner, int[] guardaTamanho) {
        int tamanho = guardaTamanho[0];
        if (tamanho == 0) return;

        String jogador1 = "PARTICIPANTE1";
        String jogador2 = "PARTICIPANTE2";
        System.out.println("Digite o nome do jogador 1: ");
        jogador1 = scanner.nextLine();
        System.out.println("Digite o nome do jogador 2: ");
        jogador2 = scanner.nextLine();

        Random rand = new Random();
        String corJogador1 = rand.nextBoolean() ? "R" : "B";
        String corJogador2 = corJogador1.equals("R") ? "B" : "R";
        System.out.println(jogador1 + " é " + (corJogador1.equals("R") ? "vermelho" : "azul"));
        System.out.println(jogador2 + " é " + (corJogador2.equals("R") ? "vermelho" : "azul"));

        String[][] tabuleiro = distribuirCartas(tamanho);
        boolean[][] cartaRevelacao = new boolean[tamanho][tamanho];
        int pontosJogador1 = 0;
        int pontosJogador2 = 0;
        boolean turnoJogador1 = true;

        jogar(scanner, tamanho, jogador1, jogador2, corJogador1, corJogador2, tabuleiro, cartaRevelacao, pontosJogador1, pontosJogador2, turnoJogador1);
    }

    public static String[][] distribuirCartas(int tamanho) {
        int qtdDosPares = (tamanho * tamanho) / 2;
        int vermelhoAzul = qtdDosPares / 2;
        int vermelho = vermelhoAzul / 2;
        int azul = vermelhoAzul - vermelho;
        int amarelo = qtdDosPares - vermelhoAzul - 1;
        int preto = 1;

        String[] simbolos = {"♥", "★", "▲", "◆", "■"};
        String[] cartas = new String[tamanho * tamanho];
        int indice = 0;
        Random rand = new Random();

        for (int i = 0; i < vermelho; i++) {
            String simbolo = simbolos[rand.nextInt(simbolos.length)];
            cartas[indice++] = simbolo + "R";
        }
        for (int i = 0; i < azul; i++) {
            String simbolo = simbolos[rand.nextInt(simbolos.length)];
            cartas[indice++] = simbolo + "B";
        }
        for (int i = 0; i < amarelo; i++) {
            String simbolo = simbolos[rand.nextInt(simbolos.length)];
            cartas[indice++] = simbolo + "Y";
        }
        for (int i = 0; i < preto; i++) {
            String simbolo = simbolos[rand.nextInt(simbolos.length)];
            cartas[indice++] = simbolo + "K";
        }

        for (int i = 0; i < qtdDosPares; i++) {
            cartas[indice + i] = cartas[i];
        }

        for (int i = cartas.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            String temp = cartas[i];
            cartas[i] = cartas[j];
            cartas[j] = temp;
        }

        String[][] tabuleiro = new String[tamanho][tamanho];
        indice = 0;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                tabuleiro[i][j] = cartas[indice++];
            }
        }
        return tabuleiro;
    }

    public static void exibirTabuleiro(int tamanho, String jogador1, String jogador2, String corJogador1, String corJogador2, String[][] tabuleiro, boolean[][] cartaRevelacao, int pontosJogador1, int pontosJogador2) {
        exibirPontuacoes(jogador1, jogador2, corJogador1, corJogador2, pontosJogador1, pontosJogador2);
        System.out.println("Tabuleiro:");
        System.out.print("    "); // Espaço inicial para alinhar com os números das linhas
        for (int j = 1; j <= tamanho; j++) {
            if (j < 10) {
                System.out.print("  " + j + "  "); // 5 caracteres para alinhar com "[ ? ] "
            } else {
                System.out.print(" " + j + "  "); // 5 caracteres para números de dois dígitos
            }
        }
        System.out.println();
        for (int i = 0; i < tamanho; i++) {
            if (i + 1 < 10) {
                System.out.print(" " + (i + 1) + "  "); // 4 caracteres para alinhar com o início das cartas
            } else {
                System.out.print((i + 1) + "  "); // 4 caracteres para números de dois dígitos
            }
            for (int j = 0; j < tamanho; j++) {
                if (cartaRevelacao[i][j]) {
                    String combinacao = tabuleiro[i][j];
                    char simbolo = combinacao.charAt(0);
                    String cor = "" + combinacao.charAt(1);
                    String codigoCor = switch (cor) {
                        case "R" -> "\u001B[31m";
                        case "B" -> "\u001B[34m";
                        case "Y" -> "\u001B[33m";
                        case "K" -> "\u001B[30m";
                        default -> "\u001B[0m";
                    };
                    System.out.print(codigoCor + "[ " + simbolo + " ] " + "\u001B[0m");
                } else {
                    System.out.print("[ ? ] ");
                }
            }
            System.out.println();
        }
    }

    public static void jogar(Scanner scanner, int tamanho, String jogador1, String jogador2, String corJogador1, String corJogador2, String[][] tabuleiro, boolean[][] cartaRevelacao, int pontosJogador1, int pontosJogador2, boolean turnoJogador1) {
        while (true) {
            exibirTabuleiro(tamanho, jogador1, jogador2, corJogador1, corJogador2, tabuleiro, cartaRevelacao, pontosJogador1, pontosJogador2);
            String jogadorAtual = turnoJogador1 ? jogador1 : jogador2;
            System.out.println("Turno de " + jogadorAtual);
            System.out.println("Digite a linha da primeira carta (1 a " + tamanho + "): ");
            int linha1 = scanner.nextInt();
            System.out.println("Digite a coluna da primeira carta (1 a " + tamanho + "): ");
            int col1 = scanner.nextInt();
            linha1--; col1--;
            if (!validarEscolha(linha1, col1, tamanho, cartaRevelacao)) continue;

            cartaRevelacao[linha1][col1] = true;
            exibirTabuleiro(tamanho, jogador1, jogador2, corJogador1, corJogador2, tabuleiro, cartaRevelacao, pontosJogador1, pontosJogador2);

            System.out.println("Digite a linha da segunda carta (1 a " + tamanho + "): ");
            int linha2 = scanner.nextInt();
            System.out.println("Digite a coluna da segunda carta (1 a " + tamanho + "): ");
            int col2 = scanner.nextInt();
            linha2--; col2--;
            if (!validarEscolha(linha2, col2, tamanho, cartaRevelacao) || (linha1 == linha2 && col1 == col2)) {
                cartaRevelacao[linha1][col1] = false;
                System.out.println("Escolha inválida! Tente novamente.");
                continue;
            }

            cartaRevelacao[linha2][col2] = true;
            exibirTabuleiro(tamanho, jogador1, jogador2, corJogador1, corJogador2, tabuleiro, cartaRevelacao, pontosJogador1, pontosJogador2);

            String corCarta = "" + tabuleiro[linha1][col1].charAt(1);
            String corAtual = turnoJogador1 ? corJogador1 : corJogador2;
            String corAdversario = turnoJogador1 ? corJogador2 : corJogador1;

            if (tabuleiro[linha1][col1].equals(tabuleiro[linha2][col2])) {
                System.out.println("Par encontrado!");
                if (corCarta.equals("Y")) {
                    if (turnoJogador1) pontosJogador1 += 1;
                    else pontosJogador2 += 1;
                    System.out.println("Par amarelo! +1 ponto");
                } else if (corCarta.equals("K")) {
                    if (turnoJogador1) pontosJogador1 += 50;
                    else pontosJogador2 += 50;
                    System.out.println("Par preto! +50 pontos");
                } else if (corCarta.equals(corAtual)) {
                    if (turnoJogador1) pontosJogador1 += 5;
                    else pontosJogador2 += 5;
                    System.out.println("Par da sua cor! +5 pontos");
                } else if (corCarta.equals(corAdversario)) {
                    if (turnoJogador1) pontosJogador1 += 1;
                    else pontosJogador2 += 1;
                    System.out.println("Par da cor do adversário! +1 ponto");
                }
            } else {
                System.out.println("Não é um par. Cartas serão escondidas novamente.");
                if (corCarta.equals("K")) {
                    if (turnoJogador1) pontosJogador1 -= 50;
                    else pontosJogador2 -= 50;
                    System.out.println("Erro em par preto! -50 pontos");
                } else if (corCarta.equals(corAdversario)) {
                    if (turnoJogador1) pontosJogador1 -= 2;
                    else pontosJogador2 -= 2;
                    System.out.println("Erro em par da cor do adversário! -2 pontos");
                }
                pontosJogador1 = Math.max(0, pontosJogador1);
                pontosJogador2 = Math.max(0, pontosJogador2);
                cartaRevelacao[linha1][col1] = false;
                cartaRevelacao[linha2][col2] = false;
            }

            turnoJogador1 = !turnoJogador1;
            scanner.nextLine();
            try { Thread.sleep(2000); } catch (Exception e) {}
            if (jogoAcabou(tamanho, cartaRevelacao)) {
                exibirResultadoFinal(tamanho, jogador1, jogador2, corJogador1, corJogador2, tabuleiro, cartaRevelacao, pontosJogador1, pontosJogador2);
                break;
            }
        }
    }

    public static boolean validarEscolha(int linha, int col, int tamanho, boolean[][] cartaRevelacao) {
        if (linha < 0 || linha >= tamanho || col < 0 || col >= tamanho) {
            System.out.println("Coordenadas fora do tabuleiro! Escolha de 1 a " + tamanho + ".");
            return false;
        }
        if (cartaRevelacao[linha][col]) {
            System.out.println("Essa carta já foi revelada!");
            return false;
        }
        return true;
    }

    public static boolean jogoAcabou(int tamanho, boolean[][] cartaRevelacao) {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (!cartaRevelacao[i][j]) return false;
            }
        }
        return true;
    }

    public static void exibirPontuacoes(String jogador1, String jogador2, String corJogador1, String corJogador2, int pontosJogador1, int pontosJogador2) {
        String cubo1 = corJogador1.equals("R") ? "\u001B[31m■\u001B[0m" : "\u001B[34m■\u001B[0m";
        String cubo2 = corJogador2.equals("R") ? "\u001B[31m■\u001B[0m" : "\u001B[34m■\u001B[0m";
        System.out.println(jogador1 + " " + cubo1 + ": " + pontosJogador1 + " pontos | " +
                jogador2 + " " + cubo2 + ": " + pontosJogador2 + " pontos");
    }

    public static void exibirResultadoFinal(int tamanho, String jogador1, String jogador2, String corJogador1, String corJogador2, String[][] tabuleiro, boolean[][] cartaRevelacao, int pontosJogador1, int pontosJogador2) {
        exibirTabuleiro(tamanho, jogador1, jogador2, corJogador1, corJogador2, tabuleiro, cartaRevelacao, pontosJogador1, pontosJogador2);
        System.out.println("Jogo terminado!");
        exibirPontuacoes(jogador1, jogador2, corJogador1, corJogador2, pontosJogador1, pontosJogador2);
        if (pontosJogador1 > pontosJogador2) {
            System.out.println(jogador1 + " venceu!");
        } else if (pontosJogador2 > pontosJogador1) {
            System.out.println(jogador2 + " venceu!");
        } else {
            System.out.println("Empate!");
        }
    }

    public static void exibirRegras() {
        System.out.println("Regras do jogo:\n" +
                "1. Cada participante tem uma cor (vermelho ou azul).\n" +
                "2. Participantes sem nome terão nomes padrões.\n" +
                "3. Se encontrar um par amarelo, ganha 1 ponto.\n" +
                "4. Se encontrar um par da sua cor, ganha 5 pontos.\n" +
                "5. Se encontrar um par do adversário e errar, perde 2 pontos; se acertar, ganha 1 ponto.\n" +
                "6. Se errar um par preto, perde 50 pontos; se acertar, ganha 50 pontos.\n" +
                "7. Pontuação mínima é zero.\n");
    }
}
