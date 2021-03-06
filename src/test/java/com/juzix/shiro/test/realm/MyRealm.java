package com.juzix.shiro.test.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.juzix.shiro.test.permission.BitPermission;

public class MyRealm extends AuthorizingRealm{
	
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addRole("role1");
		authorizationInfo.addRole("role2");
		
		authorizationInfo.addObjectPermission(new BitPermission("+user+10"));
		authorizationInfo.addObjectPermission(new WildcardPermission("user1:*"));
		
		authorizationInfo.addStringPermission("+user2+10");
		authorizationInfo.addStringPermission("user2:*");
		
		return authorizationInfo;
	}
	
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		String userName = (String) token.getPrincipal();// 得到用户名
		String password = new String((char[]) token.getCredentials());//得到密码
		if (!"zhang".equals(userName)) {
			throw new UnknownAccountException();
		}
		
		if (!"123".equals(password)) {
			throw new IncorrectCredentialsException();
		}
		
		return new SimpleAuthenticationInfo(userName,password,this.getClass().getSimpleName());
	}
}