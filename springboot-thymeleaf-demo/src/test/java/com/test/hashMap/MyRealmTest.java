package com.test.hashMap;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

public class MyRealmTest {
	
	@Test
	public void testA() {
		SimpleHash a=	new SimpleHash("MD5", "123456", ByteSource.Util.bytes("admin" + "123"), 1);
		System.out.println(a.getSalt());
		System.out.println(a.toHex());
		System.out.println(a.toString());
		System.out.println(a.toBase64());
	    a=	new SimpleHash("MD5", "123456", ByteSource.Util.bytes("admin" + "123"), 2);
		System.out.println(a.getSalt());
		System.out.println(a.toHex());
		System.out.println(a.toString());
		System.out.println(a.toBase64());
		
		
	}
	

}
