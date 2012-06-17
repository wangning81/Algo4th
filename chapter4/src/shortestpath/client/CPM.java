package shortestpath.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import shortestpath.AcyclicLP;
import undirectedgraph.DirectedEdge;
import directedgraph.DirectedEdgeWeightGraph;

public class CPM {
	
	private int s;
	private int t;
	private int jobCount;
	private AcyclicLP alp;
	
	public CPM(String inputFile)
	{
		DirectedEdgeWeightGraph dewg = buildGraph(inputFile);
		alp = new AcyclicLP(dewg, s);		
	}
	
	public double totalDuration()
	{
		return alp.distTo(t);
	}
	
	public double[] startTimeForEachJob()
	{
		double[] ret = new double[jobCount];
		for(int v = 0 ; v < jobCount ; v++)
			ret[v] = alp.distTo(v);
		return ret;
	}
	
	private DirectedEdgeWeightGraph buildGraph(String inputFile)
	{
		try {
			FileInputStream fis = new FileInputStream(inputFile);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader  bsr = new BufferedReader(new InputStreamReader(dis));
			
			//first V;
			int N = Integer.parseInt(bsr.readLine());
			
			//N vertices, another N for "shadow" vertices, 2 for artificial source and sink
			DirectedEdgeWeightGraph g = new DirectedEdgeWeightGraph(N * 2 + 2);
			//0..N - 1 N original vertices
			//N..2N - 1 N shadow vertices
			//2N source - s
			//2N + 1 sink - t
			this.s = 2 * N;
			this.t = 2 * N + 1;
			this.jobCount = N;
			
			for(int v = 0 ; v < N ; v++)
			{
				String line = bsr.readLine();
				String[] tokens = line.split(" ");
				double duration = Double.parseDouble(tokens[0]);
				
				g.addEdge(new DirectedEdge(2 * N, v, 0.0)); //s -> v with weight = 0.0
				
				int vShadow = N + v;
				g.addEdge(new DirectedEdge(v, vShadow , duration)); // v -> v' with weight = duration.
				g.addEdge(new DirectedEdge(vShadow, 2 * N + 1, 0.0)); //v -> t with weight = 0.0
				
				//adding constraints, if any.
				for(int i = 3 ; i < tokens.length ; i++)
				{
					if(!tokens[i].trim().equals(""))
					{
						int w = Integer.parseInt(tokens[i]);
						g.addEdge(new DirectedEdge(vShadow, w, 0.0));
					}
				}
			}
			
			bsr.close();
			dis.close();
			fis.close();
			return g;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CPM cpm = new CPM("jobsPC.txt");
		double totalDuration = cpm.totalDuration();
		System.out.println("Total Duration = " + totalDuration);
		double[] jobStartTimes = cpm.startTimeForEachJob();
		for(int i = 0 ; i < jobStartTimes.length ; i++)
			System.out.println("Job " + i + " : " + jobStartTimes[i]);
		
	}

}
