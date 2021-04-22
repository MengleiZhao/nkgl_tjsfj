package com.braker.core.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braker.common.page.Result;
import com.braker.common.security.BadCredentialsException;
import com.braker.common.security.LockedException;
import com.braker.common.security.OnlineException;
import com.braker.common.security.UserNameNotFoundException;
import com.braker.common.util.RequestUtils;
import com.braker.common.util.StringUtil;
import com.braker.common.web.BaseController;
import com.braker.core.manager.FunctionMng;
import com.braker.core.manager.IndexShortcutMng;
import com.braker.core.manager.PersonalWorkMng;
import com.braker.core.manager.SystemThemesMng;
import com.braker.core.manager.UserMng;
import com.braker.core.model.Function;
import com.braker.core.model.IndexShortcut;
import com.braker.core.model.SystemThemes;
import com.braker.core.model.User;

@SuppressWarnings("serial")
@Controller
public class LoginController extends BaseController {

	@Autowired
	private UserMng userMng;
	@Autowired
	private FunctionMng functionMng;
	@Autowired
	private PersonalWorkMng personalWorkMng;
	@Autowired
	private SystemThemesMng systemThemesMng;
	@Autowired
	private IndexShortcutMng indexShortcutMng;
	
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String input(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		List<SystemThemes> li = systemThemesMng.findByStauts("1");
		String url = "img";
		if (li.size()>0) {
			url = li.get(0).getUrl1();
		}
		request.getSession().setAttribute("themenurl", url);//皮肤路径
		
		return "welcome";
	}

/*	@RequestMapping(value = "/streetIndex.do", method = RequestMethod.GET)
	public String streetIndex(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "/WEB-INF/view/index_street_01022";
	}*/

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
//		return "/WEB-INF/view/index_jwh_"+getUser().getStreet().getStreetCode();
//		List<Function> userMenus = functionMng.getFunctions(getUser().getId());
		/*List<Role> role = userMng.getUserRole(getUser().getId());
		List<Function> userMenus = functionMng.getUserMenu(role.get(0));
		List<Function> menus = new ArrayList<Function>();
		for (int i = 0; i < userMenus.size(); i++) {
			if(userMenus.get(i).getPriority()>200 && userMenus.get(i).getParent().getId()==31){
				menus.add(userMenus.get(i));
			}
		}
		menus = changeOrder(menus);
		model.addAttribute("menus", menus);*/
		model.addAttribute("userName", getUser().getName());
		model.addAttribute("departName", getUser().getDepartName());
		model.addAttribute("taskNums", personalWorkMng.countTaskNum(getUser().getId()));
		if(request.getSession().getAttribute("themenurl")==null){
			List<SystemThemes> li = systemThemesMng.findByStauts("1");
			String url = "img";
			if (li.size()>0) {
				url = li.get(0).getUrl1();
			}
			request.getSession().setAttribute("themenurl", url);//皮肤路径
		}
		/*return "main/main";*/
		
		
		//查询用户的快速入口
		List<IndexShortcut> ksrk = indexShortcutMng.findByUserId(getUser());
		model.addAttribute("ksrk", ksrk);
		model.addAttribute("loginType", request.getSession().getAttribute("loginType"));
		request.getSession().removeAttribute("loginType");
		return "newMain/main-new";
	}
	
/*	//排序
	public List<Function> changeOrder(List<Function> list) {
		for(int i =0;i < list.size();i++)  
        {   
			for (int j = i; j < list.size(); j++) {
				if(list.get(i).getPriority()>list.get(j).getPriority())	{
					Function f = list.get(i);
					list.set(i, list.get(j));
					list.set(j, f);
				}
			}
        } 
		return list;
	}*/
	
	//左边页面显示
	@RequestMapping(value = "/leftmenu.do")
	@ResponseBody
	public List<Function> leftmenu(Long leftmenu, ModelMap model) {
		List<Function> leftlist = functionMng.getChildByUser(leftmenu, getUser().getId());
		return leftlist;
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String submit(String accountNo, String password, String captcha,
			String message,String street, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		try {
			HttpSession session = request.getSession();
			User user = userMng.login(accountNo, password);
			session.setAttribute("currentUser", user);
			session.setAttribute(Function.RIGHTS_KEY, functionMng.getFunctionItems(user.getId()));
			session.setAttribute("loginType", 0);
			user.setLastLoginTime(new Date());
//			user.setIsOnline(1);
			userMng.saveOrUpdate(user);
			// 判断进入区级首页、街镇级首页还是居委首页
			user.hasRole("STREET_ROLE");
			user.hasRole("QU_ROLE");
			//if (user.hasRole("QU_ROLE")) {
			return "redirect:/index.do";
			/*} else if (user.hasRole("STREET_ROLE")) {
				return "redirect:/streetIndex.do";
			}
			return "redirect:/jwhIndex.do";*/
		} catch (UserNameNotFoundException e) {
			model.addAttribute("loginMsg", e.getMessage());
		} catch (LockedException e) {
			model.addAttribute("loginMsg", e.getMessage());
		} catch (BadCredentialsException e) {
			model.addAttribute("loginMsg", e.getMessage());
		} catch (OnlineException e) {
			model.addAttribute("loginMsg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("loginMsg", "系统发生错误,请联系管理员!");
		}
		return "login";
	}

	@RequestMapping(value = "/logout.do")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		try {
//			User user = getUser();
//			user.setIsOnline(0);
//			userMng.merge(user);
			HttpSession session = request.getSession(false);
			if (null != session) {
				session.invalidate();
			}
			return "redirect:login.do";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:login.do";
		}
	}
	
	//判断session是否过期
	@RequestMapping(value = "/timeout.do")
	@ResponseBody
	public String timeout(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (null != session) {
			return "false";
		}else{
			return "true";
		}
	}
	
	/**
	 * 切换部门重新加载用户数据
	 * @param model
	 * @param id
	 * @param accountNo
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 * @author 陈睿超
	 * @createtime 2020年7月1日
	 * @updator 陈睿超
	 * @updatetime 2020年7月1日
	 */
	@ResponseBody
	@RequestMapping("/relogin")
	public Result relogin(ModelMap model ,String id,String accountNo,String password,HttpServletRequest request,
			HttpServletResponse response) {
		User user = userMng.findById(id);
		model.addAttribute("accountNo", user.getAccountNo());
		model.addAttribute("password", "123456");
		HttpSession session = request.getSession();
		//获得原登录账号
//		User olduser = getUser();
//		olduser.setIsOnline(0);
//		userMng.merge(olduser);
		
		session.setAttribute("currentUser", user);
		session.setAttribute(Function.RIGHTS_KEY, functionMng.getFunctionItems(user.getId()));
		session.setAttribute("loginType", 0);
		user.setLastLoginTime(new Date());
		user.setIsOnline(1);
		userMng.merge(user);
		// 判断进入区级首页、街镇级首页还是居委首页
		user.hasRole("STREET_ROLE");
		user.hasRole("QU_ROLE");
		return getJsonResult(true,null);
	}
	
	
	//退出session
	@ResponseBody
	@RequestMapping(value = "/outsession.do")
	public String outsession(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		User user = getUser();
//		user.setIsOnline(0);
		userMng.saveOrUpdate(user);
		session.removeAttribute("currentUser");
		return "false";
	}
	
	/**
	 * OA单点登录内控系统
	 * @param accountNo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @author wanping
	 * @createtime 2021年3月22日
	 * @updator wanping
	 * @updatetime 2021年3月22日
	 */
	@RequestMapping(value = "/singleSignOn", method = RequestMethod.GET)
	public String singleSignOn(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		//清除session
		Enumeration<String> em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		
		String ipAddr = RequestUtils.getIpAddr(request);
		System.out.println("-------------登录IP：" + ipAddr);
//		if (!"172.16.242.205".equals(ipAddr)) {
//			System.out.println("IP地址异常");
//			return "403";
//		}
		try {
			String accountNo = request.getParameter("accountNo");
			
			if (StringUtil.isEmpty(accountNo)) {
				System.out.println("参数【accountNo】不能为空");
				return "403";
			}
			System.out.println("-------------登录用户：" + accountNo);
			if ("admin".equals(accountNo) || "管理员1".equals(accountNo)) {
				System.out.println("您没有权限访问");
				return "403";
			}
			HttpSession session = request.getSession();
			//根据accountNo获取用户
			User user = userMng.getByName(accountNo);
			if (user == null) {
				System.out.println("用户不存在");
				return "403";
			}
			session.setAttribute("currentUser", user);
			session.setAttribute(Function.RIGHTS_KEY, functionMng.getFunctionItems(user.getId()));
			session.setAttribute("loginType", 0);
			user.setLastLoginTime(new Date());
//			user.setIsOnline(1);
			userMng.saveOrUpdate(user);
			return "redirect:/index.do";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("loginMsg", "系统发生错误,请联系管理员!");
			return "500";
		}
	}
}
