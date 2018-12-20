package org.jiira.chapter3;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter3.mapper.RoleMapper;
import org.jiira.chapter3.mapper.RoleMapper2;
import org.jiira.chapter3.pojo.Role;
import org.jiira.chapter3.utils.SqlSessionFactoryUtils;

public class Chapter3 {
	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
		Chapter3 c = new Chapter3();
		c.testRoleMapper();
//		c.testRoleMapper2();
	}
	/**
	 * XML配置文件
	 */
	public void testRoleMapper() {
		Logger log = Logger.getLogger(Chapter3.class);
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = roleMapper.getRole(1L);
			log.info(role.getRoleName());
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	/**
	 * @注解
	 */
	public void testRoleMapper2() {
		Logger log = Logger.getLogger(Chapter3.class);
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper2 roleMapper2 = sqlSession.getMapper(RoleMapper2.class);
			Role role = roleMapper2.getRole(1L);
			log.info(role.getRoleName());
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}
