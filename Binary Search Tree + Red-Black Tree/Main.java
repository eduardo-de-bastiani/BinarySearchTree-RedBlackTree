import java.util.Scanner;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        int opt;
        do {
            String msg = """
            \nESCOLHA DE ÁRVORE
            ---------------------------------------
            1 | Árvore Binária de Pesquisa
            2 | Árvore Rubro Negra
            3 | Sair
            ---------------------------------------
            Informe o que deseja fazer :
            """;
            System.out.print(msg);
            opt = scan.nextInt();
            switch (opt) {
                case 1:
                    binaria();
                    break;
                case 2:
                    rubroNegra();
                    break;
                case 3:
                    System.out.println("Saindo");
                    break;
                default:
                    System.out.println("Informe uma opção válida!");
                    break;
            }

        } while (opt != 3);
        scan.close();
    }

    public static void binaria() {
        BinarySearchTree tree = new BinarySearchTree();
        int value, op;
        do {
            String msg = """
            \nÁRVORE BINÁRIA DE PESQUISA
            ---------------------------------------
            1 | Adicionar nodo
            2 | Remover nodo
            3 | Pesquisar nodo
            4 | Exibir a árvore
            5 | Mostrar informações
            6 | Mostrar caminhamentos
            7 | Soma de valores entre nodos
            8 | Esvaziar árvore
            9 | Sair do programa
            ---------------------------------------
            Informe o que deseja fazer :
            """;
            System.out.print(msg);
            op = scan.nextInt();

            switch (op) {
                case 1 -> {
                    System.out.println("Informe um valor inteiro:");
                    value = scan.nextInt();
                    tree.add(value);
                }
                case 2 -> {
                    System.out.println("Informe um valor inteiro:");
                    value = scan.nextInt();
                    if (!tree.remove(value))
                        System.out.println("Valor não encontrado!");
                }
                case 3 -> {
                    System.out.println("Informe um valor inteiro:");
                    value = scan.nextInt();
                    if (tree.contains(value) != null)
                        System.out.print("Valor encontrado!");
                    else
                    System.out.println("Valor não encontrado!");
                }
                case 4 -> {
                    tree.printTree();
                }
                case 5 -> {
                    tree.treeInfo();
                }
                case 6 -> {
                    tree.caminhamentos();
                }
                case 7 -> {
                    System.out.println("Informe o valor inicial: ");
                    int x = scan.nextInt();

                    System.out.println("Informe o valor final: ");
                    int y = scan.nextInt();

                    System.out.print("Soma total entre " + x + " e " + y + " = " + tree.sumBetween(x, y));
                }
                case 8 -> {
                    tree.clearTree();
                }
                case 9 -> {
                    System.out.println("Saindo");
                    break;
                }
                default -> {
                    System.out.println("Informe uma opção válida!");
                    break;
                }
            }
        } while (op != 9);
    }

    public static void rubroNegra() {
        RedBlackTree RBtree = new RedBlackTree();
        RBtree.clear();

        //DEMONSTRACAO ARVORE RUBRO-NEGRA
        //incluindo números 1 a 9
        for (int i = 1; i <= 9; i++) {
            RBtree.add(i);
        }

        //visualização da árvore
        System.out.println("Visualização da Árvore:");
        System.out.println(RBtree.visualizeTree());

        // Altura da árvore
        System.out.println("\nAltura da Árvore: " + RBtree.height());

        // Limpando o conteúdo
        RBtree.clear();

        // Incluindo os números 9 a 1 (ao contrario)
        for (int i = 9; i >= 1; i--) {
            RBtree.add(i);
        }

        System.out.println("Visualização da Árvore (ao contrario): ");
        System.out.println(RBtree.visualizeTree());

        //caminho central
        System.out.println("\nConteúdo da Árvore (Caminhamento Central - inOrder):");
        System.out.println(RBtree.inOrder());
    }
}