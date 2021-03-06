package org.jiira.chapter10.annotation.service.impl;

import org.springframework.stereotype.Component;

import org.jiira.chapter10.annotation.pojo.Role;
import org.jiira.chapter10.annotation.service.RoleService;
@Component
public class RoleServiceImpl implements RoleService {
	@Override
	public void printRoleInfo(Role role) {
		System.out.println("RoleServiceImpl");
		System.out.println("id =" + role.getId());
		System.out.println("roleName =" + role.getRoleName());
		System.out.println("note =" + role.getNote());
	}
}