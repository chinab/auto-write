package com.autowrite.scheduling;


import java.util.HashMap;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ReserveJob extends QuartzJobBean{
	//실행될 클래스는 꼭 QuartzJobBean을 상속받아야 되며 
	//executeInternal method를 override 하면 자동으로 이 메소드가 실행
	
	HttpBizRuleExecutor bizRuleExecutor = new HttpBizRuleExecutor();
	
	protected void executeInternal(JobExecutionContext ex) throws JobExecutionException {
		// HTTP로 해당 서비스 호출.
		httpServiceCall();
	}
	
	/**
	 * @MethodName : httpServiceCall
	 * @작성일      : 2010. 12. 21.
	 * @작성자      : Seunghyun
	 * @변경이력    :
	 * @Method 설명 : 
	 * @return
	 */
	protected int httpServiceCall() {
    	Map<String, String> request = new HashMap<String, String>();
    	
    	// service.xml 에 서비스명 기술.
        request.put("_common_serviceName", "startResultPayordServer");
        request.put("_common_userID", "SAP_INTERFACE_SERVER");
        request.put("_common_view", "cm/empty.html");
        
        return bizRuleExecutor.execute(request);
    }
}