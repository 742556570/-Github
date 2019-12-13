package com.jeeplus.modules.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.jeeplus.modules.app.api.product.PropertiesUtil;

public class FtpConManager {

	private static FtpConManager instance;  
    private FTPClient ftpClient =null;  
      
    /** 
     * get FtpConManager 
     * @return FtpConManager 
     */  
    public synchronized static FtpConManager getInstance(){  
        if( instance == null ){  
            instance = new FtpConManager();  
        }  
        return instance;  
    }  
  
      
    /** 
     * <p>ftp登录</p> 
     * @param s_url ftp服务地址 
     * @param uname 用户名 
     * @param pass  密码 
     */  
    public void login(String s_url,String uname,String pass){  
        ftpClient = new FTPClient();  
        try{  
            //连接  
            System.out.println("连接---");
            ftpClient.connect(s_url);  
            System.out.println("连接成功");
            ftpClient.login(uname,pass);  
            //检测连接是否成功  
            int reply = ftpClient.getReplyCode();  
            if(!FTPReply.isPositiveCompletion(reply)) {  
                this.closeCon();  
                System.err.println("FTP server refused connection.");  
                System.exit(1);  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
            //关闭  
            this.closeCon();  
        }  
    }  
      
      
    /** 
     * <p>ftp上传文件</p> 
     * @param srcUrl 须上传文件 
     * @param targetFname 生成目标文件 
     * @return true||false 
     */  
    public boolean uploadFile(String srcUrl,String targetFname){  
        boolean flag = false;  
        if( ftpClient!=null ){  
             File srcFile = new File(srcUrl);   
             FileInputStream fis = null;  
             try {  
                fis  = new FileInputStream(srcFile);  
                  
                //设置上传目录  
                boolean changeDirResut = ftpClient.changeWorkingDirectory(PropertiesUtil.getString("uplodfileurl"));
                System.out.println("切换目录至:"+PropertiesUtil.getString("uplodfileurl")+","+changeDirResut);
                ftpClient.setBufferSize(1024);   
                ftpClient.setControlEncoding("GBK");   
                  
                //设置文件类型（二进制）   
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
                //上传  
                flag = ftpClient.storeFile(targetFname, fis);   
            } catch (Exception e) {  
                e.printStackTrace();  
                this.closeCon();  
            }finally{  
                IOUtils.closeQuietly(fis);  
            }  
        }  
        return flag;  
    }//end method uploadFile  
      
      
    /** 
     *  
     * <p>删除ftp上的文件</p> 
     * @param srcFname 
     * @return true || false 
     */  
    public boolean removeFile(String srcFname){  
        boolean flag = false;  
        if( ftpClient!=null ){  
            try {  
                flag = ftpClient.deleteFile(srcFname);  
            } catch (IOException e) {  
                e.printStackTrace();  
                this.closeCon();  
            }  
        }  
        return flag;  
    }  
      
    /** 
     *<p>销毁ftp连接</p>  
     */  
    public void closeCon(){  
        if(ftpClient !=null){  
            if(ftpClient.isConnected()){  
                try {  
                    ftpClient.logout();  
                    ftpClient.disconnect();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }   
            }  
        }  
    }  
    
	public static void main(String[] args) {
		FtpConManager.getInstance().login("192.168.1.62", "iver", "iver");  
        //boolean flag = FtpConManager.getInstance().uploadFile("d://aa.zip", "2342sd.zip");  
        boolean flag = FtpConManager.getInstance().uploadFile("/Users/andy_cui/1work/开桌项目/Code/kzhuo/branchDev/code/veradm/app/tam/classes/com/murong/ecp/app/tam/action/btambat1/T2031924.class","T2031924.class");  
        System.out.println("操作结果:"+flag);  
        //FtpConManager.getInstance().closeCon(); 
	}

}
