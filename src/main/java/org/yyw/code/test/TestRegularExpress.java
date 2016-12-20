package org.yyw.code.test;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TestRegularExpress {

	public static void main(String[] args) {
		//
		 String a = "/";
		 System.out.println(a);
		 File f = new File("D:/jav/a");
		 String  str ="\\\\";//字符串将反斜杠当做转义字符处理\\=\,\\\\=\\
		 /*java String ��\�Զ�����ת��, \\\\�൱��\\,�����ٽ���\,����൱��ƥ��һ�����\ ,*/
         String regex = "\\\\\\\\";/* �൱�� \\\\ */
		 Pattern p = Pattern.compile(regex);
		 Matcher m = p.matcher(str);
		 boolean b = m.matches();
		 System.out.println("Pattern:"+regex);
		 System.out.println("InputString:"+str);
		 System.out.println("PaternResult:"+b);

	}
	
	@Test
	public void testReplace() {
		
		
		String str = "YaoYouWei  ";
		
		String regex = "[\\s\\p{Zs}]";
		System.out.println(str+"#"+(str.replaceAll(regex, ""))+"#");
		
		str = "Yao  YouWei";
		System.out.println(str+"#"+(str.replaceAll(regex, ""))+"#");

		
		str = "Yao YouWei";
		System.out.println(str+"#"+(str.replaceAll(regex, ""))+"#");

		
		str ="YaoYouWei      ";
		System.out.println(str+"#"+(str.replaceAll(regex, ""))+"#");

		
		str =" Yao   YouWei     ";
		System.out.println(str+"#"+(str.replaceAll(regex, ""))+"#");
		
		str ="Ｙａｏ　Ｙｏｕ　　　Ｗｅｉ　";//英文全角
		System.out.println(str+"#"+(str.replaceAll(regex, ""))+"#");
		
		
		//（空格，中横杠，下横杠，前斜杠，后斜杠，冒号，引号）
		String [] regexs ={"[\\s\\p{Zs}]","[-－]","[＿_]","[/／]","[＼\\\\]","[：:]","[＂\"]"}; 
		str ="姚　有　伟　　";//中文全角
		System.out.println(str+"#"+(str.replaceAll(regex, ""))+"#");
		
		/*str ="姚　有　　伟　";//中文全角
		System.out.println(str+"#"+(str.replaceAll("\\s*", ""))+"#");
		
		str ="姚 有 伟";//中文半角
		System.out.println(str+"#"+(str.replaceAll("\\s*", ""))+"#");*/
		str ="Y-－460-";//中文半角
		System.out.println(str+"#"+(str.replaceAll("[-－]", ""))+"#");
		
		str ="＿Y_＿460＿";//中文半角
		System.out.println(str+"#"+(str.replaceAll("[＿_]", ""))+"#");
		
		str ="／/／／／Y／/／／460／/／／／";//中文半角
		System.out.println(str+"#"+(str.replaceAll("[/／]", ""))+"#");
		
		
		str ="＼\\＼＼Y\\＼\\＼4＼6＼0＼＼";//中文半角
		System.out.println(str+"#"+(str.replaceAll("[＼\\\\]", ""))+"#");
		
		str ="：:：Y::：：:46：：:0：：:：";//中文半角
		System.out.println(str+"#"+(str.replaceAll("[：:]", ""))+"#");
		
		str ="＂＂\"＂＂Y\"＂460\"＂";//中文半角
		System.out.println(str+"#"+(str.replaceAll("[＂\"]", ""))+"#");

		
		
	}
	@Test
	public void testReplaceStr() {
		File targetImageFile = new File(this.getClass().getClassLoader().getResource("").getFile());
		System.out.println(targetImageFile.getAbsolutePath()+File.separator+"tempImage.jpg");
		String str = "С��:'���'!";
		System.out.println(str);
		str = str.replace("'", "''");
		System.out.println(str);
		
	}
}
