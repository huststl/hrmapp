package com.hust18.hrm.dao.provider;

import com.hust18.hrm.domain.Dept;
import com.hust18.hrm.domain.Job;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

import static com.hust18.hrm.util.common.HrmConstants.DEPTTABLE;
import static com.hust18.hrm.util.common.HrmConstants.JOBTABLE;


public class DeptDynaSqlProvider {
	// 分页动态查询
	public String selectWhitParams(final Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(DEPTTABLE);
				if(params.get("dept") != null){
					Dept dept = (Dept) params.get("dept");
					if(dept.getName() != null && !dept.getName().equals("")){
						WHERE("  name LIKE CONCAT ('%',#{dept.name},'%') ");
					}
				}
			}
		}.toString();
		
		if(params.get("pageModel") != null){
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}
		
		return sql;
	}	
	// 动态查询总数量
	public String count(final Map<String, Object> params){
		return new SQL(){
			{
				SELECT("count(*)");
				FROM(DEPTTABLE);
				if(null != params.get("dept")){
					Dept dept= (Dept) params.get("dept");
					if(dept.getName() != null && !dept.getName().equals("")){
						WHERE(" name LIKE CONCAT ('%',#{dept.name},'%') ");
					}
				}
			}
		}.toString();
	}	
	
	// 动态插入
	public String insertDept(final Dept dept){
		
		return new SQL(){
			{
				INSERT_INTO(DEPTTABLE);
				if (dept.getName() != null && !dept.getName().equals("")) {
					VALUES("name", "#{name}");
				}
				if (dept.getRemark() != null && !dept.getRemark().equals("")) {
					VALUES("remark", "#{remark}");
				}
			}

		}.toString();
	}
	// 动态更新
		public String updateDept(final Job job){

			return new SQL(){
				{
					UPDATE(JOBTABLE);
					if(job.getName() != null){
						SET(" name = #{name} ");
					}
					if(job.getRemark() != null){
						SET(" remark = #{remark} ");
					}
					WHERE(" id = #{id} ");
				}
			}.toString();
		}
}
