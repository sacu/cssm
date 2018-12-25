package org.jiira.chapter13.mapper;

import org.jiira.chapter13.pojo.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {
	public int insertRole(Role role);
}