package com.hust18.hrm.controller;

import com.hust18.hrm.domain.Job;
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
public class JobController {
    //自动注入UserService
    @Autowired
    @Qualifier("HrmService")
    private HrmService hrmService;
    //处理login请求
    @RequestMapping(value = "/job/selectJob")
    public String selectJob(Model model, Integer pageIndex, @ModelAttribute Job job){
        System.out.println("selectJob -->>" + job);
        PageModel pageModel = new PageModel();
        if(pageIndex !=null){
            pageModel.setPageIndex(pageIndex);
        }
        //查询用户信息
        List<Job> jobs = hrmService.findJob(job,pageModel);
        model.addAttribute("jobs",jobs);
        model.addAttribute("pageModel",pageModel);
        return "job/job";
    }
    //处理删除职位请求
    @RequestMapping(value = "/job/removeJob")
    public ModelAndView removeJob(String ids,ModelAndView mv){
        //分解id字符串
        String[] idArray = ids.split(",");
        for(String id:idArray){
            hrmService.removeJobById(Integer.parseInt(id));
        }
        //设置客户端跳转到查询请求
        mv.setViewName("redirect:/job/selectJob");
        return mv;
    }
    //添加
    @RequestMapping(value = "/job/addJob")
    public ModelAndView addJob(String flag,@ModelAttribute Job job,
                               ModelAndView mv){
        if(flag.equals("1")){
            mv.setViewName("job/showAddJob");//设置跳转到添加页面
        }else {
            hrmService.addJob(job);
            mv.setViewName("redirect:/job/selectJob");
        }
        return mv;
    }
    //修改
    @RequestMapping(value = "/job/updateJob")
    public ModelAndView updateJob(String flag,@ModelAttribute Job job,ModelAndView mv){
        if(flag.equals("1")){
            Job target = hrmService.findJobById(job.getId());
            mv.addObject("job",target);
            mv.setViewName("job/showUpdateJob");
        }else {
            hrmService.modifyJob(job);
            mv.setViewName("redirect:/job/selectJob");
        }
        return mv;
    }
}
