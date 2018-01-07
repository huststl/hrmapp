package com.hust18.hrm.dao;


import com.hust18.hrm.dao.provider.EmployeeDynaSqlProvider;
import com.hust18.hrm.domain.Employee;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

public interface EmployeeDao {

    //根据参数查询员工总数
    @SelectProvider(type = EmployeeDynaSqlProvider.class,method = "count")
    Integer count(Map<String,Object> params);

    //根据参数动态查询员工
    @SelectProvider(type = EmployeeDynaSqlProvider.class,method = "selectWhitParams")
    @Results({
       @Result(id = true,column = "id",property = "id"),
       @Result(column = "CARD_ID",property = "cardId"),
       @Result(column="POST_CODE",property="postCode"),
       @Result(column="QQ_NUM",property="qqNum"),
       @Result(column="BIRTHDAY",property="birthday",javaType=java.util.Date.class),
       @Result(column="CREATE_DATE",property="createDate",javaType=java.util.Date.class),
       @Result(column="DEPT_ID",property="dept",
            one=@One(select="com.hust18.hrm.dao.DeptDap.selectById",fetchType= FetchType.EAGER))

    })
    List<Employee> selectByPage(Map<String,Object> params);
}
