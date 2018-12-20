package org.jiira.chapter10;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter10.annotation.config.DataSourceBean;
import org.jiira.chapter10.annotation.service.RoleDataSourceService;
import org.jiira.chapter10.pojo.UserRoleAssembly;
public class Chapter10 {
	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
		test3();
	}
	public static void test1() {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("spring-config.xml");
		UserRoleAssembly userRoleAssembly = context.getBean(UserRoleAssembly.class);
			System.err.println(userRoleAssembly.getList().get(0).getId());
	}
	public static void test2() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-props.xml");
		DataSourceBean dsBean = context.getBean(DataSourceBean.class);
		System.out.println(dsBean.getUrl());
	}
	public static void test3() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring10/spring-props10.xml");
		RoleDataSourceService RoleService = ctx.getBean(RoleDataSourceService.class);
		RoleDataSourceService RoleService2 =  ctx.getBean(RoleDataSourceService.class);
		System.out.println(RoleService == RoleService2);
	}
}
