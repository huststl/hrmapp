package com.hust18.hrm.interceptor;


import com.hust18.hrm.domain.User;
import com.hust18.hrm.util.common.HrmConstants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断用户权限的Spring MVC拦截器
 */
public class AuthorizedInterceptor implements HandlerInterceptor {
    //定义不需要拦截的请求
    private static final String[] IGNORE_URI = {"/loginForm","/login","/404.html"};

    /**
     *preHandle方法是进行处理器拦截用的，该方法在Controller处理之前调用
     * 当preHandle的返回值为false的时候整个请求就结束了
     *如果preHandle的返回值为true,则会继续执行postHandle和afterCompeletion
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //默认用户没有登录
        boolean flag = false;
        String servletPath = httpServletRequest.getServletPath();//获得请求的ServletPath
        for(String s:IGNORE_URI) //判断请求拦截
        {
            if(servletPath.contains(s)){
                flag = true;
                break;
            }
        }
        //拦截请求
        if(!flag){
            //1获取session中的用户
            User user = (User) httpServletRequest.getSession().getAttribute(HrmConstants.USER_SESSION);
            //2判断用户是否已经登录
            if(user == null){
                //如果没有用户登录,跳转到登录页面
                httpServletRequest.setAttribute("message","请先登录再访问网站");
                httpServletRequest.getRequestDispatcher(HrmConstants.LOGIN).forward(httpServletRequest,httpServletResponse);
                return flag;
            }else {
                flag = true;
            }
        }

        return flag;
    }
    /**
     *该方法需要在prehandle方法的返回值为true时才会执行.
     * 执行时间是在处理器进行处理之后，也就是在Controller的方法调用之后执行
     *
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    /**
     *该方法需要在prehandle方法的返回值为true时才会执行.
     * 该方法将在整个请求完成之后执行，主要是清理资源
     *
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
