public class RedBlackTree {
	private TreeFormatter treeFormatter;
	private Node root;
	private Node nill;

	public RedBlackTree() {
		treeFormatter = new TreeFormatter();
		nill = new Node();
		nill.color = 0;
		nill.left = null;
		nill.right = null;
		root = nill;
	}

	public int size() {
		return calculateSize(root);
	}
	
	private int calculateSize(Node node) {
		if (node == nill) {
			return 0;
		} else {
			return calculateSize(node.left) + calculateSize(node.right) + 1;
		}
	}
	
	public boolean isEmpty() {
		return root == nill;
	}

	private void preOrderHelper(Node node) {
		if (node != nill) {
			System.out.print(node.data + " ");
			preOrderHelper(node.left);
			preOrderHelper(node.right);
		} 
	}

	private void inOrderHelper(Node node) {
		if (node != nill) {
			inOrderHelper(node.left);
			System.out.print(node.data + " ");
			inOrderHelper(node.right);
		} 
	}

	private void postOrderHelper(Node node) {
		if (node != nill) {
			postOrderHelper(node.left);
			postOrderHelper(node.right);
			System.out.print(node.data + " ");
		} 
	}

	private Node searchTreeHelper(Node node, int key) {
		if (node == nill || key == node.data) {
			return node;
		}

		if (key < node.data) {
			return searchTreeHelper(node.left, key);
		} 
		return searchTreeHelper(node.right, key);
	}

	// fix the rb tree modified by the delete operation
	private void fixDelete(Node x) {
		Node s;
		while (x != root && x.color == 0) {
			if (x == x.parent.left) {
				s = x.parent.right;
				if (s.color == 1) {
					// case 3.1
					s.color = 0;
					x.parent.color = 1;
					leftRotate(x.parent);
					s = x.parent.right;
				}

				if (s.left.color == 0 && s.right.color == 0) {
					// case 3.2
					s.color = 1;
					x = x.parent;
				} else {
					if (s.right.color == 0) {
						// case 3.3
						s.left.color = 0;
						s.color = 1;
						rightRotate(s);
						s = x.parent.right;
					} 

					// case 3.4
					s.color = x.parent.color;
					x.parent.color = 0;
					s.right.color = 0;
					leftRotate(x.parent);
					x = root;
				}
			} else {
				s = x.parent.left;
				if (s.color == 1) {
					// case 3.1
					s.color = 0;
					x.parent.color = 1;
					rightRotate(x.parent);
					s = x.parent.left;
				}

				if (s.right.color == 0 && s.right.color == 0) {
					// case 3.2
					s.color = 1;
					x = x.parent;
				} else {
					if (s.left.color == 0) {
						// case 3.3
						s.right.color = 0;
						s.color = 1;
						leftRotate(s);
						s = x.parent.left;
					} 

					// case 3.4
					s.color = x.parent.color;
					x.parent.color = 0;
					s.left.color = 0;
					rightRotate(x.parent);
					x = root;
				}
			} 
		}
		x.color = 0;
	}


	private void rbTransplant(Node u, Node v){
		if (u.parent == null) {
			root = v;
		} else if (u == u.parent.left){
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}
		v.parent = u.parent;
	}

	private void deleteNodeHelper(Node node, int key) {
		// find the node containing key
		Node z = nill;
		Node x, y;
		while (node != nill){
			if (node.data == key) {
				z = node;
			}

			if (node.data <= key) {
				node = node.right;
			} else {
				node = node.left;
			}
		}

		if (z == nill) {
			System.out.println("Couldn't find key in the tree");
			return;
		} 

		y = z;
		int yOriginalColor = y.color;
		if (z.left == nill) {
			x = z.right;
			rbTransplant(z, z.right);
		} else if (z.right == nill) {
			x = z.left;
			rbTransplant(z, z.left);
		} else {
			y = minimum(z.right);
			yOriginalColor = y.color;
			x = y.right;
			if (y.parent == z) {
				x.parent = y;
			} else {
				rbTransplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}

			rbTransplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}
		if (yOriginalColor == 0){
			fixDelete(x);
		}
	}
	
	// fix the red-black tree
	private void fixInsert(Node k){
		Node u;
		while (k.parent.color == 1) {
			if (k.parent == k.parent.parent.right) {
				u = k.parent.parent.left; // uncle
				if (u.color == 1) {
					// case 3.1
					u.color = 0;
					k.parent.color = 0;
					k.parent.parent.color = 1;
					k = k.parent.parent;
				} else {
					if (k == k.parent.left) {
						// case 3.2.2
						k = k.parent;
						rightRotate(k);
					}
					// case 3.2.1
					k.parent.color = 0;
					k.parent.parent.color = 1;
					leftRotate(k.parent.parent);
				}
			} else {
				u = k.parent.parent.right; // uncle

				if (u.color == 1) {
					// mirror case 3.1
					u.color = 0;
					k.parent.color = 0;
					k.parent.parent.color = 1;
					k = k.parent.parent;	
				} else {
					if (k == k.parent.right) {
						// mirror case 3.2.2
						k = k.parent;
						leftRotate(k);
					}
					// mirror case 3.2.1
					k.parent.color = 0;
					k.parent.parent.color = 1;
					rightRotate(k.parent.parent);
				}
			}
			if (k == root) {
				break;
			}
		}
		root.color = 0;
	}

	private void printHelper(Node root, String indent, boolean last) {
		// print the tree structure on the screen
	   	if (root != nill) {
		   System.out.print(indent);
		   if (last) {
		      System.out.print("R----");
		      indent += "     ";
		   } else {
		      System.out.print("L----");
		      indent += "|    ";
		   }
            
           String sColor = root.color == 1?"RED":"BLACK";
		   System.out.println(root.data + "(" + sColor + ")");
		   printHelper(root.left, indent, false);
		   printHelper(root.right, indent, true);
		}
	}

	// Pre-Order traversal
	// Node.Left Subtree.Right Subtree
	public void preorder() {
		preOrderHelper(this.root);
	}

	// In-Order traversal
	// Left Subtree . Node . Right Subtree
	public boolean inOrder() {
		inOrderHelper(this.root);
		return false;
	}

	// Post-Order traversal
	// Left Subtree . Right Subtree . Node
	public void postorder() {
		postOrderHelper(this.root);
	}

	// search the tree for the key k
	// and return the corresponding node
	public Node searchTree(int k) {
		return searchTreeHelper(this.root, k);
	}

	// find the node with the minimum key
	public Node minimum(Node node) {
		while (node.left != nill) {
			node = node.left;
		}
		return node;
	}

	// find the node with the maximum key
	public Node maximum(Node node) {
		while (node.right != nill) {
			node = node.right;
		}
		return node;
	}

	// find the successor of a given node
	public Node successor(Node x) {
		// if the right subtree is not null,
		// the successor is the leftmost node in the
		// right subtree
		if (x.right != nill) {
			return minimum(x.right);
		}

		// else it is the lowest ancestor of x whose
		// left child is also an ancestor of x.
		Node y = x.parent;
		while (y != nill && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	// find the predecessor of a given node
	public Node predecessor(Node x) {
		// if the left subtree is not null,
		// the predecessor is the rightmost node in the 
		// left subtree
		if (x.left != nill) {
			return maximum(x.left);
		}

		Node y = x.parent;
		while (y != nill && x == y.left) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	// rotate left at node x
	public void leftRotate(Node x) {
		Node y = x.right;
		x.right = y.left;
		if (y.left != nill) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
	}

	// rotate right at node x
	public void rightRotate(Node x) {
		Node y = x.left;
		x.left = y.right;
		if (y.right != nill) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;
	}


	private int calculateHeight(Node node) {
		if (node == nill) {
			return 0; // Retorna 0 se o nó for nulo (folha)
		} else {
			// Calcula a altura recursivamente para a subárvore esquerda e direita
			int leftHeight = calculateHeight(node.left);
			int rightHeight = calculateHeight(node.right);

			// Retorna a altura máxima entre a subárvore esquerda e direita + 1 (considerando o nó atual)
			return Math.max(leftHeight, rightHeight) + 1;
		}
	}

	// Método público para calcular a altura da árvore vermelho-preto
	public int height() {
		// Começa o cálculo da altura a partir da raiz da árvore
		return calculateHeight(root)-1;
	}


	public void clear() {
		root = nill; // Define a raiz como o nó nulo, efetivamente limpando a árvore.
	}

	// insert the key to the tree in its appropriate position
	// and fix the tree
	public void add(int key) {
		Node node = new Node();
		node.parent = null;
		node.data = key;
		node.left = nill;
		node.right = nill;
		node.color = 1; // Novo nodo deve ser vermelho

		Node y = null;
		Node x = this.root;

		while (x != nill) {
			y = x;
			if (node.data < x.data) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		// y é o pai de x
		node.parent = y;
		if (y == null) {
			root = node;
		} else if (node.data < y.data) {
			y.left = node;
		} else {
			y.right = node;
		}

		// Se o novo nodo é o nodo raiz, apenas retorne
		if (node.parent == null){
			node.color = 0;
			return;
		}

		// Se o avô é nulo, apenas retorne
		if (node.parent.parent == null) {
			return;
		}

		// Corrija a árvore
		fixInsert(node);
	}


	public Node getRoot(){
		return this.root;
	}

	// delete the node from the tree
	public void deleteNode(int data) {
		deleteNodeHelper(this.root, data);
	}

	public Node getNill() {
		return this.nill;
	}

	public String visualizeTree() {
		return treeFormatter.topDownRedBlackTree(root, this); // Passando a instância de RedBlackTree
	}
	

}