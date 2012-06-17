package elementarysorting;

public class ProduceBestCaseArray {

	static int[] produceBestCaseArray(int n)
	{
		int[] a = new int[n];
		for(int i = 0 ; i < n ; i++)
			a[i] = i;
		produceBestCaseArray(a, 0, n-1);
		return a;
	}
		
	static void produceBestCaseArray(int[] a, int low, int high)
	{
		if(low >= high)
			return;
		int mid = low + (high - low) / 2;
		produceBestCaseArray(a, low, mid - 1);
		produceBestCaseArray(a, mid + 1, high);
		exch(a, low, mid);
	}
	
	static void exch(int[] a, int i, int j)
	{
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = produceBestCaseArray(10);
		for(int i : a)
			System.out.println(i);

	}

}
