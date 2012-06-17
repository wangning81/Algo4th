package shortestpath.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import directedgraph.DirectedEdgeWeightGraph;
import elementary.Bag;

import shortestpath.BellmanFordSP;
import undirectedgraph.DirectedEdge;

public class Arbitrage {
	
	private String[] names;
	private DirectedEdgeWeightGraph dewg;
	private Iterable<Integer> arbiV;
		
	public Arbitrage(String file)
	{
		buildDirectedEdgeWeightGraph(file);
		
		BellmanFordSP sp = new BellmanFordSP(dewg, 0);
		if(sp.hasNegativeCycle())
		{
			arbiV = sp.negativeCycle();
		}
	}
	
	public Iterable<String> getArbitragePath()
	{
		Bag<String> ret = new Bag<String>();
		for(int v : arbiV)
		{
			ret.add(names[v]);
		}
		return ret;
	}
	
	public double profitRate()
	{
		double ret = 0.0;
		int prev = -1;
		for(int v : arbiV)
		{
			if(prev != -1)
			{
				ret += dewg.getEdge(prev, v).weight(); 
			}
			prev = v;
		}
			
		return 1.0 / Math.pow(Math.E, ret);
	}
	
	private void buildDirectedEdgeWeightGraph(String filePath)
	{
		try {
			FileInputStream fis = new FileInputStream(filePath);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader  bsr = new BufferedReader(new InputStreamReader(dis));
			
			//first V;
			String line = bsr.readLine();
			int V = Integer.parseInt(line);	
			dewg = new DirectedEdgeWeightGraph(V);
			this.names = new String[V];
			
			for(int i = 0 ; i < V ; i++)
			{
				line = bsr.readLine();
				String[] tokens = line.split("\\s+");
				names[i] = tokens[0];
				for(int j = 1 ; j < tokens.length ; j++)
					if(i != j - 1)
						dewg.addEdge(new DirectedEdge(i, j - 1, -1 * Math.log(Double.parseDouble(tokens[j]))));
			}
			
			bsr.close();
			dis.close();
			fis.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Arbitrage ab = new Arbitrage("rates.txt");
		for(String s : ab.getArbitragePath())
		{
			System.out.print(s + "=>");
		}
		
		System.out.println();
		System.out.println("Profit rate: " + ab.profitRate());

	}

}
