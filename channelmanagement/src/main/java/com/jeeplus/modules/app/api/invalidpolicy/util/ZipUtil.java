package com.jeeplus.modules.app.api.invalidpolicy.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.apache.tools.tar.TarOutputStream;

/**
 * <p>客户端基类:压缩解压报文工具类</p>
 * <p></p>
 * @author XuQZ
 * @createDate 2016-7-27 15:09:59
 */
public class ZipUtil {
	
	/**
	 * 压缩
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] compress(byte[] data) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gos = new GZIPOutputStream(baos);
		TarOutputStream tos = new TarOutputStream(gos);
		TarEntry tarEn = new TarEntry("gzip");
		
		tarEn.setSize(data.length);
		tos.putNextEntry(tarEn);
		tos.write(data);
		tos.closeEntry();
		tos.close();
		gos.close();
		byte[] b = baos.toByteArray();
		baos.close();
		return b;
	}
	
	/**
	 * 解压缩
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] decompress(byte[] data) throws IOException {
		
		ByteArrayInputStream bais = null;
		ByteArrayOutputStream baos = null;
		GZIPInputStream gis = null;
		//???
		TarInputStream tis = null;
		byte[] bb = null;
		try {
		 bais = new ByteArrayInputStream(data);
		 baos = new ByteArrayOutputStream();
		 gis = new GZIPInputStream(bais);
		//???
		 tis = new TarInputStream(gis);
		byte[] b = new byte[2048];
		int num = -1;
		//???
		TarEntry tarEn = tis.getNextEntry();
		if (tarEn != null) {
			while ((num = tis.read(b)) != -1) {
				baos.write(b, 0, num);
			}
		}
		
		bb = baos.toByteArray();
		
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			tis.close();
			gis.close();
			baos.close();
			
			if(bais!=null){
				bais.close();
			}
		}
		return bb;
	}
}