package elementary;

public class Stopwatch {
	private long start;
	public Stopwatch(){}
	public void start()
	{
		start = System.currentTimeMillis();
	}
	public double ticks()
	{
		return System.currentTimeMillis() - start;
	}
	
	public double elapsedSeconds()
	{
		return ticks() / 1000;
	}
}
