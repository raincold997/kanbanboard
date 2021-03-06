package com.edu.nju.kanbanboard.comm.aop;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class LoggerAdvice {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("within(com.edu.nju.kanbanboard..*) && @annotation(loggerManager)")
    public void addBeforeLogger(JoinPoint joinpoint, LoggerManager loggerManager){
        logger.info("执行 "+loggerManager.description()+" 开始");
        logger.info(joinpoint.getSignature().toString());
        logger.info(parseParames(joinpoint.getArgs()));
    }

    @AfterReturning("within(com.edu.nju.kanbanboard..*) && @annotation(loggerManager)")
    public void addAfterReturningLogger(JoinPoint joinPoint, LoggerManager loggerManager) {
        logger.info("执行 " + loggerManager.description() + " 结束");
    }

    @AfterThrowing(pointcut = "within(com.edu.nju.kanbanboard..*) && @annotation(loggerManager)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, LoggerManager loggerManager, Exception ex) {
        logger.error("执行 " + loggerManager.description() + " 异常", ex);
    }


    private String parseParames(Object[] parames) {
        if (null == parames || parames.length <= 0 || parames.length >1024) {
            return "";
        }
        StringBuffer param = new StringBuffer("传入参数[{}] ");
        for (Object obj : parames) {
            param.append(ToStringBuilder.reflectionToString(obj)).append("  ");
        }
        return param.toString();
    }
}
