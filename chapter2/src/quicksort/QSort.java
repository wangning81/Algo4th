package quicksort;

import java.util.Random;

import sorthelpers.SortHelper;

public class QSort {
	
	public static <T extends Comparable<T>> void ThreeWayQSort(T[] a)
	{
		ThreeWayQSort(a, 0, a.length - 1);
	}
	
	public static <T extends Comparable<T>> void ThreeWayQSort(T[] a, int low, int high)
	{
		if(low < high)
		{
			T piv = a[low];
			int lt = low - 1, gt = high + 1;
			int i = low + 1;
			
			while(i < gt)
			{
				if(SortHelper.Less(a[i], piv))
					SortHelper.Exch(a, ++lt, i++);
				else if(SortHelper.Less(piv, a[i]))
					SortHelper.Exch(a, --gt, i);
				else i++;
			}
			
			ThreeWayQSort(a, low, lt);
			ThreeWayQSort(a, gt, high);
		}
	}
	
	public static <T extends Comparable<T>> void sort(T[] a)
	{
		sort(a, 0, a.length - 1);
	}
	
	private static <T extends Comparable<T>> void sort(T[] a, int low, int high)
	{
		if(low < high)
		{
			int p = partition(a, low, high);
			sort(a, low, p - 1);
			sort(a, p + 1, high);
		}
	}
	
	private static <T extends Comparable<T>> int partition(T[] a, int low, int high)
	{
		T piv = a[low];
		int i = low + 1, j = high;
		while(i <= j)
		{
			while(i <= j && a[i].compareTo(piv) <= 0)
				i++;
			while(i <= j && a[j].compareTo(piv) >= 0)
				j--;
			if(i > j) break;
			SortHelper.Exch(a, i, j);
		}
		SortHelper.Exch(a, low, j);
		return j;
	}
	
	private static <T extends Comparable<T>> T select(T[] a, int k)
	{
		if(k < 0 || k >= a.length) return null;
		
		int low = 0, high = a.length - 1;
		
		while(low < high)
		{
			int p = partition(a, low, high);
			if( p < k ) low = p + 1;
			else if( p > k) high = p - 1;
			else break;
		}
		
		return a[k];
	}
	
	

	public static void main(String[] args) {
		
		int N = 2000000;
		
		Integer[] arr = SortHelper.GenerateRandomArray(N);
		
		Random rd = new Random();
		
		int rdi = rd.nextInt(N);
		
		int ss = select(arr, rdi);
		
		System.out.println(SortHelper.IsSorted(arr));
		sort(arr, 0, arr.length - 1);
		//SortHelper.PrintArray(arr);
		System.out.println(SortHelper.IsSorted(arr));
		
		assert(ss == arr[rdi]);
		
		System.out.println(ss);
		System.out.println(arr[rdi]);

	}

}
