package stringsort;

public class Quick3WaySort {
	
	public static void Sort(String[] ss)
	{
		Sort(ss, 0, ss.length - 1, 0);
	}
	
	private static void Sort(String[] ss, int low, int high, int d)
	{
		if(low < high)	
		{
			int v = CharAt(ss[low], d);
			int lt = low - 1, gt = high + 1;
			int i = low + 1;
			while( i < gt )
			{
				if(CharAt(ss[i], d) < v)
					Exch(ss, ++lt, i++);
				else if(CharAt(ss[i], d) > v)
					Exch(ss, --gt, i);
				else i++;
			}
			
			Sort(ss, low, lt, d + 1);
			if(v > 0) Sort(ss, lt + 1, gt - 1, d + 1);
			Sort(ss, gt, high, d + 1);
		}
	}
	
	private static int CharAt(String s, int d)
	{
		return d < s.length() ? s.charAt(d) : -1;
	}
	
	private static void Exch(String[] ss, int a, int b)
	{
		String s = ss[a];
		ss[a] = ss[b];
		ss[b] = s;
	}

	public static void main(String[] args) {
		String[] ss = new String[]{
				"she",
				"sells",
				"seashells",
				"by",
				"the",
				"seashore",
				"the",
				"shells",
				"she",
				"sells",
				"are",
				"surely",
				"seashells"
		};
		
		Quick3WaySort.Sort(ss);
		
		for(String s : ss)
		{
			System.out.println(s);
		}


	}

}
