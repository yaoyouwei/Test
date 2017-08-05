package com.yaoyouwei.tess4j;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import org.junit.Test;

public class TesseractExample {
	
	
	
	/*public static void main(String[] args) {
		
        //System.setProperty("jna.library.path", "32".equals(System.getProperty("sun.arch.data.model")) ? "lib/win32-x86" : "lib/win32-x86-64");
		//System.out.println(new File(System.getenv("TESSDATA_PREFIX"),"./tessdata/eng.traineddata").exists());
        File imageFile = new File("eurotext.tif");
        System.out.println(imageFile.getAbsolutePath());
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
        instance.setDatapath(tessDataFolder.getParent());

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }*/
	
	@Test
    public void testDoOCR_File() throws Exception {
		//String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		String datapath = "src/test/resources";
		String testResourcesDataPath = "src/test/resources/test-data";
		ITesseract  instance = new Tesseract();
        instance.setDatapath(new File(datapath).getPath());//设置词库
        instance.setLanguage("chi_sim");  
        instance.setLanguage("fontyyw");  
        File imageFile = new File(testResourcesDataPath, "chiyyw.fontyyw.exp0.tif");
        String result = instance.doOCR(imageFile);
        System.out.println(result);
        //assertEquals(expResult, result);
    }

}


/**
Error opening data file ./tessdata/eng.traineddata
Please make sure the TESSDATA_PREFIX environment variable is set to the parent directory of your "tessdata" directory.
Failed loading language 'eng'
 * 
 */
