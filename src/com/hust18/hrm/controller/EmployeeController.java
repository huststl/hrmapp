package com.hust18.hrm.controller;

import com.hust18.hrm.domain.Dept;
import com.hust18.hrm.domain.Employee;
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
public class EmployeeController {
    //自动注入hrmService
    @Autowired
    @Qualifier("HrmService")
    private HrmService hrmService;
    /**
     * 处理查询请求
     *
     */
    @RequestMapping(value = "/employee/selectEmployee")
    public String selectEmployee(Integer pageIndex, Integer job_id, Integer dept_id, @ModelAttribute Employee employee,
            Model model){
        // 模糊查询时判断是否有关联对象传递，如果有，创建并封装关联对象
        this.genericAssociation(job_id, dept_id, employee);
        PageModel pageModel = new PageModel();
        if(pageIndex!=null){
            pageModel.setPageIndex(pageIndex);
        }
        //查询职位信息（模糊查询）
        List<Job> jobs = hrmService.findAllJob();
        List<Dept> depts = hrmService.findAllDept();
        List<Employee> employees = hrmService.findEmployee(employee,pageModel);//查询员工信息

        //设置Model数据
        model.addAttribute("employees",employees);
        model.addAttribute("jobs",jobs);
        model.addAttribute("depts",depts);
        model.addAttribute("pageModel",pageModel);
        //返回员工页面
        return "employee/employee";
    }

    //处理添加员工请求
    @RequestMapping(value = "/employee/addEmployee")
    public ModelAndView addEmployee(
            String flag,Integer job_id,Integer dept_id,@ModelAttribute Employee employee,ModelAndView mv
    ){
        if(flag.equals("1")){
            //查询职位信息
            List<Job> jobs = hrmService.findAllJob();
            List<Dept> depts = hrmService.findAllDept();
            mv.addObject("jobs",jobs);
            mv.addObject("depts",depts);
            mv.setViewName("employee/showAddEmployee");
        }else {
            //判断是否有关联对象传递，如果有，创建关联对象
            this.genericAssociation(job_id,dept_id,employee);
            hrmService.addEmployee(employee);

            mv.setViewName("redirect:/employee/selectEmployee");
        }
        return mv;
    }
    //处理删除员工请求
    @RequestMapping(value = "/employee/removeEmployee")
    public ModelAndView removeEmployee(String ids,ModelAndView mv){
        String[] idArray = ids.split(",");
        for(String id:idArray){
            hrmService.removeEmployeeById(Integer.parseInt(id));
        }

        mv.setViewName("redirect:/employee/selectEmployee");
        return mv;
    }

    @RequestMapping(value="/employee/updateEmployee")
    public ModelAndView updateEmployee(
            String flag,
            Integer job_id,Integer dept_id,
            @ModelAttribute Employee employee,
            ModelAndView mv){
        if(flag.equals("1")){
            // 根据id查询员工
            Employee target = hrmService.findEmployeeById(employee.getId());
            // 需要查询职位信息
            List<Job> jobs = hrmService.findAllJob();
            // 需要查询部门信息
            List<Dept> depts = hrmService.findAllDept();
            // 设置Model数据
            mv.addObject("jobs", jobs);
            mv.addObject("depts", depts);
            mv.addObject("employee", target);
            // 返回修改员工页面
            mv.setViewName("employee/showUpdateEmployee");
        }else{
            // 创建并封装关联对象
            this.genericAssociation(job_id, dept_id, employee);
            System.out.println("updateEmployee -->> " + employee);
            // 执行修改操作
            hrmService.modifyEmployee(employee);
            // 设置客户端跳转到查询请求
            mv.setViewName("redirect:/employee/selectEmployee");
        }
        // 返回
        return mv;
    }



    private void genericAssociation(Integer job_id, Integer dept_id, Employee employee) {

        if(job_id !=null){
            Job job = new Job();
            job.setId(job_id);
            employee.setJob(job);
        }
        if(dept_id !=null){
            Dept dept = new Dept();
            dept.setId(dept_id);
            employee.setDept(dept);

        }
    }
}
