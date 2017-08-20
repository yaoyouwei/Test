package com.yaoyouwei.spring.javacfgexplicit;

/** 
 * @ClassName: CdPlayer   
 * @Author yaoyouwei
 * @Date 2017年8月16日 下午9:20:04 
 * @Description: 
 */
public class CdPlayer {
	
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
