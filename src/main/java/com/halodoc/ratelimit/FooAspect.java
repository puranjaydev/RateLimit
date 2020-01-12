package com.halodoc.ratelimit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Configuration
public class FooAspect {
    @Value("${FooClassInvocationThreshold}")
    String fooClassThreshold;

    @Value("${FooMethodAInvocationThreshold}")
    String fooMethodAThreshold;


    @Value("${FooMethodBInvocationThreshold}")
    String fooMethodBThreshold;

    public static  Map<String,FooClassRateLimit> map=new ConcurrentHashMap<>();

    @Around("(execution(* com.halodoc.ratelimit.Foo.*(..)))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws InterruptedException,Throwable  {
        Long cuurenttime=System.currentTimeMillis();

        if(map.isEmpty())
        {
            FooClassRateLimit fooClassRateLimit=new FooClassRateLimit();
            fooClassRateLimit.setStart(cuurenttime);
            fooClassRateLimit.setEnd(cuurenttime + 60000l);
            fooClassRateLimit.setNoOfrequest(1);
            map.put("Foo",fooClassRateLimit);
        }
        else
        {
            FooClassRateLimit fooClassRateLimit=map.get("Foo");
            if(fooClassRateLimit.getEnd()<cuurenttime)
            {
                map.remove("Foo");
            }
            else if(cuurenttime>=fooClassRateLimit.getStart() && cuurenttime<=fooClassRateLimit.getEnd())
            {
                fooClassRateLimit.setNoOfrequest(fooClassRateLimit.getNoOfrequest()+1);

                map.put("Foo",fooClassRateLimit);
                if(fooClassRateLimit.getNoOfrequest()>Integer.parseInt(fooClassThreshold))
                {
                    if(map.get("FooA").getNoOfrequest()>Integer.parseInt(fooMethodAThreshold)) {
                        // Thread.currentThread().sleep(60000);
                        fooClassRateLimit = new FooClassRateLimit();
                        fooClassRateLimit.setStart(cuurenttime);
                        fooClassRateLimit.setNoOfrequest(0);
                        fooClassRateLimit.setEnd(cuurenttime * 60000l);
                        map.put("Foo", fooClassRateLimit);
                    }
                }
            }
        }
            return proceedingJoinPoint.proceed();
    }

    @Around("(execution(* com.halodoc.ratelimit.Foo.a()))")
    public Object around2(ProceedingJoinPoint proceedingJoinPoint) throws InterruptedException,Throwable {

        Long cuurenttime = System.currentTimeMillis();
        FooClassRateLimit fooClassRateLimit = map.get("Foo");
        if (fooClassRateLimit.getEnd() < cuurenttime) {
            map.remove("FooA");
        } else if (cuurenttime >= fooClassRateLimit.getStart() && cuurenttime <= fooClassRateLimit.getEnd()) {
            fooClassRateLimit.setNoOfrequest(fooClassRateLimit.getNoOfrequest() + 1);

            map.put("FooA", fooClassRateLimit);

            if (map.get("FooA").getNoOfrequest() > Integer.parseInt(fooMethodAThreshold)) {
                Thread.currentThread().sleep(10000);
                fooClassRateLimit = new FooClassRateLimit();
                fooClassRateLimit.setStart(cuurenttime);
                fooClassRateLimit.setNoOfrequest(0);
                fooClassRateLimit.setEnd(cuurenttime * 60000l);
                map.put("FooA", fooClassRateLimit);
            }
        }
  return  proceedingJoinPoint.proceed();
    }
}
