package stringst;

import elementary.ArrayQueue;
import elementary.LinkedList;
import elementary.Stack;
import symbolTables.SymbolTable;


public class Trie<TValue> implements SymbolTable<String, TValue> {
	
	private static final int R = 256;
	private Node root;
	private int size;
	private class Node
	{
		TValue value;
		@SuppressWarnings("unchecked")
		Node[] next = new Trie.Node[R];
		int count;
	}

	public void put(String key, TValue value) {
		if(root == null) root = new Node();
		put(root, key, value, 0);		
	}
	
	private void put(Node n, String key, TValue value, int d)
	{
		if(d == key.length()) { //search hit.
			if(n.value == null) //newly added
				size++;
			n.value = value; 
			return;
		}
		
		int c = key.charAt(d);
		
		if(n.next[c] == null)
		{
			n.next[c] = new Node();
			n.count++;
		}
		
		put(n.next[c], key, value, d + 1);
	}

	public TValue get(String key) {
		return get(root, key, 0).value;
	}
	
	private Node get(Node n, String key, int d)
	{
		if(n == null) return null;
		if(d == key.length()) return n; //search hit.
		return get(n.next[key.charAt(d)], key, d+1);		
	}

	public void delete(String key) {
		
		if(root == null) return;
		
		Stack<Node> sn = new Stack<Node>();
		int d = 0;
		Node n = this.root;
		while(n != null && d < key.length())
		{
			char c = key.charAt(d);
			if(n.next[c] != null)
			{
				sn.push(n);
				d++;
			}
			n = n.next[c];
		}
		
		if( d < key.length() || n.value == null ) return; //not found
		n.value = null;
		size--;
		if(n.count == 0)//this is a leaf, delete to up level
		{
			for(int k = key.length() - 1 ; k >= 0 ; k--)
			{
				Node nn = sn.pop();
				nn.next[key.charAt(k)] = null;
				nn.count--;
				if(nn.count > 0  || nn.value != null) break;
			}
		}
		if(root.count == 0) root = null;
	}

	public boolean contains(String key) {
		return get(key) != null;
	}

	public int size() {
		return size;
	}

	public void clear() {
		root = null;
		size = 0;
	}
	
	public boolean isEmpty()
	{
		return root == null;
	}
		
	public String longestPrefixOfNonRec(String s)
	{
		Node n = root;
		StringBuilder sb = new StringBuilder();
		int maxKeyLen = 0;
		for(int d = 0 ; d < s.length() && n != null ; d++)
		{
			char c = s.charAt(d);
			n = n.next[c];
			if(n != null)
			{
				sb.append(c);
				if(n.value != null)
					maxKeyLen = sb.length();
			}
		}
		return sb.length() == 0 ? null : sb.substring(0, maxKeyLen).toString();
	}
	
	public String longestPrefixOf(String s)
	{
		if(root == null) return null;
		return longestPrefixOf(root, s, 0);
	}
	
	private String longestPrefixOf(Node n, String s, int d)
	{
		if(d == s.length() || n.next[s.charAt(d)] == null) // all matched or no more matched
		{
			return n.value != null ? s.substring(0, d) //it's a key here, return the result.
								   : null; 
		}
		
		//still in matching...
		String ret = longestPrefixOf(n.next[s.charAt(d)], s, d + 1); //looking for more matching
		if(ret == null && n.value != null) return s.substring(0, d); //not matched in descent but matched here.
		return ret;
	}
	
	public Iterable<String> keys()
	{
		return keysWithPrefix("");
	}
	
	public Iterable<String> keysWithPrefix(String pre)
	{
		ArrayQueue<String> ret = new ArrayQueue<String>();
		collect(get(root, pre, 0), new StringBuilder(pre), ret);
		return ret;
	}
	
	protected void collect(Node n, StringBuilder sb, ArrayQueue<String> q)
	{
		if(n == null) return;
		if(n.value != null) q.enqueue(sb.toString());
		
		for(int c = 0 ; c < R ; c++)
		{
			sb.append((char)c);
			collect(n.next[c], sb, q);
			sb.deleteCharAt(sb.length() - 1);
		}
	}
	
	public Iterable<String> keysThatMatch(String s)
	{
		return keysThatMatch(root, new StringBuilder(s), 0);
	}
	
	public Iterable<String> keysThatMatch(Node n, StringBuilder s, int d)
	{
		LinkedList<String> ret = new LinkedList<String>();
		if(n == null) return null;
		if(d == s.length())
		{
			if(n.value != null)
			{
				ret.add(s.toString());
				return ret;
			}
			else return null;
		}
		
		char c = s.charAt(d);
		if(c == '.') //wildcard
		{
			for(int i = 0 ; i < n.next.length ; i++)
			{
				if(n.next[i] != null)
				{
					s.setCharAt(d, (char)i);
					ret.add(keysThatMatch(n.next[i], s, d+1));
					s.setCharAt(d, '.');
				}
			}
			return ret;
		}
		else
		{
			return keysThatMatch(n.next[c], s, d+1);			
		}
	}
	
	protected int totalNodes()
	{
		return totalNodes(root);
	}
	
	protected int totalNodes(Node n)
	{
		if(n == null) return 0;
		int ret = 0;
		for(int i = 0 ; i < n.next.length ; i++)
			if(n.next[i] != null)
				ret += totalNodes(n.next[i]);
		return ret + 1;
	}
	
	
	
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
		
		Trie<Integer> trie = new Trie<Integer>();
		
		for(int i = 0 ; i < ss.length ; i++)
			trie.put(ss[i], i);
		
		System.out.println("size = " + trie.size());
		
		for(int i = 0 ; i < ss.length ; i++)
			System.out.println(trie.get(ss[i]));
		
		for(String s : trie.keys())
		{
			System.out.print(s + " ");
		}
		System.out.println();

		
		System.out.println(trie.longestPrefixOfNonRec("xshell"));
		for(String s : trie.keysThatMatch(".he"))
			System.out.println(s);
		
		for(String s : trie.keysThatMatch("sea......."))
			System.out.println(s);
		System.out.println(trie.get("shells"));
		
		for(String s : trie.keysWithPrefix("shesfs"))
		{
			System.out.println(s);
		}
		
		
		
		for(String s : trie.keys())
		{
			System.out.print(s + " ");
		}
		
		System.out.println();
		System.out.println(trie.totalNodes());
		
		System.out.println("------------");
		
		trie.delete("shells");
		
				System.out.println(trie.totalNodes());
		
	
	}
}
