package com.autowrite.common.framework.bean;

import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autowrite.common.config.Constant;
import com.autowrite.common.framework.dao.UserDao;

@Component
public class CommonService {
	@Autowired 
    private ServletContext servletContext;

	@Autowired 
	UserDao userDao;

	protected void setCondition(Map param) {
		long pageNum = 0;
		long pageSize = Constant.BOARD_PAGE_SIZE;
		int startNum;
		int endNum;
		
		if ( param.get("pageNum") != null && param.get("pageNum").toString().length() > 0) {
			pageNum = new Long(param.get("pageNum").toString());
			param.put("PAGE_NUM", pageNum);
		}
		
		if ( pageNum == 0 ){
			pageNum = 1;
		}
		
		startNum = (int) (( pageNum - 1 ) * pageSize);
		endNum = (int) (pageNum * pageSize);
		
		param.put("PAGE_NUM", pageNum);
		param.put("PAGE_SIZE", pageSize);
		param.put("START_NUM", startNum);
		param.put("END_NUM", endNum);
	}
}
