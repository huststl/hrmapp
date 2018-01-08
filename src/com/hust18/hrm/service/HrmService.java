package com.hust18.hrm.service;

import com.hust18.hrm.domain.*;
import com.hust18.hrm.util.tag.PageModel;
import org.aspectj.weaver.ast.Not;

import java.util.List;

public interface HrmService {
    /**
     * 用户登录
     * @param loginname
     * @param password
     * @return User对象
     */
    User login(String loginname,String password);
    /**
     * 根据id查询用户
     * @param id
     * @return 用户对象
     */
    User findUserById(Integer id);
    /**
     * 获得所有用户
     * @return User对象的list集合
     */
    List<User> findUser(User user, PageModel pageModel);
    /**
     * 根据id删除用户
     * @param  id
     */
    void removeUserById(Integer id);
    /**
     * 修改用户
     * @param user 用户对象
     */
    void modifyUser(User user);
    /**
     * 添加用户
     * @param user
     */
    void addUser(User user);

    /**
     * 获取所有员工
     * @param employee 查询条件
     * @param pageModel 分页对象
     * @return Dept 对象的List集合
     */
    List<Employee> findEmployee(Employee employee,PageModel pageModel);
    /**
     * 根据id删除员工
     * @param id
     */
    void removeEmployeeById(Integer id);
    /**
     * 根据id查询员工
     * @param id
     * @return 员工对象
     */
    Employee findEmployeeById(Integer id);

    //添加员工
    void addEmployee(Employee employee);
    //修改员工
    void modifyEmployee(Employee employee);

    //获取所有部门，分页查询

    List<Dept> findDept(Dept dept,PageModel pageModel);
    List<Dept> findAllDepy();
    void removeDeptById(Integer id);
    void addDept(Dept dept);
    Dept findDeptById(Integer id);//查询
    void modifyDept(Dept dept);

    //获取所有职位
    List<Job> findAllJob();
    List<Job> findJob(Job job,PageModel pageModel);//获取所有职位，分页查询

    void removeJobById(Integer id);
    void addJob(Job job);
    void modifyJob(Job job);//修改
    Job findJobById(Integer id);

    //获取所有公告
   /* List<Notice> findAllNotice();*/
    List<Notice> findNotice(Notice notice,PageModel pageModel);

    void removeNoticeById(Integer id);
    void addNotice(Notice notice);
    void modifyNotice(Notice notice);
    Notice findNoticeById(Integer id);

    //获取所有文档
    List<Document> findDocument(Document document,PageModel pageModel);
  /*  List<Document> findAllDocument();*/
    void addDocument(Document document);

    void removeDocumentById(Integer id);
    void modifyDocument(Document document);
    Document findDocumentById(Integer id);






}
