package binraySearchTree;

import java.io.File;

import elementary.Stopwatch;

import symbolTables.OrderedWordFrequencyCounter;

public class RedBlackTree<Key extends Comparable<Key>, Value> extends BinarySearchTreeST<Key, Value> {
	
	private final static class Color
	{
		@SuppressWarnings("unused")
		private int color;
		private Color(int c){color = c;}
		public final static Color RED = new Color(0);
		public final static Color BLACK = new Color(1);
	}
	
	private class RBNode extends Node
	{
		public Color Color;
		@SuppressWarnings("static-access")
		public RBNode(Key key, Value value)
		{
			super(key, value);
			Color = Color.RED;
		}
		@SuppressWarnings("unchecked")
		@Override
		public RBNode getRight()
		{
			return (RBNode)this.Right;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public RBNode getLeft()
		{
			return (RBNode)this.Left;
		}
	}
	
	public RedBlackTree()
	{}
	
	@SuppressWarnings("unchecked")
	@Override
	public void put(Key key, Value value)
	{
		root = put((RBNode)root, key, value);
		((RBNode)root).Color = Color.BLACK;
	}

	private RBNode put(RBNode n, Key key, Value value)
	{
		if(n == null) 
			return new RBNode(key, value);
		int cmp = key.compareTo(n.Key);
		if(cmp < 0)
			n.setLeft(put(n.getLeft(), key, value));
		else if(cmp > 0)
			n.setRight(put(n.getRight(), key, value));
		else 
			n.Value = value;
		
		//use code below will greatly improve the insert performance
		//over directly using balance(n)
		//because huge number of useless rotate left then right are avoid
		//MUST RED = LEFT and only left is black we rotate.
		if(colorOf(n.getRight()) == Color.RED && colorOf(n.getLeft()) == Color.BLACK)
			n = rotateLeft(n);
		//TO RED-root-RED
		if(colorOf(n.getLeft()) == Color.RED && colorOf(n.getLeft().getLeft()) == Color.RED)
			n = rotateRight(n);
		//Flip to send RED upwards
		if(colorOf(n.getLeft()) == Color.RED && colorOf(n.getRight()) == Color.RED)
			flipColors(n);
		
		n.Size = sizeOf(n.getLeft()) + sizeOf(n.getRight()) + 1;
		
		return n;	
	}

	private RBNode balance(RBNode n) {
		//MUST RED = LEFT
		if(colorOf(n.getRight()) == Color.RED)
			n = rotateLeft(n);
		//TO RED-root-RED
		if(colorOf(n.getLeft()) == Color.RED && colorOf(n.getLeft().getLeft()) == Color.RED)
			n = rotateRight(n);
		//Flip to send RED upwards
		if(colorOf(n.getLeft()) == Color.RED && colorOf(n.getRight()) == Color.RED)
			flipColors(n);
		
		n.Size = sizeOf(n.getLeft()) + sizeOf(n.getRight()) + 1;
		
		return n;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteMin()
	{
		RBNode rt = (RBNode)root;
		
		if(colorOf(rt.getLeft()) == Color.BLACK && colorOf(rt.getRight()) == Color.BLACK)
			rt.Color = Color.RED;
		
		root = delMin(rt);
		if(size() > 0)
			((RBNode)root).Color = Color.BLACK;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteMax()
	{
		RBNode rt = (RBNode)root;
		
		if(colorOf(rt.getLeft()) == Color.BLACK && colorOf(rt.getRight()) == Color.BLACK)
			rt.Color = Color.RED;
		
		root = delMax(rt);
		if(size() > 0)
			((RBNode)root).Color = Color.BLACK;				
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void delete(Key key)
	{
		if(get(key) == null)
			return;
		
		RBNode rt = (RBNode)root;
		if(colorOf(rt.getLeft()) == Color.BLACK && colorOf(rt.getRight()) == Color.BLACK)
			rt.Color = Color.RED;
		
		root = delete(rt, key);
		if(sizeOf(root) > 1)
			((RBNode)root).Color = Color.BLACK;
	}
	
	protected RBNode delete(RBNode n, Key key)
	{
		int cmp = key.compareTo(n.Key);
		if(cmp < 0)
		{
			if(colorOf(n.getLeft()) == Color.BLACK && colorOf(n.getLeft().getLeft()) == Color.BLACK)
				n = moveRedToLeft(n);
			n.Left = delete(n.getLeft(), key);
		}
		else
		{
			if(colorOf(n.getLeft()) == Color.RED)
				n = rotateRight(n);
			
			if(key.compareTo(n.Key) == 0 && n.getRight() == null)
				return null;
			
			if(colorOf(n.getRight()) == Color.BLACK 
					&& colorOf(n.getRight().getLeft()) == Color.BLACK)
			{
				n = moveRedToRight(n);
			}
			
			if(key.compareTo(n.Key) == 0)
			{
				Node succ = this.minNode(n.Right);
				n.Value = succ.Value;
				n.Key = succ.Key;
				n.Right = delMin(n.getRight());				
			}
			else
			{
				n.Right = delete(n.getRight(), key);
			}
		}
		return balance(n);
	}
	
	protected RBNode delMax(RBNode n)
	{
		assert n != null && n.Color == Color.RED;
		
		if(colorOf(n.getLeft()) == Color.RED)
			n = rotateRight(n);
		
		if(n.Right == null)
			return null;
		
		if(colorOf(n.getRight()) == Color.BLACK && colorOf(n.getRight().getLeft()) == Color.BLACK)
		{
			n = moveRedToRight(n);
		}
		
		n.Right = delMin(n.getRight());
		return balance(n);
	}
	
	private RBNode moveRedToRight(RBNode n) {
		assert n != null && n.Color == Color.RED
				&& colorOf(n.getRight()) == Color.BLACK;
		
		flipColors(n);
		if(colorOf(n.getLeft().getLeft()) == Color.RED)
		{
			n = rotateRight(n);
		}
		return n;
	}

	protected RBNode delMin(RBNode n)
	{
		assert n!= null && n.Color == Color.RED;
		
		if(n.getLeft() == null) 
			return null;
		
		if(colorOf(n.getLeft()) == Color.BLACK
				&& colorOf(n.getLeft().getLeft()) == Color.BLACK)
			n = moveRedToLeft(n);
		
		n.Left = delMin(n.getLeft());
		return balance(n);
	}
	
	private RBNode moveRedToLeft(RBNode n)
	{
		assert(n.Color == Color.RED 
				&& n.getLeft() != null
				&& colorOf(n.getLeft()) == Color.BLACK
				&& n.getRight() != null
			  );
		
		flipColors(n);
		if(colorOf(n.getRight().getLeft()) == Color.RED)
		{
			n.setRight(rotateRight(n.getRight()));
			n = rotateLeft(n);			
		}
		return n;
	}
	
	private RBNode rotateLeft(RBNode n)
	{
		RBNode x = n.getRight();
		n.setRight(x.getLeft());
		x.setLeft(n);
		x.Color = n.Color;
		x.Size = n.Size;
		n.Color = Color.RED;
		n.Size = sizeOf(n.getLeft()) + sizeOf(n.getRight()) +1;
		return x;
	}
	
	private RBNode rotateRight(RBNode n)
	{
		RBNode x = (RBNode)n.getLeft();
		n.setLeft(x.getRight());
		x.setRight(n);
		x.Color = n.Color;
		n.Color = Color.RED;
		x.Size = n.Size;
		n.Size = sizeOf(n.getLeft()) + sizeOf(n.getRight()) + 1;
		return x;
	}
	
	private void flipColors(RBNode n)
	{
		assert (n.getLeft().Color == n.getRight().Color && n.getLeft().Color != n.Color);
				
		n.Color = (n.Color == Color.RED ? Color.BLACK : Color.RED);
		RBNode right = n.getRight(); 
		right.Color = (right.Color == Color.RED ? Color.BLACK : Color.RED);
		RBNode left = n.getLeft();
		left.Color = (left.Color == Color.RED? Color.BLACK : Color.RED);
	}
	
	private Color colorOf(RBNode n)
	{
		return n == null ? Color.BLACK : n.Color;
	}
	
	public static void main(String[] args) {
/*		RedBlackTree<Integer, Integer> rbt = new RedBlackTree<Integer, Integer>();
		rbt.put(10, 10);
		rbt.put(11, 11);
		rbt.put(7, 7);
		rbt.put(8, 8);
		rbt.put(9, 9);
		rbt.put(3, 3);
		rbt.put(6, 6);
		System.out.println(rbt.size());
		printInOrderTree(rbt);
		for(int i = 0 ; i <7 ;i++)
		{
			rbt.deleteMin();
			System.out.println(rbt.size());
			printInOrderTree(rbt);
		}
		
		rbt.clear();
		rbt.put(5, 5);
		rbt.put(2, 2);
		rbt.put(7, 7);
		rbt.put(6, 6);
		rbt.put(1, 1);
		System.out.println(rbt.size());
		printInOrderTree(rbt);
		for(int i = 0 ; i < 4 ;i++)
		{
			rbt.deleteMin();
			System.out.println(rbt.size());
			printInOrderTree(rbt);
		}*/
		Stopwatch sw = new Stopwatch();
		sw.start();
		RedBlackTree<String, Integer> rbt = new RedBlackTree<String, Integer>();
		OrderedWordFrequencyCounter c = new OrderedWordFrequencyCounter(rbt, 2);
		c.feed(new File("D:\\bible.txt"));
		System.out.println("TREE HEIGHT = " + rbt.height());
		System.out.println("TOTAL TIME = " + sw.elapsedSeconds());
	}

	@SuppressWarnings("unused")
	private static void printInOrderTree(RedBlackTree<Integer, Integer> rbt) {
		for(int it : rbt.inOrderVisit())
		{
			System.out.print(it + " ,");
		}
		System.out.println();
	}

}
