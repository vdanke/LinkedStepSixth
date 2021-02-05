package org.step.linked.step.configuration.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.step.linked.step.model.User;

import java.util.List;
import java.util.stream.Stream;

@Aspect
@Component
@Slf4j
@Order(1)
public class LoggingAspect {

     /*
    Modifiers are optional
    Params:
    () - no args
    * - one any type
    .. - 0 or more any type
    @Pointcut for reusability of expression
    @After working always
    @AfterReturning only on success
    @AfterThrowing only on exception
    @Around working before and after method
     */

    @After("execution(public * org.step.linked.step.service.impl.UserServiceImpl.*(..))")
    public void afterUserSaveMethod(JoinPoint joinPoint) {
        log.info("User service method of UserService class is called: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(
            pointcut = "execution(public * org.step.linked.step.service.impl.UserServiceImpl.findAll())",
            returning = "users"
    )
    public void afterReturningMethod(JoinPoint joinPoint, List<User> users) {
        log.info("FindAll method of UserServiceImpl is called: " + joinPoint.getSignature().toShortString());
        users.forEach(u -> log.info(u.toString()));
    }

    @AfterThrowing(
            pointcut = "execution(* org.step.linked.step.service.impl.UserServiceImpl.findByUsername())",
            throwing = "ex"
    )
    public void afterThrowingMethod(JoinPoint joinPoint, Throwable ex) {
        log.info("Method throws exception");
        log.info("Exception: " + ex.getClass());
    }

    @Around("execution(* org.step.linked.step.service.impl.UserServiceImpl.*(..))")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("---------------------------------------------------------");
        long start = System.currentTimeMillis();

        Object object = null;

        try {
            object = proceedingJoinPoint.proceed();
        } catch (Throwable ex) {
            log.info(String.format("Exception %s in method %s",
                    ex.getClass().getSimpleName(), proceedingJoinPoint.getSignature().toShortString()));
        }

        long finish = System.currentTimeMillis();

        log.info(String.format("Method %s is done in %d", proceedingJoinPoint.getSignature().toShortString(), finish - start));
        log.info("---------------------------------------------------------");

        return object;
    }

    @Before("org.step.linked.step.configuration.aspects.PointcutExpression.wrapUserServiceFindByIdMethod()")
    public void beforeMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        Stream.of(args)
                .forEach(System.out::println);
    }
}
