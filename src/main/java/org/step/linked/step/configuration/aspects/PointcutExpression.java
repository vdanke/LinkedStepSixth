package org.step.linked.step.configuration.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointcutExpression {

    @Pointcut(
            value = "execution(public org.step.linked.step.model.User " +
                    "org.step.linked.step.service.impl.UserServiceImpl.findById(java.lang.String))"
    )
    public void wrapUserServiceFindByIdMethod() {}

    @Pointcut("execution(public * org.step.linked.step.service.*.get*(..))")
    public void anyGetter() {}

    @Pointcut("execution(public * org.step.linked.step.service.*.set*(..))")
    public void anySetter() {}

    @Pointcut("wrapUserServiceFindByIdMethod() || (anyGetter() && anySetter())")
    public void common() {}
}
