package com.yaoyouwei.utils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Pdftk {
	
	public static void main(String[] args) {
		List <String>inputFileNames = new ArrayList<String>();
		inputFileNames.add("a.pdf");
		inputFileNames.add("page7to10.pdf");
		String outputFile = "page1to4and7to10.pdf";
		File dir = new File ("D:/test");
		System.out.println(cat(inputFileNames,outputFile,dir));
//		Properties property = getProperties("/META-INF/conf/common_properties/pdftk.properties");
//		System.out.println(property.get("windows"));

	}
	
	public static Properties getProperties(String path){
		InputStream inputStream = Pdftk.class.getResourceAsStream(path);  
        Properties property = new Properties();  
        try {  
        	property.load(inputStream);  
            inputStream.close();   
        } catch (IOException e){  
            e.printStackTrace();  
        }  
        return property;
	}
	
	public static String cat(List inputFileNames,String outputFileName,File dir){
		if(!dir .isDirectory() ){
			return dir.getAbsolutePath()+" is not a directory !";
		}
		if(!dir.exists() ){
			return dir.getAbsolutePath()+" does not exist !";
		}
		StringBuffer inputParams = new StringBuffer();
		for(int i=0;i<inputFileNames.size();i++){
			inputParams.append(" "+inputFileNames.get(i)+" ");
		}
		
		String cmd = "pdftk "+inputParams.toString()+" cat output "+outputFileName;
		System.out.println(cmd);
		String msg = exec(cmd,dir);
		File outputFile = new File(dir.getAbsolutePath()+File.separator+outputFileName);
		if(outputFile.exists()){
			msg += "SUCCESS"+System.getProperty("line.separator");
		}
		return msg;
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
