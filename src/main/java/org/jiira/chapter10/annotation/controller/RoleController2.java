package org.jiira.chapter10.annotation.controller;

import org.jiira.chapter10.annotation.pojo.Role;
import org.jiira.chapter10.annotation.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class RoleController2 {
	
	private RoleService roleService = null;
	@Autowired
	public RoleController2(@Qualifier("roleServiceImpl") RoleService roleService) {
	    this.roleService = roleService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService( RoleService roleService) {
		this.roleService = roleService;
	}
	
	public void printRole(Role role) {
		roleService.printRoleInfo(role);
	}
}
