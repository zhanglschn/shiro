package com.juzix.shiro.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.juzix.shiro.test.common.Print;

public class PermissionTest {

//	private Logger logger = LoggerFactory.getLogger(PermissionTest.class);

	@Test
	public void testCustomVerification() {
		login("classpath:shiro-authorizer.ini", "zhang", "123");
		// 判断拥有权限:user:create
		Assert.assertTrue(subject().isPermitted("+user+10"));
		// 判断 拥有权限：user:update and user:delete
//		Assert.assertTrue(subject().isPermittedAll("user:update","user:delete"));
//		// 判断没有权限：user:view
//		Assert.assertTrue(subject().isPermitted("user:view"));
//		
//		List<Permission> perms = new ArrayList<Permission>();
//		perms.add(new WildcardPermission("user:view"));
//		
//		boolean[] bls = subject().isPermitted(perms);
//		for(boolean bl : bls) {
//			Print.print(bl);
//		}
//		bls = subject().hasRoles(Arrays.asList("role3","role2"));
//		for(boolean bl : bls) {
//			Print.print(bl);
//		}
	}
	
	@Test
	public void testIsPermitted() {
		login("classpath:shiro-permission.ini", "zhang", "123");
		// 判断拥有权限:user:create
		Assert.assertTrue(subject().isPermitted("user:create"));
		// 判断 拥有权限：user:update and user:delete
		Assert.assertTrue(subject().isPermittedAll("user:update","user:delete"));
		// 判断没有权限：user:view
		Assert.assertTrue(subject().isPermitted("user:view"));
		
		List<Permission> perms = new ArrayList<Permission>();
		perms.add(new WildcardPermission("user:view"));
		
		boolean[] bls = subject().isPermitted(perms);
		for(boolean bl : bls) {
			Print.print(bl);
		}
		bls = subject().hasRoles(Arrays.asList("role3","role2"));
		for(boolean bl : bls) {
			Print.print(bl);
		}
	}

	@Test
	public void testCheckPermission() {
		login("classpath:shiro-permission.ini", "zhang", "123");
		// 断言拥有权限：user:create
		subject().checkPermission("user:create");
		// 断言拥有权限：user:delete and user:update
		subject().checkPermissions("user:delete", "user:update");
		// 断言拥有权限：user:view 失败抛出异常
		subject().checkPermission("user:view");
		subject().checkPermissions("system:user:update", "system:user:delete");
		subject().checkPermissions("system:user:update,delete");
		subject().checkPermission("system:user:view");
		subject().checkPermission("system:user:delete");
		subject().checkPermissions("system:user:create,update,delete,view");
		subject().checkPermission(new WildcardPermission("system:user:delete"));
		subject().hasRole("role3");
	}

	public void login(String configFile, String userName, String password) {
		TestPub.loginSecurity(configFile, userName, password);
	}

	public Subject subject() {
		return TestPub.subject();
	}
}