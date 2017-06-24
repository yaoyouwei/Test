package com.yaoyouwei.utils;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.Test;

public class OtherTest {

	
	public OtherTest() {
		super();
	}

	public static void main(String[] args) throws Exception {
		String sql = "";
		sql = "A:\"hello!\"";
		System.out.println(sql);
	}
	
	@Test
	public  void testIsImage() {
		OtherTest ot = new OtherTest();
		String [] filePathList = {"C:/Users/Think/Desktop/����/sitimage3.jpg",
				 "C:/Users/Think/Desktop/����/QQ��ͼ20161116155839.png",
				 "C:/Users/Think/Desktop/����/05(B)___��еʽ___OPP6�������ڵڶ���(�½ṹ).wmf",
				 "C:/Users/Think/Desktop/����/explosion.pdf",
				 "C:/Users/Think/Desktop/����/22022011003414_BomExport_20161116111550.xls",
				 "C:/Users/Think/Desktop/����/�ĵ�����������_1.0.pptx",
				 "C:/Users/Think/Desktop/����/uatword.docx"
				};
		for(String filePath:filePathList){
			if(ot.testIsImage(filePath)){
				System.out.println(filePath+"       true ");
			}else{
				System.out.println(filePath+"       false");
			}
		}

	}
    
    
    public boolean testIsImage(String filepath){
    	boolean result = false;
        try {
			File f = new File(filepath);
			BufferedImage buffImage=ImageIO.read(f);  
			if (buffImage != null) {  
			     result =true;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}  
        return result;
        
    }

}
