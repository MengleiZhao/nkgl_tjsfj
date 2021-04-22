package com.braker.common.security;

/**
 * 账号重复登录错误
 * @author 陈睿超
 *
 */
@SuppressWarnings("serial")
public class OnlineException extends AuthenticationException{

	
	public OnlineException(){
		
	}
	
	public OnlineException(String msg){
		super(msg);
	}
	
	
}
