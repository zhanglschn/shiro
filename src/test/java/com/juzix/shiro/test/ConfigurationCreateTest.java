package com.juzix.shiro.test;

import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class ConfigurationCreateTest {
	@Test
	public void test() {
		Factory<org.apache.shiro.mgt.SecurityManager> factory = 
				new IniSecurityManagerFactory("classpath:shiro-config.ini");
	}
}