package mergesort;

import sorthelpers.SortHelper;

public class BottomUpMergeSort {
	
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
	
	static void bottomUpMergeSort(int[] a)
	{
		int n = a.length;
		int[] aux = new int[n];
		for( int i = 1; i < n ; i *=2)
		{
			for(int j = 0 ; j < n ; j += 2 * i)
			{
				merge(a, j, j + i - 1, Math.min(j + 2 * i - 1, n - 1), aux);				
			}
		}
		
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
	
	static boolean isSorted(int[] a)
	{
		for(int i = 0 ; i < a.length - 1; i++)
			if(a[i] > a[i + 1])
				return false;
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = generateRandomArray(2000000);
		//printArray(a);
		System.out.println(isSorted(a));
		bottomUpMergeSort(a);
		System.out.println(isSorted(a));
	}

}
