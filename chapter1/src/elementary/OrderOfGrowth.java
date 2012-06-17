package elementary;
public class OrderOfGrowth {

	static int func1(int N)
	{
		int sum = 0;
		for(int i = N ; i > 0 ; i /= 2 )
			for(int j = 0 ; j < i ; j++)
				sum++;
		return sum;
	}
	
	static int func2(int N)
	{
		int sum = 0;
		for (int i = 1 ; i < N; i *= 2)
			for (int j = 0; j < i; j++)
		sum++;
		return sum;
	}
	
	static int func3(int N)
	{
		int sum = 0;
		for (int i = 1 ; i < N; i *= 2)
			for (int j = 0; j < N; j++)
		sum++;
		return sum;
	}
	
	static void printSameElementInArrays(int[] a, int[] b)
	{
		int i = 0 , j = 0;
		while(i < a.length && j < b.length)
		{
			if(a[i] < b[j])
				i++;
			else if(a[i] > b[j])
				j++;
			else
			{
				System.out.println(a[i]);
				while(i < a.length && j < b.length && a[i++] == b[j++])
					;
			}
		}
	}
	
	static int findLowestKey(int[] a, int key)
	{
		int low = 0 , high = a.length - 1;
		int lowIndex = high + 1;
		while(low <= high)
		{
			int mid = low + ( high - low ) / 2;
			if(a[mid] == key)
			{
				if(mid < lowIndex)
					lowIndex = mid;
				high = mid - 1;
			}
			else if(a[mid] > key)
			{
				high = mid - 1;
			}
			else
			{
				low = mid + 1;
			}
		}
		
		return lowIndex == a.length + 1 ? -1 : lowIndex;		
	}
	
	static int findHighestKey(int[] a, int key)
	{
		int low = 0 , high = a.length - 1;
		int highIndex = -1;
		while(low <= high)
		{
			int mid = low + ( high - low ) / 2;
			if(a[mid] == key)
			{
				if(mid > highIndex)
					highIndex = mid;
				low = mid + 1;
			}
			else if(a[mid] > key)
			{
				high = mid - 1;
			}
			else
			{
				low = mid + 1;
			}
		}
		
		return highIndex;		
	}
	
	static int howMany(int[] a, int key)
	{
		int lowIndex = findLowestKey(a, key);
		if(lowIndex != -1)
			return findHighestKey(a, key) - lowIndex + 1;
		else 
			return -1;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(func1(1024));
		System.out.println(func2(1024));
		System.out.println(func3(1024));
		
		int[] arr1 = new int[]{1,2,3,4,5,6,6,6,6};
		int[] arr2 = new int[]{2,4,6,6,6,6,6};
		printSameElementInArrays(arr1, arr2);
		
		System.out.print(howMany(arr1, 5));

	}

}
