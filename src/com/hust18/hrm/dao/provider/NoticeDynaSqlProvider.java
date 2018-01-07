package com.hust18.hrm.dao.provider;

import com.hust18.hrm.domain.Notice;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

import static com.hust18.hrm.util.common.HrmConstants.NOTICETABLE;



public class NoticeDynaSqlProvider {
	// 分页动态查询
	public String selectWhitParams(final Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(NOTICETABLE);
				if(params.get("notice") != null){
					Notice notice = (Notice)params.get("Notice");
					if(notice.getTitle() != null && !notice.getTitle().equals("")){
						WHERE("  title LIKE CONCAT ('%',#{notice.title},'%') ");
					}
					if(notice.getContent() != null && !notice.getContent().equals("")){
						WHERE(" content LIKE CONCAT ('%',#{notice.content},'%') ");
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
				FROM(NOTICETABLE);
				if(null != params.get("notice")){
					Notice notice = (Notice)params.get("notice");
					if(notice.getTitle() != null && !notice.getTitle().equals("")){
						WHERE(" title LIKE CONCAT ('%',#{notice.title},'%') ");
					}
					if(notice.getContent() != null && !notice.getContent().equals("")){
						WHERE(" content LIKE CONCAT ('%',#{notice.content},'%') ");
					}
				}
			}
		}.toString();
	}	
	
	// 动态插入
	public String insertNotice(final Notice notice){
		
		return new SQL(){
			{
				INSERT_INTO(NOTICETABLE);
				if(notice.getTitle() != null && !notice.getTitle().equals("")){
					VALUES("title", "#{title}");
				}
				if(notice.getContent() != null && !notice.getContent().equals("")){
					VALUES("content", "#{content}");
				}
				if(notice.getUser() != null && notice.getUser().getId()!=null){
					VALUES("user_id", "#{user_id}");
				}
			}
		}.toString();
	}
	// 动态更新
		public String updateNotice(final Notice notice){
			
			return new SQL(){
				{
					UPDATE(NOTICETABLE);
					if(notice.getTitle() != null){
						SET(" title = #{title} ");
					}
					if(notice.getContent() != null){
						SET(" content = #{content} ");
					}
					if(notice.getUser()!= null && notice.getUser().getId() != null){
						SET(" user_id = #{user_id} ");
					}
					WHERE(" id = #{id} ");
				}
			}.toString();
		}
}
