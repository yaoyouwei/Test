package com.yaoyouwei.test;

import java.util.Random;
import java.util.UUID;

import com.yaoyouwei.utils.FileUtils;

public class GenerateSqltData {
	
	private static String string = "abcdefghijklmnopqrstuvwxyz";    


	public static String getRandomString(int length){
	    StringBuffer sb = new StringBuffer();
	    int len = string.length();
	    for (int i = 0; i < length; i++) {
	        sb.append(string.charAt(Math.round( (int)Math.random() * (len-1))));
	    }
	    return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(getRandomString(5));
		System.out.println(getRandomString(5));
		System.out.println(getRandomString(5));
		System.out.println(getRandomString(5));
		
	}
	
	public void generateData(){
		long startTime = System.currentTimeMillis();    //获取开始时间
		
		Random random=new java.util.Random();// 定义随机类
		int result=random.nextInt(10);// 返回[0,10)集合中的整数，注意不包括10
		String [] stuNames = {"赵一","钱二","孙三","李四","周五","吴六","郑七","王八","张九"};
		String [] classNames = {"九一","九二","九三","九四","九五","九六","九七","九八","九九"};
		//INSERT INTO  class  (id,NAME,TYPE,LEVEL) VALUES ('514514515','九五班','1','18')
		//INSERT INTO  student (id,sex,NAME,age,address,class_id) VALUES ('2','男','姚有伟','18','安徽','514514515')
		StringBuilder sql_student = new StringBuilder();
		StringBuilder sql_class = new StringBuilder();
		for(int i=1;i<=500000;i++){
			String class_id = UUID.randomUUID().toString();
			sql_class.append("'");
			sql_class.append(class_id).append("','");
			sql_class.append(classNames[random.nextInt(9)]).append("','");
			sql_class.append(random.nextInt(1000)).append("','");
			sql_class.append(+random.nextInt(100)).append("'\n");
			sql_student.append("'");
			sql_student.append(UUID.randomUUID().toString()).append("','");
			sql_student.append("男").append("','");
			sql_student.append(stuNames[random.nextInt(9)]).append("','");
			sql_student.append(random.nextInt(100)).append("','");
			sql_student.append("上海").append("','");
			sql_student.append(class_id).append("'\n");
			class_id = null;
		}
		FileUtils.writeFile(sql_student.toString(), "D:/test_student.txt");
		FileUtils.writeFile(sql_class.toString(), "D:/test_class.txt");
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime-startTime)/1000 + "s"); 
	}

}
