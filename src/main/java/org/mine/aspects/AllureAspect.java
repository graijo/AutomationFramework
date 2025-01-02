package org.mine.aspects;
import io.qameta.allure.Allure;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.JoinPoint;
/*
        Not working
 */
//Combining Allure with Aspect-Oriented Programming (AOP)
//Using AOP with libraries like AspectJ allows you to define cross-cutting concerns (e.g., logging, step reporting) in a centralized manner.
// You can automatically inject Allure.step calls into specific methods (e.g., Page Object methods, or test methods) without modifying their code.
@Aspect
public class AllureAspect {

    @Before("execution(* com.example.pages.*.*(..))")
    public void logSteps(JoinPoint joinPoint) {
        System.out.println("inside logSteps");
    String methodName = joinPoint.getSignature().getName();
        System.out.println("inside logSteps methodName:"+methodName);
    Object[] args = joinPoint.getArgs();

    // Log method name and arguments in Allure
    String stepDescription = "Executing method: " + methodName;
        System.out.println("inside logSteps stepDescription:"+stepDescription);
    if (args.length > 0) {
        stepDescription += " with arguments: " + java.util.Arrays.toString(args);
    }
    Allure.step(stepDescription);
}
}