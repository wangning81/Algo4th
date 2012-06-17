package stringhelpers;

import java.io.*;

public class BinaryOut {
	private OutputStream stream;
	private byte buf;
	private int bufLen;
	private final static char AllOne16Bit = (char)~0;
	private final static byte AllOne8Bit = (byte)~0;
	private final static int AllOne32Bit = (int)~0;
	
	public BinaryOut(OutputStream stream)
	{
		this.stream = stream;				
	}
	
	public void write(byte b)
	{
		write(b, 8);
	}
	
	public void write(byte b, int n)
	{
		if(n > 8) throw new IllegalArgumentException("bits to write cannot greater than 16");
		for(int i = 0 ; i < n ; i++)
		{
			boolean bit = (char) ((b >> (n - i - 1)) & 1) == 1;
			write(bit);
		}		
	}
	
	public void write(int i) 
	{
		write((char)(i >> 16)); //high word
		write((char)(i & AllOne16Bit));			  //low word
	}
	
	public void write(char c)
	{
		write((byte)(c >> 8)); //high byte
		write((byte)(c & AllOne8Bit));  //low byte
	}
	
	public void write(long lg)
	{
		write((int) (lg >> 32));
		write((int) (lg & AllOne32Bit));
	}
	
	public void write(float f)
	{
		write((int)Float.floatToRawIntBits(f));
	}
	
	public void write(double d)
	{
		write((int)Double.doubleToLongBits(d));
	}
	
	public void write(char c, int b)
	{
		if(b > 16) throw new IllegalArgumentException("bits to write cannot greater than 16");
		for(int i = 0 ; i < b ; i++)
		{
			boolean bit = (char) ((c >> (b - i - 1)) & 1) == 1;
			write(bit);
		}
	}
	
	public void write(boolean bit)
	{
		buf = (byte) ((buf << 1) | (bit ? 1 : 0));
		bufLen++;
		if(bufLen == 8)
			flush();
	}
	
	public void flush()
	{
		if(bufLen > 0)
		{
			buf <<= (8 - bufLen);
			try
			{
				stream.write(buf);
				stream.flush();
			}catch(Exception e){}
			buf = 0;
			bufLen = 0;
		}
	}
	
	public void close() throws IOException
	{
		if(stream != null)
		{
			flush();
			stream.close();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream("BinaryInOut_Test.bin");	
		}
		catch(IOException ioe)
		{}
		
		BinaryOut bo = new BinaryOut(fs);
		bo.write(true);
		bo.write(false);
		bo.write(true);
		bo.write(false);
		bo.write(true);
		bo.write(true);
		bo.write(false);
		bo.flush();
		try {
			bo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
	}

}
