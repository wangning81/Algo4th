package directedgraph;

import graphHelper.DigraphBuilder;

public class TopologicalSort {
	
	public static Iterable<Integer> sort(Digraph g)
	{
		CycleDetector cd = new CycleDetector(g);
		if(!cd.hasCycle())
		{
			DfsOrder dfsOrder = new DfsOrder(g);
			return dfsOrder.reversePostOrder();
		}
		return null;
	}

	
	public static boolean isTopological(Digraph g, int[] vSeq)
	{
		for(int i = vSeq.length - 1 ; i > 0 ; i--)
		{
			DirectedDfsPath ddp = new DirectedDfsPath(g, vSeq[i]);
			for(int j = i - 1 ; j >= 0 ; j--)
				if(ddp.pathTo(vSeq[j]) != null)
					return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		Digraph g = DigraphBuilder.FromFile("tinyDAG.txt");
		for(int v : TopologicalSort.sort(g))
			System.out.print(v + " ");
	}
}
