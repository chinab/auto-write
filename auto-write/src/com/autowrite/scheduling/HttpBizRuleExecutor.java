package com.autowrite.scheduling;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * 웹을 통해서 비지니스 로직을 호출하고 그 결과를 Map으로 반환받는다.
 * </pre>
 *
 * @author 이은호
 * @version v1.00
 */
public class HttpBizRuleExecutor {
    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * 환경설정
     */
//    private Const config;
//
//    public void setConfig(Const config) {
//        this.config = config;
//    }

    /**
     * HTTP로 비지니스를 호출한다.
     * @param request 호출할 HTTP 비지니스 파라메터
     */
    public int execute(Map request) {
//    	Const config = new Const();
        int statusCode = 0; 
//        HttpClient client = new HttpClient();
//        PostMethod method = new PostMethod(config.getURL());
//        
//        if (log.isDebugEnabled()) {
//            log.debug("URL : " + config.getURL());
//        }
//        try {
//            Set set = request.keySet();
//            Iterator it = set.iterator();
//
//            while (it.hasNext()) {
//                String key = (String) it.next();
//                log.debug("key : "+key);
//                String value = (String) request.get(key);
//                log.debug("value : "+value);
//                if (key.startsWith("_")) {
//                    method.addParameter(key, value);
//                } else {
//                    method.addParameter("_param1_"+ key, value);
//                }
//            }
//            statusCode = client.executeMethod(method);
//            log.debug("statusCode : "+ statusCode);
//            if (statusCode != HttpStatus.SC_OK) {
//                log.error("HTTP 수행 전송 실패");
//            }
//        } catch (IOException e) {
//            log.error("HTTP 수행 실패 : " + e.getMessage());
//        }
        return statusCode;
    }
}
