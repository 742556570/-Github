package com.jeeplus.modules.app.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * <p>
 * detailed comment
 * 
 * @since
 */
public class MD5Util {
	private MessageDigest mdInst = null;
	public static final String ENCODE = "GBK"; // UTF-8
	 // UTF-8

	public MD5Util() {
		try {
			mdInst = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

		}
	}

	public final byte[] md5(byte[] btInput) {
		// 使用指定的字节更新摘要
		mdInst.update(btInput);
		// 获得密文
		return mdInst.digest();
	}

	public final String md5(String s) {
		// 使用指定的字节更新摘要
		mdInst.update(s.getBytes());
		// 获得密文
		byte[] md = mdInst.digest();
		// 把密文转换成十六进制的字符串形式
		return BytesUtils.bytesToHexString(md);
	}

	public static String hmacSign(String aValue) {
		String aKey = "asdfghjkl";
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(ENCODE);
			value = aValue.getBytes(ENCODE);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}
		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return MD5Util.toHex(dg);
	}

	public static String digest(String aValue) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(ENCODE);
		} catch (UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return MD5Util.toHex(md.digest(value));
	}

	public static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	public static boolean vlidateMD5sign(String sign, String inputStr) {
		String md5edStr = MD5Util.hmacSign(inputStr);
		if (md5edStr.equals(sign)) {
			return true;
		}
		{
			return false;
		}
	}

	public static void main(String[] args) {
		String inputStr = "123456";
		String sign = "aad92c8ec0a3f3334ed298fba6b8122d";
		String md5edStr = MD5Util.hmacSign(inputStr);
		System.out.println(md5edStr);
		boolean boolean1 = MD5Util.vlidateMD5sign(sign, inputStr);
		System.out.println(boolean1);
	}

}
