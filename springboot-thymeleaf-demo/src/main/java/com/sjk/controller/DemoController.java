package com.sjk.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.sjk.bean.SysUser;
import com.sjk.controller.tools.JsonObject;
import com.sjk.controller.tools.VerifyCode;

@Controller
public class DemoController {
	
	private static final Logger logger=LoggerFactory.getLogger(DemoController.class);
	@RequestMapping("/")
	public String demo() {
		return "demo";
	}
	
	@RequestMapping("/zichan")
	public String zichan() {
		return "demo1";
	}
	@RequestMapping("/fuzhai")
	public String fuzhai() {
		return "demo2";
	}
	@RequestMapping("/shouru")
	public String shouru() {
		return "demo";
	}
	@RequestMapping("/zhichu")
	public String zhichu() {
		return "demo";
	}
	@RequestMapping("/baobiao")
	@RequiresPermissions("userInfo:view11")
	public String baobiao() {
		return "demo";
	}
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/user/login")
	public String login(HttpServletRequest request) {
		return "/user/login";
	}
	
	@RequestMapping("/user/login2")
	public String login2(HttpServletRequest request) {
		return "/user/login2";
	}
	
	
	@RequestMapping("/image")
	public void image(HttpServletRequest request,HttpServletResponse response) throws IOException {
		VerifyCode a=new VerifyCode();
		BufferedImage image=a.getImage();
		request.getSession().setAttribute("image-text",a.getText().toLowerCase());
		VerifyCode.output(image,response.getOutputStream());
	}
	
	
	
	 
//	
//	@RequestMapping("/login")
//	public String login(HttpServletRequest request, Map<String, Object> map) throws Exception{
//	    System.out.println("HomeController.login()");
//	    // 登录失败从request中获取shiro处理的异常信息。
//	    // shiroLoginFailure:就是shiro异常类的全类名.
//	    String exception = (String) request.getAttribute("shiroLoginFailure");
//	    System.out.println("exception=" + exception);
//	    String msg = "";
//	    if (exception != null) {
//	        if (UnknownAccountException.class.getName().equals(exception)) {
//	            System.out.println("UnknownAccountException -- > 账号不存在：");
//	            msg = "UnknownAccountException -- > 账号不存在：";
//	        } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
//	            System.out.println("IncorrectCredentialsException -- > 密码不正确：");
//	            msg = "IncorrectCredentialsException -- > 密码不正确：";
//	        } else if ("kaptchaValidateFailed".equals(exception)) {
//	            System.out.println("kaptchaValidateFailed -- > 验证码错误");
//	            msg = "kaptchaValidateFailed -- > 验证码错误";
//	        } else {
//	            msg = "else >> "+exception;
//	            System.out.println("else -- >" + exception);
//	        }
//	    }
//	    map.put("msg", msg);
//	    // 此方法不处理登录成功,由shiro进行处理
//	    return "index";
//	}

	@RequestMapping(value="/user/login",method=org.springframework.web.bind.annotation.RequestMethod.POST)
	public String login(@Valid SysUser user,String checkCode,RedirectAttributes redirectAttributes) throws Exception{
	    
		String userName= user.getUsername();
		
		UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(), user.getPassword());
		
		/**获取当前用户**/
		 Subject currentUser=SecurityUtils.getSubject();
		
		 
		try {
			//调用login方法后 SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查。
			//每个realm都能在必要时对提交的AuthenticationTokens做出反应
			//所以这一步在调用login(token)方法时，他会走到Myrealm的doGetAuthenticationInfo()方法中 具体验证方式详见代码
			logger.info("对用户["+userName+"]进行登陆验证。。验证开始");
			currentUser.login(token);
			logger.info("对用户["+userName+"]进行登陆验证。。验证通过");
		}catch(UnknownAccountException uae){
			logger.info("对用户["+userName+"]进行登陆验证。。验证未通过 未知账户");
			redirectAttributes.addFlashAttribute("message", "未知账户");
		}catch (IncorrectCredentialsException ice) {
			logger.info("对用户["+userName+"]进行登陆验证。。验证未通过  错误的凭证");
			redirectAttributes.addFlashAttribute("message", "密码不正确");
		}catch(LockedAccountException lae) {
			logger.info("对用户["+userName+"]进行登陆验证。。验证未通过 账户已锁定");
			redirectAttributes.addFlashAttribute("message", "账户锁定");
		}catch(ExcessiveAttemptsException eae) {
			logger.info("对用户["+userName+"]进行登陆验证。。验证未通过 错误次数太多");
			redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
		}catch(AuthenticationException ae) {
			logger.info("对用户["+userName+"]进行登陆验证。。验证未通过 堆栈轨迹如下");
			ae.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
		}
		
		if(currentUser.isAuthenticated() && redirectAttributes.getFlashAttributes().get("message")==null) {
			logger.info("对用户["+userName+"]登陆认证通过 此处可以进行一些认证通过后的一些系统参数初始化操作");
			return "redirect:/index";
		} else {
			token.clear();
			return "redirect:/user/login";
		}
		 
	}
	
	@RequestMapping(value="/user/login2",method=org.springframework.web.bind.annotation.RequestMethod.POST)
	@ResponseBody
	public String login2(@Valid SysUser user,String checkCode,HttpServletRequest request) throws Exception{
	    
		String errorMessage="";
		JsonObject jsonObject=new JsonObject();
		String userName= user.getUsername();
		UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(), user.getPassword());
		
		/**获取当前用户**/
		 Subject currentUser=SecurityUtils.getSubject();
		 String validCode=(String)request.getSession().getAttribute("image-text");
		 if(!checkCode.equals(validCode.toLowerCase())) {
			 errorMessage="验证码错误！";
		 }
		if("".equals(errorMessage)) { 
			try {
				//调用login方法后 SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查。
				//每个realm都能在必要时对提交的AuthenticationTokens做出反应
				//所以这一步在调用login(token)方法时，他会走到Myrealm的doGetAuthenticationInfo()方法中 具体验证方式详见代码
				logger.info("对用户["+userName+"]进行登陆验证。。验证开始");
				currentUser.login(token);
				logger.info("对用户["+userName+"]进行登陆验证。。验证通过");
			}catch(UnknownAccountException uae){
				logger.info("对用户["+userName+"]进行登陆验证。。验证未通过 未知账户");
				 errorMessage="未知用户";
			}catch (IncorrectCredentialsException ice) {
				logger.info("对用户["+userName+"]进行登陆验证。。验证未通过  错误的凭证");
				errorMessage="密码不正确";
			}catch(LockedAccountException lae) {
				logger.info("对用户["+userName+"]进行登陆验证。。验证未通过 账户已锁定");
				errorMessage="账户锁定";
			}catch(ExcessiveAttemptsException eae) {
				logger.info("对用户["+userName+"]进行登陆验证。。验证未通过 错误次数太多");
				errorMessage="用户名或密码错误次数过多";
			}catch(AuthenticationException ae) {
				logger.info("对用户["+userName+"]进行登陆验证。。验证未通过 堆栈轨迹如下");
				ae.printStackTrace();
				errorMessage="用户名或密码不正确";
			}
			if(currentUser.isAuthenticated() && "".equals(errorMessage)) {
				logger.info("对用户["+userName+"]登陆认证通过 此处可以进行一些认证通过后的一些系统参数初始化操作");
				jsonObject.setStatus("success");
				jsonObject.setsMsg("登陆成功！");
				jsonObject.setPath("/demo/index");
				
				return JSON.toJSON(jsonObject).toString();
			} else {
				token.clear();
				jsonObject.setStatus("error");
				jsonObject.seteMsg(errorMessage);
				jsonObject.setPath("#");
				return JSON.toJSON(jsonObject).toString();
			}
		}else {
			jsonObject.setStatus("error");
			jsonObject.seteMsg(errorMessage);
			jsonObject.setPath("#");
			return JSON.toJSON(jsonObject).toString();
		}
		
		
		
		
		
		
		
		 
	}
	
	
	
	@RequestMapping(value="/user/loginout",method=RequestMethod.GET)
	public String login() throws Exception{
		    SecurityUtils.getSubject().logout();
			return "redirect:/user/login";
	}
	
	
	
}
