package com.yaoyouwei.comp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Test{
    //编写Comparator,根据User的id对User进行排序
    private static final Comparator<User> COMPARATOR = new Comparator<User>() {
       public int compare(User o1, User o2) {
           return o1.compareTo(o2);//运用User类的compareTo方法比较两个对象       
      }
   };
    
    public static void main(String[] args) {
        ArrayList<User> student = new ArrayList<User>();
        User user1 = new User(1,"Alan");
        User user2 = new User(3,"Tom");
        User user3 = new User(2,"Jack");
        User user4 = new User(4,"Tony");
        student.add(user1);
        student.add(user2);
        student.add(user3);
        student.add(user4);
        
        for(int i=0;i<student.size();i++){
            System.out.println(student.get(i).getId()+"-"+student.get(i).getName());
        }
        System.out.println("------");
         
        Collections.sort(student, COMPARATOR);//用我们写好的Comparator对student进行排序
        
        for(int i=0;i<student.size();i++){
            System.out.println(student.get(i).getId()+"-"+student.get(i).getName());
        }
    }
}