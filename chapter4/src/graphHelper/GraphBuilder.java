package graphHelper;

import java.io.*;

import undirectedgraph.*;

public class GraphBuilder {
	
	public static Graph FromArray(int vertexCount, int[] Us, int[] Vs)
	{
		if(Us.length != Vs.length)
			throw new IllegalArgumentException();
		
		Graph g = new Graph(vertexCount);
		int E = Us.length;
		for(int i = 0 ; i < E ; i++ )
			g.addEdge(Us[i], Vs[i]);
		
		return g;		
	}
	
	public static Graph FromFile(String filePath)
	{
		return FromFile(filePath, " ");
	}
	
	public static Graph FromFile(String filePath, String delimeter)
	{
		try {
			FileInputStream fis = new FileInputStream(filePath);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader  bsr = new BufferedReader(new InputStreamReader(dis));
			
			//first V;
			String line = bsr.readLine();
			
			Graph g = new Graph(Integer.parseInt(line));
			
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
	
	public static SymbolGraph<String> SymbolGraphFromFile(String filePath, String delimeter)
	{
		try
		{
			FileInputStream fis = new FileInputStream(filePath);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader  bsr = new BufferedReader(new InputStreamReader(dis));
			
			SymbolGraph<String> g = new SymbolGraph<String>();
			
			String line;
			while((line = bsr.readLine()) != null)
			{
				String[] s = line.split(delimeter);
				for(int i = 1 ; i < s.length ; i++)
					g.addEdge(s[0], s[i]);
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
		Graph g = GraphBuilder.FromFile("tinyG.txt");
		DfsCC cc = new DfsCC(g);
		for(int i = 0 ; i < cc.ccCount(); i++)
		{
			for(int v : cc.verticesOfCC(i))
			{
				System.out.print(v + " ");
			}
			System.out.println();
		}
	}

}
