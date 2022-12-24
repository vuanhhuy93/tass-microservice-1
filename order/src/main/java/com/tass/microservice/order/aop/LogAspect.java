package com.tass.microservice.order.aop;

import com.tass.common.model.constans.AUTHENTICATION;
import com.tass.common.model.userauthen.UserDTO;
import com.tass.common.utils.ThreadLocalCollection;
import com.tass.microservice.order.utils.HttpUtil;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
@Log4j2
@Aspect
public class LogAspect {

    @Autowired
    protected HttpServletRequest httpServletRequest;



    @Pointcut("execution(* com.tass.microservice.order.controllers..*(..)))")
    protected void applicationControllerAllMethod() {
    }

    @Around("(applicationControllerAllMethod()) ")
    public Object logAspectController(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        String ip = getIPRequest(this.httpServletRequest);
        String headers = header(httpServletRequest);
        String token = HttpUtil.getValueFromHeader(this.httpServletRequest, AUTHENTICATION.HEADER.TOKEN);
        String userId = HttpUtil.getValueFromHeader(this.httpServletRequest, AUTHENTICATION.HEADER.USER_ID);

        if (userId != null){
            try {
                UserDTO userDTO = new UserDTO();
                userDTO.setUserId(Long.parseLong(userId));
                userDTO.setToken(token);
                ThreadLocalCollection.putData(userDTO);
            } catch (Exception e){
                e.printStackTrace();
            }

        }
        Object output = null;
        output = pjp.proceed();

        long elapsedTime = System.currentTimeMillis() - startTime;


        log.debug("[REQ] ip : {} ; Request uri: {} ;method: {} ;params: {} ;header: {} ",
            ip,
            this.httpServletRequest.getRequestURI(),
            this.httpServletRequest.getMethod(),
            getParams(this.httpServletRequest.getParameterMap()),
            headers);


        log.debug(" >>> Exiting method <<< ,time: {} ms", elapsedTime);



        return output;
    }

    private String header(HttpServletRequest httpServletRequest) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> x = httpServletRequest.getHeaderNames();
        while (x.hasMoreElements()) {
            String key = x.nextElement();
            sb.append(key).append("=").append(httpServletRequest.getHeader(key)).append(",");
        }
        return sb.toString();
    }


    private String getIPRequest(HttpServletRequest servletRequest) {
        if (servletRequest == null) {
            return null;
        }
        String remoteIPAddress = null;
        remoteIPAddress = servletRequest.getHeader("X-FORWARDED-FOR");
        if (remoteIPAddress == null || "".equals(remoteIPAddress)) {
            remoteIPAddress = servletRequest.getRemoteAddr();
        }

        return remoteIPAddress;
    }

    private String getParams(Map<String, String[]> parameterMap) {
        if (parameterMap == null || parameterMap.isEmpty()) {
            return "No Params";
        }
        StringBuilder sb = new StringBuilder();
        for (String key : parameterMap.keySet()) {
            sb.append(key).append("=").append(parameterMap.get(key)[0]).append(",");
        }
        return sb.toString();
    }

    private String getTraceId() {
        String traceId = httpServletRequest.getHeader("trans_id");

        if (traceId == null || traceId.isEmpty()) {
            traceId = UUID.randomUUID().toString();
        }
        httpServletRequest.setAttribute("trans_id", traceId);
        return traceId;
    }

}
