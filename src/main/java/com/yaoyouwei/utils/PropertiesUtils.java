package com.yaoyouwei.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
[General]
Account =  			; 提交打印任务的帐号，会同步到其客户端，如果不指定就使用缺省的ExtPrinter帐号
Subject =   			; 传真主题
Comments =			; 传真备注
FaxFlowAccounts =	; 传真签审流程，如 zhangfan > limo > wangyan
SerialNO =			; 传真流水号，用于同步到结果数据库,必填
Priority = 1			; 传真优先级，0 低，1 普通，2 高，该配置生效的前提是账户具有“重要传真优先发送”的权限，默认为1
EmailResult =		; 发送者的邮箱，如果填写，则发送结果会发送到该邮箱
ReceiverList = 65654240 (复园科技\小张); 55666857(复园科技\小王)  ; 接收者传真号码，如果填写的是内部账户名，则发送给内部该用户,采用客户端输入传真号码文本框中的样式，多个号码以英文半角分号间隔
ReceiverNumber_1= 88889797 	; 第二种接收者传真号码表示方式，多个接收者用 _1, _2 表示，如果填写的是内部账户名，则发送给内部该用户,也支持EastFax地址
ReceiverName_1= 姓名1
ReceiverCompany_1 = 公司1
ReceiverNumber_2 = 99998787
ReceiverName_2 = 姓名2 
ReceiverCompany_2 = 公司2
SendLine=-1 	 ;指定线路发送(默认-1为自动选择)，0表示0通道，暨表示第一个线路功能不为“无”的线路，该配置生效的前提是帐号具有“指定线路发送”的权限
SendTime=	;指定时间发送，格式为YYYYMMDDhhmmss，如果不填或者填写的时间早于服务器当前时间表示即时发送
EastFax = Yes	;该字段非常重要，只有这样填写，才被认为是一个合法的打印提交文件。且该字段应该最后填写，因为程序一旦检测到该值为Yes即开始转换文件

*/

public class PropertiesUtils {

	public static void main(String[] args) {
		
		Properties prop = new Properties();
		prop.put("Account", "ExtPrinter");
		prop.put("Subject", "");
		prop.put("Comments", "");
		prop.put("FaxFlowAccounts", "zhangfan > limo > wangyan");
		prop.put("SerialNO", "");
		prop.put("Priority", "1");
		prop.put("EmailResult", "yaoyouwei@forwave.com");
		prop.put("ReceiverList", "65654240;55666857");
		prop.put("ReceiverNumber_1", "");
		prop.put("ReceiverName_1", "");
		prop.put("ReceiverCompany_1", "");
		prop.put("ReceiverNumber_2", "");
		prop.put("ReceiverName_2", "");
		prop.put("ReceiverCompany_2", "");
		prop.put("SendLine", "-1");
		prop.put("SendTime", "");
		prop.put("EastFax", "Yes");
		
		
		

		try {
			// 保存
			FileOutputStream out = new FileOutputStream("D:/program.properties");
			// 为properties添加注释
			prop.store(out, "the properties's comment");
			out.close();

			// 读取
			FileInputStream in = new FileInputStream("program.properties");
			prop.load(in);
			System.out.println(prop.getProperty("title"));
			System.out.println(prop.getProperty("name"));
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
