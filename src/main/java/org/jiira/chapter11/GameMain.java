package org.jiira.chapter11;

import org.aspectj.lang.annotation.DeclareParents;
import org.jiira.chapter11.game.Interceptor;
import org.jiira.chapter11.game.ProxyBeanFactory;
import org.jiira.chapter11.game.interceptor.RoleInterceptor;
import org.jiira.chapter11.game.pojo.Role;
import org.jiira.chapter11.game.service.RoleService;
import org.jiira.chapter11.game.service.impl.RoleServiceImpl;

public class GameMain {
	public static void main(String[] args) {
		/**
		 * 这里用的jdk动态代理
		 * 理解AOP
		 * AOP常用语数据库事务处理，在发生错误时回滚当前整体操作或者提交当前整体操作。
		 * 以下基于AOP思想，首先创建RoleService(RoleServiceImpl)实例，
		 * 通过RoleServiceImpl实例和RoleInterceptor拦截器从代理工厂ProxyBeanFactory(实际调用ProxyBeanUtil.getBean(...))，
		 * 获得RoleService代理，当RoleService执行操作时，在ProxyBeanUtil.invoke(...)中进行细化步骤，
		 * 首先在调用实际函数前调用before，然后调用实际函数，在调用实际函数时获得调用状态，然后调用after，
		 * 最后通过函数调用返回状态判断执行afterThrowing或afterReturning。
		 * 这里可以把ProxyBeanUtil.invoke(...)理解成around，或在拦截器中创建around，并传参到内部处理。
		 * 
		 * 名词解释:
		 * aspect					切面，包含所有下级运行流程，主题概念其实就是个拦截器
		 * 		advice				通知，包含所有下级通知
		 * 			before			前置通知
		 * 			after			后置通知
		 * 			afterReturning	返回通知
		 * 			afterThrowing	异常通知
		 * 			around			环绕通知
		 * 		introduction		引入，允许在现有类添加添加自定义类和方法，注解@DeclareParents
		 * 		pointcut			切点，就是拦截点，通过注解或配置定义哪些函数需要拦截，叫做定义切点，相当于给需要拦截的函数加个标记，这个标记叫做切点
		 * 		joinpoint			连接点，RoleServiceImpld的接口，就是连接点，
		 * 		weaving				织入,动态反射的过程叫做织入
		 * 
		 * AOP说白了，主要就是拦截器，APO不是spring特有的，而spring aop只支持函数拦截，
		 * 在框架设计来说加以封装后可以做到参数拦截，例如mybatis的TypeHandler。
		 * 
		 * AOP中有四种方式实现拦截功能:
		 * 	使用ProxyBeanFactory和对应的接口实现。
		 * 	使用XML配置实现。
		 * 	使用@AspectJ注入驱动切面。(主流)
		 * 	使用AspectJ注入切面。
		 */
		RoleService roleService = new RoleServiceImpl();//要执行的实例
		Interceptor interceptor = new RoleInterceptor();//拦截器（切面）
		//创建动态代理
		RoleService proxy = ProxyBeanFactory.getBean(roleService, interceptor);
		Role role = new Role(1L, "role_name_1", "role_note_1");
		proxy.printRole(role);
		System.out.println("##############测试afterthrowing方法###############");
		role = null;
		proxy.printRole(role);
	}
}
