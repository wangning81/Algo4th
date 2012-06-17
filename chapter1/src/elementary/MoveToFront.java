package elementary;

import java.io.IOException;

public class MoveToFront {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LinkedList<Character> cache = new LinkedList<Character>();
		while(true)
		{
			char c;
			try {
				c = (char) System.in.read();
				if(c != '\r' && c != '\n')
				{
					if(cache.contains(c) != -1)
						cache.remove(c);
					cache.add(c);
					printList(cache);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	static void printList(LinkedList<Character> l)
	{
		for(char c : l)
		{
			System.out.print(c + ",");			
		}
		System.out.println();
	}

}
