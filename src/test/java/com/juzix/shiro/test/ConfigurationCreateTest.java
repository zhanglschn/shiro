package com.juzix.shiro.test;

import junit.framework.Assert;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;




import static com.juzix.shiro.test.common.Print.print;

public class ConfigurationCreateTest {
	@Test
	public void testHex() {
		String str = "测试";
		String base64Encoded = Hex.encodeToString(CodecSupport.toBytes(str, "UTF-8"));
		print(base64Encoded);
		
		String str2 =CodecSupport.toString(Hex.decode(base64Encoded.getBytes()),"UTF-8");
		Assert.assertEquals(str, str2);
	}
	
	@Test
	public void testBase64() {
		String str = "hello";
		String base64Encoded = Base64.encodeToString(str.getBytes());
		
		print(base64Encoded);
		
		String str2 = Base64.decodeToString(base64Encoded);
		print(str2);
		Assert.assertEquals(str, str2);
	}
	
	@Test
	public void test() {
		Factory<org.apache.shiro.mgt.SecurityManager> factory = 
				new IniSecurityManagerFactory("classpath:shiro-config.ini");
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		//  将SecurityManager设置到SecurityUtils方便全局使用
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		subject.login(token);
		
		Assert.assertTrue(subject.isAuthenticated());
		
	}
}