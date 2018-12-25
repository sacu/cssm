package org.jiira.chapter12;

import java.sql.ResultSet;

import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter12.jdbc.JdbcExample;
import org.jiira.chapter12.mapper.RoleMapper;
import org.jiira.chapter12.pojo.Role;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class Chapter12Main {

	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
//		testJdbcExample();//基础jdbc
//		tesSpring();//基于spring的JdbcTemplate
//		testSqlSessionTemplate();//开始mybatis-spring，sqlSessionTemplate的使用
//		myRoleMapper();//MapperFactoryBean的使用(就是如何把Mapper加载到spring ioc里)
		testRoleMapper();//MapperScannerConfigurer的使用，就是批量加载Mapper到spring ioc里
		double result = (119*10000*0.12 + 119*(10000-4000)*0.11)*0.94;
		System.out.println(result*0.88);
	}
	public static void testJdbcExample() {
		JdbcExample exp = new JdbcExample();
		Role role = exp.getRole(1L);
		System.out.println(role.getRoleName());
	}
	public static void tesSpring() {
		///详细使用见 JdbcTemplateTest
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config/chapter12/spring-config.xml");
		JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
		Long id = 1L;
		String sql = "select id, role_name, note from t_role where id = " + id;
//		Role role = jdbcTemplate.queryForObject(sql, new RowMapper<Role>() {
//			@Override
//			public Role mapRow(ResultSet rs, int rownum) throws SQLException {
//				Role result = new Role();
//				result.setId(rs.getLong("id"));
//				result.setRoleName(rs.getString("role_name"));
//				result.setNote(rs.getString("note"));
//				return result;
//			}
//		});
		//lambda写法
		Role role = jdbcTemplate.queryForObject(sql, (ResultSet rs, int rownum) -> {
			Role result = new Role();
			result.setId(rs.getLong("id"));
			result.setRoleName(rs.getString("role_name"));
			result.setNote(rs.getString("note"));
			return result;
		});
		System.out.println(role.getRoleName());
	}
	public static void testSqlSessionTemplate() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config/chapter12/spring-config.xml");
		//ctx为Spring IoC容器
		SqlSessionTemplate sqlSessionTemplate = ctx.getBean(SqlSessionTemplate.class);
		Role role = new Role();
		role.setRoleName("role_name_sqlSessionTemplate");
		role.setNote("note_sqlSessionTemplate");
		sqlSessionTemplate.insert("org.jiira.chapter12.mapper.RoleMapper.insertRole", role);
		Long id = role.getId();
		sqlSessionTemplate.selectOne("org.jiira.chapter12.mapper.RoleMapper.getRole", id);
		role.setNote("update_sqlSessionTemplate");
		sqlSessionTemplate.update("org.jiira.chapter12.mapper.RoleMapper.updateRole", role);
		sqlSessionTemplate.delete("org.jiira.chapter12.mapper.RoleMapper.deleteRole", id);
	}
	
	public static void myRoleMapper() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config/chapter12/spring-config.xml");
		//ctx为Spring IoC容器
		SqlSessionTemplate sqlSessionTemplate = ctx.getBean(SqlSessionTemplate.class);
		//自己的简化版本
//		RoleMapper roleMapper = ctx.getBean(RoleMapper.class);//需要启用spring配置的MapperFactoryBean
		RoleMapper roleMapper = sqlSessionTemplate.getMapper(RoleMapper.class);//不需要启动MapperFactoryBean，因为他内部已经初始化rolemapper
		Role r = new Role();
		r.setNote("sa::note");
		r.setRoleName("sa::name");
		roleMapper.insertRole(r);//曾
		r = roleMapper.getRole(7L);
		System.out.println(r.getRoleName());
		r.setId(8L);
		r.setNote("sa::update");
		r.setRoleName("sa::update");
		roleMapper.updateRole(r);
		roleMapper.deleteRole(7L);
	}
	
	public static void testRoleMapper() {
		//需要打开 MapperScannerConfigurer，和 myRoleMapper()用途一样，只是能够批量加入Mapper
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config/chapter12/spring-config.xml");
		RoleMapper roleMapper = ctx.getBean(RoleMapper.class);
		roleMapper.getRole(2L);
	}
}
