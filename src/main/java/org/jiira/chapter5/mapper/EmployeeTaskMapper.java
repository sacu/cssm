package org.jiira.chapter5.mapper;

import org.jiira.chapter5.pojo.EmployeeTask;

public interface EmployeeTaskMapper {

	public EmployeeTask getEmployeeTaskByEmpId(Long empId);
}
