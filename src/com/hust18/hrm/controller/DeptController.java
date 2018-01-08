package com.hust18.hrm.controller;

import com.hust18.hrm.domain.Dept;
import com.hust18.hrm.service.HrmService;
import com.hust18.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DeptController {
    /**
     * 自动注入UserService
     */
    @Autowired
    @Qualifier("HrmService")
    private HrmService hrmService;

    /**
     * 处理/login请求
     */
    @RequestMapping(value = "/dept/selectDept")
    public String selectDept(Model model, Integer pageIndex, @ModelAttribute Dept dept){
        System.out.println("selectDept -->>");
        System.out.println("pageIndex=" +pageIndex);
        System.out.println("dept =" +dept);
        PageModel pageModel = new PageModel();
        System.out.println("getPageIndex = " + pageModel.getPageIndex());
        System.out.println("getPageSize = " + pageModel.getPageSize());
        System.out.println("getRecordCount = " + pageModel.getRecordCount());
        if(pageIndex!=null){
            pageModel.setPageIndex(pageIndex);
        }
        //查询用户信息
        List<Dept> depts = hrmService.findDept(dept,pageModel);
        model.addAttribute("depts",depts);
        model.addAttribute("pageModel",pageModel);
        return "dept/dept";

    }

    /**
     * 处理删除部门请求
     * @param ids 需要删除的id字符串
     * @param  mv
     */
    @RequestMapping(value = "/dept/removeDept")
    public ModelAndView removeDept(String ids,ModelAndView mv){
        //分解id字符串
        String[] idArray = ids.split(",");
        for(String id:idArray){
            //根据id删除部门
            hrmService.removeDeptById(Integer.parseInt(id));
        }
        //设置客户端跳转到查询请求
        mv.setViewName("redirect:/dept/selectDept");

        return mv;
    }

    //处理添加请求
    @RequestMapping(value = "/dept/addDept")
    public ModelAndView addDept(String flag,@ModelAttribute Dept dept,ModelAndView mv){
        if(flag.equals("1")){
            mv.setViewName("dept/showAddDept");
        }else{
            hrmService.addDept(dept);
            mv.setViewName("redirect:/dept/selectDept");
        }
        return mv;
    }

    //处理修改部门请求
    @RequestMapping(value = "/dept/updateDept")
    public ModelAndView updateDept(String flag,@ModelAttribute Dept dept,
                                   ModelAndView mv){
        if(flag.equals("1")){
            Dept target = hrmService.findDeptById(dept.getId());//根据id查询部门
            mv.addObject("dept",target);//设置Model数据
            mv.setViewName("dept/showUpdateDept");//设置跳转到修改页面
        }else{
            hrmService.modifyDept(dept);//执行修改操作
            mv.setViewName("redirect:/dept/selectDept");//设置客户端查询请求

        }
        return mv;//返回
    }
}
