package network;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import elementary.ArrayQueue;

public class ShotestAugmentingPath {
	private FlowNetwork network;
	private int s, t;
	private FlowEdge[] edgeTo;
	private boolean[] marked;
	private int V;
	private double flowValue;
		
	public ShotestAugmentingPath(FlowNetwork network, int s, int t)
	{
		this.network = network;
		this.s = s;
		this.t = t;
		this.V = network.V();
		
		while(hasAugmentingPath())
		{
			//find the bottleneck (the minimum augmenting capacity)
			double bottleneck = Double.POSITIVE_INFINITY;
			for(int v = t ; v != s ; v = edgeTo[v].from())
			{
				FlowEdge u = edgeTo[v];
				double res = u.residualCapicityTo(v);
				if(res < bottleneck)
					bottleneck = res;
			}
			
			//augment the path.
			for(int v = t ; v != s ; v = edgeTo[v].from())
			{
				FlowEdge e = edgeTo[v];
				e.addFlowTo(v, bottleneck);
			}
			flowValue += bottleneck;
		}
	}
	
	public double maxFlowValue()
	{
		return flowValue;
	}
	
	public boolean isInMinCut(int v)
	{
		return marked[v];
	}
	
	private boolean hasAugmentingPath()
	{
		edgeTo = new FlowEdge[V];
		marked = new boolean[V];
		
		ArrayQueue<Integer> q = new ArrayQueue<Integer>();
		marked[s] = true;
		q.enqueue(s);
		
		while(!q.isEmpty())
		{
			int u = q.dequeue();
			for(FlowEdge e : network.adj(u))
			{
				int v = e.other(u);
				if(e.residualCapicityTo(v) > 0 && !marked[v])
				{
					q.enqueue(v);
					edgeTo[v] = e;
					marked[v] = true;
				}
			}
		}
		return marked[t];
	}
	
	public static void main(String[] args)
	{
		try {
			FileReader fr = new FileReader("tinyFN.txt");
			BufferedReader br = new BufferedReader(fr);
			int v = Integer.parseInt(br.readLine());
			int e = Integer.parseInt(br.readLine());
			
			FlowNetwork fn = new FlowNetwork(v);
			
			for(int i = 0 ; i < e ; i++)
			{
				String line = br.readLine();
				String[] tokens = line.split(" ");
				fn.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]));
			}
			
			ShotestAugmentingPath sap = new ShotestAugmentingPath(fn, 0 , 5);
			System.out.println(sap.maxFlowValue());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
