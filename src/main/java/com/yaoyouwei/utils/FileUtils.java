package com.yaoyouwei.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
//import org.bouncycastle.jce.provider.asymmetric.ec.Signature.ecCVCDSA;

//import com.google.api.client.util.Base64;

public class FileUtils {

	public static File writeFile(InputStream in, String basePath, String fileName) throws IOException {
		OutputStream out =null;
		try {
			String fullPath = basePath + File.separator + fileName;
			File dir = new File(basePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(fullPath);
			 out = new FileOutputStream(file);
			out = new BufferedOutputStream(out);
			byte[] bytes = new byte[1024];
			int length = 0;
			while ((length = in.read(bytes)) != -1) {
				out.write(bytes, 0, length);
			}
			
			return file;
		} catch (Exception ie) {
			ie.printStackTrace();
		}finally{
			try {
				out.flush();
				if(out!=null)
					out.close();
				if(in!=null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;

	}

	/*
	 * public static File writeFile(InputStream in, String basePath, String
	 * fileName) throws IOException { return new
	 * File(writeFile(in,basePath,fileName)); }
	 */

	public static String getExtensionName(String fileName) {
		if ((fileName != null) && (fileName.length() > 0)) {
			int dot = StringUtils.lastIndexOf(fileName, ".");
			if ((dot > -1) && (dot < (fileName.length() - 1))) {
				return StringUtils.substring(fileName, dot + 1);

			}
		}
		return fileName;
	}

	public static String warpFileName(String fileName) {
		fileName = StringUtils.remove(fileName, "\\");
		fileName = StringUtils.remove(fileName, "/");
		fileName = StringUtils.remove(fileName, ":");
		fileName = StringUtils.remove(fileName, "*");
		fileName = StringUtils.remove(fileName, "?");
		fileName = StringUtils.remove(fileName, "\"");
		fileName = StringUtils.remove(fileName, "<");
		fileName = StringUtils.remove(fileName, ">");
		fileName = StringUtils.remove(fileName, "|");

		return fileName;
	}
	
    public static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs(); // 创建文件夹
        }
    } 
    
    
    public static void writeFile(String str,String filePath){
       
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(new File(filePath));
            osw = new OutputStreamWriter(fos, "UTF-8");
            bw = new BufferedWriter(osw);
            bw.write(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                bw.close();
                osw.close();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        
    }
    
    
 /*   public static void writeStreamToResponse(Map<String,Object> map,HttpServletRequest request,HttpServletResponse response) {
        File file = new File(String.valueOf(map.get("filePath")));
        
        String fileName = String.valueOf(map.get("fileName"));
        String agent = request.getHeader("USER-AGENT");
        InputStream fis = null;
        OutputStream out = null;

        try {
            // 将文件输出到Response

            String fileType = FileUtils.getExtensionName(fileName).toLowerCase();
            String mime = new MimetypesFileTypeMap().getContentType(file);

            fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[1024];
            int bytesRead;
            // 清空response
            response.reset();
            // 设置response的Header

            if (null != agent && -1 != agent.indexOf("MSIE")) {
                fileName = URLEncoder.encode(fileName, "UTF8");
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
                fileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
            } else {

                fileName = FileUtils.warpFileName(fileName);
            }
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType(mime);
            out = new BufferedOutputStream(response.getOutputStream());
            while (-1 != (bytesRead = fis.read(buffer, 0, buffer.length))) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                }

            }
        }
    }*/
    

}
