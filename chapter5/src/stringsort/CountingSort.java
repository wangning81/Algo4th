package stringsort;

public class CountingSort {
	
	public static <T> void sort(int[] key, int R, T[] items)
	{
		if(key.length != items.length)
			throw new IllegalArgumentException();
		int N = key.length;
		int[] count = new int[R + 1];
		int[] keyAux = new int[N];
		@SuppressWarnings("unchecked")
		T[] itemAux = (T[])new Object[N];
		
		//1. count the frequency
		for(int i = 0 ; i < N ; i++)
			count[key[i] + 1]++;
		
		//2. convert frequency to index
		for(int i = 1 ; i < R ; i++)
			count[i + 1] += count[i];
		
		//3. sort in aux
		for(int i = 0 ; i < N ; i++)
		{
			int k = key[i];
			keyAux[count[k]] = key[i];
			itemAux[count[k]] = items[i];
			count[k]++;
		}
		
		//4. copy back
		for(int i = 0 ; i < N ; i++)
		{
			key[i] = keyAux[i];
			items[i] = itemAux[i];
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] keys = new int[]
		                     {
				2, 3, 3, 4, 
				1, 3, 4, 3, 
				1, 2, 2, 1, 
				2, 4, 3, 4, 
				4, 2, 3, 4
		                 };
		
		String[] items = new String[]{
				"Anderson",	"Brown", "Davis", "Garcia",
				"Harris", "Jackson", "Johnson", "Jones",
				"Martin", "Martinez", "Miller", "Moore",
				"Robinson", "Smith", "Taylor", "Thomas",
				"Thompson", "White", "Williams", "Wilson"
		};
		
		CountingSort.sort(keys, 5, items);
		
		for(int i = 0 ; i < keys.length ; i++)
			System.out.println(items[i] + " " + keys[i]);

	}

}
