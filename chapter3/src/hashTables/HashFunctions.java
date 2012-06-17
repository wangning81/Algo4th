package hashTables;

public class HashFunctions {
	
	public static int hash(String s)
	{
		int hash = 0;
		int R = 256;
		int M = 255;
		for(int i = 0 ; i < s.length() ; i++)
			hash = (hash * R + s.charAt(i)) % M; 
		
		return hash;
	}
	
	public static int hashString(String s)
	{
		int hash = 0;
		int r = 31;
		for(int i = 0 ; i < s.length(); i++)
			hash = hash * r + s.charAt(i);
		return hash;
	}
	
	public static String[] stringsWithSameHash(int n)
	{
		String[] ret = new String[2 * n];
		
		for(int i = 0 ; i < 2*n ; i++)
		{
			StringBuilder sb = new StringBuilder();			
			String bs = Integer.toBinaryString(i);
			for(int k = 0 ; k < bs.length() ; k++)
			{
				if(bs.charAt(k) == '0') sb.append("Aa");
				else sb.append("BB");
			}
			while(sb.length() / 2 < Math.floor(Math.log(2 * n)/Math.log(2)) + 1)
				sb.insert(0, "Aa");
			ret[i] = sb.toString();
		}
		return ret;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] s = stringsWithSameHash(10);
		for(int i = 0 ; i < s.length ; i++)
		{
			System.out.println(s[i] + " h = " + s[i].hashCode());
		}
	}

}
