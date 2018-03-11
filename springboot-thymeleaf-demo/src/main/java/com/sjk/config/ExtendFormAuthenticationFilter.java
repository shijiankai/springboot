package com.sjk.config;

import com.alibaba.fastjson.JSON;
import com.sjk.controller.tools.JsonObject;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ExtendFormAuthenticationFilter extends FormAuthenticationFilter{
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest=(HttpServletRequest)request;
        HttpServletResponse httpServletResponse=(HttpServletResponse)response;
        if(!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))){
            return super.onLoginSuccess(token, subject, request, response);
        }else{
           httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out=httpServletResponse.getWriter();
            JsonObject jsonObject=new JsonObject();
            jsonObject.setStatus("success");
            jsonObject.setsMsg("登陆成功！");
            jsonObject.setPath("/demo/index");
            out.print(JSON.toJSON(jsonObject).toString());
            return false;
        }
    }
}
