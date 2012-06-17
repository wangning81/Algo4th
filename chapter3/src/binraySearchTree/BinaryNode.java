package binraySearchTree;

public class BinaryNode <TKey extends Comparable<TKey>, TValue> {
	
	public BinaryNode<TKey, TValue> Left;
	public BinaryNode<TKey, TValue> Right;
	public TKey Key;
	public TValue Value;
	private int size;
	
	public BinaryNode(TKey key, TValue value)
	{
		this.Key = key;
		this.Value = value;
		size = 1;
	}
	
	
	public static <TKey extends Comparable<TKey>, TValue> 
			boolean isBinaryTree(BinaryNode<TKey, TValue> n)
	{
		return n == null 
				||
				(
						n.size == 1 + (n.Left == null ? 0 : n.Left.size) + (n.Right == null ? 0 : n.Right.size)
						&& isBinaryTree(n.Left)
						&& isBinaryTree(n.Right)
				);
	}
	
	private static <TKey extends Comparable<TKey>, TValue> 
			BinaryNode<TKey, TValue> maxNode(
					BinaryNode<TKey, TValue> n
					) {
		if(n == null) return null;
		BinaryNode<TKey, TValue> p = n;
		while(p. Right != null)
			p = p.Right;
		return p;
	}

	private static <TKey extends Comparable<TKey>, TValue> 
			BinaryNode<TKey, TValue> minNode(
			BinaryNode<TKey, TValue> n) {
		if(n == null) return null;
		BinaryNode<TKey, TValue> p = n;
		while(p.Left != null)
			p = p.Left;
		return p;
	}
	
	public static <TKey extends Comparable<TKey>, TValue> 
		boolean isOrdered(BinaryNode<TKey, TValue> n, TKey min, TKey max)
	{
		if(n == null) return true;
		int minCmp = n.Key.compareTo(min);
		int maxCmp = n.Key.compareTo(max);
		return 
				minCmp >= 0 && maxCmp <= 0
				&& isOrdered(n.Left, min, n.Key) 
				&& isOrdered(n.Right, n.Key, max);		
	}
	
	public static <TKey extends Comparable<TKey>, TValue> 
		boolean hasNoDuplicate(BinaryNode<TKey, TValue> n)
	{
		if(n == null) return true;
		for(int i = 1 ; i < n.size ; i++)
			if(n.select(n, i).equals(n.select(n, i - 1)))
				return false;
		return true;
	}
	
	public static <TKey extends Comparable<TKey>, TValue> 
		boolean isBST(BinaryNode<TKey, TValue> n)
	{
		return isBinaryTree(n)
			   &&
			   isOrdered(n, minNode(n).Key, maxNode(n).Key)
			   &&
			   hasNoDuplicate(n);
	}
	
	
	
	
	private TKey select(BinaryNode<TKey, TValue> n, int i)
	{
		if(n == null) return null;
		int leftSize = n.Left == null ? 0 : n.Left.size; 
		if(leftSize == i) return n.Key;
		if(leftSize > i) return select(n.Left, i);
		return select(n.Right, i - leftSize - 1);
	}
	
}
