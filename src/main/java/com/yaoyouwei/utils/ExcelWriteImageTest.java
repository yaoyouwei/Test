package com.yaoyouwei.utils;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelWriteImageTest {
	
    private final static int EXPLOSION_HEIGHT = 30;//写入excel的爆炸图所占的行数
    private short startCol = 0;

	public static void main(String[] args) {
		ExcelWriteImageTest test = new ExcelWriteImageTest();
		test.exportBOMWithExplosion("yaoyouwei");
	}
	
    public Map<String,Object> exportBOMWithExplosion(String productId) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("ProductBom");
            
            int startRow = 0;
            boolean isExitExplosion = false;
            String [] fileList = {
            		"C:/Users/Think/Desktop/桌面/22023016000243爆炸图.pdf",
            		"C:/Users/Think/Desktop/桌面/05(B)___机械式___OPP6冷凝器在第二层(新结构).pdf",
            		"C:/Users/Think/Desktop/桌面/pdfbox-0.png",
                    "C:/Users/Think/Desktop/桌面/ExplosionPng3.png"
            		
            		};
            for (String filePath : fileList) {
            	//if(this.createExplosion1(filePath, startCol, sheet, workbook)){
                if(this.createExplosion(filePath, startCol, sheet, workbook)){
                    isExitExplosion = true;
                }
            }
            if(isExitExplosion){//
                startRow = this.EXPLOSION_HEIGHT+5;
            }
           
            String excelFilePath = "C:/Users/Think/Desktop";
            FileUtils.createDir(excelFilePath);
            
            File excelFile = new File(excelFilePath + File.separator + productId+ DateUtils.getCurrDateTime("yyyyMMddhhmmss") + "" + ".xls");
            ExcelUtils.writeAndClose(workbook, excelFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
	
	
	
    private boolean createExplosion(String filePath,short imageStartCol,Sheet sheet,HSSFWorkbook workbook ){
    	 boolean result = true;
         int imageStartRow = 0;
         try {
            BufferedImage  bufferImg = null;
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            if(filePath.indexOf(".pdf")!= -1){
                PDDocument document = PDDocument.load(new File(filePath));
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                bufferImg = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);//PDF:0ҳ,DPI:300
                
                //ImageIOUtil.writeImage(bim, "C:/Users/Think/Desktop/桌面/pdfbox1" + "-" + (pageCounter++) + ".png", 300);
                
                ImageIO.write(bufferImg, "png", byteArrayOut);  
                document.close();
                
            }else{
                bufferImg = ImageIO.read(new File(filePath));  
                
                //bufferImg = ImageUtils.resize(bufferImg, this.EXPLOSION_RESIZE_HEIGHT, this.EXPLOSION_RESIZE_WIDTH,true );
                ImageIO.write(bufferImg,"jpg", byteArrayOut);  
            }
            /*int pictureIndex = workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG);    
            HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
			HSSFPicture picture = patriarch.createPicture( new HSSFClientAnchor(),pictureIndex);*/
            
            HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
			HSSFPicture picture = patriarch.createPicture(new HSSFClientAnchor(), workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
			Dimension dimension = picture.getImageDimension();
			dimension = ExcelImageGeneratorUtils.getResizeDimension(dimension, 600, 600);
			
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) imageStartCol, imageStartRow, (short) 0, 0);
			anchor.setCol1(imageStartCol);
			anchor.setRow1(imageStartRow);
			HSSFClientAnchor preferredSize = ExcelImageGeneratorUtils.getPreferredSize((HSSFSheet)sheet,anchor,dimension);
			picture.setAnchor(preferredSize);
			this.startCol = (short)(preferredSize.getCol2()-preferredSize.getCol1()+this.startCol+2);  
			System.out.println("行数:"+(preferredSize.getRow2()-preferredSize.getRow1()));
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
        
    }

    
}
