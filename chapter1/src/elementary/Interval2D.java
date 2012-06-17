package elementary;


public class Interval2D {
	private Interval x;
	private Interval y;
	
	public Interval2D(Interval x, Interval y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double area()
	{
		return x.length() * y.length();
	}
	
	public boolean intersects(Interval2D that)
	{
		return this.x.intersects(that.x) && this.y.intersects(that.y);		
	}
	
	public boolean contains(Point2D p)
	{
		return this.x.contains(p.getX()) && this.y.contains(p.getY());
	}
	
	@Override
	public int hashCode()
	{
		return x.hashCode() ^ y.hashCode();		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
