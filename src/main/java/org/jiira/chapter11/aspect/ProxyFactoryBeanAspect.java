package org.jiira.chapter11.aspect;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public class ProxyFactoryBeanAspect implements MethodBeforeAdvice, AfterReturningAdvice, MethodInterceptor {

	@Override
	/***
	 * 前置通知
	 * 
	 * @param method
	 *            被拦截方法（切点）
	 * @param params
	 *            参数 数组[role]
	 * @param roleService
	 *            被拦截对象
	 */
	public void before(Method method, Object[] params, Object roleService) throws Throwable {
		System.out.println("前置通知！！");
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("后置通知！！");

	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("环绕通知之前！！");
		Object result = invocation.proceed();
		System.out.println("环绕通知之后！！");
		return null;
	}

}