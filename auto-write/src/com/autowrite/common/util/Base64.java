package com.autowrite.common.util;

import java.io.*;

/**
 * Encoding and decoding the data in Base64
 */
public class Base64 {

	static public char[] encode(byte[] data) {
		char[] out = new char[((data.length + 2) / 3) * 4];

		//
		// 3 bytes encode to 4 chars. Output is always an even
		// multiple of 4 characters.
		//
		for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
			boolean quad = false;
			boolean trip = false;

			int val = (0xFF & (int) data[i]);
			val <<= 8;
			if ((i + 1) < data.length) {
				val |= (0xFF & (int) data[i + 1]);
				trip = true;
			}
			val <<= 8;
			if ((i + 2) < data.length) {
				val |= (0xFF & (int) data[i + 2]);
				quad = true;
			}
			out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 1] = alphabet[val & 0x3F];
			val >>= 6;
			out[index + 0] = alphabet[val & 0x3F];
		}
		return out;
	}

	static public byte[] decode(char[] data) {

		int tempLen = data.length;
		for (int ix = 0; ix < data.length; ix++) {
			if ((data[ix] > 255) || codes[data[ix]] < 0)
				--tempLen; // ignore non-valid chars and padding
		}

		int len = (tempLen / 4) * 3;
		if ((tempLen % 4) == 3)
			len += 2;
		if ((tempLen % 4) == 2)
			len += 1;

		byte[] out = new byte[len];

		int shift = 0; // # of excess bits stored in accum
		int accum = 0; // excess bits
		int index = 0;

		for (int ix = 0; ix < data.length; ix++) {
			int value = (data[ix] > 255) ? -1 : codes[data[ix]];

			if (value >= 0) // skip over non-code
			{
				accum <<= 6; // bits shift up by 6 each time thru
				shift += 6; // loop, with new bits being put in
				accum |= value; // at the bottom.
				if (shift >= 8) // whenever there are 8 or more shifted in,
				{
					shift -= 8; // write them out (from the top, leaving any
					out[index++] = // excess at the bottom for next
									// iteration.
					(byte) ((accum >> shift) & 0xff);
				}
			}

		}

		if (index != out.length) {
			throw new Error("Miscalculated data length (wrote " + index
					+ " instead of " + out.length + ")");
		}

		return out;
	}

	static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
			.toCharArray();

	static private byte[] codes = new byte[256];
	static {
		for (int i = 0; i < 256; i++)
			codes[i] = -1;
		for (int i = 'A'; i <= 'Z'; i++)
			codes[i] = (byte) (i - 'A');
		for (int i = 'a'; i <= 'z'; i++)
			codes[i] = (byte) (26 + i - 'a');
		for (int i = '0'; i <= '9'; i++)
			codes[i] = (byte) (52 + i - '0');
		codes['+'] = 62;
		codes['/'] = 63;
	}

	public char[] encodeContent(String content) {
		byte[] b = content.getBytes();
		char[] encoded = encode(b);
		return encoded;

	}

	public byte[] decodeContent(char[] content) {
		byte[] decoded = decode(content);
		return decoded;
	}

	/* added by etrade 2005/07/14 */
	public static String encode(String s) {
		char chr[] = encode(s.getBytes());
		return new String(chr);
	}

	/* added by etrade 2005/07/14 */
	public static String decode(String s) {
		byte abyte0[] = decode(s.toCharArray());
		String s1 = new String(abyte0);
		return s1.trim();
	}

	/** Decode the content and write it into file -- writeBytes(file, decoded); */
	private static byte[] readBytes(File file) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			InputStream fis = new FileInputStream(file);
			InputStream is = new BufferedInputStream(fis);
			int count = 0;
			byte[] buf = new byte[16384];
			while ((count = is.read(buf)) != -1) {
				if (count > 0)
					baos.write(buf, 0, count);
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return baos.toByteArray();
	}

	private static char[] readChars(File file) {
		CharArrayWriter caw = new CharArrayWriter();
		try {
			Reader fr = new FileReader(file);
			Reader in = new BufferedReader(fr);
			int count = 0;
			char[] buf = new char[16384];
			while ((count = in.read(buf)) != -1) {
				if (count > 0)
					caw.write(buf, 0, count);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return caw.toCharArray();
	}

	private static void writeBytes(File file, byte[] data) {
		try {
			OutputStream fos = new FileOutputStream(file);
			OutputStream os = new BufferedOutputStream(fos);
			os.write(data);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	private static void writeChars(char[] data) {
		System.out.println(data);
		// Write into the Content tag
	}

	/** ******** Test ********* */
	/* changed by etrade 2005/07/14 */
	public static void main(String args[]) {
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				String s = encode(args[i]);
				String s1 = decode(s);
				System.out.println("Original:[" + args[i] + "]");
				System.out.println("Encoded: [" + s + "]");
				System.out.println("Decoded: [" + s1 + "]");
			}

		}
	}
}
