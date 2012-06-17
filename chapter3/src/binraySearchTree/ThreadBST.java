package binraySearchTree;

public class ThreadBST<Key extends Comparable<Key>, Value>  extends BinarySearchTreeST<Key, Value> {
	
	protected class ThreadNode extends Node
	{
		public ThreadNode Succ;
		public ThreadNode Pred;
		public ThreadNode(Key key, Value value) {
			super(key, value);
		}
		
		@SuppressWarnings("unchecked")
		public ThreadNode getLeft()
		{
			return (ThreadNode)Left;
		}
		
		@SuppressWarnings("unchecked")
		public ThreadNode getRight()
		{
			return (ThreadNode)Right;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void putWithThreading(Key key, Value value)
	{
		if(key == null) return;
		
		ThreadNode p = null;
		ThreadNode c = (ThreadNode)root;
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
		ThreadNode newNode = new ThreadNode(key, value);
		if(p == null)
			root = newNode;
		else
		{
			if(key.compareTo(p.Key) > 0)
			{
				p.setRight(newNode);
				
				//fix threading
				newNode.Succ = p.Succ;
				p.Succ = newNode;
				newNode.Pred = p;
				if(newNode.Succ !=  null)
					newNode.Succ.Pred = newNode;
			}
			else
			{
				p.setLeft(newNode);
				
				//fix threading
				newNode.Pred = p.Pred;
				p.Pred = newNode;
				newNode.Succ = p;
				if(newNode.Pred != null)
					newNode.Pred.Succ = newNode;
				
			}
		}
		
		fixUpSize(root, newNode, 1);
		this.internalPathLength += depth;	
	}
	
	@SuppressWarnings("unchecked")
	public void deleteWithThreading(Key k)
	{
		if(root == null)//no bother delete anything in an empty tree;
			return;
		
		ThreadNode p = null;
		ThreadNode c = (ThreadNode)root;
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
			
			//fix threading.
			if(c.Pred != null)
			{
				c.Pred.Succ = c.Succ;
				c.Succ = null;
			}
			if(c.Succ != null)
			{
				c.Succ.Pred = c.Pred;
				c.Pred = null;
			}
		}
		else // 2 children, look for its successor
		{
			ThreadNode succParent = null;
			ThreadNode succ = c.getRight();
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
			
			//fix threading
			c.Succ = succ.Succ;
			succ.Succ = null;
			if(c.Succ != null)
				c.Succ.Pred = c;
		}
			
		this.internalPathLength -= depth;
	}
	
	@SuppressWarnings("unchecked")
	public Key prev(Key key)
	{
		ThreadNode n = (ThreadNode)root;
		while(n != null)
		{
			int cmp = key.compareTo(n.Key);
			if(cmp < 0) n = n.getLeft();
			else if(cmp > 0) n = n.getRight();
			else return n.Pred == null ? null : n.Pred.Key;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Key next(Key key)
	{
		ThreadNode n = (ThreadNode)root;
		while(n != null)
		{
			int cmp = key.compareTo(n.Key);
			if(cmp < 0) n = n.getLeft();
			else if(cmp > 0) n = n.getRight();
			else return n.Succ == null ? null : n.Succ.Key;
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		ThreadBST<String, Integer> st = new ThreadBST<String, Integer>();
		st.putWithThreading("7", 7);
		st.putWithThreading("5", 5);
		st.putWithThreading("2", 2);
		st.putWithThreading("6", 6);
		st.putWithThreading("8", 8);
		st.putWithThreading("9", 9);
		st.putWithThreading("1", 1);
		System.out.println(st.size());
		System.out.println(st.size("2", "3"));
		System.out.println(st.avgCompares());
		System.out.println("min =" + st.minNonRec());
		System.out.println("max =" + st.maxNonRec());
		System.out.println("floor=" + st.floorNonRec("3"));
		System.out.println("ceiling=" + st.ceilingNonRec("3"));
		System.out.println("floor=" + st.floorNonRec("5"));
		System.out.println("ceiling=" + st.ceilingNonRec("5"));
		System.out.println("rank=" + st.rankNonRec("2"));
		System.out.println("select=" + st.select(2));
		System.out.println("next=" + st.prev("8"));
		st.deleteWithThreading("7");
		System.out.println("next=" + st.prev("8"));		
	}
	
}
