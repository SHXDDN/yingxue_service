package com.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.Set;

@Aspect
@Configuration
public class CacheAspectHash {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    RedisTemplate redisTemplate;

    @Around("@annotation(com.baizhi.annotation.AddCache)")
    public Object addCaChe(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("=======进入环绕通知=======");

        //序列化解决乱码
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //KEY=类的全限定名,key=方法名+实参,value=数据

        /**
        com.baizhi.serviceimpl.UserServiceimpl
                                 queryByPage-1-2    数据
                                 queryByPage-1-5    数据
                                 queryByPage-1-10    数据
                                 queryByPage-2-10    数据
                                 queryByPage-2-3    数据

        *
        * */

        //字符串拼接
        StringBuilder sb = new StringBuilder();

        //获取累的全限定名
        String className = proceedingJoinPoint.getTarget().getClass().getName();

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

        //获取hash类型的操作
        HashOperations hash = redisTemplate.opsForHash();

        //判断该key在redis是否存在
        Boolean aBoolean = hash.hasKey(className, key);

        Object result =null;
        if(aBoolean){
            //存在 取出缓存返回数据
            result = hash.get(className,key);
        }else{
            //不存在
            //放行方法
            result = proceedingJoinPoint.proceed();

            //添加缓存
            hash.put(className,key,result);
        }
        return result;
    }

    @AfterReturning("@annotation(com.baizhi.annotation.DelCache)")
    public void delCache(JoinPoint joinPoint){

        System.out.println("-------清除缓存--------");

        //清除该类下所有的缓存
        //com.baizhi.serviceimpl.UserServiceImpl

        //获取类的全限定名
        String className = joinPoint.getTarget().getClass().getName();

        //清除缓存
        stringRedisTemplate.delete(className);

        /*
        *
        *
        * */

    }
}
