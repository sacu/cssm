package org.jiira.chapter6;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter6.mapper.RoleMapper;
import org.jiira.chapter6.pojo.Role;
import org.jiira.chapter6.utils.SqlSessionFactoryUtils;

public class Chapter6 {

	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
//		testFindRole1();
//		testFindRole2();//这个条件是INT主键的模糊查询。放弃比较
//		testFindRole3();
//		testFindRole4();
//		testFindRole5();
//		testFindRole6();
//		testUpdateRole();
//		testFindRoleByNums();//一次多条查询
		testGetRoleTest();
	}
	public static void testFindRole1() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<Role> roleList = roleMapper.findRole1("role_name");
			System.out.println(roleList.size());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testFindRole2() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = new Role();
			role.setId(1L);
			role.setRoleName("role_name");
			List<Role> roleList = roleMapper.findRole2(role);
			System.out.println(roleList.size());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testFindRole3() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = new Role();
			role.setId(1L);
			role.setRoleName("role_name");
			role.setNote("_1");
			List<Role> roleList = roleMapper.findRole3(role);
			System.out.println(roleList.size());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testFindRole4() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<Role> roleList = roleMapper.findRole4("role_name");
			System.out.println(roleList.size());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testFindRole5() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<Role> roleList = roleMapper.findRole5("role_name");
			System.out.println(roleList.size());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testFindRole6() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<Role> roleList = roleMapper.findRole6("role_name", "note");
			System.out.println(roleList.size());
		} catch(Exception ex) {
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
			role.setNote("note_1_update");
			role.setRoleName("role_name_1_update");
			role.setId(1L);
			int result = roleMapper.updateRole(role);
			System.out.println(result);
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
	public static void testFindRoleByNums() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<Long> roleNoList = new ArrayList<Long>();
			roleNoList.add(2L);
			roleNoList.add(5L);
			roleNoList.add(4L);
			List<Role> roleList = roleMapper.findRoleByNums(roleNoList);
			System.out.println(roleList.size());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	public static void testGetRoleTest() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<Role> roleList = roleMapper.getRoleTest("Y");
			System.out.println(roleList.size());
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}