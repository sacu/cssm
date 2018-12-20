package org.jiira.chapter8.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import org.jiira.chapter8.params.PageParams;
import org.jiira.chapter8.pojo.Role;

public interface RoleMapper {
	
	public Role getRole(Long id);

	public List<Role> findRole(@Param("pageParams") PageParams pageParams, @Param("roleName") String roleName);
}