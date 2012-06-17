package mergesort;

import sorthelpers.SortHelper;

public class NaturalMergeSort {
	
	static void merge(int[] a, int low, int mid, int high, int[] aux)
	{
		for(int i = low ; i <= high ; i++)
			aux[i] = a[i];
		
		int i = low, j = mid + 1;
		for(int k = low ; k <= high ; k++)
			if(i > mid) a[k] = aux[j++];
			else if(j > high) a[k] = aux[i++];
			else if(aux[i] < aux[j]) a[k] = aux[i++];
			else a[k] = aux[j++];
		
	}
	
	static void naturalMergeSort(int[] a)
	{
		int n = a.length;
		int[] aux = new int[n];
		
		int mid = 0;
		while(mid < n - 1)
		{
			int h = mid + 1;
			while(h < n - 1 && a[h] <= a[h + 1] )
				h++;
			merge(a, 0, mid, h, aux );
			mid = h;			
		}
	}
	
	static void naturalMergeSort2(int[] a)
	{
		int n = a.length;
		boolean sorted = false;
		int[] aux = new int[n];
		
		while(!sorted)
		{
			int low = 0, mid = 0, high = 0;
			while(low < n - 1)
			{
				for(	
						mid = low ;
						mid < n - 1 && a[mid] <= a[mid + 1];
						mid++
					);
				
				if(low == 0 && mid == n -1)
				{
					sorted = true;
					break;
				}
				
				for(
						high = mid + 1;
						high < n - 1 && a[high] <= a[high + 1];
						high++
					);
				if(mid < n -1)
					merge(a, low, mid, high, aux);
				low = high + 1;
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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = generateRandomArray(2000000);
		//printArray(a);
		System.out.println(isSorted(a));
		naturalMergeSort2(a);
		System.out.println(isSorted(a));
		//printArray(a);

	}

}
