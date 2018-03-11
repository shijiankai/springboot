package com.sjk.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

public class MyCredentialsMatcher extends HashedCredentialsMatcher {
	
	
	public MyCredentialsMatcher() {
		super();
		this.setHashAlgorithmName("MD5");
		this.setHashIterations(2);
		this.setStoredCredentialsHexEncoded(false);
		
	}
	
	

}
