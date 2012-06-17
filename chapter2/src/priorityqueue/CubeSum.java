package priorityqueue;

class Cube implements Comparable<Cube>
{
	public long Sum;
	public long a;
	public long b;
	
	public int compareTo(Cube o) {
		long ret = Sum - o.Sum;
		if( ret > 0) return 1;
		else if(ret < 0) return -1;
		else return 0;
	}
	
	public Cube(long a2, long l)
	{
		this.a = a2;
		this.b = l;
		Sum = a2*a2*a2 + l*l*l;
	}
}

public class CubeSum {
	public static void main(String[] args) {
		
		MinPQ<Cube> pq = new MinPQ<Cube>();
		int upper = 400;
		for(int i = 0 ; i <= upper ; i++)
			pq.enqueue(new Cube(i, 0));
		
		long prevSum = -1;
		Cube prevCube = null;
		
		int totalCount = 0;
		
		while(!pq.isEmpty())
		{
			Cube c = pq.dequeue();
			
			if(prevCube != null && c.Sum == prevSum && c.a != prevCube.b )
			{
				totalCount++;
				System.out.println(totalCount + " FOUND ONE: SUM = " + c.Sum + " a = " + prevCube.a + " b = " + prevCube.b + " c = " + c.a + " d = " + c.b);
			}
			else
			{
				prevCube = c;
				prevSum = c.Sum;
			}
			
			if(c.b < upper)
				pq.enqueue(new Cube(c.a, c.b + 1));
			
			
		}
		
		System.out.println(totalCount);
	}
}
