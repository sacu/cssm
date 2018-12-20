package org.jiira.chapter4.mapper;

import java.util.List;

import org.jiira.chapter4.pojo.Role;
public interface RoleMapper {
	public int insertRole(Role role);
	public int deleteRole(Long id);
	public int updateRole(Role role);
	public Role getRole(Long id);
	public List<Role> findRoles(String roleName);
}