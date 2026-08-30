package com.secondshelf.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    @Around("execution(* com.secondshelf.service.AddressService.updateAddress(..))")
    public Object logExecutionTime(ProceedingJoinPoint pjp) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("Method Started");
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        System.out.println("Method Completed");
        System.out.println("Excecution time : "+((end-start)/1000) + " bhosda  sec.");
        return result;
    }
}
