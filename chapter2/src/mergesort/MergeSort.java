package mergesort;

import java.util.Arrays;
import sorthelpers.SortHelper;

public class MergeSort {
	
	static void merge(int[] a, int low, int mid, int high, int[] aux)
	{
		int i = low, j = mid + 1;
		for(int k = low; k <= high ; k++)
			aux[k] = a[k];
		
		for(int k = low ; k <= high; k++)
			if(i > mid) a[k] = aux[j++];
			else if(j > high) a[k] = aux[i++];
			else if(aux[i] < aux[j]) a[k] = aux[i++];
			else a[k] = aux[j++];
	}

	static int[] generateRandomArray(int n)
	{
		int[] ret = new int[n];
		for(int i = 0 ; i < n ; i++)
		{
			ret[i] = SortHelper.uniform(-n * 2, n * 2);			
		}
		return ret;
	}
	
	static void natualMergeSort(int[] a)
	{
		int i = 0;
		int[] aux = new int[a.length];
		
		while(i < a.length - 1)
		{
			int j = i + 1;
			while(j <= a.length - 2 && a[j] <= a[j+1] )
				j++;
			merge(a, 0, i, j, aux);
			i = j;
		}
	}
	
	static void printArray(int[] a)
	{
		for(int c : a)
		{
			System.out.print(c + " ");
		}
		System.out.println();
	}
	
	static boolean isSorted(int[] a)
	{
		for(int i = 0 ; i < a.length - 1; i++)
			if(a[i] > a[i + 1])
				return false;
		return true;
	}
	
	public static void main(String[] args) {
		int[] ra = generateRandomArray(10000000);
		//printArray(ra);
		//natualMergeSort(ra);
		long tick = System.currentTimeMillis();
		Arrays.sort(ra);
		System.out.println(System.currentTimeMillis() - tick);		//printArray(ra);

	}

}
