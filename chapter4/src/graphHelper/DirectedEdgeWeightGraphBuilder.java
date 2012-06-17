package graphHelper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import directedgraph.DirectedEdgeWeightGraph;

import undirectedgraph.DirectedEdge;

public class DirectedEdgeWeightGraphBuilder {
	public static DirectedEdgeWeightGraph FromFile(String filePath, String delimeter)
	{
		try {
			FileInputStream fis = new FileInputStream(filePath);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader  bsr = new BufferedReader(new InputStreamReader(dis));
			
			//first V;
			String line = bsr.readLine();
			
			DirectedEdgeWeightGraph g = new DirectedEdgeWeightGraph(Integer.parseInt(line));
			
			//then edge count
			int count = Integer.parseInt(bsr.readLine());
			
			for(int i = 0 ; i < count ; i++)
			{
				line = bsr.readLine();
				String[] vs = line.split("\\s+");
				DirectedEdge e = new DirectedEdge(Integer.parseInt(vs[0]), Integer.parseInt(vs[1]),Double.parseDouble(vs[2]));
				g.addEdge(e);
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
}
