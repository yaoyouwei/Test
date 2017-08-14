package com.yaoyouwei.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/** 
 * @ClassName: CommandUtils   
 * @Author yaoyouwei
 * @Date 2017年8月12日 上午9:05:53 
 * @Description: 
 */
public class CommandUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//tesseract D:\ocrdata\input\1.png D:\ocrdata\output\1  -l chi_sim+eng+osd  -psm 1 
		String cmd = " tesseract D:\\ocrdata\\input\\1.png D:\\ocrdata\\output\\3  -l chi_sim+eng+osd  -psm 1  ";
		String filePath = "C:\\jTessBoxEditor\\tesseract-ocr";
		File dir = new File(filePath);
		String msg = CommandUtils.exec(cmd, dir);
		System.out.println(msg);
	}
	
	public static String exec(String cmd,File dir){
		byte[] resultarry;
		byte[] errorarry;
		Process p = null;
		InputStream resultis = null;
		InputStream erroris = null;
		String msg = "";
		try {
			p = Runtime.getRuntime().exec(cmd,null,dir);
			//Thread.sleep(5000);
			while(true){
				if(p.waitFor()==0);{
					//System.out.println("execute completely!");
					break;
				}
			}
			resultis = p.getInputStream();
			erroris = p.getErrorStream();
			resultarry = new byte[resultis.available()];  
			errorarry = new byte[erroris.available()];  
			resultis.read(resultarry);  
			erroris.read(errorarry);  
			String rsstr = new String(resultarry,"UTF-8");  
			String errstr = new String(errorarry,"UTF-8");
			
			msg = rsstr +System.getProperty("line.separator")+errstr;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(p != null){
				p.destroy();
			}
			if (resultis != null ) {
				try {
					resultis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (erroris != null ) {
				try {
					erroris.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}  
	
		return msg;
	}

}
