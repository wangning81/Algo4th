package stringsort;

import elementarysorting.ElementarySorting;;

public class MSD {
	
	private static String[] aux;
	private static int N;
	private static int R = 256;
	private static int M = 7; //insertion cut-off.
	
	public static void sort(String[] a)
	{
		N = a.length;
		aux = new String[a.length];
		sort(a, 0, N - 1, 0);
	}
	
	private static int charAt(String s, int k)
	{
		return k < s.length() ? s.charAt(k) : -1;
	}
	
	private static void sort(String[] a, int low, int high, int d)
	{
		if(high - low < M)
		{
			ElementarySorting.insertionSort(a, low, high);
			return;
		}
		
		int[] count = new int[R+2];
		
		//1.count;
		for(int i = low ; i <= high ; i++)
			count[charAt(a[i], d) + 2]++;
		
		//2. to index
		for(int i = 0 ; i < R + 1 ; i++)
			count[i + 1] += count[i];
		
		//3. distribute
		for(int i = low ; i <= high ; i++)
			aux[count[charAt(a[i], d) + 1]++] = a[i];
		
		//4. copy back
		for(int i = low ; i <= high ; i++)
			a[i] = aux[i - low];
		
		//Recursively sort 
		for(int r = 0 ; r < R ; r++)
			sort(a, low + count[r], low + count[r+1] - 1, d + 1);
		
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
		
		MSD.sort(ss);
		
		for(String s : ss)
		{
			System.out.println(s);
		}

	}

}
