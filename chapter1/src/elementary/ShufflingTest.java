package elementary;
public class ShufflingTest {
	
	static void shuffle(int[] a)
	{
		int len = a.length;
		for(int i = 0 ; i < len ; i++)
		{
			int r = i + StdRandom.uniform(len - i);
			int t = a[i];
			a[i] = a[r];
			a[r] = t;						
		}
		return;
	}
	
	static void badShuffle(int[] a)
	{
		int len = a.length;
		for(int i = 0 ; i < len ; i++)
		{
			int r = StdRandom.uniform(len);
			int t = a[i];
			a[i] = a[r];
			a[r] = t;						
		}
		return;
	}
	
	public static void initialArr(int[] a)
	{
		for(int i = 0 ; i < a.length ; i++)
		{
			a[i] = i;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = 10000000, M = 10;
		
		int[] originalArr = new int[M];
		int[][] retMatrix = new int[M][M];
		
		for(int i = 0 ; i < N; i++)
		{
			initialArr(originalArr);
			badShuffle(originalArr);
			
			for(int j = 0 ; j < M ; j++)
			{
				retMatrix[originalArr[j]][j] += 1; 
			}
		}
		
		for(int i = 0 ; i < M ; i++)
		{
			for(int j = 0 ; j < M ; j++)
			{
				System.out.print(retMatrix[i][j] + " ");
			}
			System.out.println();
		}

	}

}
