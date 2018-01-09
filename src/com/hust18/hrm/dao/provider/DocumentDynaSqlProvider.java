package com.hust18.hrm.dao.provider;

import com.hust18.hrm.domain.Document;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

import static com.hust18.hrm.util.common.HrmConstants.DOCUMENTTABLE;



public class DocumentDynaSqlProvider {
	// 分页动态查询
	public String selectWhitParam(final Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(DOCUMENTTABLE);
				if(params.get("document") != null){
					Document document = (Document)params.get("document");
					if(document.getTitle() != null && !document.getTitle().equals("")){
						WHERE("  title LIKE CONCAT ('%',#{document.title},'%') ");
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
				FROM(DOCUMENTTABLE);
				if(null != params.get("document")){
					Document document = (Document)params.get("document");
					if(document.getTitle() != null && !document.getTitle().equals("")){
						WHERE(" title LIKE CONCAT ('%',#{document.title},'%') ");
					}
				}
			}
		}.toString();
	}	
	
	// 动态插入
	public String insertDocument(final Document document){
		
		return new SQL(){
			{
				INSERT_INTO(DOCUMENTTABLE);
				if(document.getTitle() != null && !document.getTitle().equals("")){
					VALUES("title", "#{title}");
				}
				if(document.getFilename() != null && !document.getFilename().equals("")){
					VALUES("filename", "#{filename}");
				}
				if(document.getRemark() != null && !document.getRemark().equals("")){
					VALUES("remark", "#{remark}");
				}
				if(document.getUser() != null && document.getUser().getId() != null){
					VALUES("user_id", "#{user_id}");
				}
			}
		}.toString();
	}
	// 动态更新
		public String updateDocument(final Document document){
			
			return new SQL(){
				{
					UPDATE(DOCUMENTTABLE);
					if(document.getTitle() != null && !document.getTitle().equals("")){
						SET(" title = #{title} ");
					}
					if(document.getFilename() != null && !document.getFilename().equals("")){
						SET(" filename = #{fileName} ");
					}
					if(document.getRemark() != null && !document.getRemark().equals("")){
						SET("remark = #{remark}");
					}
					if(document.getUser() != null && document.getUser().getId() != null){
						SET("user_id = #{user.id}");
					}
					WHERE(" id = #{id} ");
				}
			}.toString();
		}
}
