package org.jiira.chapter4;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter4.mapper.RoleMapper;
import org.jiira.chapter4.mapper.UserMapper;
import org.jiira.chapter4.pojo.Role;
import org.jiira.chapter4.pojo.User;
import org.jiira.chapter4.utils.SqlSessionFactoryUtils;

public class Chapter4 {
	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
		Chapter4 c = new Chapter4();
//		c.testRoleMapper();
		c.testTypeHandler();
	}
	public void testRoleMapper() {
		Logger log = Logger.getLogger(Chapter4.class);
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = roleMapper.getRole(3L);
			log.info(role.getId());
			log.info(role.getRoleName());
			log.info(role.getNote());
			
			Role r1 = new Role();
			r1.setNote("哈哈");
			r1.setRoleName("呵呵");
			roleMapper.insertRole(r1);
			sqlSession.commit();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	public void testTypeHandler() {
		Logger log = Logger.getLogger(Chapter4.class);
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			UserMapper userMapper  = sqlSession.getMapper(UserMapper.class);
			User user = userMapper.getUser(2L);
			log.info(user.getSex().getName());
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}
