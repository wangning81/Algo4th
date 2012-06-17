package binraySearchTree;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import elementary.Stopwatch;

import symbolTables.OrderedSymbolTable;
import symbolTables.OrderedWordFrequencyCounter;

public class BinarySearchTreeST<Key extends Comparable<Key>, Value> implements OrderedSymbolTable<Key, Value> {
	
	protected class Node
	{
		protected Node Left;
		protected Node Right;
		public Value Value;
		public Key Key;
		public int Size;
				
		public Node(Key key, Value value)
		{
			this.Key = key;
			this.Value = value;
			this.Size = 1;
		}

		public void setRight(Node right) {
			Right = right;
		}

		public Node getRight() {
			return Right;
		}

		public void setLeft(Node left) {
			Left = left;
		}

		public Node getLeft() {
			return Left;
		}
	}
	
	protected Node root;
	protected int internalPathLength;
	private Node cachedVisitedNode;
	
	public BinarySearchTreeST()
	{
		
	}
	
	/////////////////////////////////////////////////////////////
	//  Various put methods start from here.
	/////////////////////////////////////////////////////////////
	protected void fixUpSize(Node startNode, Node destNode,int diff)
	{
		//fix down the size;
		Node p = root;
		while(p != destNode)
		{
			p.Size += diff;
			if(p.Key.compareTo(destNode.Key) > 0)
				p = p.getLeft();
			else
				p = p.getRight();
		}
	}
	
	
	protected void putNonRec(Key key, Value value)
	{
		if(key == null) return;
		
		Node p = null;
		Node c = root;
		int depth = 0;
		while(c != null)
		{
			int cmp = key.compareTo(c.Key);
			if(cmp > 0)
			{
				p = c;
				c = c.getRight();
				depth++;
			}
			else if(cmp < 0)
			{
				p = c;
				c = c.getLeft();
				depth++;
			}
			else
			{
				c.Value = value;
				return;
			}
		}
		Node newNode = new Node(key, value);
		if(p == null)
			root = newNode;
		else
		{
			if(key.compareTo(p.Key) > 0)
				p.setRight(newNode);
			else
				p.setLeft(newNode);				
		}
		
		fixUpSize(root, newNode, 1);
		
		this.internalPathLength += depth;	
	}
	
	public void put(Key key, Value value)
	{
		if(cachedVisitedNode != null && key.equals(cachedVisitedNode.Key))
			cachedVisitedNode.Value = value;			
		root = put(root, key, value, 0);
	}
	
	private Node put(Node n, Key key, Value value, int depth)
	{
		if(n == null) 
		{
			//save it to last visited node for caching.
			cachedVisitedNode = new Node(key, value);
			return cachedVisitedNode;
		}
		
		int cmp = n.Key.compareTo(key);
		if(cmp > 0)
		{
			n.setLeft(put(n.getLeft(), key, value, depth + 1));
		}
		else if (cmp < 0)
		{
			n.setRight(put(n.getRight(), key, value, depth + 1));
		}
		else 
		{
			n.Value = value;
		}
		n.Size = sizeOf(n.getLeft()) + sizeOf(n.getRight()) + 1;
		return n;
	}
	////////////////////END OF PUT METHODS/////////////////////////////
	
	/////////////////////////////////////////////////////////////
	//  Various get methods start from here.
	/////////////////////////////////////////////////////////////
	public Value get(Key key)
	{
		if(cachedVisitedNode != null && key.equals(cachedVisitedNode.Key))
			return cachedVisitedNode.Value;
		return get(root, key);
	}
	
	private Value get(Node n, Key key)
	{
		if(n == null) return null;
		int cmp = n.Key.compareTo(key);
		if(cmp > 0) return get(n.getLeft(), key);
		else if(cmp < 0) return get(n.getRight(), key);
		else
		{	
			this.cachedVisitedNode = n;
			return n.Value;
		}
	}
	public boolean contains(Key key)
	{
		return get(key) != null;
	}
	////////////////////END OF GET METHODS/////////////////////////////
	
	/////////////////////////////////////////////////////////////
	//  Various ORDER related methods start from here.
	/////////////////////////////////////////////////////////////
	
	public Key min()
	{
		return min(root);
	}
	
	protected Key min(Node n)
	{
		if(n == null) return null;
		if(n.getLeft() == null) return n.Key;
		return min(n.getLeft());		
	}
	
	public Key max()
	{
		return max(root);
	}
	
	protected Key max(Node n)
	{
		if(n == null) return null;
		if(n.getRight() == null) return n.Key;
		return max(n.getRight());		
	}
	
	public int rank(Key key)
	{
		return rank(root, key);		
	}
	
	protected int rank(Node n, Key key)
	{
		if(n == null) return 0;
		int cmp = key.compareTo(n.Key);
		if(cmp > 0) return sizeOf(n.getLeft()) + 1 + rank(n.getRight(), key);
		else if(cmp < 0) return rank(n.getLeft(), key);
		else return sizeOf(n.getLeft());
	}
	
	public Key select(int k)
	{
		return select(root, k);		
	}
	protected Key select(Node n, int k)
	{
		if(n == null) return null;
		int leftSize = sizeOf(n.getLeft());
		if(leftSize > k)
			return select(n.getLeft(), k);
		else if (leftSize < k)
			return select(n.getRight(), k - leftSize - 1);
		else return n.Key;
	}
	public Key floor(Key k)
	{
		return floor(root, k);
	}
	protected Key floor(Node n, Key k)
	{
		if(n == null) return null;
		int cmp = n.Key.compareTo(k);
		if(cmp > 0) return floor(n.getLeft(), k);
		else if(cmp == 0) return n.Key;
		else
		{
			Key t = floor(n.getRight(), k);
			return t == null ? n.Key : t;
		}
	}
	public Key ceiling(Key k)
	{
		return ceiling(root, k);		
	}
	
	protected Key ceiling(Node n, Key k)
	{
		if(n == null) return null;
		int cmp = n.Key.compareTo(k);
		if(cmp == 0) return n.Key;
		else if(cmp < 0) return ceiling(n, k);
		else 
		{
			Key t = ceiling(n.getLeft(), k);
			return t == null ? n.Key : t;
		}
	}
	
	protected Node minNode(Node n)
	{
		Node p = n;
		while(p.getLeft() != null)
			p = p.getLeft();
		return p;
	}
	
	public Key minNonRec()
	{
		if(root == null) return null;
		Node n = root;
		while(n.getLeft() != null)
			 n = n.getLeft();
		return n.Key;
	}
	
	public Key maxNonRec()
	{
		if(root == null) return null;
		Node n = root;
		while(n.getRight() != null)
			 n = n.getRight();
		return n.Key;
	}
	
	public Key floorNonRec(Key k)
	{
		if(root == null || k == null) return null;
		Node n = root;
		Key ret = null;
		while(n != null)
		{
			int cmp = k.compareTo(n.Key);
			if(cmp == 0)
				return n.Key;
			else if (cmp < 0)
				n = n.getLeft();
			else
			{
				ret = n.Key;
				n = n.getRight();
			}
		}
		return ret;
	}
	
	public Key ceilingNonRec(Key k)
	{
		if(root == null || k == null) return null;
		Node n = root;
		Key ret = null;
		while(n != null)
		{
			int cmp = k.compareTo(n.Key);
			if(cmp == 0)
				return n.Key;
			else if (cmp > 0)
				n = n.getRight();
			else
			{
				ret = n.Key;
				n = n.getLeft();
			}
		}
		return ret;
	}
	
	public int rankNonRec(Key k)
	{
		if(root == null || k == null) return -1;
		
		int r = 0;
		Node n = root;
		
		while( n != null )
		{
			int cmp = k.compareTo(n.Key);
			if(cmp < 0)
				n = n.getLeft();
			else if(cmp > 0)
			{
				r += sizeOf(n.getLeft()) + 1;
				n = n.getRight();
			}
			else
			{
				r += sizeOf(n.getLeft());
				return r;
			}
		}
		return r;
	}
	
	public Key selectNonRec(int k)
	{
		if(root == null || k < 0) return null;
		Node n = root;
		while(n != null)
		{
			int leftSize = sizeOf(n.getLeft());
			if(leftSize == k)
				return n.Key;
			else if(leftSize > k)
				n = n.getLeft();
			else
			{
				k -= leftSize+1;
				n = n.getRight();
			}
		}
		return null;
	}
	
	////////////////////END OF ORDER RELATED METHODS/////////////////////////////
		
	/////////////////////////////////////////////////////////////
	//  Various delete related methods start from here.
	/////////////////////////////////////////////////////////////
	@SuppressWarnings("unused")
	private void deleteNonRec(Key k)
	{
		if(root == null)//no bother delete anything in an empty tree;
			return;
		
		Node p = null;
		Node c = root;
		int depth = 0;		
			
		//look for node with k as its key and its parent.
		while(c != null && k.compareTo(c.Key) != 0)
		{
			int cmp = k.compareTo(c.Key);
			if(cmp > 0)
			{
				p = c;
				c = c.getRight();
				depth++;
			}
			else if(cmp < 0)
			{
				p = c;
				c = c.getLeft();
				depth++;
			}
		}
		if(c == null) return; //not found;
		//till now, c is node to be deleted and p is its parent;
		fixUpSize(root, c, -1);
		//if c is a node with 0 to 1 child.
		if(c.getLeft() == null || c.getRight() == null)
		{
			if(p.getLeft() == c)
			{
				if(c.getLeft() == null) p.setLeft(c.getRight());
				else p.setLeft(c.getLeft());
			}
			else
			{
				if(c.getLeft() == null) p.setRight(c.getRight());
				else p.setRight(c.getLeft());
			}
		}
		else // 2 children, look for its successor
		{
			Node succParent = null;
			Node succ = c.getRight();
			c.Size -= 1;
			depth++;
			while(succ.getLeft() != null)
			{
				succ.Size -= 1;
				succParent = succ;
				succ = succ.getLeft();
				depth++;
			}
			if(succParent == null) //just one node as its right child and its c's successor
				c.setRight(succ.getRight());
			else
				succParent.setLeft(succ.getRight());
						
			c.Key = succ.Key;
			c.Value = succ.Value;
		}
			
		this.internalPathLength -= depth;
	}
	
	
	
	public void delete(Key k)
	{
		root = delete(root,k);		
	}
	
	private Node delete(Node n, Key k)
	{
		if(n == null) return null;
		int cmp = n.Key.compareTo(k);
		if(cmp > 0) n.setLeft(delete(n.getLeft(), k));
		else if(cmp < 0) n.setRight(delete(n.getRight(), k));
		else
		{
			if(n.getLeft() == null) return n.getRight();
			if(n.getRight() == null) return n.getLeft();
			Node t = n;
			n = minNode(t.getRight());
			n.setRight(delMin(t.getRight()));
			n.setLeft(t.getLeft());
		}
		
		n.Size = sizeOf(n.getLeft()) + sizeOf(n.getRight()) + 1;
		return n;
	}
	
	public void deleteMin() {
		delMin(root);		
	}

	public void deleteMax() {
		delMax(root);
	}
	
	private Node delMax(Node n)
	{
		if(n == null) return null;
		if(n.getRight() == null) return n.getLeft();
		n.setRight(delMax(n.getRight()));
		n.Size = sizeOf(n.getLeft()) + sizeOf(n.getRight()) + 1;
		return n;
	}
		
	private Node delMin(Node n)
	{
		if(n == null) return null;
		if(n.getLeft() == null) return n.getRight();
		n.setLeft(delMin(n.getLeft()));
		n.Size = sizeOf(n.getLeft()) + sizeOf(n.getRight()) + 1;
		return n;
	}
	////////////////////END OF DELETE RELATED METHODS/////////////////////////////
	
	/////////////////////////////////////////////////////////////
	//  Various SIZE & HEIGHT related methods start from here.
	/////////////////////////////////////////////////////////////
	protected int sizeOf(Node n) 	
	{
		if(n == null) return 0;
		return n.Size;
	}
	
	private int height(Node n)
	{
		if(n == null) return 0;
		return Math.max(height(n.getLeft()), height(n.getRight())) + 1;
	}
	
	public int size()
	{
		return sizeOf(root);
	}
	
	public int size(Key low, Key high)
	{
		int cmp = high.compareTo(low);
		if(cmp < 0) return 0;
		if(cmp == 0) return contains(low) ? 1 : 0;
		
		int[] ret = new int[1];
		size(ret, root, low, high);
		return ret[0];
	}
	
	private void size(int[] ret, Node n, Key low, Key high)
	{
		if(n == null) return;
		int lowComp = low.compareTo(n.Key);
		int highComp = high.compareTo(n.Key);
		if(lowComp < 0) size(ret, n.getLeft(), low, high);
		if(lowComp <= 0 && highComp >= 0) ret[0] += 1;
		if(highComp > 0) size(ret, n.getRight(), low, high);
	}
	
	public int height()
	{
		return height(root);
	}
	
	////////////////////END OF SIZE & HEIGHT RELATED METHODS/////////////////////////////
	
	////////////////////////////////////////////////////////////////////////////////////
	//  Various iteration & in/post/level-order traverse methods start from here.
	///////////////////////////////////////////////////////////////////////////////////
	public Iterable<Key> keys()
	{
		//Queue<Key> q = new LinkedList<Key>();
		//inOrderVisit(root, q);
		//return q;
		return inOrderVisitNonRec();
	}
	
	public Iterable<Key> keys(Key low, Key high)
	{
		Queue<Key> ret = new LinkedList<Key>();
		keys(ret, root, low, high);
		return ret;
		
	}
	
	private void keys(Queue<Key> keys, Node n, Key low, Key high)
	{
		if(n == null)
			return;
		
		int lowComp = low.compareTo(n.Key);
		int highComp = high.compareTo(n.Key);
		if(lowComp < 0) keys(keys, n.getLeft(), low, high);
		if(lowComp <= 0 && highComp >= 0) keys.add(n.Key);
		if(highComp > 0) keys(keys, n.getRight(), low, high);
	}
	
	public void preOrderVisit(Node n, Queue<Key> q)
	{
		if(n == null) return;
		q.add(n.Key);
		preOrderVisit(n.getLeft(), q);
		preOrderVisit(n.getRight(), q);
	}
	
	public Queue<Key> preOrderVisitNonRec(Node n)
	{
		Queue<Key> ret = new LinkedList<Key>();
		Stack<Node> s = new Stack<Node>();
		if(n == null) return ret;
		s.push(n);
		while(!s.isEmpty())
		{
			Node m = s.pop();
			ret.add(m.Key);
			if(m.getRight() != null)
				s.add(m.getRight());
			if(m.getLeft() != null)
				s.add(m.getLeft());
		}
		return ret;
	}
	
	public Queue<Key> inOrderVisit()
	{
		Queue<Key> ret = new LinkedList<Key>();
		inOrderVisit(root, ret);
		return ret;
	}
	
	protected void inOrderVisit(Node n, Queue<Key> q)
	{
		if(n == null) return;
		inOrderVisit(n.getLeft(), q);
		q.add(n.Key);
		inOrderVisit(n.getRight(), q);
	}
	
	public Queue<Key> inOrderVisitNonRec()
	{
		Node n = root;
		Stack<Node> s = new Stack<Node>();
		Queue<Key> ret = new LinkedList<Key>();
		while(!s.empty() || n != null)
		{
			while(n!=null)
			{
				s.push(n);
				n = n.getLeft();
			}
			n = s.pop();
			ret.add(n.Key);
			n = n.getRight();
		}
		return ret;
	}
	
	public Queue<Key> postOrderVisitNonRec()
	{
		Node n = root;
		
		Stack<Node> s = new Stack<Node>();
		Queue<Key> ret = new LinkedList<Key>();
		Node lastVisitedNode = null;
		
		while(n != null)
		{
			while(n.getLeft() != null)
			{
				s.push(n);
				n = n.getLeft();
			}
			while(n.getRight() == null || n.getRight() == lastVisitedNode)
			{
				ret.add(n.Key);
				lastVisitedNode = n;
				if(s.empty()) return ret;
				else n = s.pop();
			}
			s.push(n);
			n = n.getRight();
		}
		return ret;		
	}
	
	public void levelOrderVisit()
	{
		if(root == null) return;
		Queue<Node> q = new LinkedList<Node>();
		q.add(root);
		while(!q.isEmpty())
		{
			Node n = q.remove();
			System.out.println("LOT: " + n.Value);
			if(n.getLeft() != null) q.add(n.getLeft());
			if(n.getRight() != null) q.add(n.getRight());
		}
	}
	
	public Key randomKey()
	{
		if(this.size() == 0) return null;
		java.util.Random rd= new java.util.Random();
		return select(rd.nextInt(this.size()));
	}
	
	public void clear() {
		root = null;
		this.cachedVisitedNode = null;
		this.internalPathLength = 0;
	}
	////////////////////END OF iteration & in/post/level-order traverse METHODS/////////////////////////////
	
	///////////////////////////////////////////////////////////////////////////////////
	//  Helper and Misc methods.
	///////////////////////////////////////////////////////////////////////////////////
	
	public static <TKey extends Comparable<TKey>, TValue> BinarySearchTreeST<TKey, TValue> 
		buildFromArray(KeyValuePair<TKey, TValue>[] pairs)
	{
		BinarySearchTreeST<TKey, TValue> tree = new BinarySearchTreeST<TKey, TValue>();
		Arrays.sort(pairs);
		tree.root = tree.buildFromArray(pairs, 0, pairs.length - 1); 
		return tree;		
	}
	
	private Node buildFromArray(KeyValuePair<Key, Value>[] pairs, int lo, int hi)
	{
		Node n = null;
		if(lo <= hi)
		{
			int mid = lo + (hi - lo) / 2;
			n = new Node(pairs[mid].Key, pairs[mid].Value);
			n.setLeft(buildFromArray(pairs, lo, mid - 1));
			n.setRight(buildFromArray(pairs, mid + 1, hi));
		}
		return n;
	}
	
	@SuppressWarnings("unchecked")
	public static <TKey extends Comparable<TKey>, TValue> BinarySearchTreeST<TKey, TValue> 
		buildFromArray2(KeyValuePair<TKey, TValue>[] pairs)
	{
		BinarySearchTreeST<TKey, TValue> tree = new BinarySearchTreeST<TKey, TValue>();
		KeyValuePair<TKey, TValue>[] aux = new KeyValuePair[pairs.length]; 
		Arrays.sort(pairs);
		permuteToBalancedArray(pairs, 0, pairs.length - 1, aux, new int[]{0});
		for(KeyValuePair<TKey, TValue> pair : aux)
		{
			tree.put(pair.Key, pair.Value);
		}
		return tree;		
	}
	
	private static <TKey extends Comparable<TKey>, TValue> 
		void permuteToBalancedArray(KeyValuePair<TKey, TValue>[] pairs, int lo, int hi, KeyValuePair<TKey, TValue>[] aux, int[] auxIndex)
	{
		if(lo <= hi)
		{
			int mid = lo + (hi - lo) / 2;
			aux[auxIndex[0]++] = pairs[mid];			
			permuteToBalancedArray(pairs, lo, mid - 1, aux, auxIndex);
			permuteToBalancedArray(pairs, mid + 1, hi, aux, auxIndex);
		}
	}

	
	public double avgCompares()
	{
		//return root == null ? 0.0 : internalPathLength(root, 0) * 1.0 / root.Size;
		return internalPathLength * 1.0 / root.Size + 1;
	}
	
	public static double optCompares(int n)
	{
		/*
		 * Internal path length of a perfect balancing binary search tree:
		 * 
		 * h = floor(lgN)
		 * 
		 * 2 items
		 * 
		 * 1. root to the next to the last level that contains full nodes
		 * 
		 * Sum (k from 0 to h - 1) k * 2^k 
		 * 
		 * 2. remaining nodes
		 * 
		 * h * ( n - 2^h + 1)
		 * 
		 * By calculation Sum ( k from 0 to m ) k * 2^k = 2^(m+1) * (m - 1) + 2
		 * 
		 * So finally we can get the result
		 * 
		 * h * (n + 1) - 2^(h+1) + 2
		 * 
		 */
		
		int h = (int)Math.floor(Math.log(n) / Math.log(2));
		return (h * (n+1) - (1 << (h+1)) + 2) * 1.0 / n + 1;
	}
	
	public boolean isRankConsistent()
	{
		if(root == null) return true;
		for(int i = 0; i < root.Size ; i++)
		{
			System.out.println(i + " " + root.Size);
			if(i != rank(select(i)))
				return false;
		}
		
		for(Key key : keys())
		{
			if(!key.equals(select(rank(key))))
				return false;
		}
		return true;
	}
	
	
	////////////////////END OF HELPER & MISC METHODS/////////////////////////////////////
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
/*		System.out.println(optCompares(7));
				
		st.levelOrderTraverse();
		
		for(String s : st.keys())
		{
			System.out.println("key = " + s + " : " + st.selectNonRec(st.rankNonRec(s)));
		}
		
		System.out.println("consistent = " + st.isRankConsistent());
		
		KeyValuePair<String, Integer>[] pairs = (KeyValuePair<String, Integer>[])new KeyValuePair[8];
		pairs[0] = new KeyValuePair<String, Integer>("1", 1);
		pairs[1] = new KeyValuePair<String, Integer>("2", 2);
		pairs[2] = new KeyValuePair<String, Integer>("3", 3);
		pairs[3] = new KeyValuePair<String, Integer>("4", 4);
		pairs[4] = new KeyValuePair<String, Integer>("5", 5);
		pairs[5] = new KeyValuePair<String, Integer>("6", 6);
		pairs[6] = new KeyValuePair<String, Integer>("7", 7);
		pairs[7] = new KeyValuePair<String, Integer>("8", 8);
		//pairs[8] = new KeyValuePair<String, Integer>("9", 9);
		BinarySearchTreeST<String, Integer> st2 = BinarySearchTreeST.buildFromArray2(pairs); 
		System.out.println();
		System.out.println("consistent = " + st2.isRankConsistent());
*/
		Stopwatch sw = new Stopwatch();
		sw.start();
		BinarySearchTreeST<String, Integer> bst = new BinarySearchTreeST<String, Integer>();
		OrderedWordFrequencyCounter c = new OrderedWordFrequencyCounter(bst, 2);
		c.feed(new File("D:\\bible.txt"));
		System.out.println("TREE HEIGHT = " + bst.height());
		System.out.println("TOTAL TIME = " + sw.elapsedSeconds());
		testPostOrderSearch();
	}
	
	private static void testPostOrderSearch()
	{
		BinarySearchTreeST<Integer, Integer> bst = new BinarySearchTreeST<Integer, Integer>();
		bst.put(10, 10);
		bst.put(11, 11);
		bst.put(7, 7);
		bst.put(8, 8);
		bst.put(9, 9);
		bst.put(3, 3);
		bst.put(6, 6);
		
		for(Integer k : bst.postOrderVisitNonRec())
		{
			System.out.println(k);
		}
		
		bst.clear();
		
		System.out.println("===================================");
		bst.put(1, 1);
		
		for(Integer k : bst.postOrderVisitNonRec())
		{
			System.out.println(k);
		}
		
		bst.clear();
		System.out.println("===================================");
		for(Integer k : bst.postOrderVisitNonRec())
		{
			System.out.println(k);
		}
		
		bst.clear();
		System.out.println("===================================");
		bst.put(1, 1);
		bst.put(2, 2);
		bst.put(3, 3);
		for(Integer k : bst.postOrderVisitNonRec())
		{
			System.out.println(k);
		}
		
		bst.clear();
		System.out.println("===================================");
		bst.put(5, 5);
		bst.put(4, 4);
		bst.put(3, 3);
		for(Integer k : bst.postOrderVisitNonRec())
		{
			System.out.println(k);
		}
		
		bst.clear();
		System.out.println("===================================");
		bst.put(2, 2);
		bst.put(1, 1);
		bst.put(3, 3);
		for(Integer k : bst.postOrderVisitNonRec())
		{
			System.out.println(k);
		}
		
	}
}


