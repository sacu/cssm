package org.jiira.chapter10;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter10.annotation.config.ApplicationConfig;
import org.jiira.chapter10.annotation.config.AutowiredConfig;
import org.jiira.chapter10.annotation.controller.RoleController;
import org.jiira.chapter10.annotation.controller.RoleController2;
import org.jiira.chapter10.annotation.pojo.JuiceMaker2;
import org.jiira.chapter10.annotation.pojo.PojoConfig;
import org.jiira.chapter10.annotation.pojo.Role;
import org.jiira.chapter10.annotation.service.RoleDataSourceService;
import org.jiira.chapter10.annotation.service.RoleService;
import org.jiira.chapter10.annotation.service.RoleService2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Chapter10_Annotation {
	public static void main(String[] args) {
		/**
		 * spring 先完成Bean的定义和生成，然后再寻找需要注入的资源
		 */
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
		// test1();
		// test2();
		// test3();
		// test4();
		// test5();
		// test6();
		// test7();
		// test8();
		test9();
	}

	private static void test1() {
		/**
		 * 通过PojoConfig中的扫描注解开始运行…… 可查看PojoConfig的内容
		 */
		ApplicationContext context = new AnnotationConfigApplicationContext(PojoConfig.class);
		Role role = context.getBean(Role.class);
		System.err.println(role.getId());
		// JuiceMaker2没有@Component注解，所以没有被spring扫描成Bean实例
		// JuiceMaker2 jm = context.getBean(JuiceMaker2.class);
		// jm.getBeverageShop();
	}

	private static void test2() {
		/**
		 * 通过ApplicationConfig中的扫描注解开始运行…… basePackages和basePackageClasses可实现同样效果
		 */
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		Role role = context.getBean(Role.class);
		RoleService roleService = context.getBean("roleService", RoleService.class);
		roleService.printRoleInfo(role);
		context.close();
	}

	private static void test3() {
		/**
		 * 注解@Autowired，可以为函数或属性进行自动装配 详情查看RoleServiceImpl2
		 */
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		RoleService2 roleService = context.getBean(RoleService2.class);
		roleService.printRoleInfo();
		context.close();
	}

	private static void test4() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowiredConfig.class);
		/**
		 * 如果实现RoleController中，自动注解属性RoleService接口如果只有一个实现类，则可以顺利运行
		 * 但现在实现RoleService接口的存在RoleServiceImpl和RoleServiceImpl3 这时spring ioc会发生歧义，解决方案：
		 * 1.使用@Primary，将实现RoleService的某个类设置首要注解
		 * 2.使用@Qualifier，在RoleController中设置要使用的实例id
		 */
		RoleController roleController = context.getBean(RoleController.class);
		Role role = context.getBean(Role.class);
		roleController.printRole(role);
		context.close();
	}

	private static void test5() {
		/**
		 * 自动装载带有参数的构造方法类 可配合@Primary和@Qualifier使用
		 */
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowiredConfig.class);
		RoleController2 roleController = context.getBean(RoleController2.class);
		Role role = context.getBean(Role.class);
		roleController.printRole(role);
		context.close();
	}

	private static void test6() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		/**
		 * 自动装配MySQL ApplicationConfig.getDataSource() 注解@Bean，可实现函数返回实例类型
		 * 也可以使用@Autowired和@Qualifier
		 */
		DataSource dataSource = context.getBean(DataSource.class);
		try {
			System.out.println(dataSource.getConnection().getMetaData().getDatabaseProductName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void test7() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		/**
		 * 注解@Bean，装载自定义类型 测试时需要在ApplicationConfig，打开initJuiceMaker2方法注释，因为测试其他比较乱，所以关掉了
		 */
		JuiceMaker2 jm = context.getBean(JuiceMaker2.class);
		System.out.println(jm.makeJuice());
		context.close();
	}

	private static void test8() {
		/***
		 * 测试注解引入XML时候，注意到ApplicationConfig关于这个方法的注释，需要修改后才能测试。
		 */
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		/**
		 * 装配的混合使用 获取实现RoleDataSourceService接口的实例RoleDataSourceServiceImpl
		 * 自动装配DataSource dataSource的三个途径: 1. 其中DataSource dataSource通过
		 * ApplicationConfig.getDataSource()自动装配 接下来就是jdbc连接mysql取值、赋值，返回Role对象了…… 2.
		 * 使用ApplicationConfig的@ImportResource注解记载XML配置，通过配置文件初始化返回DataSource 3.
		 * 使用ApplicationConfig的@PropertySource注解加载properties配置文件，可配合@Value注解，
		 * 在全局使用，需要打开ApplicationConfig.getDataSource()。
		 * 注解@PropertySource，还可自动装配Environment(RoleDataSourceServiceImpl中查看)。
		 */
		RoleDataSourceService roleDataSourceService = context.getBean(RoleDataSourceService.class);
		Role role = roleDataSourceService.getRole(1L);
		System.out.println(role.getRoleName());
		context.close();
	}

	private static void test9() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		/**
		 * 关于@PropertySource注解的一个新玩法，实际他是初始化在Spring IOC上下文中的getEnvironment()中
		 */
		String url = context.getEnvironment().getProperty("jdbc.database.url");
		System.out.println(url);
	}
}
