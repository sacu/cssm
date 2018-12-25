package org.jiira.chapter11;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.apache.log4j.xml.DOMConfigurator;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.jiira.chapter11.aop.aspect.RoleAspect;
import org.jiira.chapter11.aop.config.AopConfig;
import org.jiira.chapter11.aop.service.RoleService;
import org.jiira.chapter11.aop.service.impl.RoleServiceImpl;
import org.jiira.chapter11.aop.verifier.RoleVerifier;
import org.jiira.chapter11.game.pojo.Role;

public class AopMain {
	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
//		testAnnotation();
//		testXML();
//		testAopParams();
		testIntroduction();
	}
	
	private static void testAnnotation() {
		ApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
		Role role = new Role();
		role.setId(1L);
		role.setRoleName("role_name_1");
		role.setNote("note_1");
		RoleService roleService = context.getBean(RoleService.class);
		roleService.printRole(role);
	}
	
	private static void testXML() {
		ApplicationContext context = new ClassPathXmlApplicationContext("config/chapter11/spring-config11.2.xml");
		Role role = new Role();
		role.setId(1L);
		role.setRoleName("role_name_1");
		role.setNote("note_1");
		RoleService roleService = context.getBean(RoleService.class);
		roleService.printRole(role);
	}
	
	private static void testAopParams() {
		ApplicationContext context = new ClassPathXmlApplicationContext("config/chapter11/spring-config11.2.xml");
		Role role = new Role();
		role.setId(1L);
		role.setRoleName("role_name_1");
		role.setNote("note_1");
		RoleService roleService = context.getBean(RoleService.class);
		roleService.printRole(role, 1);
	}
	
	private static void testIntroduction() {
		/**
		 * AopConfig为配置类
		 * 注解@Configuration，声明自己为配置类
		 * 注解@EnableAspectJAutoProxy，表示启动切面动态代理（即启动拦截器）
		 * 注解@ComponentScan，表示要扫描的包路径
		 * 只有RoleServiceImpl使用了注解@Component，因此，spring容器只会初始化他一个bean
		 * 注解@Bean，声明拦截器对象创建
		 * 
		 * 注意！RoleAspect内注解@Aspect也声明自己是一个拦截器
		 * 因此在通过配置类初始化时，RoleAspect会被声明为一个拦截器
		 * 这里，spring内部的aop会自动使用jdk动态代理。
		 * 
		 * 通过步骤(GameMain)理解当前操作：
		 * 1.创建要执行的实例
		 * 2.创建拦截器
		 * 3.通过jdk动态代理方式将要执行的实例和拦截器对象绑定（jdk动态代理通过“接口”代理要执行实例的函数并生成实例）
		 * 4.运行动态代理对象（动态代理对象内invoke函数执行时，调用切面属于中的“通知”函数）
		 * 
		 * 关于spring aop解析：
		 * 1.通过AopConfig声明配置（如上）。
		 * 2.spring aop内部自动生成jdk动态代理实例，并绑定拦截器（RoleAspect）
		 * 3.getBean获取到的对象为动态代理对象，并且通过动态代理绑定所有目录下的Bean实例（实际只有RoleServiceImpl）
		 * 4.关键1：动态代理实例在函数运行时，自动根据“切面术语”中的“通知”顺序调用拦截器RoleAspect中的函数。
		 * 这里和GameMain的过程原理几乎就一样了
		 * 
		 * 但是这里有个关键：RoleVerifier roleVerifier = (RoleVerifier) roleService;
		 * 其实就是“切面术语”中的引入概念
		 * 详情：RoleAspect中的@DeclareParents(value="...+", defaultImpl=..)
		 * 含义是，为对象添增实现接口。
		 * value为对象路径，defaultImpl为默认要添加的类实例(虽然是需要添加新的接口函数，但是类实例是要有的，不然函数调用对象就没办法指定了)
		 * 在RoleAspect中@DeclareParents的下方是要注入初始化的接口,字面意思为将接口函数新增到value对象中
		 * value最后的+号表示，对象子类也可以新增其defaultImpl中的接口函数，如果没有+号则表示只为value对象增加接口函数
		 * defaultImpl类或其实现接口不会被动态代理
		 * 
		 * 在这里还有一个关键点：ProceedingJoinPoint
		 * 即：进程连接点，他连接的就是回调的实际对象函数,也就是拦截器所定义的拦截连接点
		 * 进程连接点对象调用proceed()实现调用实际对象函数
		 */
		ApplicationContext ctx = new AnnotationConfigApplicationContext (AopConfig.class);
		/**
		 * 关于jdk动态代理和cglib动态代理
		 * 如果实例类(@Component)使用了接口，则spring aop会自动使用jdk动态代理（如下：RoleService）
		 * 如果实例类(@Component)没有使用接口，则spring aop会自动使用cglib动态代理（如下：RoleServiceImpl）
		 */
//		RoleServiceImpl roleService = ctx.getBean(RoleServiceImpl.class);
		RoleService roleService = ctx.getBean(RoleService.class);
		RoleVerifier roleVerifier = (RoleVerifier) roleService;

		Role role = new Role();
		role.setId(1L);
		role.setRoleName("role_name_1");
		role.setNote("note_1");

		if (roleVerifier.verify(role)) {
		    roleService.printRole(role);
		}
	}
}
