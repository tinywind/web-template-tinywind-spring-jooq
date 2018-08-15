package org.tinywind.webtemplate.controller;

import org.tinywind.webtemplate.util.JsonResult;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

@Aspect
public class ApiControllerAspect {
    private static final Logger logger = LoggerFactory.getLogger(ApiControllerAspect.class);

    @AfterReturning(pointcut = "execution(public org.tinywind.webtemplate.util.JsonResult org.tinywind.webtemplate.controller..*.*(..))", returning = "returnValue")
    public void setStatusByApiResult(Object returnValue) {
        if (!(returnValue instanceof JsonResult))
            return;

        final JsonResult result = (JsonResult) returnValue;
        if (result.getResult().equals(JsonResult.Result.success))
            return;

        final HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        if (response.getStatus() == HttpServletResponse.SC_OK)
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
