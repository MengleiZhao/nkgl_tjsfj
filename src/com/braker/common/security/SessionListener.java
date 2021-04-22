package com.braker.common.security;

import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.braker.common.util.SpringContextUtil;
import com.braker.core.manager.UserMng;
import com.braker.core.model.User;
import com.braker.icontrol.contract.expiration.manager.ExpirationMng;

/**
 * session的监听器
 * @author 陈睿超
 *
 */
@WebListener
@SuppressWarnings("serial")
public class SessionListener implements HttpSessionListener{

	private UserMng userMng;
	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		
		//System.out.println("session创建了");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		
		/*System.out.println("session要关闭了");
		HttpSession session = arg0.getSession();
		User user = (User) session.getAttribute("currentUser");
		ApplicationContext scu = SpringContextUtil.getApplicationContext();
		userMng = scu.getBean(UserMng.class);
		user = userMng.findById(user.getId());
		user.setIsOnline(0);
		userMng.merge(user);*/
		
		
	}

	
	
}
