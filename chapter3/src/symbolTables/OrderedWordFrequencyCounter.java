package symbolTables;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.regex.Pattern;

import binraySearchTree.BinarySearchTreeST;

public class OrderedWordFrequencyCounter{
	private BinarySearchTreeST<String, Integer> st;
	private int minWordLen;
	public OrderedWordFrequencyCounter(BinarySearchTreeST<String, Integer> st, int minWordLen)
	{
		this.st = st;
		this.minWordLen = minWordLen; 
	}
	
	public void feed(File f)
	{
		try {
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(fis);
			Scanner sc = new Scanner(isr);
			sc.useDelimiter(Pattern.compile("[^a-z^A-Z]"));
			
			while(sc.hasNext())
			{
				String word = sc.next().toLowerCase();
				if (word.length() < minWordLen) continue; // Ignore short keys.
				if (!st.contains(word)) st.put(word, 1);
				else st.put(word, st.get(word) + 1);				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ST Size = " + st.size());
				
		st.delete("the");
		st.delete("and");
		st.delete("that");
		st.delete("all");
		st.delete("with");
		st.delete("for");
		st.delete("they");
		st.delete("its");
		st.delete("their");
		st.delete("but");
		st.delete("this");
		st.delete("has");
		st.delete("are");
		st.delete("into");
		st.delete("more");
		st.delete("not");
		st.delete("have");
		st.delete("which");
		st.delete("from");
		st.delete("these");
		
		String max = "";
		st.put(max, 0);
		
		
		PriorityQueue<Stat> pq = new PriorityQueue<Stat>();
		
		pq.add(new Stat("",0));
		
		for(String word : st.keys())
		{
			pq.add(new Stat(word, st.get(word)));
			if(pq.size() > 10)
				pq.remove();
			
		}
		
		for(int i = 0 ; i < 10 ; i++)
		{
			Stat s = pq.remove();
			System.out.println(s.word + " " + s.freq);
		}
	}
		/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
