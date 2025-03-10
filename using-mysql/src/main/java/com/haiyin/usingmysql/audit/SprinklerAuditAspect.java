package com.haiyin.usingmysql.audit;

import com.haiyin.usingmysql.dto.SprinklerAllocationDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SprinklerAuditAspect {
//    @AfterReturning(pointcut = "execution(* com.haiyin.usingmysql.service.SprinklerService.allocateSprinkler(*))",
//            returning = "result")
//    public void auditAllocation(JoinPoint joinPoint, Object result) {
//        SprinklerAllocationDTO dto = (SprinklerAllocationDTO) joinPoint.getArgs()[0];
//        System.out.println("hello ha");
//    }

    @AfterReturning(pointcut = "execution(* com.haiyin.usingmysql.service.SprinklerService.allocateSprinkler(*))")
    public void auditAllocation(JoinPoint joinPoint) {
        SprinklerAllocationDTO dto = (SprinklerAllocationDTO) joinPoint.getArgs()[0];
        System.out.println("hello ha");
    }
}
