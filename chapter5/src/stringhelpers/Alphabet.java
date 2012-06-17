package stringhelpers;

import hashTables.SeparateChainingHashST;

public class Alphabet {
	private SeparateChainingHashST<Character, Integer> htByChar = new SeparateChainingHashST<Character, Integer>();
	private SeparateChainingHashST<Integer, Character> htByIndex = new SeparateChainingHashST<Integer, Character>();
	
	private final double lg2 =  Math.log(2);
	
	public Alphabet(String sigma)
	{
		for(int i = 0 ; i < sigma.length() ; i++)
		{
			char c = sigma.charAt(i);
			htByChar.put(c, i);
			htByIndex.put(i, c);
		}
	}
	
	public char toChar(int index)
	{
		return htByIndex.get(index);
	}
	
	public int toIndex(char c)
	{
		return htByChar.get(c);
	}
	
	public boolean contains(char c)
	{
		return htByChar.contains(c);		
	}
	
	public int R()
	{
		return htByChar.size();
	}
	
	public int lgR()
	{
		return (int)Math.ceil(Math.log(htByChar.size()) / lg2);		
	}
	
	public int[] toIndices(char[] chars)
	{
		int n = chars.length;
		int[] ret = new int[n];
		for(int i = 0 ; i < n ; i++)
			ret[i] = toIndex(chars[i]);
		return ret;
	}
	
	public char[] toChars(int[] indices)
	{
		int n = indices.length;
		char[] ret = new char[n];
		for(int i = 0 ; i < n ; i++)
			ret[i] = toChar(indices[i]);
		return ret;
	}
	

	public static void main(String[] args) {
		Alphabet alphabet = new Alphabet("ACTG");
		System.out.println(alphabet.R());
		System.out.println(alphabet.lgR());
		System.out.println(alphabet.toChar(0));
		System.out.println(alphabet.toChar(1));
		System.out.println(alphabet.toChar(2));
		System.out.println(alphabet.toChar(3));
		
		System.out.println(alphabet.toIndex('C'));
		System.out.println(alphabet.toIndex('T'));
		System.out.println(alphabet.toIndex('G'));
		System.out.println(alphabet.toIndex('A'));
		
	}

}
