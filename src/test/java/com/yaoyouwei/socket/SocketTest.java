package com.yaoyouwei.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketTest {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("127.0.0.1",80);
		PrintWriter pw = new PrintWriter(socket.getOutputStream());
		pw.print("hello world");
		pw.print("test socket");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		StringBuffer sb = new StringBuffer(8096);
		if(br.ready()){
			int i = 0;
			while((i=br.read())>0){
				sb.append((char)i);
			}
		}
		System.out.println(sb.toString());
		
		
		
	}

}
