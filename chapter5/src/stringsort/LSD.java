package stringsort;

public class LSD {
	
	//sort keys with last (least) w characters
	public static void sort(String[] keys, int w)
	{
		if(keys.length <= 0) return;
		
		int N = keys.length;
		int R = 256;
		int strLen = keys[0].length();
				
		for(int i = 1 ; i <= w ; i++)
		{
			String[] aux = new String[N];
			int[] count = new int[R + 1];
			int j = strLen - i;
			
			//1. count;
			for(int k = 0 ; k < N ; k++)
				count[keys[k].charAt(j) + 1]++;
			
			//2. to index
			for(int k = 0 ; k < R ; k++)
				count[k + 1] += count[k];
			
			//3. sort to aux;
			for(int k = 0 ; k < N ; k++)
				aux[count[keys[k].charAt(j)]++] = keys[k];
			
			//4. copy back
			for(int k = 0 ; k < N ; k++)
				keys[k] = aux[k];
			
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] input = new String[]{
				"4PGC938",
				"2IYE230",
				"3CIO720",
				"1ICK750",
				"1OHV845",
				"4JZY524",
				"1ICK750",
				"3CIO720",
				"1OHV845",
				"1OHV845",
				"2RLA629",
				"2RLA629",
				"3ATW723"	
		};
		
		LSD.sort(input, input[0].length());
		
		for(String s : input)
			System.out.println(s);

	}

}
