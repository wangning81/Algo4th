package elementary;
import java.util.Arrays;

public class RandomMatch {
	static int[] randomArr(int len)
	{
		int[] ret = new int[len];
		for(int i = 0 ; i < len; i++)
		{
			ret[i] = StdRandom.uniform(100000, 1000000);
		}
		return ret;
	}
	
	static int binarySearch(int key, int[] a)
	{
		int low = 0 , high = a.length - 1;
		while(low <= high)
		{
			int mid = low + (high - low) / 2;
			if(a[mid] < key) low = mid + 1;
			else if(a[mid] > key) high = mid - 1;
			else return mid;
		}
		return -1;
	}
	
	static int dupCount(int[] a)
	{
        int i = 0;
        int dupCount = 0;
        for( ; i + dupCount < a.length ; i++)
        {
        	a[i] = a[i + dupCount];
        	for(int j = i + dupCount + 1 ; j < a.length && a[j] == a[i] ; j++)
        	{
        		dupCount++;
        	}        	
        }
        return dupCount;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] N = new int[4];
		N[0] = 1000;
		N[1] = 10000;
		N[2] = 100000;
		N[3] = 1000000;
		
		double[] result = new double[4];
		
		int T = 100;
		
		for(int i = 0 ; i < N.length ; i++)
		{
			int n = N[i];
			int sumOfDup = 0;
			for(int j = 0 ; j < T; j++)
			{
				int[] a1 = randomArr(n);
				int[] a2 = randomArr(n);
				
				Arrays.sort(a1);
				Arrays.sort(a2);
				
				for(int k = 0 ; k < a1.length ; k++)
				{
					if( binarySearch(a1[k], a2) != -1)
					{
						//System.out.println("---->" + a1[k]);
						sumOfDup++;
					}
				}
			}
			
			result[i] = sumOfDup * 1.0 / T;
			System.out.println(result[i]);
		}
		
		
	}

}
