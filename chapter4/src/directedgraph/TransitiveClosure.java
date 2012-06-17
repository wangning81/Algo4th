package directedgraph;

public class TransitiveClosure {
	private DirectedDfs[] dirDfs;
	
	public TransitiveClosure(Digraph g)
	{
		int V = g.verticeCount();
		dirDfs = new DirectedDfs[V];
		for(int i = 0 ; i < V ; i++)
		{
			dirDfs[i] = new DirectedDfs(g, i);				
		}
	}
	
	public boolean reachable(int u, int v)
	{
		return dirDfs[u].isConnected(v);
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
