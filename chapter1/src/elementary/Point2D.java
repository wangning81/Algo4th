package elementary;

public class Point2D {
	private double x;
	private double y;
	
	public static final Point2D Zero = new Point2D(0,0);
	
	public Point2D()
	{}
	public Point2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Point2D(Point2D p)
	{
		this.x = p.x;
		this.y = p.y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getR()
	{
		return distTo(Zero);
	}
	
	public double theta()
	{
		return Math.atan2(y, x);
	}
	
	public double distTo(Point2D that)
	{
		return Math.sqrt(Math.pow(x - that.x, 2) + Math.pow(y - that.y, 2));
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	@Override
	public int hashCode()
	{
		return (int) ((Double.doubleToLongBits(x) ^ Double.doubleToLongBits(y)) % Integer.MAX_VALUE); 
	}
	
	public static void main(String[] args)
	{
		System.out.println(Integer.MAX_VALUE);
	}
	
}
