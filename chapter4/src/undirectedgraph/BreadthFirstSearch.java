package undirectedgraph;
import elementary.ArrayQueue;

public class BreadthFirstSearch extends GraphSearch{

	
	public BreadthFirstSearch(Graph g, int s) {
		super(g, s);		
	}

	@Override
	protected void search(int v)
	{
		ArrayQueue<Integer> q = new ArrayQueue<Integer>();
		q.enqueue(v);
		marked[v] = true;
		while(!q.isEmpty())
		{
			int v2v = q.dequeue();
			count++;
			for(int w : g.adj(v2v))
				if(!marked[w])
				{
					marked[w] = true;
					q.enqueue(w);
				}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
