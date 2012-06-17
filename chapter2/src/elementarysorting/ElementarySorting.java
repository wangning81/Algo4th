package elementarysorting;

import sorthelpers.SortHelper;

public class ElementarySorting {
	public static <T> void selectionSort(Comparable<T>[] a)
	{
		int n = a.length;
		for(int i = 0 ; i < n ; i++)
		{
			int min = i;
			for(int j = i + 1 ; j < n ; j++ )
				if(SortHelper.Less(a[j], a[min]) )
					min = j;
			SortHelper.Exch(a, i, min);								
		}
	}
	
	public static <T> void insertionSort(Comparable<T>[] a)
	{
		int n = a.length;
		for(int i = 1 ; i < n ; i++)
			for(int j = i ; j > 0 && SortHelper.Less(a[j], a[j - 1]) ; j--)
				SortHelper.Exch(a, j, j - 1);
	}
	
	public static <T> void insertionSort(Comparable<T>[] a, int low, int high)
	{
		for(int i = low ; i < high ; i++)
		{
			int j = i + 1;
			Comparable<T> t = a[j];
			for( ; j > 0 && SortHelper.Less(t, a[j - 1]) ; j--)
			{
				a[j] = a[j - 1];
			}
			a[j] = t;
		}
	}
	
	
	
	public static <T> void shellSort(Comparable<T>[] a)
	{ // Sort a[] into increasing order.
		int N = a.length;
		int h = 1;
		int compare = 0;
		while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
		while (h >= 1)
		{ // h-sort the array.
			for (int i = h; i < N; i+=h )
			{ // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
				compare++;
				for (int j = i; j >= h && SortHelper.Less(a[j], a[j-h]); j -= h)
					SortHelper.Exch(a, j, j-h);
			}
			h = h/3;
		}
		
		System.out.println("Compare/ArraySize = " + compare * 1.0 / N );
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[] arr = SortHelper.GenerateRandomArray(10000);
		insertionSort(arr, 0, arr.length - 1);
		SortHelper.PrintArray(arr);
		System.out.println(SortHelper.IsSorted(arr));
	}

}
