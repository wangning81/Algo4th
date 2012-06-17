package elementary;

public class Interval {
	private double low;
	private double high;
	public Interval(double low, double high)
	{
		this.low = low;
		this.high = high;
	}
	
	public boolean contains(double x)
	{
		return x >= low && x <= high;
	}
	
	public boolean intersects(Interval that)
	{
		return 	   this.contains(that.low) 
				|| this.contains(that.high) 
				|| that.contains(this.low) 
				|| that.contains(this.high);
	}
	
	public double length()
	{
		return high - low;
	}
	
	@Override
	public int hashCode()
	{
		return (int) ((Double.doubleToLongBits(low) ^ Double.doubleToLongBits(high)) % Integer.MAX_VALUE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
