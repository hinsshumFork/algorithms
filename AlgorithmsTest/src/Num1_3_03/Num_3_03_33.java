package Num1_3_03;

/**
 * P289 T32 T33
 * 
 * @author he
 *
 */
class BSTBR<Key extends Comparable<Key>, Value> {
	private final static boolean RED = true;
	private final static boolean BLACK = false;

	private Node root;

	private class Node {
		Key key;
		Value value;
		Node left, right;
		int N;
		boolean color;

		public Node(Key key, Value value, int N, boolean color) {
			this.key = key;
			this.value = value;
			this.N = N;
			this.color = color;
		}
	}

	public int size() {
		return size(root);
	}

	private int size(Node x) {
		if (x == null)
			return 0;
		else
			return x.N;
	}

	// ????ת
	private Node rotateLeft(Node x) {
		Node t = x.right;
		x.right = t.left;
		t.left = x;

		t.color = x.color;
		x.color = RED;

		t.N = x.N;
		x.N = 1 + size(x.left) + size(x.right);

		return t;
	}

	private Node rotateRight(Node x) {
		Node t = x.left;
		x.left = t.right;
		t.right = x;

		t.color = x.color;
		x.color = RED;

		t.N = x.N;
		x.N = 1 + size(x.left) + size(x.right);

		return t;

	}

	private boolean isRed(Node x) {
		if (x == null)
			return false;
		return x.color == RED;
	}

	private void flipColors(Node h) {
		h.color = !h.color;
		h.left.color = !h.left.color;
		h.right.color = !h.right.color;

	}

	private Node min(Node x) {
		if (x.left == null)
			return x;
		return min(x.left);
	}

	public void put(Key key, Value value) {
		root = put(root, key, value);
		root.color = BLACK;
	}

	private Node put(Node x, Key key, Value value) {
		if (x == null) {
			return new Node(key, value, 1, RED);
		}

		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			x.left = put(x.left, key, value);
		else if (cmp > 0)
			x.right = put(x.right, key, value);
		else {
			x.value = value;
		}

		if (isRed(x.right) && !isRed(x.left))
			x = rotateLeft(x);
		if (isRed(x.left) && isRed(x.left.left))
			x = rotateRight(x);
		if (isRed(x.left) && isRed(x.right))
			flipColors(x);

		x.N = size(x.left) + size(x.right) + 1;
		return x;

	}

	public Value get(Key key) {
		return get(root, key);
	}

	private Value get(Node x, Key key) {
		if (x == null)
			return null;

		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			return get(x.left, key);
		else if (cmp > 0)
			return get(x.right, key);
		else
			return x.value;
	}

	public boolean is23() {
		return is23(root);
	}

	private boolean is23(Node x) {
		if (x == null)
			return true;
		if (isRed(x.right))
			return false;
		if (x != root && isRed(x) && isRed(x.left))
			return false;
		return is23(x.left) && is23(x.right);
	}

	public boolean isBalanced() {
		int black = 0;// ????????
		Node x = root;
		if (!isRed(x)) {
			black++;
			x = x.left;
		}

		return isBalanced(x, black);

	}

	private boolean isBalanced(Node x, int black) {
		if (x == null)
			return black == 0;
		if (!isRed(x))
			black--;
		return isBalanced(x.left, black) && isBalanced(x.right, black);
	}

}

public class Num_3_03_33 {
	public static void main(String[] args) {
		BSTBR<String, Integer> b = new BSTBR<String, Integer>();
		b.put("A", 1);
		b.put("A", 2);
		b.put("B", 3);
		b.put("C", 4);
		b.put("E", 5);
		b.put("D", 7);
		System.out.println(b.is23());
		System.out.println(b.isBalanced());

	}

}
