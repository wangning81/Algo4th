package unionfind;

public abstract class UnionFind {
	public abstract int find(int p);
	public abstract void union(int p, int q);
	public boolean connected(int p, int q)
	{
		return find(p) == find(q);
	}
}
