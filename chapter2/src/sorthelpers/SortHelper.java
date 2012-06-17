package sorthelpers;

import java.util.Random;

public class SortHelper {
	@SuppressWarnings("unchecked")
	public static <T> boolean Less(Comparable<T> a, Comparable<T> b)
	{
		return a.compareTo((T)b) < 0;
	}
	
	public static <T> void Exch(Comparable<T>[] a, int i, int j)
	{
		Comparable<T> t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	
	public static <T> void PrintArray(Comparable<T>[] a)
	{
		for(Comparable<T> c : a)
		{
			System.out.print(c + " ");
		}
		System.out.println();
	}
	
	public static <T extends Comparable<T>> boolean IsSorted(T[] a)
	{
		for(int i = 0 ; i < a.length - 1; i++)
			if(a[i].compareTo(a[i + 1]) > 0)
				return false;
		return true;
	}
	
	public static Integer[] GenerateRandomArray(int n)
	{
		Integer[] ret = new Integer[n];
		for(int i = 0 ; i < n ; i++)
		{
			ret[i] = uniform(-n * 2, n * 2);			
		}
		return ret;
	}
	
	public static int uniform(int low, int high)
	{
		Random rd = new Random();
		return low + (int)((high - low) * rd.nextDouble());
	}

}
