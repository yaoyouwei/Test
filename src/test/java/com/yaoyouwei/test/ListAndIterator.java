package com.yaoyouwei.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//测试List的Iterator
public class ListAndIterator {

	public static void main(String[] args) {
		List <String>list = new ArrayList<String>();
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		Iterator<String> it = list.iterator();
		it.next();
		it.next();//
		it.remove();
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
	}

}
