package com.juzix.shiro.test;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class RoleTest {

	private void loginSecurity(String configFile, String userName, String password) {
		// 1.获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(configFile);

		// 2.得到SecurityManager实例
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();

		// 3.绑定SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);

		// 4.得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		subject.login(token);
	}

	@Test
	public void testHasRole() {
		loginSecurity("classpath:shiro-role.ini", "zhang", "123");
		// 判断拥有角色：role1
		Subject subject = SecurityUtils.getSubject();
		Assert.assertTrue(subject.hasRole("role1"));
		// 判断拥有角色：role1 and role2
		Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1", "role2")));
		// 判断拥有角色：role1 and role2 and !role3
		boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));
		Assert.assertEquals(true, result[0]);
		Assert.assertEquals(true, result[1]);
		Assert.assertEquals(false, result[2]);
	}
	
	@Test(expected=UnauthorizedException.class)
	public void testCheckRole() {
		loginSecurity("classpath:shiro-role.ini", "zhang", "123");
		// 断言拥有角色:role1
		subject().checkRoles("role1");
		// 断言拥有角色：role1和role3 失败抛出异常
		subject().checkRoles("role1","role3");
	}
	
	public Subject subject() {
		return SecurityUtils.getSubject();
	}
}