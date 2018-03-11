package com.sjk.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice  
public class GolabelExceptionHandler {  
      
    public static final String DEFAULT_ERROR_VIEW = "error";  
      
    @ExceptionHandler(value = Exception.class) 
    public ModelAndView businessExceptionHandler(HttpServletRequest req, Exception e) throws Exception {  
        
    	
    	e.printStackTrace();
    	
    	String message="";
    	if(e instanceof UnauthorizedException ) {
    		message=e.getMessage();
    		message="权限不足:"+message;
    		
    	}else {
    		message=e.getMessage();
    	}
    	
    	
        ModelAndView mav = new ModelAndView();  
        mav.addObject("message", message);  
        mav.setViewName(DEFAULT_ERROR_VIEW);  
        return mav;  
    }  
      
//    @ExceptionHandler(value = Exception.class)  
//    @ResponseBody  
//    public Map<String, String> jsonExceptionHandler(HttpServletRequest req, Exception e) {  
//          
//        Map<String, String> re = new HashMap<String, String>();  
//        re.put("error", "1");  
//        re.put("msg", e.getMessage());  
//        return re;  
//    }  
      
}  