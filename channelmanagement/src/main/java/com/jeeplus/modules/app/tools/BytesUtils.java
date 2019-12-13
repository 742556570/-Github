package com.jeeplus.modules.app.tools;


import java.math.BigInteger;

import org.apache.commons.codec.binary.Base64;

public class BytesUtils {
	private static final byte[] hex = "0123456789abcdef".getBytes();

	public static int byte2Int(final byte[] b) {
		long res = 0;
		int tem = 0;
		for (int i = 0; i < b.length; i++) {
			res <<= 8;
			tem = b[i] & 0xff;
			res |= tem;
		}

		return (int) res;
	}

	// 从字节数组到十六进制字符串转换
	public static String bytesToHexString(final byte[] b) {
		byte[] buff = new byte[2 * b.length];

		for (int i = 0; i < b.length; i++) {
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}

		return new String(buff);
	}

	/**
	 * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF,
	 * 0xD9}
	 * @param src String
	 * @return byte[]
	 */
	public static byte[] hexString2Bytes(final String src) {
		int length = src.length() / 2;
		byte[] ret = new byte[length];
		byte[] tmp = src.getBytes();

		for (int i = 0; i < length; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}

		return ret;
	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * @param src0 byte
	 * @param src1 byte
	 * @return byte
	 */
	private static byte uniteBytes(final byte src0, final byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);

		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();

		byte ret = (byte) (_b0 ^ _b1);

		return ret;
	}

	/**
	 * 十六进制大写
	 */
	public static boolean isUpperHex(String value, int length, int lineSize) {
		char ch;
		int count = 0;
		for (int i = 0; i < length; i++) {
			ch = value.charAt(i);
			if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'F')) {

			} else if (ch == '-') {
				count++;
				if (count > lineSize) {
					return false;
				}
			} else {
				return false;
			}
		}
		return count == lineSize;
	}

	/**
	 * 十六进制小写
	 */
	public static boolean isLowerHex(String value, int length, int lineSize) {
		char ch;
		int count = 0;
		for (int i = 0; i < length; i++) {
			ch = value.charAt(i);
			if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f')) {

			} else if (ch == '-') {
				count++;
				if (count > lineSize) {
					return false;
				}
			} else {
				return false;
			}
		}
		return count == lineSize;
	}
	
	/**
	 * 定长数字
	 */
	public static boolean isFixLengthNumber(String value, int length) {
		if (value == null || value.length() != length) {
			return false;
		}
		char ch;
		for (int i = 0; i < length; i++) {
			ch = value.charAt(i);
			if (!(ch >= '0' && ch <= '9')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 格式化字符串
	 * @param finTag
	 * @param len 
	 * @return
	 */
	public static String zero12 = "000000000000";
	public static String zero13 = "0000000000000";
	private static String zeroStr = "00000000000000000000";

	public static String formatString(String str, int len) {
		if (str == null) {
			return zeroStr.substring(0, len);
		}
		str = Long.toString(Long.parseLong(str), 36);
		return zeroStr.substring(0, len - str.length()) + str;
	}

	public static String formatString(String str, int len, int rawRadix, int targetRadix) {
		if (str == null || rawRadix < 2 || targetRadix > 62) {
			return zeroStr.substring(0, len);
		}
		str = new BigInteger(str, rawRadix).toString(targetRadix);
		return zeroStr.substring(0, len - str.length()) + str;
	}

	public static String test1(String str) {
		if (str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if ('\t' != str.charAt(i)) {
				sb.append(String.format("%03d", (str.charAt(i) + (i % 3))));
			} else {
				sb.append(str.charAt(i));
			}
		}
		return sb.toString();
	}

	public static String test2(String str) {
		if (str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0, j = 0; i < str.length();) {
			if ('\t' != str.charAt(i)) {
				sb.append((char) (Integer.parseInt(str.substring(i, i + 3)) - (j % 3)));
				i += 3;
			} else {
				sb.append(str.charAt(i));
				i++;
			}
			j++;
		}
		return sb.toString();
	}

	/**
	 * 对于格式为字母数字组合的数据尝试做base64解码
	 * @param input
	 * @return
	 */
	public static String tryBase64Decode(String input) {
		if (maybeBase64Code(input)) {
			return new String(Base64.decodeBase64(input));
		}
		return input;
	}

	/**
	 * 可能是base64编码的字符串
	 * @param input
	 * @return
	 */
	private static boolean maybeBase64Code(String input) {
		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (i >= input.length() - 2 && ch == '=')) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

}
