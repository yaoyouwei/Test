package com.yaoyouwei.utils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
//import org.apache.pdfbox.tools.imageio.ImageIOUtil;

/**
 * ͼƬתpdf
 * @author Think
 *
 */
public class PDFBoxTest {

	public static void main(String[] args) throws IOException {
		String pdfFilename = "C:/Users/Think/Desktop/����/(MA-001)MA-26,35��53(2015).pdf";
		PDDocument document = PDDocument.load(new File(pdfFilename));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		int pageCounter = 0;
		for (PDPage page : document.getPages())
		{
		    // note that the page number parameter is zero based,DPI(Dots Per Inch)
		    BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter, 300, ImageType.RGB);

		    // suffix in filename will be used as the file format
		    //ImageIOUtil.writeImage(bim, "C:/Users/Think/Desktop/����/pdfbox1" + "-" + (pageCounter++) + ".png", 300);
		}
		document.close();

	}

}
