package com.hust18.hrm.controller;

import com.hust18.hrm.domain.Notice;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class NoticeController {

    @Autowired
    @Qualifier("HrmService")
    private HrmService hrmService;

    //处理Login请求
    @RequestMapping(value = "/notice/selectNotice")
    public String selectNotice(Model model, Integer pageIndex, @ModelAttribute Notice notice){
        PageModel pageModel = new PageModel();
        if(pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        //查询用户信息
        List<Notice> notices = hrmService.findNotice(notice,pageModel);
        model.addAttribute("notices",notices);
        model.addAttribute("pageModel",pageModel);
        return "notice/notice";
    }
    //处理添加请求
    @RequestMapping(value = "/notice/previewNotice")
    public String previewNotice(Integer id,Model model){
        Notice notice = hrmService.findNoticeById(id);

        model.addAttribute("notice",notice);

        return "notice/previewNotice";
    }
    //删除
    @RequestMapping(value = "/notice/removeNotice")
    public ModelAndView removeNotice(String ids, ModelAndView mv){
        String[] idArray = ids.split(",");
        for(String id:idArray){
            hrmService.removeNoticeById(Integer.parseInt(id));
        }
        //设置客户端跳转到查询请求
        mv.setViewName("redirect:/notice/selectNotice");
        return mv;
    }

    //1表示跳转到添加界面，2表示执行添加操作
    @RequestMapping(value = "/notice/addNotice")
    public ModelAndView addNotice(String flag, @ModelAttribute Notice notice,
            ModelAndView mv, HttpSession session){
        if(flag.equals("1")){
            mv.setViewName("notice/showAddNotice");
        }else {
            User user = (User) session.getAttribute(HrmConstants.USER_SESSION);
            notice.setUser(user);
            hrmService.addNotice(notice);
            mv.setViewName("redirect:/notice/selectNotice");
        }
        return mv;
    }
    //处理更新请求
    @RequestMapping(value = "/notice/updateNotice")
    public ModelAndView updateNotice(String flag,@ModelAttribute Notice notice,
                                     ModelAndView mv,HttpSession session){
        if(flag.equals("1")){
            Notice target = hrmService.findNoticeById(notice.getId());
            mv.addObject("notice",target);
            mv.setViewName("notice/showUpdateNotice");
        }else {
            hrmService.modifyNotice(notice);
            mv.setViewName("redirect:/notice/selectNotice");
        }
        return mv;
    }
}
