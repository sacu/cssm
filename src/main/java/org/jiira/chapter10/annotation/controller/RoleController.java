package org.jiira.chapter10.annotation.controller;
import org.jiira.chapter10.annotation.pojo.Role;
import org.jiira.chapter10.annotation.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RoleController {
	
	@Autowired
//	@Qualifier("roleServiceImpl")//开启后会优先选择
	private RoleService roleService = null;
	
	public void printRole(Role role) {
		roleService.printRoleInfo(role);
	}
}