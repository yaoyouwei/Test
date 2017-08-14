package com.yaoyouwei.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** 
 * @ClassName: CompactDiskMyImplTest   
 * @Author yaoyouwei
 * @Date 2017年8月14日 下午11:00:08 
 * @Description: 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=CdPlayerConfig.class)//使用Java配置
public class CdPlayerTest {
	@Autowired
	private ICompactDisk compactDiskCd;

	@Test
	public void test() {
		compactDiskCd.play();
	}

}
