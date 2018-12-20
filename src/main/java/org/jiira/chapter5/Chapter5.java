package org.jiira.chapter5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter5.mapper.EmployeeMapper;
import org.jiira.chapter5.mapper.PdRoleMapper;
import org.jiira.chapter5.mapper.RoleMapper;
import org.jiira.chapter5.mapper2.RoleMapper2;
import org.jiira.chapter5.mapper2.UserMapper2;
import org.jiira.chapter5.param.PageParams;
import org.jiira.chapter5.param.PdCountRoleParams;
import org.jiira.chapter5.param.PdFindRoleParams;
import org.jiira.chapter5.param.RoleParams;
import org.jiira.chapter5.pojo.Employee;
import org.jiira.chapter5.pojo.Role;
import org.jiira.chapter5.pojo2.Role2;
import org.jiira.chapter5.pojo2.User2;
import org.jiira.chapter5.utils.SqlSessionFactoryUtils;

public class Chapter5 {
	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
//		testPdCountRole();
//		testPdFindRole();//貌似mysql不支持sys_refcursor游标
//		testGetRole();
//		testFindRolesByMap();
//		testFindRolesByAnnotation();
//		testFindRolesByBean();
//		testFindByMix();
//		testGetRoleUseResultMap();
//		testUpdateRole();
//		testDeleteRole();
//		testGetEmployee();//利用mybatis自动映射链表查询
//		testGetEmployee2();//sql语句链表查询
//		testUserRole();
//		testOneLevelCache();//一级缓存
		testOneLevelCache2();//
	}
	public static void testPdCountRole() {
		/**
		 * roleName	in
		 * total	out
		 * execDate	out
		 */
		PdCountRoleParams params = new PdCountRoleParams();
		SqlSession sqlSession = null;
		try {
			Logger logger = Logger.getLogger(Chapter5.class);
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			PdRoleMapper roleMapper = sqlSession.getMapper(PdRoleMapper.class);
			params.setRoleName("role_name");
			roleMapper.countRole(params);//执行一个存储过程
			logger.info(params.getTotal());//获取参数
			logger.info(params.getExecDate());//获取参数
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testPdFindRole() {
		PdFindRoleParams params = new PdFindRoleParams();
		SqlSession sqlSession = null;
		try {
			Logger logger = Logger.getLogger(Chapter5.class);
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			PdRoleMapper roleMapper = sqlSession.getMapper(PdRoleMapper.class);
			params.setRoleName("role_name");
			params.setStart(0);
			params.setEnd(100);
			roleMapper.findRole(params);
			logger.info(params.getRoleList().size());
			logger.info(params.getTotal());
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testGetRole() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = roleMapper.getRole(1L);
			System.out.println(role.getRoleName());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testFindRolesByMap() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("roleName", "1");
			parameterMap.put("note", "1");
			List<Role> roles = roleMapper.findRolesByMap(parameterMap);
			System.out.println(roles.size());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testFindRolesByAnnotation() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<Role> roles = roleMapper.findRolesByAnnotation("1", "1");
			System.out.println(roles.size());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testFindRolesByBean() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			RoleParams roleParam = new RoleParams();
			roleParam.setNote("1");
			roleParam.setRoleName("1");
			List<Role> roles = roleMapper.findRolesByBean(roleParam);
			System.out.println(roles.size());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testFindByMix() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			RoleParams roleParam = new RoleParams();
			roleParam.setNote("1");
			roleParam.setRoleName("1");
			PageParams pageParams = new PageParams();
			pageParams.setStart(0);
			pageParams.setLimit(100);
			List<Role> roles = roleMapper.findByMix(roleParam, pageParams);
			System.out.println(roles.size());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testGetRoleUseResultMap() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = roleMapper.getRoleUseResultMap(1L);
			System.out.println(role.getRoleName());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testInsertRole() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = new Role();
			role.setRoleName("role_name_5");
			role.setNote("note_5");
			roleMapper.insertRole(role);
			//回填
			System.out.println(role.getId());
			sqlSession.commit();
		} catch(Exception ex) {
			sqlSession.rollback();
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testInsertRole2() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = new Role();
			role.setRoleName("role_name");
			role.setNote("note");
			roleMapper.insertRole2(role);
			//回填
			System.out.println(role.getId());
			sqlSession.commit();
		} catch(Exception ex) {
			sqlSession.rollback();
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testUpdateRole() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = new Role();
			role.setRoleName("role_name_1_update");
			role.setNote("note_1_update");
			role.setId(1L);
			roleMapper.updateRole(role);
			sqlSession.commit();
		} catch(Exception ex) {
			sqlSession.rollback();
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testDeleteRole() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			roleMapper.deleteRole(3L);
			sqlSession.commit();
		} catch(Exception ex) {
			sqlSession.rollback();
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testGetEmployee() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employee = employeeMapper.getEmployee(1L);
			System.out.println(employee.getWorkCard().getPosition());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testGetEmployee2() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employee = employeeMapper.getEmployee2(1L);
			System.out.println(employee.getWorkCard().getPosition());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testUserRole() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper2 roleMapper2 = sqlSession.getMapper(RoleMapper2.class);
			Role2 role2 = roleMapper2.getRole(1L);
			System.out.println(role2.getUserList().size());
//			UserMapper2 userMapper2 = sqlSession.getMapper(UserMapper2.class);
//			User2 user2 = userMapper2.getUser(1L);
//			System.out.println(user2.getRoleList().size());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testOneLevelCache() {
		SqlSession sqlSession = null;
		Logger logger = Logger.getLogger(Chapter5.class);
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = roleMapper.getRole(1L);
			logger.info("再获取一次POJO......");
			Role role2 = roleMapper.getRole(1L);
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testOneLevelCache2() {
		SqlSession sqlSession = null;
		SqlSession sqlSession2 = null;
		Logger logger = Logger.getLogger(Chapter5.class);
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			sqlSession2 = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = roleMapper.getRole(1L);
			//需要提交，如果是一级缓存，MyBatis才会缓存对象到SqlSessionFactory层面
			sqlSession.commit();
			logger.info("不同sqlSession再获取一次POJO......");
			RoleMapper roleMapper2 = sqlSession2.getMapper(RoleMapper.class);
			Role role2 = roleMapper2.getRole(1L);
			//需要提交，MyBatis才缓存对象到SQLSessionFactory
			sqlSession2.commit();
		} catch(Exception e) {
			logger.info(e.getMessage(), e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
			if (sqlSession2 != null) {
				sqlSession.close();
			}
		}
	}
	
}
