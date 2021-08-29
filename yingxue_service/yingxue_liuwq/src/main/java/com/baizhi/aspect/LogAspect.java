package com.baizhi.aspect;


import com.baizhi.annotation.AddLog;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Log;
import com.baizhi.service.LogService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


@Configuration  //配置类交给工厂管理
@Aspect //该类是个切面类
//@Slf4j
public class LogAspect {

    //private static final org.apache.log4j.Logger log1 = org.apache.log4j.Logger.getLogger(LogAspect.class);
    @Resource
    HttpServletRequest request;
    @Resource
    LogService logService;

    @Around("@annotation(com.baizhi.annotation.AddLog)")
    public Object AddLog(ProceedingJoinPoint proceedingJoinPoint) {

        System.out.println("---------进入环绕通知-------");


        Admin admin = (Admin) request.getServletContext().getAttribute("admin");
        String username = admin.getUsername();

        //操作的方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        //获取方法的signature对象
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

        //获取方法
        Method method = signature.getMethod();

        //获取方法上的注解
        AddLog addLog = method.getAnnotation(AddLog.class);

        //获取注解上的属性值
        String value = addLog.value();

        String status = null;
        Object result = null;

        try {
            //放行通知执行目标方法
            result = proceedingJoinPoint.proceed();
            status = "Success(执行成功)";
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("======="+e.getMessage());
            status = "Error(执行失败)";
            throw new RuntimeException(e.getMessage());
        }finally {
            Log log = new Log(null, "admin", new Date(), methodName + "(" + value + ")", status);

            System.out.println("管理员操作日志{}"+log);
            //入库
            logService.add(log);

        }
        return result;
    }
}