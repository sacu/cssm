package org.jiira.chapter5.mapper;


import org.jiira.chapter5.param.PdCountRoleParams;
import org.jiira.chapter5.param.PdFindRoleParams;

public interface PdRoleMapper {

	public void countRole(PdCountRoleParams pdCountRoleParams);
	
	public void findRole(PdFindRoleParams pdFindRoleParams);
}
