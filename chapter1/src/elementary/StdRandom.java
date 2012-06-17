package elementary;

import java.util.Random;

public class StdRandom {
	public static int uniform(int lo, int hi)
	{
		Random r = new Random();
		return (int)(lo + (hi - lo) * r.nextDouble());
	}
	
	public static int uniform(int upper)
	{
		return uniform(0, upper);		
	}
}
