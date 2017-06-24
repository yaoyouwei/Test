package com.yaoyouwei.utils;
import java.awt.Dimension; 

import org.apache.poi.hssf.usermodel.HSSFClientAnchor; 
import org.apache.poi.hssf.usermodel.HSSFRow; 
import org.apache.poi.hssf.usermodel.HSSFSheet; 
 
public class ExcelImageGeneratorUtils { 
 
 
 
 
 
 
 
	
	
	/**
	 * 是图片按照指定最大宽度和高度缩小,达不到指定的最大宽度和高度则保留原图
	 * 若maxWidth=0且高度大于maxHeight,则将高度缩小到maxHeight，宽度等比例缩小
	 * 若maxHeight=0且宽度大于maxWidth,则将宽度缩小到maxWidth，高度等比例缩小
	 * 若maxWidth=0且maxHeight=0,则图片大小不变
	 * 其他情况按照maxWidth和maxHeight和原图的宽高比之中的较小的比例缩放保证按比例缩放图片
	 * @param dimension
	 * @param maxWidth
	 * @param maxHeight
	 */
	
	public static Dimension getResizeDimension(Dimension dimension,int maxWidth,int maxHeight ){
						double Ratio = 1;
			Double w = dimension.getWidth();
			Double h = dimension.getHeight();
			double wRatio = maxWidth / w;
			double hRatio = maxHeight / h;
			if (maxWidth == 0 && maxHeight == 0) {
				Ratio = 1;
			} else if (maxWidth == 0) {
				if (hRatio < 1)
					Ratio = hRatio;
			} else if (maxHeight == 0) {
				if (wRatio < 1)
					Ratio = wRatio;
			} else if (wRatio < 1 || hRatio < 1) {
				Ratio = (wRatio <= hRatio ? wRatio : hRatio);
			}
			if (Ratio < 1) {
				w = w * Ratio;
				h = h * Ratio;
			}
			dimension.setSize(w, h);
			return dimension;
	}
	
	
	public static HSSFClientAnchor getPreferredSize(HSSFSheet sheet,
			HSSFClientAnchor anchor, int width, int height) {
		float w = 0.0F;
		w += getColumnWidthInPixels(sheet, anchor.getCol1())
				* (float) (1 - anchor.getDx1() / 1024);

		short col2 = (short) (anchor.getCol1() + 1);
		int dx2 = 0;
		for (; w < (float) width; w += getColumnWidthInPixels(sheet, col2++))
			;
		if (w > (float) width) {
			col2--;
			float cw = getColumnWidthInPixels(sheet, col2);
			float delta = w - (float) width;
			dx2 = (int) (((cw - delta) / cw) * 1024F);
		}
		anchor.setCol2(col2);
		anchor.setDx2(dx2);
		float h = 0.0F;
		h += (float) (1 - anchor.getDy1() / 256)
				* getRowHeightInPixels(sheet, anchor.getRow1());
		int row2 = anchor.getRow1() + 1;
		int dy2 = 0;
		for (; h < (float) height; h += getRowHeightInPixels(sheet, row2++))
			;
		if (h > (float) height) {
			row2--;
			float ch = getRowHeightInPixels(sheet, row2);
			float delta = h - (float) height;
			dy2 = (int) (((ch - delta) / ch) * 256F);
		}
		anchor.setRow2(row2);
		anchor.setDy2(dy2);
		return anchor;
	}
	
	
	public static HSSFClientAnchor getPreferredSize(HSSFSheet sheet,
			HSSFClientAnchor anchor, Dimension dimension) {
		double width = dimension.getWidth();
		double height = dimension.getHeight(); 
		float w = 0.0F;
		w += getColumnWidthInPixels(sheet, anchor.getCol1())
				* (float) (1 - anchor.getDx1() / 1024);

		short col2 = (short) (anchor.getCol1() + 1);
		int dx2 = 0;
		for (; w < (float) width; w += getColumnWidthInPixels(sheet, col2++))
			;
		if (w > (float) width) {
			col2--;
			float cw = getColumnWidthInPixels(sheet, col2);
			float delta = w - (float) width;
			dx2 = (int) (((cw - delta) / cw) * 1024F);
		}
		anchor.setCol2(col2);
		anchor.setDx2(dx2);
		float h = 0.0F;
		h += (float) (1 - anchor.getDy1() / 256)
				* getRowHeightInPixels(sheet, anchor.getRow1());
		int row2 = anchor.getRow1() + 1;
		int dy2 = 0;
		for (; h < (float) height; h += getRowHeightInPixels(sheet, row2++))
			;
		if (h > (float) height) {
			row2--;
			float ch = getRowHeightInPixels(sheet, row2);
			float delta = h - (float) height;
			dy2 = (int) (((ch - delta) / ch) * 256F);
		}
		anchor.setRow2(row2);
		anchor.setDy2(dy2);
		return anchor;
	}
 
	private static float getColumnWidthInPixels(HSSFSheet sheet, int column) {
		int cw = sheet.getColumnWidth(column);
		float px = getPixelWidth(sheet, column);
		return (float) cw / px;
	}

	private static float getRowHeightInPixels(HSSFSheet sheet, int i) {
		HSSFRow row = sheet.getRow(i);
		float height;
		if (row != null) {
			height = row.getHeight();
		} else {
			height = sheet.getDefaultRowHeight();
		}

		return height / 15F;
	}

	private static float getPixelWidth(HSSFSheet sheet, int column) {
		int def = sheet.getDefaultColumnWidth() * 256;
		int cw = sheet.getColumnWidth(column);
		return cw != def ? 36.56F : 32F;
	}

}


