package org.jiira.chapter2;

import org.jiira.chapter2.builder.TicketBuilder;
import org.jiira.chapter2.builder.TicketHelper;
import org.jiira.chapter2.intercept.InterceptorJdkProxy;
import org.jiira.chapter2.observer.JDObserver;
import org.jiira.chapter2.observer.ProductList;
import org.jiira.chapter2.observer.TBObserver;
import org.jiira.chapter2.proxy.CglibProxyExample;
import org.jiira.chapter2.proxy.ITestProxy;
import org.jiira.chapter2.proxy.JdkProxyExample;
import org.jiira.chapter2.proxy.TestProxy;
import org.jiira.chapter2.reflect.ReflectServiceImpl;

public class Chapter2 {
	public static void main(String[] args) {
		Chapter2 l = new Chapter2();
//		l.reflect();
//		l.jdkProxy();
//		l.cglibProxy();
//		l.builder();
//		l.observer();
//		l.interceptor();
		l.interceptorChain();
	}
	
	/**
	 * 拦截器
	 * 基于动态代理类
	 */
	public void interceptor() {
		ITestProxy proxy = (ITestProxy) InterceptorJdkProxy.bind(new TestProxy(), 
				"org.jiira.intercept.MyInterceptor");
		proxy.print();
	}
	/**
	 * 拦截器链
	 * 基于动态代理类
	 */
	public void interceptorChain() {
		ITestProxy proxy1 = (ITestProxy) InterceptorJdkProxy.bind(
                new TestProxy(), "org.jiira.intercept.Interceptor1");
		ITestProxy proxy2 = (ITestProxy) InterceptorJdkProxy.bind(
                proxy1, "org.jiira.intercept.Interceptor2");
		ITestProxy proxy3 = (ITestProxy) InterceptorJdkProxy.bind(
                proxy2, "org.jiira.intercept.Interceptor3");
        proxy3.print();
	}
	/**
	 * 观察者模式
	 */
	public void observer() {
		ProductList observable = ProductList.getInstance();
		TBObserver taoBaoObserver = new TBObserver();
		JDObserver jdObserver = new JDObserver();
		observable.addObserver(jdObserver);
		observable.addObserver(taoBaoObserver);
		observable.addProudct("新增产品1");
	}
	/**
	 * 建造者模式
	 */
	public void builder() {
		TicketHelper helper = new TicketHelper();
		helper.buildAdult("成人票");
		helper.buildChildrenForSeat("有座儿童");
		helper.buildchildrenNoSeat("无票儿童");
		helper.buildElderly("老人票");
		helper.buildSoldier("军人票");
		TicketBuilder.builder(helper);
	}
	/**
	 * jdk动态代理
	 */
	public void jdkProxy() {
		JdkProxyExample jdk = new JdkProxyExample();
		TestProxy tp = new TestProxy();
		// 绑定关系，因为挂在接口ITestProxy下，所以声明代理对象TestProxy proxy
		ITestProxy proxy = (ITestProxy) jdk.bind(tp);
		// 注意，此时ITestProxy对象已经是一个代理对象，它会进入代理的逻辑方法invoke里
		proxy.print();
	}
	/**
	 * cglib动态代理
	 */
	public void cglibProxy() {
	    CglibProxyExample cpe = new CglibProxyExample();
	    ReflectServiceImpl obj = (ReflectServiceImpl)cpe.getProxy(ReflectServiceImpl.class);
	    obj.print("你好哈");
	}
	/**
	 * 基础反射
	 */
	public void reflect() {
		ReflectServiceImpl.reflect(true, true);
		ReflectServiceImpl.reflect(false, false);
	}
}
