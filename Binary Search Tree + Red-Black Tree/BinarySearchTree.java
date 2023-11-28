class BinarySearchTree {
    private Node root;
    private int count;

    public void add(Integer v) {
        Node prev, current;
        Node node = new Node();
        node.data = v; // Modificado: atribuição ao campo 'data' em vez de 'element'
        node.right = null;
        node.left = null;

        if (root == null) {
            root = node;
        } else {
            current = root;
            while(true) {
                prev = current;
                if (v <= current.data) { // Modificado: referência ao campo 'data' em vez de 'element'
                    current = current.left;
                    if (current == null) {
                        prev.left = node;
                        return;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        prev.right = node;
                        return;
                    }
                }
            }
        }
    }

    public Node contains(Integer v) {
        if (root == null)
            return null;

        Node current = root;
        while (current.data != v) { // Modificado: referência ao campo 'data' em vez de 'element'
            if (v < current.data)
                current = current.left;
            else
                current = current.right;

            if (current == null)
                return null;
        }

        return current;
    }



    public boolean remove(Integer v) {
        // se arvore vazia
        if (root == null)
            return false;

        Node current = root;
        Node father = root;
        boolean child_left = true;

        // buscando o valor
        while(current.data != v) {
            // enquanto nao encontrou
            father = current;
            // caminha para esquerda
            if (v < current.data) {
                current = current.left;
                // é filho a esquerda? sim
                child_left = true;
            }
            // caminha para direita
            else {
                current = current.right;
                // é filho a esquerda? NAO
                child_left = false;
            }
            // encontrou uma folha -> sai
            if (current == null)
                return false;
        }
        // Se nao possui nenhum filho (é uma folha), elimine-o
        if (current.left == null && current.right == null) {
            // se raiz
            if (current == root)
                root = null;
            // se for filho a esquerda do pai
            else if (child_left)
                father.left = null;
            // se for filho a direita do pai
            else
                father.right = null;
        }
        // Se é pai e nao possui um filho a direita, substitui pela subarvore a direita
        else if (current.right == null) {
            // se raiz
            if (current == root)
                root = current.left;
            // se for filho a esquerda do pai
            else if (child_left)
                father.left = current.left;
            // se for filho a direita do pai
            else
                father.right = current.left;
        }
        // Se é pai e nao possui um filho a esquerda, substitui pela subarvore a esquerda
        else if (current.left == null) {
            // se raiz
            if (current == root)
                root = current.right;
            // se for filho a esquerda do pai
            else if (child_left)
                father.left = current.right;
            // se for  filho a direita do pai
            else
                father.right = current.right;
        }
        // Se possui mais de um filho, se for um avô ou outro grau maior de parentesco
        else {
            Node successor = node_successor(current);
            // Usando sucessor que seria o Nó mais a esquerda da subarvore a direita do No que deseja-se remover
            // se raiz
            if (current == root)
                root = successor;
            // se for filho a esquerda do pai
            else if (child_left)
                father.left = successor;
            // se for filho a direita do pai
            else
                father.right = successor;
            // acertando o ponteiro a esquerda do sucessor agora que ele assumiu
            successor.left = current.left;
            // a posição correta na arvore
        }
        return true;
    }

    // O sucessor é o nodo mais a esquerda da subarvore a direita do nodo que foi passado como parâmetro do método
    public Node node_successor(Node node) {
        Node father_successor = node;
        Node successor = node;
        Node current = node.left;

        // enquanto nao chegar no nodo mais a esquerda
        while (current != null) {
            father_successor = successor;
            successor = current;
            // caminha para a esquerda
            current = current.left;
        }
        // se sucessor nao é o filho a direita do Nó que deverá ser eliminado
        if (successor != node.right) {
            // pai herda os filhos do sucessor que sempre serão a direita
            father_successor.left = successor.right;
            successor.right = node.right;
        }
        return successor;
    }

    void clearTree() {
        root = null;
    }

    public void inOrder(Node current) {
        if (current != null) {
            inOrder(current.left);
            System.out.print(current.data + " ");
            inOrder(current.right);
        }
    }

    public void preOrder(Node current) {
        if (current != null) {
            System.out.print(current.data + " ");
            preOrder(current.left);
            preOrder(current.right);
        }
    }

    public void posOrder(Node current) {
        if (current != null) {
            posOrder(current.left);
            posOrder(current.right);
            System.out.print(current.data + " ");
        }
    }

    public int height(Node current) {
        if (current == null || (current.left == null && current.right == null)) {
            return 0;
        } else {
            if (height(current.left) > height(current.right))
                return (1 + height(current.left));
            else
                return (1 + height(current.right));
        }
    }

    public int countNodes(Node current) {
        if (current == null)
            return 0;
        else
            return (1 + countNodes(current.left) + countNodes(current.right));
    }

    public Node getRoot() {
        return root;
    }

    public void caminhamentos() {
        System.out.print("\n Caminhamento Central ou Em or (Interfixado): ");
        inOrder(root);
        System.out.print("\n Exibindo em Pós-ordem (Pós-fixado): ");
        posOrder(root);
        System.out.print("\n Exibindo em Pré-ordem (Pré-fixado): ");
        preOrder(root);
    }

    public void treeInfo() {
        System.out.println("Altura da arvore: " + height(root));
        System.out.println("Quantidade de Nós: " + countNodes(root));
        if (root != null) {
            System.out.println("Valor minimo: " + minNode());
            System.out.println("Valor maximo: " + maxNode());
        }
        System.out.println("Quantidade de folhas: " + countLeaves());
    }

    public void printTree() {
        if(root != null) {
            TreeFormatterBT formatter = new TreeFormatterBT();
            System.out.println(formatter.topDown(root));
        } else {
            System.out.println("Árvore vazia!");
        }
    }


    // Atividade 1a - Achar o menor
    public int minNode() {
        int menor = 0;
        Node current = root;
        while (true) {
            current = current.left;
            menor = current.data;

            if (current.left == null)
                return menor;
        }
    }

    // Atividade 1b - Achar o maior
    public int maxNode() {
        int maior = 0;
        Node current = root;
        while (true) {
            current = current.right;
            maior = current.data;

            if (current.right == null)
                return maior;
        }
    }

    // Atividade 1c - Contador de folhas
    public int countLeaves() {
        count = 0;
        countLeavesAux(root);
        return count;
    }

    public void countLeavesAux(Node current) {
        if (current != null) {
            countLeavesAux(current.left);
            countLeavesAux(current.right);
            if(current.right == null && current.left == null)
                count++;
        }
    }

    // Atividade 1d - Soma entre nodos
    public int sumBetween(int start, int end) {
        int soma = 0;
        Node current = root;

        while (current.data != start) {
            if (start < current.data)
                current = current.left;
            else
                current = current.right;
        }

        while (current.data != end) {
            if (end < current.data) {
                current = current.left;
                if (current.data == end)
                    break;
                soma += current.data;
            }
            else {
                current = current.right;
                    if (current.data == end)
                        break;
                soma += current.data;
            }
        }

        return soma;
    }

}