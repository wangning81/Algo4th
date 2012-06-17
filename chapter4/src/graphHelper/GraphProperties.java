package graphHelper;

import elementary.ArrayQueue;
import undirectedgraph.Graph;

public class GraphProperties {
	private int[] eccentricity;
	private int diameter;
	private int radius;
	private int center;
	private int girth;
	private Graph g;
		
	public GraphProperties(Graph g)
	{
		eccentricity = new int[g.verticeCount()];
		diameter = 0;
		radius = g.verticeCount() + 1;
		center = -1;
		girth = Integer.MAX_VALUE;
		this.g = g;
				
		for(int i = 0 ; i < g.verticeCount(); i++)
			bfs(g, i);
		
	}
	
	private void bfs(Graph g, int s)
	{
		boolean[] marked = new boolean[g.verticeCount()];
		ArrayQueue<Integer> q = new ArrayQueue<Integer>(); 
		ArrayQueue<Integer> disQ = new ArrayQueue<Integer>();
		
		marked[s] = true;
		q.enqueue(s);
		disQ.enqueue(0);
		
		while(!q.isEmpty())
		{
			int u = q.dequeue();
			int dis = disQ.dequeue();
			if(dis > eccentricity[s])
				eccentricity[s] = dis;
			
			for(int v : g.adj(u))
			{
				if(!marked[v])
				{
					marked[v] = true;
					q.enqueue(v);	
					disQ.enqueue(dis + 1);
				}
			}
		}
		
		if(eccentricity[s] > diameter)
			diameter = eccentricity[s];
		
		if(eccentricity[s] < radius)
		{
			radius = eccentricity[s];
			center = s;
		}
	}
	
	public int diameter()
	{
		return diameter;
	}
	
	public int eccentricity(int v)
	{
		return eccentricity[v];
	}
	
	public int center()
	{
		return center;
	}
	
	public int radius()
	{
		return radius;
	}
	
	public int girth()
	{
		int n = g.verticeCount();
		for(int s = 0 ; s < n; s++)
		{
			int circleLen = bfsFindShortestCircle(s);
			if(circleLen < this.girth)
				this.girth = circleLen;
		}
		return this.girth;
	}

	private int bfsFindShortestCircle(int s) {
		int n = g.verticeCount();
		ArrayQueue<Integer> q = new ArrayQueue<Integer>();
		int[] edgeTo = new int[n];
		boolean[] marked = new boolean[n];
		
		marked[s] = true;
		edgeTo[s] = s;
		q.enqueue(s);
		
		while(!q.isEmpty())
		{
			int u = q.dequeue();
			for(int v : g.adj(u))
			{
				if(!marked[v])
				{
					marked[v] = true;
					edgeTo[v] = u;
					q.enqueue(v);
				}
				//marked and it's not the source vertex, this is the shorted circle
				else if(v != edgeTo[u])
				{
					return calcCircleLen(u, v, edgeTo);									
				}
			}
		}
		
		return Integer.MAX_VALUE;
	}
	
	private int calcCircleLen(int u, int v, int[] edgeTo)
	{
		int ret = 1;
		while(u != v)
		{
			if(u != edgeTo[u])
			{
				u = edgeTo[u];
				ret++;
			}
			if(v != edgeTo[v])
			{
				v = edgeTo[v];
				ret++;
			}
		}
		return ret;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
/*		int nV = 3;
		int[] uS = new int[]{0, 1, 2};
		int[] vS = new int[]{1, 2, 0};
	
/*		int nV = 3;
		int[] uS = new int[]{0, 1};
		int[] vS = new int[]{1, 2};
*/
/*		
		int nV = 4;
		int[] uS = new int[]{0, 1, 2, 3};
		int[] vS = new int[]{1, 2, 3, 0};
*/
		
/*		int nV = 5;
		int[] uS = new int[]{0, 1, 2, 3, 4};
		int[] vS = new int[]{1, 2, 3, 4, 2};
*/
		
/*		int nV = 7;
		int[] uS = new int[]{0, 1, 2, 0, 3, 4, 5};
		int[] vS = new int[]{1, 2, 0, 3, 4, 5, 6};*/
		
/*		int nV = 7;
		int[] uS = new int[]{0, 1, 2, 3, 0, 4, 5, 6};
		int[] vS = new int[]{1, 2, 3, 0, 4, 5, 6, 0};
		
		
		Graph g = GraphBuilder.FromArray(nV, uS, vS);*/
		
		Graph g = GraphBuilder.FromFile("mediumG.txt");
		
		GraphProperties gp = new GraphProperties(g);
		System.out.println(gp.girth());
		
	}

}
