package stringst;

import elementary.LinkedList;
import symbolTables.SymbolTable;

public class TST<TValue> implements SymbolTable<String, TValue>{
	
	private class Node
	{
		Node left;
		Node mid;
		Node right;
		char c;
		TValue value;
		public Node(char c)
		{
			this.c = c;
		}
	}
	
	private Node root;
	private int size;
	
	public TST()
	{
		
	}
	
	public void put(String key, TValue value) {
		root = put(root, key, value, 0);
	}
	
	private Node put(Node n, String key, TValue value, int d)
	{
		if(n == null) n = new Node(key.charAt(d));
				
		int cmp = n.c - key.charAt(d);
		if(cmp < 0)
			n.right = put(n.right, key, value, d);
		else if(cmp > 0)
			n.left = put(n.left, key, value, d);
		else //matched
		{
			if(d == key.length() - 1){
				if(n.value == null)
					size++;
				n.value = value; //and all matched already, set the value.
			}
			else n.mid = put(n.mid, key, value, d+1);
		}
		return n;
	}



	public TValue get(String key) {
		if(key.length() == 0) return null;
		Node n = get(root, key, 0);
		return n != null ? n.value : null;
	}
	
	private Node get(Node n, String key, int d)
	{
		if(n == null || key.length() == 0) return null;
		
		int cmp = n.c - key.charAt(d);
		if(cmp < 0)
			return get(n.right, key, d);
		else if(cmp > 0)
			return get(n.left, key, d);
		else //matched
		{
			if(d == key.length() - 1) return n;
			else return get(n.mid, key, d+1);
		}
	}

	public void delete(String key) {
		delete(root, key, 0);						
	}
	
	private Node delete(Node n, String s, int d)
	{
		if(n == null) return null;
		
		char nc = n.c;
		char sc = s.charAt(d);
		
		if(d == s.length() - 1)
		{
			if(nc == sc && n.value != null)
			{
				n.value = null;
				size--;
				return n.mid == null ? null : n;
			}
			return n; // nothing to delete, for it is either not a key or the last char not equal.
		}
		
		int cmp = nc - sc;
		
		if(cmp < 0) 
			n.right = delete(n.right, s, d);
		else if(cmp > 0) 
			n.left = delete(n.left, s, d);
		else
		{
			n.mid = delete(n.mid, s, d + 1);
			if(n.mid != null) return n;
			if(n.left == null) return n.right;
			if(n.right == null) return n.left;
			
			Node succ = minNode(n.right);
			n.value = succ.value;
			n.mid = succ.mid;
			n.right = delMin(n.right);
		}
		return n;
	}
	
	protected Node delMin(Node n)
	{
		if(n == null) return null;
		if(n.left == null) return n.right;
		n.left = delMin(n.left);
		return n;
	}
	
	protected Node minNode(Node n)
	{
		if(n == null) return null;
		while(n.left != null)
			n = n.left;
		return n;
	}

	public boolean contains(String key) {
		return get(key) != null;
	}

	public int size() {
		return size;
	}
	public Iterable<String> keys() {
		LinkedList<String> lst = new LinkedList<String>();
		collect(root, new StringBuilder(), lst);
		return lst;
	}
	
	public Iterable<String> keysThatMatch(String pat)
	{
		LinkedList<String> lst = new LinkedList<String>();
		collect(root, pat, 0, new StringBuilder(), lst);
		return lst;
	}
	
	private void collect(Node n, String pat, int d, StringBuilder sb, LinkedList<String> lst)
	{
		if(n == null) return;
		
		char pc = pat.charAt(d);
		char nc = n.c;
		
		if(d == pat.length() - 1)
		{
			if( (n.c == pc || pc == '.') && n.value != null) lst.add(sb.toString() + n.c);
			return;
		}
		
		
		int cmp = nc - pc;
		
		if(cmp < 0 || pc == '.') collect(n.right, pat, d, sb, lst);
		if(cmp > 0 || pc == '.') collect(n.left, pat, d, sb, lst);
		if(cmp == 0 || pc == '.')
		{
			sb.append(n.c);
			collect(n.mid, pat, d + 1, sb, lst);
			sb.deleteCharAt(sb.length() - 1);
		}
	}
	
	public Iterable<String> keyWithPrefix(String pre)
	{
		LinkedList<String> lst = new LinkedList<String>();
		Node preNode = get(root, pre, 0);
		if(preNode != null)
		{
			collect(preNode.mid, new StringBuilder(pre), lst);
		}
		return lst;
	}
	
	public String longestPrefixOf(String s)
	{
		int t = search(root, s, 0, 0);
		return s.substring(0, t);
	}
	
	private int search(Node n, String s, int d, int longest)
	{
		if(n == null || d == s.length()) return longest;
		
		int cmp = n.c - s.charAt(d);
		if(cmp < 0) return search(n.right, s, d, longest);
		else if(cmp > 0) return search(n.left, s, d, longest);
		else{
			if(n.value != null) longest = d + 1;
			return search(n.mid, s, d+1, longest);
		}
	}
	
	private void collect(Node n, StringBuilder sb, LinkedList<String> lst)
	{
		if(n == null) return;
		if(n.value != null) lst.add(sb.toString() + n.c);
		
		if(n.left != null) collect(n.left, sb, lst);
		if(n.right != null) collect(n.right, sb, lst);
		
		if(n.mid != null)
		{
			sb.append(n.c);
			collect(n.mid, sb, lst);
			sb.deleteCharAt(sb.length() - 1);
		}
	}

	public void clear() {
		root = null;
		size = 0;		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] ss = new String[]{
				"she",
				"sells",
				"seashells",
				"by",
				"the",
				"seashore",
				"the",
				"shells",
				"she",
				"sells",
				"are",
				"surely",
				"seashells"
		};
		
		TST<Integer> trie = new TST<Integer>();
		
		for(int i = 0 ; i < ss.length ; i++)
			trie.put(ss[i], i);
		
		for(int i = 0 ; i < ss.length ; i++)
			System.out.println(trie.get(ss[i]));
		
		printTST(trie);
		
		for(String s : trie.keyWithPrefix("s"))
		{
			System.out.print(s + " ");
		}
		System.out.println();
		
		System.out.println("Longest = " + trie.longestPrefixOf("surel."));
		
		for(String s : trie.keysThatMatch("sea...r."))
		{
			System.out.print(s + " ");
		}
		System.out.println();
		
		trie.delete("she");
		printTST(trie);
		trie.delete("shex");
		printTST(trie);
		trie.delete("are");
		printTST(trie);

	}

	private static void printTST(TST<Integer> trie) {
		System.out.println();
		for(String s : trie.keys())
		{
			System.out.print(s + " ");
		}
		System.out.println();
	}


}
