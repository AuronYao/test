package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	/**
	 * 将源目录打包为zip文件
	 * @param zipFile 目标文件如 new File("x/x/xxx.zip");
	 * @param resourceFile 源文件
	 */
	public static void zipStart(File zipFile,File resourceFile){
		ZipOutputStream out = null;
		BufferedOutputStream bos = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFile));
			bos = new BufferedOutputStream(out);
			zip(out,resourceFile,resourceFile.getName(),bos);
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(bos!=null){
				try {
					bos.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void zip(ZipOutputStream out,File sourceFile,String sourceFileName,BufferedOutputStream bos){
		FileInputStream in = null;
		BufferedInputStream bis = null;
		try{
			if (sourceFile.isDirectory()) {
				File[] subFiles = sourceFile.listFiles();
				if (subFiles.length == 0) {
					 out.putNextEntry(new ZipEntry(sourceFileName + "/"));
				 }
				 for (int i = 0; i < subFiles.length; i++) { 
					 zip(out, subFiles[i], sourceFileName+"/"+ subFiles[i].getName(), bos); 
				 }
			}else{
				 out.putNextEntry(new ZipEntry(sourceFileName)); // 创建zip压缩进入点base
				 in = new FileInputStream(sourceFile);  
				 bis = new BufferedInputStream(in);
				 byte[] buf = new byte[1024];
				 int len = -1;
				 while((len=bis.read(buf))!=-1){
					 bos.write(buf,0,len);
				 }
				 bos.flush();
			 }
		}catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 if(bis!=null){
				 try {
					bis.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
			 if(in!=null){
				 try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
		 }
	}
}
