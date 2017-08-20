package com.yaoyouwei.utils;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/** 
 * @ClassName: WildcardUtils   
 * @Author yaoyouwei
 * @Date 2017年8月16日 下午11:09:24 
 * @Description: 通配符转义
 */
public class WildcardUtils {
	private static Pattern  wildcardPattern;
	private static Pattern winUnsupportFileNamePattern;
	private static String wildcardRegex = "\\[[^\\[\\]]+\\]"; //匹配[]
	private static String winUnsupportFileNameRegex = "[\\s\\\\/:\\*\\?\\\"<>\\|]"; //windows不支持的文件名字符  / \ " : | * ? < >
	private static String nullReplaceChar = "null";
	private static String errorReplaceChar = "undefined";
	private static String unsupportChar = "-";
	
	static{
		wildcardPattern = Pattern.compile(wildcardRegex);
		winUnsupportFileNamePattern = Pattern.compile(winUnsupportFileNameRegex);
	}
	
	public static void main(String[] args) {
		
		test();
	}
	
	public static void  test(){
		User user = new User();
		user.setName("姚有伟 0/1\\2\"3:4|5*6?7<8>9 10");
		user.setAge(null);
		String str = "D:/[tt][TT]/[ms]年[yy][mm]/[m]/[mm]/[dd]/[user.name]/[user.age]/ww";
		String value = WildcardUtils.getWildcardValue(str,user);
		System.out.println(value);
		
		str = "D:/[YYYY]/[YY]/[MM]/[M]/[dd]/[d]/[tt]/[TT]/[HH]/[H]/[hh]/[h]/[mm]/[m]/[ss]/[s]/[ms]/[4]";
		value = WildcardUtils.getWildcardValue(str,user);
		System.out.println(value);
	}
	
	
	
	public static <T> String getWildcardValue(String wildcards,T template){
		String result = null;
		try {
			
			StringBuffer sb = new StringBuffer();
			Matcher matcher =wildcardPattern.matcher(wildcards);
			while(matcher.find()) {
				System.out.println(matcher.group()); 
				String wildcard = matcher.group().substring(1,matcher.group().length()-1);//去掉[]
				String value = "";
				if(wildcard.indexOf(".")<0){//通用
					value = getCommonWildcardValue(wildcard);
				}else{//自定义
					value = getCustomWildcardValue(wildcard,template);
				}
				Matcher winUnsupportFileNameMtc = winUnsupportFileNamePattern.matcher(value);
				value= winUnsupportFileNameMtc.replaceAll(unsupportChar); // 将匹配到的非法字符以指定字符替换
				matcher.appendReplacement(sb, value);
			}
			matcher.appendTail(sb);
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	public static <T> String getCustomWildcardValue(String wildcard,T template){
		String value = errorReplaceChar;
		try {
			String [] classInfo = wildcard.split("\\.");
			String className = classInfo[0];
			String classFieldName = classInfo[1];
			Class<? extends Object> templateClass = template.getClass();
			if(!StringUtils.equalsIgnoreCase(className, templateClass.getSimpleName())){
				throw new Exception("Class expected "+className+" but was "+templateClass.getSimpleName());
			}
			//value = getFieldWildcardValue(classField, template);
			Field field = templateClass.getDeclaredField(classFieldName);
			field.setAccessible(true); //打开javabean的访问权限
			value = String.valueOf(field.get(template));
		} catch (Exception e) {
			value = errorReplaceChar;
			e.printStackTrace();
		}
		
		return value;
	}
	
	public static String getCommonWildcardValue(String wildcard){
		String value = errorReplaceChar;
		try {
			Calendar calendar = Calendar.getInstance();
			String ignoreCase = wildcard.toLowerCase();
			switch (ignoreCase) {//忽略大小写
			case "yyyy":
				value = String.valueOf(calendar.get(Calendar.YEAR));
				break;
			case "yy":
				value = String.valueOf(calendar.get(Calendar.YEAR));
				value = value.substring(value.length()-2);
				break;
			case "ms":
				value = String.valueOf(calendar.get(Calendar.MILLISECOND));
				break;
			case "d":
				value = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
				break;
			case "dd":
				value = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
				value = value.length()>1?value:"0"+value;
				break;
			default:
				value = errorReplaceChar;
				break;
			}
			if(!errorReplaceChar.equals(value)){
				return value;
			}
			switch (wildcard) {//忽略大小写
			case "M":
				value = String.valueOf(calendar.get(Calendar.MONTH)+1);
				break;
			case "MM":
				value = String.valueOf(calendar.get(Calendar.MONTH)+1);
				value = value.length()>1?value:"0"+value;
				break;
			case "h":
				value = String.valueOf(calendar.get(Calendar.HOUR));
				break;
			case "hh":
				value = String.valueOf(calendar.get(Calendar.HOUR));
				value = value.length()>1?value:"0"+value;
				break;
			case "H":
				value = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
				break;
			case "HH":
				value = String.valueOf(calendar.get(Calendar.HOUR));
				value = value.length()>1?value:"0"+value;
				break;
			case "m":
				value = String.valueOf(calendar.get(Calendar.MINUTE));
				break;
			case "mm":
				value = String.valueOf(calendar.get(Calendar.MINUTE));
				value = value.length()>1?value:"0"+value;
				break;
			case "s":
				value = String.valueOf(calendar.get(Calendar.SECOND));
				break;
			case "ss":
				value = String.valueOf(calendar.get(Calendar.SECOND));
				value = value.length()>1?value:"0"+value;
				break;
			case "tt":
				value =calendar.get(Calendar.AM_PM)==0?"AM":"PM";
				break;
			case "TT":
				value =calendar.get(Calendar.AM_PM)==0?"上午":"下午";
				break;
			default:
				value = errorReplaceChar;
				break;
			}
		} catch (Exception e) {
			value = errorReplaceChar;
			e.printStackTrace();
		}
		return value;
	}
	
	public static <T> String getFieldWildcardValue(String fieldName,T template ){
		String value = errorReplaceChar;
		try {
			Class<? extends Object> cls = template.getClass();
			Field field = cls.getDeclaredField(fieldName);
			field.setAccessible(true); //打开javabean的访问权限
			value = String.valueOf(field.get(template));
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	// 首字母转小写
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0))){
			return s;
		}else{
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
		}
	}

	// 首字母转大写
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0))){
			return s;
		}else{
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
		}
	}

	

}
