package undirectedgraph;

public class DepthFirstSearch extends GraphSearch {
	private boolean marked[];
	private int count;

	public DepthFirstSearch(Graph g, int s) {
		super(g, s);
		search(s);
	}

	@Override
	protected void search(int v)
	{
		marked[v] = true;
		count++;
		for(int w : g.adj(v))
			if(!marked[w])
				search(w);		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
