package elementary;

public class BitonicSearch {
	
	static int findMaxValue(int[] a)
	{
		int low = 0, high = a.length - 1;
		
		int maxPos = -1;
				
		while(low < high)
		{
			int mid = low + (high - low) / 2;
			if(mid > 0 && mid < a.length - 1 && a[mid] > a[mid - 1] && a[mid] > a[mid + 1])
			{
				low = high = mid;
				break;
			}
			else if(a[mid] > a[mid + 1])
			{
				high = mid - 1;
			}
			else
			{
				low = mid + 1;
			}
		}
		
		maxPos = low;
		return maxPos;
	}
	
	static int binarySearch(int[] arr, int low, int high, int key, boolean acs)
	{
		while(low <= high)
		{
			int mid = low + (high - low) / 2;
			if(arr[mid] == key) return mid;
			else if(arr[mid] > key) 
			{
				if(acs) high = mid - 1;
				else low = mid + 1;
			}
			else 
			{
				if(acs) low = mid + 1;
				else high = mid - 1;
			}
		}
		return -1;
	}
	
	static int bitonicSearch(int[] arr, int key)
	{
		int maxPos = findMaxValue(arr);
		if(arr[maxPos] == key)
			return maxPos;
		
		int lowIndex = binarySearch(arr, 0, maxPos -1, key, true);
		if(lowIndex != -1) return lowIndex;
		else return binarySearch(arr, maxPos + 1, arr.length - 1, key, false);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr1 = new int[]{1,13,7,6,2,0};
		int[] arr2 = new int[]{1,2,7,16,22,0};
		int[] arr3 = new int[]{1,3,5,7,6,2,0};
		int[] arr4 = new int[]{7,6,2,0};
		int[] arr5 = new int[]{1,2,3,5,7};
		int[] arr6 = new int[]{1,2};
		int[] arr7 = new int[]{2,1};
		int[] arr8 = new int[]{1};
		
		System.out.println(bitonicSearch(arr1,7));
		System.out.println(bitonicSearch(arr2,7));
		System.out.println(bitonicSearch(arr3,7));
		System.out.println(bitonicSearch(arr4,7));
		System.out.println(bitonicSearch(arr5,7));
		System.out.println(bitonicSearch(arr6,2));
		System.out.println(bitonicSearch(arr7,2));
		System.out.println(bitonicSearch(arr8,2));
		
		

	}

}
