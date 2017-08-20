package com.yaoyouwei.spring.javacfgautoscan;

import org.springframework.stereotype.Component;

/** 
 * @ClassName: CompactDiskMyImpl   
 * @Author yaoyouwei
 * @Date 2017年8月14日 下午10:49:47 
 * @Description: 
 */
@Component//默认的ID为类名首字母小写即 compactDiskMyImpl
public class CompactDiskMyImpl implements ICompactDisk {

	@Override
	public void play() {
		System.out.println(this.getClass().getSimpleName()+" is play ");
	}

}
