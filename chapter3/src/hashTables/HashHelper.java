package hashTables;

public final class HashHelper {
	
	/*
	 * 
	 * Write a program to find values of a and M, with M as small as possible, 
	 * such that the hash function (a * k) % M for transforming the kth letter of the alphabet 
	 * into a table index produces distinct values (no collisions) for the keys S E A R C H X M P L. 
	 * The result is known as a perfect hash function.
	 * 
	 */
	public static int[] getPerfectHashingParameter(char[] keys)
	{
		int[] ret = new int[2];
		
		for(int M = keys.length ; ; M++)
		{
			boolean[] marker = new boolean[M];
			boolean hasCollision = false;
			int a = 1;
			for( ; a < M ; a++)
			{
				hasCollision = false;
				clearMarker(marker);
				for(int i = 0 ; i < keys.length && !hasCollision ; i++)
				{
					int hash = hash(keys[i], a, M);
					if(!marker[hash])
						marker[hash] = true;
					else
						hasCollision = true;
				}
				
				if(!hasCollision)
				{
					ret[0] = a;
					ret[1] = M;
					return ret;
				}
			}
		}
	}
	
	private static int hash(char c, int a, int M)
	{
		return ((a * (c - 'A')) % M);		
	}
	
	private static void clearMarker(boolean[] marker)
	{
		for(int i = 0 ; i < marker.length ; i++)
			marker[i] = false;
	}
	
	//from k = 5 to 31
	//2^k - deltaForPrime[k-5] is a prime;
	private static final int[] deltaForPrime = {1,3,1,5,3,3,9,3,1,3,
										19,15,1,5,1,3,9,3,
										15,3,39,5,39,57,3,35,1};	
	public static int nextPrimeSize(int originalSize, boolean isIncrease)
	{
		int lgM = (int)Math.ceil(Math.log(originalSize)/Math.log(2));
		int index = lgM + (isIncrease ? 1 : -1);
		int newSize = (1 << index) - deltaForPrime[index - 5];
		return newSize;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[] keys = {'S', 'E', 'A', 'R', 'C', 'H', 'X', 'M', 'P', 'L'};
		for(char c : keys)
		{
			//System.out.println(c - 'A');
			System.out.println(hash(c, 7, 13));
		}
		int[] para = getPerfectHashingParameter(keys);
		System.out.println("a = " + para[0] + " M = " + para[1]);
	}

}
