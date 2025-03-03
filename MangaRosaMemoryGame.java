package games.mangarosa.com.br;

import java.util.*;

public class MangaRosaMemoryGame{
static int opcao;
static Scanner scanner = new Scanner(System.in);
static int escolha;
static int tamanho = 0;
static String jogador1 = "PARTICIPANTE1";
static String jogador2 = "PARTICIPANTE2";

public static void main(String[] args){

    System.out.println("Bem-vindo ao Jogo da Memoria Manga Rosa!!");
    System.out.println("Escolha uma opção para continuar:");

    do {
        System.out.println("1 - Iniciar o jogo");
        System.out.println("2 - Ver pontuações");
        System.out.println("3 - Regras do jogo");
        System.out.println("4 - Sair");

        System.out.println("Digite a sua opcao: ");

        opcao = scanner.nextInt();
        switch(opcao){
            case 1:
                iniciarJogo();
                break;
            case 2:
                System.out.println("teste 2");
                break;
            case 3:
                System.out.println("Regras do jogo:\n" +
                        "1. Cada participante deve ter atribuído a si uma cor (vermelho ou azul) no início do jogo.\n" +
                        "2. Todo participante deve ter um nome registrado. Senão, o nome padrão\n" +
                        "“PARTICIPANTE01” e “PARTICIPANTE02” devem ser atribuídos a cada um das(os)\n" +
                        "participantes.\n" +
                        "3. Cada participante possui uma pontuação atrelada a si.\n" +
                        "4. Se a(o) participante encontrar um par de cartas com fundo amarelo, fatura 1\n" +
                        "ponto.\n" +
                        "5. Se a(o) participante encontrar um par de cartas com o fundo da sua cor, fatura 5\n" +
                        "pontos.\n" +
                        "6. Se a(o) participante encontrar um par de cartas com o fundo da cor de seu\n" +
                        "adversário e errar, perde 2 pontos. Porém, se acertar, ganha apenas 1 ponto. Essa\n" +
                        "regra deve ser aplicada a cor da primeira carta virada na rodada.\n" +
                        "7. A(o) participante não pode ter pontuação negativa. Se ela(ele) perder mais\n" +
                        "pontos do que possui, ficará com a pontuação zerada.\n" +
                        "8. Se a(o) participante encontrar uma carta com o fundo preto e errar o seu par,\n" +
                        "perde 50 pontos, mas se acertar, ganha os 50 pontos.\n\n" +
                        "Tenha um bom jogo!!\n\n");
                break;
        }
    }
    while(opcao != 4);
    System.out.println("Obrigado por jogar, volte sempre!!");

    }

    public static void escolherTamanhoTabuleiro(){

        System.out.println("Para começar, escolha o tamanho do tabuleiro (digite 1, 2, 3 ou 4. Para voltar ao inicio, digite 0: )");

    do {System.out.println("1 - 4x4");
        System.out.println("2 - 6x6");
        System.out.println("3 - 8x8");
        System.out.println("4 - 10x10");

        System.out.println("Digite a sua opcao: ");
        escolha = scanner.nextInt();
        switch(escolha){
            case 1:
                tamanho = 4;
                break;
            case 2:
                tamanho = 6;
                break;
            case 3:
                tamanho = 8;
                break;
            case 4:
                tamanho = 10;
                break;
        }
    } while(escolha != 0);

    }


    public static void iniciarJogo(){

        System.out.println("Digite o nome do jogador 1: ");
        jogador1 = scanner.nextLine();
        System.out.println("Digite o nome do jogador 2: ");
        jogador2 = scanner.nextLine();

        escolherTamanhoTabuleiro();

    }

}

