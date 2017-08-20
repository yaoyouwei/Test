package com.yaoyouwei.spring.javacfgexplicit;


/** 
 * @ClassName: CompactDiskMyImpl   
 * @Author yaoyouwei
 * @Date 2017年8月14日 下午10:49:47 
 * @Description: 
 */
public class CompactDiskMyImpl implements ICompactDisk {

	@Override
	public void play() {
		System.out.println(this.getClass().getSimpleName()+" is play ");
	}

}
