package suffixarray;

import stringsort.Quick3WaySort;

public class SuffixArray {
	
	private String[] suffixes;
	private int N;
	
	public SuffixArray(String s)
	{
		N = s.length();
		suffixes = new String[N];
		for(int i = 0 ; i < N ; i++)
			suffixes[i] = s.substring(i);
		Quick3WaySort.Sort(suffixes);
	}
	
	//longest common string between i-1 and i;
	public String lcp(int i)
	{
		if(i == 0 || i >= N)
			throw new IllegalArgumentException();
		return lcp(suffixes[i - 1], suffixes[i]);
	}
	
	public String lcp(String s1, String s2)
	{
		int m = Math.min(s1.length(), s2.length());
		for(int k = 0; k < m ; k++)
		{
			if(s1.charAt(k) != s2.charAt(k))
				return s1.substring(0, k);
		}
		return s1.substring(0, m);
	}
	
	public String select(int i)
	{
		return suffixes[i];
	}
	
	public int index(int i)
	{
		return N - suffixes[i].length();
	}
	
	public int rank(String s)
	{
		int lo = 0, hi = N - 1;
		while(lo <= hi)
		{
			int mid = lo + (hi - lo)/2;
			int cmp = s.compareTo(suffixes[mid]);
			if(cmp > 0) lo = mid + 1;
			else if(cmp < 0) hi = mid - 1;
			else return mid;
		}
		return lo;
	}

	
	public static void main(String[] args) {
		String s = "HAMLET: to be, or not to be--that is the question";
		SuffixArray sa = new SuffixArray(s);
		String rep = "";
		for(int i = 1 ; i < s.length() ; i++)
		{
			String lcs_i = sa.lcp(i);
			if(lcs_i.length() > rep.length())
				rep = lcs_i;
		}
		System.out.println(rep);
		
		
	}

}
