package com.juzix.shiro.test;

import java.util.Arrays;

import junit.framework.Assert;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.juzix.shiro.test.realm.MyRealm1;

public class NonConfigurationCreateTest {
	@Test
	public void testRealmCustomize() {
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		MyRealm1 myRealm = new MyRealm1();
		securityManager.setRealms(Arrays.asList((Realm) myRealm));
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","456");
		subject.login(token);
		
		subject.getPrincipal();
		Assert.assertTrue(subject.isAuthenticated());
	}
	
	
	@Test
	public void test() {
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		// 设置authenticator
		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
		authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		securityManager.setAuthenticator(authenticator);

		// 设置authorizer
		ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
		authorizer.setPermissionResolver(new WildcardPermissionResolver());
		securityManager.setAuthorizer(authorizer);

		// 设置Realm
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://192.168.106.128:3306/shiro");
		ds.setUsername("zhangls");
		ds.setPassword("1q2w3e4r");

		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(ds);
		jdbcRealm.setPermissionsLookupEnabled(true);
		securityManager.setRealms(Arrays.asList((Realm) jdbcRealm));

		// 将SecurityManager设置到SecurityUtils方便全局使用
		SecurityUtils.setSecurityManager(securityManager);

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "456");
		subject.login(token);

		
		subject.getPrincipal();
		Assert.assertTrue(subject.isAuthenticated());
	}
}