package org.jiira.chapter11;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter11.game.pojo.Role;
import org.jiira.chapter11.game.service.RoleService;

public class ProxyFactoryBean {
	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
		ApplicationContext ctx = 
				new ClassPathXmlApplicationContext("config/chapter11/spring-config11.1.xml");
		Role role = new Role();
		role.setId(1L);
		role.setRoleName("role_name");
		role.setNote("note");
		RoleService roleService = (RoleService) ctx.getBean("roleService");
		roleService.printRole(role);
	}
}
