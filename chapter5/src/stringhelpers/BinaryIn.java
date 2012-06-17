package stringhelpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BinaryIn {
	
	private InputStream is;
	private byte buf = 0;
	private int bufLen = 0;
	
	public BinaryIn(InputStream is)
	{
		this.is = is;
	}
	
	public long readLong()
	{
		long ret = 0;
		ret |= readInt()<<32;
		ret |= readInt();
		return ret;
	}
		
	public int readInt()
	{
		int ret = 0;
		ret |= readChar() << 16;
		ret |= readChar();
		return ret;
	}
	
	public char readChar()
	{
		char ret = 0;
		ret |= readByte() << 8;
		ret |= readByte();
		return ret;
	}
	
	public int readByte()
	{
		int ret = readBits(8);
		return ret;
	}
	
	public float readFloat()
	{
		int intRep = readInt();
		return Float.intBitsToFloat(intRep);
	}
	
	public double readDouble()
	{
		long intRep = readLong();
		return Double.longBitsToDouble(intRep);
	}
	
	public boolean readBoolean()
	{
		return readBits(1) == 1;
	}
	
	public int readBits(int bits)
	{
		if(bits > 32) throw new IllegalArgumentException();
		int ret = 0;
		
		//0. read from buffer
		if(bits <= bufLen)
			return readFromBuf(bits);
		
		if(bufLen > 0)
		{
			bits -= bufLen;
			ret = readFromBuf(bufLen);
		}
		
		//1. read byte directly from stream if bit >= 8
		while(bits >= 8)
		{
			ret <<= 8;
			try {
				ret |= is.read();
			} catch (IOException e) {				
			}
			bits -= 8;
		}
		
		//2. the remaining
		if(bits > 0)
		{
			try {
				buf = (byte)is.read();
				bufLen = 8;
			} catch (IOException e) {
				
			}
			ret <<= bits;
			ret |= readFromBuf(bits);
		}
		return ret;
	}
	
	private int readFromBuf(int bits)
	{
		if(bits > bufLen) throw new IllegalArgumentException();
		int ret = (buf>>>(8 - bits)) & (0xff >> (8 - bits));
		buf <<= bits;
		bufLen -= bits;
		return ret;
	}
	
	public void close() throws IOException
	{
		if(is != null)
			is.close();
	}

	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream("BinaryInOut_Test.bin");
			BinaryIn in = new BinaryIn(fis);
			int c = in.readInt();
			System.out.println(c);
			
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		}
		
	}

}
