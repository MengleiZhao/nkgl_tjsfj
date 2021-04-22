package com.braker.common.security;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

import com.braker.common.util.SpringContextUtil;
import com.braker.core.manager.UserMng;
import com.braker.core.model.User;

/**
 * tomcat监听器
 * @author 陈睿超
 *
 */
@WebListener
@SuppressWarnings("serial")
public class SystemListener implements ServletContextListener{
	
	private UserMng userMng;
	
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        
		System.out.println("-----------销毁了Tomcat-----------");
    }
    
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        
    	System.out.println("-----------创建了Tomcat-----------");
    	/*ApplicationContext scu = SpringContextUtil.getApplicationContext();
    	userMng = scu.getBean(UserMng.class);
    	List<User> userlist = userMng.getALL();
    	for (int i = 0; i < userlist.size(); i++) {
    		User user = userlist.get(i);
    		user.setIsOnline(0);
    		userMng.updateDefault(user);
    	}*/
    }
}
