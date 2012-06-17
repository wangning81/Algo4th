package datacompression;

import java.io.*;

import stringhelpers.*;
import priorityqueue.*;

class HuffmanTrieNode implements Comparable<HuffmanTrieNode>
{
	public char ch;
	public int freq;
	public HuffmanTrieNode left;
	public HuffmanTrieNode right;
	
	public HuffmanTrieNode(char ch, int freq)
	{
		this.ch = ch;
		this.freq = freq;
	}
	
	public HuffmanTrieNode(int freq, HuffmanTrieNode left, HuffmanTrieNode right)
	{
		this.freq = freq;
		this.left = left;
		this.right = right;		
	}

	public int compareTo(HuffmanTrieNode rhs) {
		return this.freq - rhs.freq;
	}
	
	public boolean isLeaf()
	{
		return ch != 0;
	}
}

public class Huffman {
	private static final int R = 256;
	
	private static HuffmanTrieNode buildTrie(int[] freq)
	{
		MinPQ<HuffmanTrieNode> pq = new MinPQ<HuffmanTrieNode>();
		for(int c = 0 ; c < R ; c++)
		{
			if(freq[c] > 0)
				pq.enqueue(new HuffmanTrieNode((char)c, freq[c]));
		}
		
		while(pq.size() > 1)
		{
			HuffmanTrieNode l = pq.dequeue();
			HuffmanTrieNode r = pq.dequeue();
			HuffmanTrieNode n = new HuffmanTrieNode(l.freq + r.freq, l, r);
			pq.enqueue(n);	
		}
		return pq.dequeue();
	}
	
	private static String[] buildCodeTable(HuffmanTrieNode r)
	{
		String[] ret = new String[R];
		buildCodeTable(r, "", ret);
		return ret;
	}
	
	private static void buildCodeTable(HuffmanTrieNode n, String code, String[] codeTable)
	{
		if(n == null) return;
		if(n.isLeaf())
		{
			codeTable[n.ch] = code;						
		}
		buildCodeTable(n.left, code + '0', codeTable);
		buildCodeTable(n.right, code + '1', codeTable);
	}
	
	private static void writeTrie(BinaryOut out, HuffmanTrieNode node)
	{
		if(node.isLeaf())
		{
			out.write(true);
			out.write(node.ch);
		}
		else
		{
			out.write(false);
			writeTrie(out, node.left);
			writeTrie(out, node.right);
		}
	}
	
	private static HuffmanTrieNode readTrie(BinaryIn in)
	{
		HuffmanTrieNode ret;
		int b = in.readBits(1);
		if(b == 1) //read a leaf, build and return
		{
			char c = in.readChar();
			ret = new HuffmanTrieNode(c, 1);
		}
		else
		{
			ret = new HuffmanTrieNode(1, readTrie(in), readTrie(in));
		}
		return ret;
	}
	
	public static void compress(String inputFile, String outputFile)
	{
		int[] freq = new int[R];
		int totalChar = 0;
		try 
		{
			FileInputStream fis = new FileInputStream(inputFile);
			totalChar = fis.available();
			for(int i = 0 ; i < totalChar ; i++)
			{
				int c = fis.read();
				freq[c]++;
				
			}
			fis.close();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
				
		HuffmanTrieNode r = buildTrie(freq);
		String[] codeTable = buildCodeTable(r);
		
		try 
		{
			FileOutputStream fos = new FileOutputStream(outputFile);
			BinaryOut bout = new BinaryOut(fos);
			
			writeTrie(bout, r);
			bout.write(totalChar);

			FileInputStream fis = new FileInputStream(inputFile);
			
			for(int i = 0 ; i < totalChar ; i++)
			{
				int c = fis.read();
				String code = codeTable[c];
				for(int k = 0 ; k < code.length() ; k++)
					bout.write(code.charAt(k) == '1');
			}
			bout.flush();
			bout.close();
			fos.close();
			fis.close();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
		
	public static void expand(String inputFile, String outputFile)
	{
		try {
			FileInputStream fis = new FileInputStream(inputFile);
			FileOutputStream fos = new FileOutputStream(outputFile);
			BinaryIn in = new BinaryIn(fis);
			
			HuffmanTrieNode r = readTrie(in);
			int totalChar = in.readInt();
						
			for(int i = 0 ; i < totalChar ; i++)
			{
				HuffmanTrieNode n = r;
				while(!n.isLeaf())
				{
					int b = in.readBits(1);
					if(b == 0) n = n.left;
					else n = n.right;					
				}
				fos.write(n.ch);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Huffman.compress("genomeVirus.txt", "genomeVirus.huffman");
		Huffman.expand("genomeVirus.huffman", "genomeVirus.out.txt");
	}

}

