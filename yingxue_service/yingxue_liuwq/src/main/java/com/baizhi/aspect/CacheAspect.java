package com.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.Set;

@Aspect
@Configuration
public class CacheAspect {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    RedisTemplate redisTemplate;

    //@Around("@annotation(com.baizhi.annotation.AddCache)")
    public Object addCaChe(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("=======进入环绕通知=======");

        //序列化解决乱码
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //(String)key=类的全限定名+方法名+实参     (String)value=数据

        //字符串拼接
        StringBuilder sb = new StringBuilder();

        //获取累的全限定名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        sb.append(className);
        sb.append("-");

        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        sb.append(methodName);
        sb.append("-");

        //获取参数的数组
        Object[] args = proceedingJoinPoint.getArgs();

        //遍历参数
        for (Object arg : args) {
            sb.append(arg);
        }

        //获取拼接好的key
        String key = sb.toString();

        //获取String类型的操作
        ValueOperations stringOpt = redisTemplate.opsForValue();

        //判断该key在redis是否存在
        Boolean aBoolean = redisTemplate.hasKey(key);

        Object result =null;
        if(aBoolean){
            //存在 取出缓存返回数据
            result = stringOpt.get(key);
        }else{
            //不存在
            //放行方法
            result = proceedingJoinPoint.proceed();

            //添加缓存
            stringOpt.set(key,result);
        }
        return result;
    }

    //@AfterReturning("@annotation(com.baizhi.annotation.DelCache)")
    public void delCache(JoinPoint joinPoint){

        System.out.println("-------清除缓存--------");

        //清除缓存
        //清除该类下所有的缓存
        //com.baizhi.serviceimpl.UserServiceImpl

        //获取类的全限定名
        String className = joinPoint.getTarget().getClass().getName();

        //获取所有的key
        Set<String> keys = stringRedisTemplate.keys("*");

        //遍历所有的key
        for (String key : keys) {
            //判断key的前缀是否是该方法类的全限定名
            if(key.startsWith(className)){
                //删除key
                stringRedisTemplate.delete(key);
            }
        }
    }
}
