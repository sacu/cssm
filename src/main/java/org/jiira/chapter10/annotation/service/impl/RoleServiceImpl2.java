package org.jiira.chapter10.annotation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.jiira.chapter10.annotation.pojo.Role;
import org.jiira.chapter10.annotation.service.RoleService2;

@Component("RoleService2")
public class RoleServiceImpl2 implements RoleService2 {

//	@Autowired
	private Role role = null;

	public Role getRole() {
		return role;
	}

	@Autowired
	public void setRole(Role role) {
		/**
		 * spring ioc自动装配的时候会调用一次
		 */
//		System.out.print("执行了函数自动装配");
		this.role = role;
	}

	@Override
	public void printRoleInfo() {
		System.out.println("id =" + role.getId());
		System.out.println("roleName =" + role.getRoleName());
		System.out.println("note =" + role.getNote());
	}
}