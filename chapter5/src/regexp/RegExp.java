package regexp;

import directedgraph.Digraph;
import directedgraph.DirectedDfs;
import elementary.Bag;
import elementary.Stack;

public class RegExp {
	private Digraph epsilonTrans;
	private char[] regEx;
	private int M;
	
	public RegExp(String pattern)
	{
		M = pattern.length();
		epsilonTrans = new Digraph(M + 1); //0 for start the left most '(', M for accept
		regEx = pattern.toCharArray();
		
		Stack<Integer> s = new Stack<Integer>();		
		
		for(int i = 0 ; i < M ; i++)
		{
			int lp = i;
			char c = pattern.charAt(i);
			if(c == '(' || c == '|')
			{
				s.push(i);				
			}
			else if(c == ')')
			{
				int last = s.pop();
				if(pattern.charAt(last) == '|')
				{
					lp = s.pop();
					epsilonTrans.addEdge(lp, last + 1);
					epsilonTrans.addEdge(last, i);
				}
				else lp = last;
			}
			
			if( i < M - 1 && pattern.charAt(i + 1) == '*')
			{
				epsilonTrans.addEdge(lp, i + 1);
				epsilonTrans.addEdge(i + 1, lp);
			}
			
			if(c == '(' || c == ')' || c == '*')
			{
				epsilonTrans.addEdge(i, i + 1);				
			}
		}	
	}
	
	public boolean recoginze(String s)
	{
		Bag<Integer> pc = new Bag<Integer>();
		DirectedDfs dfs = new DirectedDfs(epsilonTrans, 0);
		
		for(int v = 0 ; v < epsilonTrans.verticeCount() ; v++)
			if(dfs.isConnected(v))
				pc.add(v);
		
		for(int i = 0 ; i < s.length() ; i++)
		{
			Bag<Integer> matched = new Bag<Integer>();
			for(int v : pc)
			{
				if((regEx[v] == s.charAt(i) || regEx[v] == '.') && v < M )
				{
					matched.add(v + 1);
				}
			}
			
			dfs = new DirectedDfs(epsilonTrans, matched);
			pc.clear();
			for(int v = 0 ; v < epsilonTrans.verticeCount() ; v++)
				if(dfs.isConnected(v))
					pc.add(v);	
		}
		
		for(int v : pc)
			if(v == M)
				return true;
		return false;
	}

	
	public static void main(String[] args) {
		RegExp regExp = new RegExp("((A*B|AC)D|AD)");
		System.out.println(regExp.recoginze("AABD"));
		System.out.println(regExp.recoginze("ACD"));
		System.out.println(regExp.recoginze("AD"));
		
		System.out.println(regExp.recoginze("D"));
	}

}
