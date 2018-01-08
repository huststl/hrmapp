package com.hust18.hrm.service.impl;

import com.hust18.hrm.dao.*;
import com.hust18.hrm.domain.*;
import com.hust18.hrm.service.HrmService;
import com.hust18.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("HrmService")
public class HrmServiceImpl implements HrmService {
    /**
     * 自动注入持久层Dao对象
     *
     */
    @Autowired
    private UserDao userDao;
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DocumentDao documentDao;
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private JobDao jobDao;

    /**********用户服务接口实现*******/
     /**
     *HrmServiceImpl接口login方法实现
      * @see {HrmService}
     */
    @Transactional(readOnly = true)
    @Override
    public User login(String loginname, String password) {
        System.out.println("HrmServiceImpl login -- >>");
        return userDao.selectByLoginnameAndPassword(loginname,password);
    }
    @Transactional(readOnly = true)
    @Override
    public User findUserById(Integer id) {
        return userDao.selectById(id);
    }
    @Transactional(readOnly = true)
    @Override
    public List<User> findUser(User user, PageModel pageModel) {
        //当前需要分页的总数据条数
        Map<String,Object> params = new HashMap<>();
        params.put("user",user);
        int recordCount = userDao.count(params);
        System.out.println("recordCount -->>"+recordCount);
        pageModel.setRecordCount(recordCount);
        if(recordCount>0){
            params.put("pageModel",pageModel);//开始分页查询：查询第几页的数据
        }
        List<User> users = userDao.selectByPage(params);
        return users;
    }
    @Override
    public void removeUserById(Integer id) {
        userDao.deleteById(id);

    }
    @Override
    public void modifyUser(User user) {
        userDao.update(user);
    }

    @Override
    public void addUser(User user) {
        userDao.save(user);
    }

    /*****员工服务接口实现 *******/
    @Transactional(readOnly = true)
    @Override
    public List<Employee> findEmployee(Employee employee, PageModel pageModel) {
        Map<String,Object> params = new HashMap<>();
        params.put("employee",employee);
        int recordCount = employeeDao.count(params);
        System.out.println("recordCount -->>"+recordCount);
        pageModel.setRecordCount(recordCount);
        if(recordCount >0){
            params.put("pageModel",pageModel);
        }
        List<Employee> employees = employeeDao.selectByPage(params);
        return employees;
    }

    @Override
    public void removeEmployeeById(Integer id) {
        employeeDao.deleteById(id);
    }
    @Transactional(readOnly = true)
    @Override
    public Employee findEmployeeById(Integer id) {
        return employeeDao.selectById(id);
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeDao.save(employee);
    }

    @Override
    public void modifyEmployee(Employee employee) {
        employeeDao.update(employee);
    }

    /*****部门接口实现 *******/
    @Transactional(readOnly = true)
    @Override
    public List<Dept> findDept(Dept dept, PageModel pageModel) {
        Map<String,Object> params = new HashMap<>();
        params.put("dept",dept);
        int recordCount = deptDao.count(params);
        System.out.println("recordCount -->>"+recordCount);
        pageModel.setRecordCount(recordCount);
        if(recordCount>0){
            params.put("pageModel",pageModel);
        }
        List<Dept> depts = deptDao.selectByPage(params);
        return depts;
    }
    @Transactional(readOnly = true)
    @Override
    public List<Dept> findAllDepy() {
        return deptDao.selectAllDept();
    }

    @Override
    public void removeDeptById(Integer id) {
        deptDao.deleteById(id);
    }

    @Override
    public void addDept(Dept dept) {
        deptDao.save(dept);
    }
    @Transactional(readOnly = true)
    @Override
    public Dept findDeptById(Integer id) {
        return deptDao.selectById(id);
    }

    @Override
    public void modifyDept(Dept dept) {
        deptDao.update(dept);
    }

    /*****职位接口实现 *******/
    @Transactional(readOnly = true)
    @Override
    public List<Job> findAllJob() {
        return jobDao.seleAllJob();
    }

    @Override
    public List<Job> findJob(Job job, PageModel pageModel) {
        Map<String,Object> params = new HashMap<>();
        params.put("job",job);
        int recordCount = jobDao.count(params);
        System.out.println("recordCount -->>"+recordCount);
        pageModel.setRecordCount(recordCount);
        if(recordCount > 0){
            params.put("pageModel",pageModel);
        }
        List<Job> jobs = jobDao.selectByPage(params);
        return jobs;
    }
    @Transactional(readOnly = true)
    @Override
    public void removeJobById(Integer id) {
        jobDao.deleteById(id);
    }

    @Override
    public void addJob(Job job) {
        jobDao.save(job);
    }

    @Override
    public void modifyJob(Job job) {
        jobDao.update(job);
    }
    @Transactional(readOnly = true)
    @Override
    public Job findJobById(Integer id) {
        return jobDao.selectById(id);
    }

    /*****公告接口实现 *******/
    @Transactional(readOnly = true)
    @Override
    public List<Notice> findNotice(Notice notice, PageModel pageModel) {
        Map<String,Object> params = new HashMap<>();
        params.put("notice",notice);
        int recordCount = noticeDao.count(params);
        System.out.println("recordCount -->>" +recordCount);
        pageModel.setRecordCount(recordCount);
        if(recordCount>0){
            params.put("pageModel",pageModel);
        }
        List<Notice> notices = noticeDao.selectByPage(params);
        return notices;
    }

    @Override
    public void removeNoticeById(Integer id) {
        noticeDao.deleteById(id);
    }

    @Override
    public void addNotice(Notice notice) {
        noticeDao.save(notice);
    }

    @Override
    public void modifyNotice(Notice notice) {
        noticeDao.update(notice);
    }
    @Transactional(readOnly = true)
    @Override
    public Notice findNoticeById(Integer id) {
        return noticeDao.selectById(id);
    }
    /*****文件接口实现 *******/
    @Transactional(readOnly = true)
    @Override
    public List<Document> findDocument(Document document, PageModel pageModel) {
        Map<String,Object> params = new HashMap<>();
        params.put("document",document);
        int recordCount = documentDao.count(params);
        System.out.println("recordCount -- >>"+recordCount);
        pageModel.setRecordCount(recordCount);
        if(recordCount>0){
            params.put("pageModel",pageModel);
        }
        List<Document> documents = documentDao.selectByPage(params);
        return documents;
    }

    @Override
    public void addDocument(Document document) {
        documentDao.save(document);
    }

    @Override
    public void removeDocumentById(Integer id) {
        documentDao.deleteById(id);
    }

    @Override
    public void modifyDocument(Document document) {
        documentDao.update(document);
    }
    @Transactional(readOnly = true)
    @Override
    public Document findDocumentById(Integer id) {
        return documentDao.selectById(id);
    }
}
