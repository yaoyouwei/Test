package com.yaoyouwei.spring.javacfgexplicit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** 
 * @ClassName: CdPlyerConfig   
 * @Author yaoyouwei
 * @Date 2017年8月14日 下午10:50:51 
 * @Description: 配置类
 */
@Configuration
public class CdPlayerConfig {
	
	//显示注入第三方类
	@Bean
	public ICompactDisk CompactDiskMyImpl(){
		return new CompactDiskMyImpl();
	}
	
	@Bean
	public CdPlayer cdlayer(ICompactDisk compactDisk){
		return new CdPlayer(compactDisk);
	}

}
