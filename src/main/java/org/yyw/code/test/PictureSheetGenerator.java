package org.yyw.code.test;
import java.awt.Dimension; 

import org.apache.poi.hssf.usermodel.HSSFClientAnchor; 
import org.apache.poi.hssf.usermodel.HSSFPatriarch; 
import org.apache.poi.hssf.usermodel.HSSFPicture; 
import org.apache.poi.hssf.usermodel.HSSFRow; 
import org.apache.poi.hssf.usermodel.HSSFSheet; 
import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import org.yyw.code.test.POIUtils.CellLocation;
 
public class PictureSheetGenerator { 
 
 private static final String KEYWORD_ER = "$ER"; 
 
 private byte[] imageBuffer; 
 
 private int pictureIndex; 
 
 private int excelPictureType; 
 
	public PictureSheetGenerator(HSSFWorkbook workbook, byte[] imageBuffer,
			int excelPictureType) {
		this.imageBuffer = imageBuffer;
		this.excelPictureType = excelPictureType;

		if (this.imageBuffer != null) {
			this.pictureIndex = workbook.addPicture(this.imageBuffer,
					this.excelPictureType);
		}
	}
 
	public void setImage(HSSFWorkbook workbook, HSSFSheet sheet) {
		CellLocation cellLocation = POIUtils.findMatchCell(sheet, "\\"
				+ KEYWORD_ER + ".*");
		System.out.println(cellLocation);
		if (cellLocation != null) {
			int width = -1;
			int height = -1;

			String value = POIUtils.getCellValue(sheet, cellLocation);

			int startIndex = value.indexOf("(");
			if (startIndex != -1) {
				int middleIndex = value.indexOf(",", startIndex + 1);
				if (middleIndex != -1) {
					width = Integer.parseInt(value.substring(startIndex + 1,
							middleIndex).trim());
					height = Integer.parseInt(value.substring(middleIndex + 1,
							value.length() - 1).trim());
				}
			}

			this.setImage(workbook, sheet, cellLocation, width, height);
		}
	}
 
	//改写
	public void setImage(HSSFWorkbook workbook, HSSFSheet sheet,
			int col1,int row1, int width, int height) {
		//POIUtils.setCellValue(sheet, cellLocation, "");
		System.out.println("this.imageBuffer:" + this.imageBuffer);
		if (this.imageBuffer != null) {
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

			HSSFPicture picture = patriarch.createPicture(
					new HSSFClientAnchor(), this.pictureIndex);

			Dimension dimension = picture.getImageDimension();
			float rate = dimension.width / dimension.height;
			float specifiedRate = width / height;

			if (width == -1 || height == -1) {
				width = dimension.width;
				height = dimension.height;

			} else {
				if (rate > specifiedRate) {
					if (dimension.width > width) {
						height = (int) (width / rate);

					} else {
						width = dimension.width;
						height = dimension.height;
					}

				} else {
					if (dimension.height > height) {
						width = (int) (height * rate);

					} else {
						width = dimension.width;
						height = dimension.height;
					}
				}
			}

			HSSFClientAnchor preferredSize = this.getPreferredSize(sheet,
					new HSSFClientAnchor(0, 0, 0, 0, (short) col1,
							row1, (short) 0, 0), width, height);
			System.out.println("起始行:"+preferredSize.getRow1());
			System.out.println("起始列:"+preferredSize.getCol1());
			System.out.println("结束行:"+preferredSize.getRow2());
			System.out.println("结束列:"+preferredSize.getCol2());
			picture.setAnchor(preferredSize);
		}
	}
	
	
	//改写1
	public void setImage1(HSSFWorkbook workbook, HSSFSheet sheet,
			int col1,int row1, int maxWidth, int maxHeight) {
		//POIUtils.setCellValue(sheet, cellLocation, "");
		System.out.println("this.imageBuffer:" + this.imageBuffer);
		if (this.imageBuffer != null) {
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

			HSSFPicture picture = patriarch.createPicture(
					new HSSFClientAnchor(), this.pictureIndex);

			Dimension dimension = picture.getImageDimension();
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
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) col1,
					row1, (short) 0, 0);
			anchor.setCol1(col1);
			anchor.setRow1(row1);
			System.out.println("(short) col1:"+(short) col1);
			HSSFClientAnchor preferredSize = this.getPreferredSize(sheet,anchor, w.intValue() , h.intValue() );
			System.out.println("起始行:"+preferredSize.getRow1());
					
			System.out.println("起始列:"+preferredSize.getCol1());
			System.out.println("结束行:"+preferredSize.getRow2());
			System.out.println("结束列:"+preferredSize.getCol2());
			picture.setAnchor(preferredSize);
		}
	}
	
	
	/**
	 * 是图片按照指定最大宽度和高度缩小,达不到指定的最大宽度和高度则保留原图
	 * 若maxWidth=0且高度大于maxHeight,则将高度缩小到maxHeight，宽度等比例缩小
	 * 若maxHeight=0且宽度大于maxWidth,则将宽度缩小到maxWidth，高度等比例缩小
	 * 若maxWidth=0且maxHeight=0,则图片大小不变
	 * 其他情况按照maxWidth和maxHeight和原图的宽高比之中的较小的比例缩放保证按比例缩放图片
	 * @param objImg
	 * @param maxWidth
	 * @param maxHeight
	 */
	/*function autoResizeImage(objImg,maxWidth,maxHeight) {
		
		var img = new Image();
		img.src = objImg.src;
		var hRatio;
		var wRatio;
		var Ratio = 1;
		var w = img.width;
		var h = img.height;
		wRatio = maxWidth / w;
		hRatio = maxHeight / h;
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
		objImg.height = h;
		objImg.width = w;
	}*/

	
	
	
	private void setImage(HSSFWorkbook workbook, HSSFSheet sheet,
			CellLocation cellLocation, int width, int height) {
		POIUtils.setCellValue(sheet, cellLocation, "");
		System.out.println("this.imageBuffer:" + this.imageBuffer);
		if (this.imageBuffer != null) {
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

			HSSFPicture picture = patriarch.createPicture(
					new HSSFClientAnchor(), this.pictureIndex);

			Dimension dimension = picture.getImageDimension();
			float rate = dimension.width / dimension.height;
			float specifiedRate = width / height;

			if (width == -1 || height == -1) {
				width = dimension.width;
				height = dimension.height;

			} else {
				if (rate > specifiedRate) {
					if (dimension.width > width) {
						height = (int) (width / rate);

					} else {
						width = dimension.width;
						height = dimension.height;
					}

				} else {
					if (dimension.height > height) {
						width = (int) (height * rate);

					} else {
						width = dimension.width;
						height = dimension.height;
					}
				}
			}

			HSSFClientAnchor preferredSize = this.getPreferredSize(sheet,
					new HSSFClientAnchor(0, 0, 0, 0, (short) cellLocation.c,
							cellLocation.r, (short) 0, 0), width, height);
			picture.setAnchor(preferredSize);
		}
	}
 
	
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


