package graphHelper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import directedgraph.Digraph;

public class DigraphBuilder {
	
	public static Digraph FromArray(int vertexCount, int[] Us, int[] Vs)
	{
		if(Us.length != Vs.length)
			throw new IllegalArgumentException();
		
		Digraph g = new Digraph(vertexCount);
		int E = Us.length;
		for(int i = 0 ; i < E ; i++ )
			g.addEdge(Us[i], Vs[i]);
		
		return g;		
	}
	
	public static Digraph FromFile(String filePath)
	{
		return FromFile(filePath, " ");
	}
	
	public static Digraph FromFile(String filePath, String delimeter)
	{
		try {
			FileInputStream fis = new FileInputStream(filePath);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader  bsr = new BufferedReader(new InputStreamReader(dis));
			
			//first V;
			String line = bsr.readLine();
			
			Digraph g = new Digraph(Integer.parseInt(line));
			
			//then edge count
			int count = Integer.parseInt(bsr.readLine());
			
			for(int i = 0 ; i < count ; i++)
			{
				line = bsr.readLine();
				String[] vs = line.split(delimeter);
				g.addEdge(Integer.parseInt(vs[0]), Integer.parseInt(vs[1]));
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
