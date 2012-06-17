package elementary;

class NumberGenerator
{
	private int theNumber;
	private int range;
	private int previousGuess = -1;
	public NumberGenerator(int n)
	{
		theNumber = StdRandom.uniform(1, n + 1);
		range = n;
	}
	
	public NumberGenerator(int n, int theNumber)
	{
		this.theNumber = theNumber;
		range = n;
	}
	
	public int guess(int g)
	{
		int oldPrev = previousGuess;
		previousGuess = g;
		
		if(oldPrev == -1)
			return -2;
		if(g == theNumber)
			return 0;
		
		
		if(Math.abs(oldPrev - theNumber) > Math.abs(g - theNumber))
			return 1;
		else
			return -1;
	}
	
	public int range()
	{
		return range;
	}
	
	public int answer()
	{
		return theNumber;
	}
	
	public boolean reset(int firstGuess)
	{
		previousGuess = firstGuess;
		if(firstGuess == theNumber)
			return true;
		return false;
	}
}

public class HotOrCold {
	
	static int hotOrColdFast(NumberGenerator ng)
	{
		int low = 1 , high = ng.range();
		if(ng.reset(low))
		;	//return low;
		
		int lastN = low;		
		while(high - low > 1)
		{
			int mid = low + (high - low)/2;
			int r = ng.guess(mid);
			if(r == 0) return mid;
			else if(r == 1)
			{
				if(mid > lastN)
				{
					low = lastN + (mid - lastN)/2 + 1;					
				}
				else
				{
					high = lastN - (lastN - mid)/2 - 1;
				}
			}
			else if(r == -1)
			{
				if(mid > lastN)
				{
					//high = (int) Math.floor(mid - (mid - lastN)/2.0);
					high = mid;
				}
				else
				{
					//low = (int) Math.ceil(mid + (lastN - mid)/2.0);
					low = mid;
				}				
			}
			lastN = mid;
			//System.out.println("low = " + low + " high = " + high + " r=" + r + " mid=" + mid);
		}
		
		if( ng.guess(low) == 0) 
			return low; 
		else if(ng.guess(high) ==0)
			return high;
		else
			return low + (high - low) /2;
	}
	
	static int hotOrCold(NumberGenerator ng)
	{
		int low = 1 , high = ng.range(), n = ng.range();
		ng.reset(low);
		
		
		while(true)
		{
			int mid = low + (high - low)/2;
			int r = ng.guess(mid);
			if(r == 0) return mid;
			else if(mid == 0)
				low = mid + 1;
			else if(mid == n)
				high = mid - 1;
			else
			{
				r = ng.guess(mid - 1);
				if(r == -1)
					low = mid + 1;
				else
					high = mid -1;
			}
			
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//436
		for(int i = 1 ; i <= 1000000 ; i++ )
		{
			for(int j = 1 ; j <= i ; j++)
			{
				NumberGenerator ng = new NumberGenerator(i, j);
				
				//System.out.println(hotOrColdFast(ng));
				System.out.println("N = " + i + " A = " + ng.answer() + " P = " + hotOrColdFast(ng));
			}
		}
	}

}
