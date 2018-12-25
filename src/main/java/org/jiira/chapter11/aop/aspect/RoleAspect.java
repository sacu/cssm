package org.jiira.chapter11.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;

import org.jiira.chapter11.aop.verifier.RoleVerifier;
import org.jiira.chapter11.aop.verifier.impl.RoleVerifierImpl;
import org.jiira.chapter11.game.pojo.Role;

@Aspect//声明自己是一个拦截器
public class RoleAspect {
	
	@DeclareParents(value= "org.jiira.chapter11.aop.service.impl.RoleServiceImpl+", defaultImpl=RoleVerifierImpl.class)
	public RoleVerifier roleVerifier;

	/**
	 * execution	表示执行方法时触发
	 * *			表示任意返回值
	 * 包类路径		表示被拦截类的全限定名
	 * printRole	表示被拦截的方法
	 * (...)		表示任意参数
	 */
	@Pointcut("execution(* org.jiira.chapter11.aop.service.impl.RoleServiceImpl.printRole(..))")
	public void print() {
	}

	@Before("print()")//意思是，使用print()函数的注解
	// @Before("execution(*
	// org.jiira.chapter11.aop.service.impl.RoleServiceImpl.printRole(..))")
	public void before() {
		System.out.println("before ....");
	}

	@After("print()")
	// @After("execution(*
	// org.jiira.chapter11.aop.service.impl.RoleServiceImpl.printRole(..))")
	public void after() {
		System.out.println("after ....");
	}

	@AfterReturning("print()")
	// @AfterReturning("execution(*
	// org.jiira.chapter11.aop.service.impl.RoleServiceImpl.printRole(..))")
	public void afterReturning() {
		System.out.println("afterReturning ....");
	}

	@AfterThrowing("print()")
	// @AfterThrowing("execution(*
	// org.jiira.chapter11.aop.service.impl.RoleServiceImpl.printRole(..))")
	public void afterThrowing() {
		System.out.println("afterThrowing ....");
	}

	@Around("print()")
	public void around(ProceedingJoinPoint jp) {
		System.out.println("around before ....");
		try {
			/**
			 * 用来调用其原始函数，这里是printRole(...)
			 */
			jp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("around after ....");
	}

	@Before("execution(* org.jiira.chapter11.aop.service.impl.RoleServiceImpl.printRole(..)) " + "&& args(role, sort)")
	public void before(Role role, int sort) {
		System.out.println("before ....带参数 :" + sort);
	}
}
