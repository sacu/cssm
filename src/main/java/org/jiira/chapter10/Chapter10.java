package org.jiira.chapter10;

import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter10.annotation.config.DataSourceBean;
import org.jiira.chapter10.annotation.service.RoleDataSourceService;
import org.jiira.chapter10.pojo.UserRoleAssembly;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Chapter10 {
	public static void main(String[] args) {
		/**
		 * spring 可以配置@Profile注解达到数据库分化功能（以便测试，这块以后用到再细查吧，在书249页）
		 */
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
		test2();
	}
	public static void test1() {
		/**
		 * 通过XML配置SpringIOC的对象生成和注入
		 */
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("config/spring10/spring-config10.xml");
		/**
		 * 通过配置文件设置 UserRoleAssembly初始化四个参数
		 */
		UserRoleAssembly userRoleAssembly = context.getBean(UserRoleAssembly.class);
			System.err.println(userRoleAssembly.getList().get(0).getId());
	}
	public static void test2() {
		ApplicationContext context = new ClassPathXmlApplicationContext("config/spring10/spring-props10.xml");
		/**
		 * 通过配置文件注入设置PropertyPlaceholderConfigurer的locations属性，来解析properties文件
		 * spring ioc 可以通过@Value("${key}")调用对应的值
		 */
		DataSourceBean dsBean = context.getBean(DataSourceBean.class);
		System.out.println(dsBean.getUrl());
	}
	public static void test3() {
		/**
		 * spring ioc中作用域范围如下:
		 * 	singleton:单例
		 * 	prototype:原型
		 * 	session:web中使用，回话过程中只创建一个（回话超时后会重新创建）
		 * 	request:请求，一次请求中会创建一个。
		 * 
		 * 通过XML配置spring ioc测试Bean的实例创建
		 * Bean中使用@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)，则每次获取创建新的实例
		 * Bean默认使用@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)，为单例模式
		 */
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring10/spring-props10.xml");
		RoleDataSourceService RoleService = ctx.getBean(RoleDataSourceService.class);
		RoleDataSourceService RoleService2 =  ctx.getBean(RoleDataSourceService.class);
		System.out.println(RoleService == RoleService2);
	}
}
