package mst;

import elementary.ArrayQueue;
import priorityqueue.MinPQ;
import undirectedgraph.EdgeWeightGraph;
import undirectedgraph.Edge;

public abstract class AbstractMST {
	protected EdgeWeightGraph g;
	protected ArrayQueue<Edge> mst;
	protected MinPQ<Edge> pq;
	protected boolean[] marked;
	
	public AbstractMST(EdgeWeightGraph ewg)
	{
		this.g = ewg;
		marked = new boolean[ewg.verticesCount()];
		mst = new ArrayQueue<Edge>();
		pq = new MinPQ<Edge>();
		find();
	}
	
	public Iterable<Edge> edges()
	{
		return mst;
	}
	
	protected abstract void find();
		
}
