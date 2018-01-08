package com.hust18.hrm.controller;

import com.hust18.hrm.domain.User;
import com.hust18.hrm.service.HrmService;
import com.hust18.hrm.util.common.HrmConstants;
import com.hust18.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

//处理用户请求控制器
@Controller
public class UserController {

    //自动注入UserService
    @Autowired
    @Qualifier("HrmService")
    private HrmService hrmService;

    /**
     * 处理登录请求
     *
     * @param loginname 登录名
     * @param password  密码
     * @return 跳转的视图
     */
    @RequestMapping(value = "/login")
    public ModelAndView login(@RequestParam("loginname") String loginname, @RequestParam("password") String password,
                              HttpSession session, ModelAndView mv) {
        //调用业务逻辑组件判断用户是否可以登录
        User user = hrmService.login(loginname, password);
        if (user != null) {
            session.setAttribute(HrmConstants.USER_SESSION, user);//将用户保存到httpsession中

            mv.setViewName("redirect:/main");//跳转到main页面
        } else {
            mv.addObject("message", "登录名或密码错误");
            mv.setViewName("forward:/loginForm");//服务器内部跳转到登录页面
        }
        return mv;
    }

    /**
     * 处理查询请求
     *
     * @param pageIndex 请求的是第几页
     * @param user  模糊查询参数
     * @param model     model
     */
    @RequestMapping(value = "/user/selectUser")
    public String selectUser(Integer pageIndex, @ModelAttribute User user, Model model) {
        System.out.println("user = " + user);
        PageModel pageModel = new PageModel();
        if (pageIndex != null) {
            pageModel.setPageIndex(pageIndex);
        }
        //查询用户信息
        List<User> users = hrmService.findUser(user, pageModel);
        model.addAttribute("users", users);
        model.addAttribute("pageModel", pageModel);
        return "user/user";
    }

    /**
     * 处理删除用户请求
     *
     * @param ids 需要删除的id字符串
     * @param mv
     */
    @RequestMapping(value = "/user/removeUser")
    public ModelAndView removeUser(String ids, ModelAndView mv) {
        //分解id字符串
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            //根据id删除员工
            hrmService.removeUserById(Integer.parseInt(id));
        }
        //设置客户端跳转到查询请求
        mv.setViewName("redirect:/user/selectUser");
        return mv;
    }
    /**
     * 处理修改用户请求
     * @param flag 标记 1表示跳转到修改页面 2表示执行修改操作
     * @param  user
     *
     */
    @RequestMapping(value = "/user/updateUser")
    public ModelAndView updateUser(String flag,@ModelAttribute User user,
                                   ModelAndView mv){
        if(flag.equals("1")){
            User target = hrmService.findUserById(user.getId());//根据id查询用户
            mv.addObject("user",target);//设置model数据
            mv.setViewName("user/showUpdateUser");//返回修改页面
        }else {
            hrmService.modifyUser(user);//执行修改操作
            mv.setViewName("redirect:/user/selectUser");
        }
        return mv;
    }
    /**
     * 处理添加请求
     * @param flag 标记 1表示跳转到添加页面 2表示执行添加操作
     * @param  user
     *
     */
    @RequestMapping(value = "/user/addUser")
    public ModelAndView addUser(String flag,@ModelAttribute User user,ModelAndView mv){
        if(flag.equals("1")){
            mv.setViewName("user/showAddUser");
        }else{
            hrmService.addUser(user);
            mv.setViewName("redirect:/user/selectUser");
        }
        return mv;
    }

}
