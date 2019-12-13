package com.jeeplus.modules.app.id5.util;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
/**
 * 
 * @author 阳光保险
 *
 */
public class Id5RsaUtils {
	 
	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	/**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws CryptException 异常
     */
	public static String encode(String key,String data) throws Exception
    {
        return encode(key, data.getBytes("GB18030"));
    }
	public static String encode(String key, byte[] data) throws Exception {
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// key的长度不能够小于8位字节
		Key secretKey =  keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
		IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());// ����
		AlgorithmParameterSpec paramSpec = iv;
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
		byte[] bytes = cipher.doFinal(data);
		return Base64.encode(bytes);
	}

	 /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
	public static byte[] decode(String key, byte[] data) throws Exception {
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	 /**
     * 获取编码后的值
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
	 /**
     * 获取编码后的值
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String decodeValue(String key,String data) 
    {
    	byte[] datas;
    	String value = null;
		try {
	    	datas = decode(key, Base64.decode(data));
	    	System.out.println("reponse returnXml:" + new String(datas,"GB18030"));
	    	value = new String(datas,"GB18030");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解密失败:"+e);
			value = "";
		}
    	return value;
    }
}
