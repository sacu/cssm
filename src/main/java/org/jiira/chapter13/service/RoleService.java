package org.jiira.chapter13.service;

import java.util.List;

import org.jiira.chapter13.pojo.Role;

public interface RoleService {
	
	public int insertRole(Role role);
	
	public int insertRoleList(List<Role> roleList);
	
	public int insertRoleList2(List<Role> roleList);
}
