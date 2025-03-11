import java.util.*;

public class MangaRosaMemoryGame {
    static int opcao;
    static int escolha;
    static int tamanho = 0;
    static String jogador1 = "PARTICIPANTE1";
    static String jogador2 = "PARTICIPANTE2";
    static String corJogador1;
    static String corJogador2;
    static String[][] tabuleiro;
    static boolean[][] revelado;
    static int pontosJogador1 = 0;
    static int pontosJogador2 = 0;
    static boolean turnoJogador1 = true;

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Jogo da Memória Manga Rosa!!");
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("1 - Iniciar o jogo");
            System.out.println("2 - Ver pontuações");
            System.out.println("3 - Regras do jogo");
            System.out.println("4 - Sair");
            System.out.println("Digite a sua opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    iniciarJogo();
                    break;
                case 2:
                    exibirPontuacoes();
                    break;
                case 3:
                    exibirRegras();
                    break;
            }
        } while (opcao != 4);
        System.out.println("Obrigado por jogar, volte sempre!!");
    }

    public static void escolherTamanhoTabuleiro() {
        Scanner scanner = new Scanner(System.in);
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
    }

    public static void iniciarJogo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do jogador 1: ");
        jogador1 = scanner.nextLine();
        System.out.println("Digite o nome do jogador 2: ");
        jogador2 = scanner.nextLine();
        escolherTamanhoTabuleiro();
        if (tamanho == 0) return;
        
        Random rand = new Random();
        if (rand.nextBoolean()) {
            corJogador1 = "R";
            corJogador2 = "B";
        } else {
            corJogador1 = "B";
            corJogador2 = "R";
        }
        System.out.println(jogador1 + " é " + (corJogador1.equals("R") ? "vermelho" : "azul"));
        System.out.println(jogador2 + " é " + (corJogador2.equals("R") ? "vermelho" : "azul"));

        distribuirCartas();
        revelado = new boolean[tamanho][tamanho];
        pontosJogador1 = 0;
        pontosJogador2 = 0;
        jogar();
    }

    public static void distribuirCartas() {
        int numPares = (tamanho * tamanho) / 2;
        int vermelhoAzul = numPares / 2;
        int vermelho = vermelhoAzul / 2;
        int azul = vermelhoAzul - vermelho;
        int amarelo = numPares - vermelhoAzul - 1;
        int preto = 1;

        String[] simbolos = {"♥", "★", "▲", "◆", "■"};
        String[] cartas = new String[tamanho * tamanho];
        int index = 0;
        Random rand = new Random();

        for (int i = 0; i < vermelho; i++) {
            String simbolo = simbolos[rand.nextInt(simbolos.length)];
            cartas[index++] = simbolo + "R";
        }
        for (int i = 0; i < azul; i++) {
            String simbolo = simbolos[rand.nextInt(simbolos.length)];
            cartas[index++] = simbolo + "B";
        }
        for (int i = 0; i < amarelo; i++) {
            String simbolo = simbolos[rand.nextInt(simbolos.length)];
            cartas[index++] = simbolo + "Y";
        }
        for (int i = 0; i < preto; i++) {
            String simbolo = simbolos[rand.nextInt(simbolos.length)];
            cartas[index++] = simbolo + "K";
        }

        for (int i = 0; i < numPares; i++) {
            cartas[index + i] = cartas[i];
        }

        for (int i = cartas.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            String temp = cartas[i];
            cartas[i] = cartas[j];
            cartas[j] = temp;
        }

        tabuleiro = new String[tamanho][tamanho];
        index = 0;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                tabuleiro[i][j] = cartas[index++];
            }
        }
    }

    public static void exibirTabuleiro() {
        exibirPontuacoes();
        System.out.println("Tabuleiro:");
        System.out.print("   ");
        for (int j = 1; j <= tamanho; j++) {
            System.out.print(" " + j + (j < 10 ? "  " : " "));
        }
        System.out.println();
        for (int i = 0; i < tamanho; i++) {
            System.out.print((i + 1) + (i + 1 < 10 ? " " : ""));
            for (int j = 0; j < tamanho; j++) {
                if (revelado[i][j]) {
                    String combinacao = tabuleiro[i][j];
                    String simbolo = combinacao.substring(0, 1);
                    String cor = combinacao.substring(1);
                    String corANSI = switch (cor) {
                        case "R" -> "\u001B[31m";
                        case "B" -> "\u001B[34m";
                        case "Y" -> "\u001B[33m";
                        case "K" -> "\u001B[30m";
                        default -> "\u001B[0m";
                    };
                    System.out.print(corANSI + "[ " + simbolo + " ] " + "\u001B[0m");
                } else {
                    System.out.print("[ ? ] ");
                }
            }
            System.out.println();
        }
    }

    public static void jogar() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            exibirTabuleiro();
            String jogadorAtual = turnoJogador1 ? jogador1 : jogador2;
            System.out.println("Turno de " + jogadorAtual);
            System.out.println("Digite a linha da primeira carta (1 a " + tamanho + "): ");
            int linha1 = scanner.nextInt();
            System.out.println("Digite a coluna da primeira carta (1 a " + tamanho + "): ");
            int col1 = scanner.nextInt();
            linha1--; col1--;
            if (!validarEscolha(linha1, col1)) continue;

            revelado[linha1][col1] = true;
            exibirTabuleiro();

            System.out.println("Digite a linha da segunda carta (1 a " + tamanho + "): ");
            int linha2 = scanner.nextInt();
            System.out.println("Digite a coluna da segunda carta (1 a " + tamanho + "): ");
            int col2 = scanner.nextInt();
            linha2--; col2--;
            if (!validarEscolha(linha2, col2) || (linha1 == linha2 && col1 == col2)) {
                revelado[linha1][col1] = false;
                System.out.println("Escolha inválida! Tente novamente.");
                continue;
            }

            revelado[linha2][col2] = true;
            exibirTabuleiro();

            String corCarta = tabuleiro[linha1][col1].substring(1);
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
                revelado[linha1][col1] = false;
                revelado[linha2][col2] = false;
            }

            turnoJogador1 = !turnoJogador1;
            scanner.nextLine();
            try { Thread.sleep(2000); } catch (Exception e) {}
            if (jogoAcabou()) break;
        }
        exibirResultadoFinal();
    }

    public static boolean validarEscolha(int linha, int col) {
        if (linha < 0 || linha >= tamanho || col < 0 || col >= tamanho) {
            System.out.println("Coordenadas fora do tabuleiro! Escolha de 1 a " + tamanho + ".");
            return false;
        }
        if (revelado[linha][col]) {
            System.out.println("Essa carta já foi revelada!");
            return false;
        }
        return true;
    }

    public static boolean jogoAcabou() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (!revelado[i][j]) return false;
            }
        }
        return true;
    }

    public static void exibirPontuacoes() {
        String cubo1 = corJogador1.equals("R") ? "\u001B[31m■\u001B[0m" : "\u001B[34m■\u001B[0m";
        String cubo2 = corJogador2.equals("R") ? "\u001B[31m■\u001B[0m" : "\u001B[34m■\u001B[0m";
        System.out.println(jogador1 + " " + cubo1 + ": " + pontosJogador1 + " pontos | " +
                         jogador2 + " " + cubo2 + ": " + pontosJogador2 + " pontos");
    }

    public static void exibirResultadoFinal() {
        exibirTabuleiro();
        System.out.println("Jogo terminado!");
        exibirPontuacoes();
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
