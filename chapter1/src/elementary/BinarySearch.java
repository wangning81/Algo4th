package elementary;

public class BinarySearch {

    // precondition: array a[] is sorted
    public static int rank(int key, int[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
         }
         return -1;
    }
    
    // count number of element in a that equals to the key
    public static int count(int key, int[] a)
    {
    	int pos = rank(key, a);
    	if(pos != -1)
    	{
	    	int count = 1;
	    	for(int i = pos - 1 ; i >= 0 ; i--)
	    	{
	    		if(a[i] == key)
	    			count++;
	    		else
	    			break;
	    	}
	    	
	    	for(int i = pos +  1; i < a.length ; i++)
	    	{
	    		if(a[i] == key)
	    			count++;
	    		else
	    			break;   		
	    	}
	    	return count;
    	}
    	return 0;  	
    }
    
    public static int bruteCount(int key, int[] a)
    {
    	int ret = 0;
    	for(int i = 0 ; i < a.length ; i++)
    	{
    		if(a[i] == key)
    			ret++;
    		else if(a[i] > key)
    			break;
    	}
    	return ret;
    }
    
    public static int countSmaller(int key, int[] a)
    {
    	int low = 0, high = a.length - 1;
    	int ret = 0;    	
    	while(low <= high)
    	{
    		int mid = low + (high - low) / 2;
    		if(a[mid] < key)
    		{
    			ret += mid - low + 1;
    			low = mid + 1;
    		}
    		else
    		{
    			high = mid - 1;    			    			
    		}
    	}
    	return ret;
    }
    
    public static int bruteCountSmaller(int key, int[] a)
    {
    	int ret = 0;
    	for(int i = 0 ; i < a.length ; i++)
    	{
    		if(a[i] < key)
    			ret++;    			
    		else
    			break;
    	}
    	return ret;
    }

    public static void main(String[] args) {
        /*int[] whitelist = In.readInts(args[0]);
        
        long ti = System.currentTimeMillis();
        Arrays.sort(whitelist);
        System.out.println(whitelist[whitelist.length - 1]);
        System.out.println(System.currentTimeMillis() - ti);
        
        ti = System.currentTimeMillis();
        System.out.println("Count = " + countSmaller(8999999, whitelist));
        System.out.println(System.currentTimeMillis() - ti);
        
        ti = System.currentTimeMillis();
        System.out.println("Count = " + bruteCountSmaller(8999999, whitelist));
        System.out.println(System.currentTimeMillis() - ti);
        */
//        int i = 0;
//        int dupCount = 0;
//        for( ; i + dupCount < whitelist.length ; i++)
//        {
//        	whitelist[i] = whitelist[i + dupCount];
//        	for(int j = i + dupCount + 1 ; j < whitelist.length && whitelist[j] == whitelist[i] ; j++)
//        	{
//        		dupCount++;
//        	}        	
//        }
//        while(i < whitelist.length)
//        	whitelist[i++] = Integer.MAX_VALUE;
        
//        for(int k = 0 ; k < whitelist.length ; k++)
//        	StdOut.println(whitelist[k]);        

        // read key; print if not in whitelist
//        while (!StdIn.isEmpty()) {
//            int key = StdIn.readInt();
//            if (rank(key, whitelist) == -1)
//                StdOut.println(key);
//            }
//        }
    }
	}
