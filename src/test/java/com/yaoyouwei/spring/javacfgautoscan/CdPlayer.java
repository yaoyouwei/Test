package com.yaoyouwei.spring.javacfgautoscan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 
 * @ClassName: CdPlayer   
 * @Author yaoyouwei
 * @Date 2017年8月16日 下午9:20:04 
 * @Description: 
 */
@Component
public class CdPlayer {
	
	@Autowired
	ICompactDisk compactDisk;

	public CdPlayer() {
		super();
	}

	public CdPlayer(ICompactDisk compactDisk) {
		super();
		this.compactDisk = compactDisk;
	}
	
	public void playCd(){
		compactDisk.play();
	}
	

}
