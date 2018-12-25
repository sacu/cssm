package org.jiira.chapter11;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter11.aop.verifier.RoleVerifier;
import org.jiira.chapter11.game.pojo.Role;
import org.jiira.chapter11.xml.service.RoleService;

public class XmlAopMain {
	public static void main(String []args) {
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
		/**
		 * 通过xml配置绑定拦截器 和 实例类
		 * 其他都一样
		 * 
		 */
		ApplicationContext ctx 
		   = new ClassPathXmlApplicationContext("config/chapter11/spring-config11.3.xml");
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
