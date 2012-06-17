package stringmatching;

public class SubstringMatching {
	
	public static final int R = 256;
	
	public static int NaiveMatching(String txt, String pat)
	{
		int N = txt.length();
		int M = pat.length();
		
		for(int i = 0 ; i <= N - M ; i++)
		{
			int j = 0;
			while( j < M && txt.charAt(i + j) == pat.charAt(j))
			{
				j++;								
			}
			if(j == M) return i;
		}
		return -1;
	}
	
	public static int NaiveMatchingWithExplicitBackup(String txt, String pat)
	{
		int N = txt.length();
		int M = pat.length();
		
		int i = 0;
		int j = 0;
		
		for(i = 0, j = 0 ; i < N && j < M ; i++)
		{
			if(txt.charAt(i) == pat.charAt(j))
				j++;
			else
			{
				i -= j;
				j = 0;
			}
		}
		
		if(j == M) return i - M;
		return -1;
	}
	
	public static int DfaRecognize(String txt, String pat)
	{
		int n = txt.length();
		int m = pat.length();
		int[][] dfa = new int[m + 1][R]; //state X alpha in Sigma
		
		//1. build up dfa;
		dfa[0][pat.charAt(0)] = 1; //start state
		int failState = 0; //track the fail state.
		for(int s = 1 ; s < m ; s++) //build up to all states
		{
			for(int c = 0 ; c < R ; c++)
				dfa[s][c] = dfa[failState][c]; //fill current fail state first.
			dfa[s][pat.charAt(s)] = s + 1; //fill match state.
			failState = dfa[failState][pat.charAt(s)]; //update fail state.
		}
		
		//2. now, try to recognize language txt with dfa.
		for(int i = 0, s = 0 ; i < n; i++)
		{
			s = dfa[s][txt.charAt(i)];
			if(s == m) //end state, accept.
				return i - m + 1;
		}	

		return -1;
	}
	
	public static int BoyerMooreHeuristic(String txt, String pat)
	{
		int N = txt.length();
		int M = pat.length();
				
		int[] right = new int[R];
		
		for(int c = 0 ; c < R ; c++)
			right[c] = -1;
		
		for(int i = 0 ; i < M ; i++)
			right[pat.charAt(i)] = i;
			
		int skip;
		for(int i = 0 ; i <= N - M ; i += skip)
		{
			skip = 0;
			for(int j = M - 1; j >= 0 ; j--)
			{
				if(txt.charAt(i + j) != pat.charAt(j))
				{
					skip = j - right[txt.charAt(i + j)];
					if(skip < 1) 
						skip = 1;
					break;
				}
			}
			
			if(skip == 0) return i;
		}
		return -1;
	}
	
	public static int RabinKarpFingerPrint(String txt, String pat)
	{
		int N = txt.length();
		int M = pat.length();
		int Q = 997;
		int txtHash = hash(txt, M);
		int patHash = hash(pat, M);
		
		if(txtHash == patHash)
			return 0;
		
		int RM = 1;
		for(int i = 1 ; i <= M - 1 ; i++)
		{
			RM *= R;
			RM %= Q;
		}
		
		for(int i = M ; i < N ; i++)
		{
			txtHash = ((txtHash + Q - (txt.charAt(i - M) * RM) % Q ) * R  % Q + txt.charAt(i)) % Q;
			if(txtHash == patHash)
				return i - M + 1;
		}
		return -1;
	}
	
	private static int hash(String s, int m)
	{
		int h = 0;
		int q = 997;
		for(int i = 0 ; i < m ; i++)
			h = (h * R + s.charAt(i)) % q;
		return h;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "The quick brown fox jumps over the lazy dog";
		System.out.println(RabinKarpFingerPrint(s, "dog"));
	}

}
