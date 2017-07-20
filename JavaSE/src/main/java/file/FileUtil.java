package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
    

	public static void copyFile(File sourceFile,File targetFile){
		InputStream in=null;
		BufferedInputStream bis=null;
		OutputStream out = null;
		BufferedOutputStream bos=null;
		try {
			in = new FileInputStream(sourceFile);
			bis = new BufferedInputStream(in);
			out = new FileOutputStream(targetFile);
			bos = new BufferedOutputStream(out);
			byte[] buffer = new byte[1024];
			int len = -1;
			while((len=bis.read(buffer))!=-1){
				out.write(buffer,0,len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bis!=null){
				try {
					bis.close();
					in=null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bos!=null){
				try {
					bos.close();
					out=null;
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
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void copyDirectiory(String sourceDirPath,String targetDirPath){
		File targetDir = new File(targetDirPath);
		if(!targetDir.exists()){
			targetDir.mkdirs();
		}
		File[] subFiles = new File(sourceDirPath).listFiles();
		for(File subFile : subFiles){
			if(subFile.isFile()){
				File targetFile = new File(targetDirPath+"/"+subFile.getName());
				copyFile(subFile,targetFile);
			}else{
				//是目录的话就调用递归
				String sourcePath = sourceDirPath+"/"+subFile.getName();
				String targetPath = targetDirPath+"/"+subFile.getName();
				copyDirectiory(sourcePath, targetPath);
			}
		}
	}
}
