package com.yaoyouwei.spring.javacfgautoscan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** 
 * @ClassName: CdPlyerConfig   
 * @Author yaoyouwei
 * @Date 2017年8月14日 下午10:50:51 
 * @Description: 
 */
@Configuration//标记为java配置类
@ComponentScan//默认扫描当前类所在的包
//@ComponentScan(basePackages="")
//@ComponentScan(basePackages={"",""})
//@ComponentScan(basePackageClasses={CdPlayerConfig.class,CdPlayer.class})//扫描类所在的包,可创建标记类只用来标记
public class CdPlayerConfig {

}
