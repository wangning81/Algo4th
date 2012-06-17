package datacompression;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import stringhelpers.BinaryIn;
import stringhelpers.BinaryOut;
import stringst.TST;

public final class LZW {

	private static final int R = 256;
	private static final int CodeTableBits = 12;
	private static final int CodeTableLength;

	static {
		CodeTableLength = 1 << CodeTableBits;
	}

	public static void compress(String inputFile, String ouputFile)
			throws Exception {
		TST<Integer> codeTable = new TST<Integer>();

		for (char c = 0; c < R; c++)
			codeTable.put("" + c, (int)c);

		String content = readFile(inputFile);
		int total = content.length();
		int k = R + 1; // R is reserved for EOF;

		FileOutputStream fos = new FileOutputStream(ouputFile);
		BinaryOut bo = new BinaryOut(fos);
		
		try {
			for (int i = 0; i < total;) {
				String s = codeTable.longestPrefixOf(content.substring(i));
				int code = codeTable.get(s);
				bo.write((char) code, CodeTableBits);
				i += s.length();
				if (i < total && k < CodeTableLength) {
					codeTable.put(s + content.charAt(i), k++);
				}
			}
			bo.write((char) R, CodeTableBits);
			bo.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		finally {
			bo.close();
			fos.close();
		}
	}

	public static void expand(String cmpFile, String outFile)
			throws IOException {
		String[] codeTable = new String[CodeTableLength];
		for (char c = 0; c < R; c++)
			codeTable[c] = "" + c;

		int k = R + 1;

		FileInputStream fis = new FileInputStream(cmpFile);
		BinaryIn in = new BinaryIn(fis);
		FileWriter fw = new FileWriter(outFile);

		try {
			int c = in.readBits(CodeTableBits);
			String val = codeTable[c];

			while (true) {
				fw.write(val);
				int lookaheadcode = in.readBits(CodeTableBits);

				if (lookaheadcode == R)
					break;

				String s;
				if (lookaheadcode == k) {
					s = val + val.charAt(0);
				} else {
					s = codeTable[lookaheadcode];
				}

				if (k < CodeTableLength)
					codeTable[k++] = val + s.charAt(0);
				val = s;
			}
		} finally {
			fw.close();
			fis.close();
			in.close();
		}
	}

	private static String readFile(String path) throws IOException {
		FileInputStream stream = new FileInputStream(new File(path));
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size());
			/* Instead of using default, pass in a decoder. */
			return Charset.defaultCharset().decode(bb).toString();
		} finally {
			stream.close();
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			LZW.compress("tale.txt", "tale.lzw");
			LZW.expand("tale.lzw", "tale.lzw.txt");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
}
