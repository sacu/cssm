package org.jiira.chapter8;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter8.mapper.RoleMapper;
import org.jiira.chapter8.params.PageParams;
import org.jiira.chapter8.pojo.Role;
import org.jiira.chapter8.utils.SqlSessionFactoryUtils;

public class Chapter8 {

	public static void main(String[] args) {
		/**
		 * mybatis SqlSession 四大对向
		 * Executor	代表执行器，由他调度StatementHandler、ParameterHandler、ResultSetHandler等，
		 * 			来执行对应的SQL。其中StatementHandler是最重要的。
		 * 
		 * StatementHandler	的作用是使用数据库的Statment(PreparedStatement)执行操作，他是四大队向的核心，
		 * 			起到承上启下的作用，许多重要的插件都是通过拦截它来实现的。
		 * ParameterHandler	是用来处理SQL参数的。
		 * ResultSetHandler	是进行数据集(ResultSet)的封装返回处理的，它相当复杂，但不常用。
		 */
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
		/**
		 * 插件基于责任链和动态代理
		 * 换句话说即：在四大对象调度的过程中插入我们自己的代码，执行一些特殊操作，就是MyBatis插件。
		 * 
		 * 插件编写需要继承Interceptor(拦截器)，拦截器是动态代理的核心概念，即：
		 * 在触发(反射)实际函数前后，进行操作。
		 * 
		 * Interceptor
		 * intercept	它将直接覆盖拦截对象的原有方法，因此它是插件的核心方法。
		 * 				可通过参数invocation对象，反射调度原来的方法。
		 * plugin		target是被拦截的对象实例，一般用于生成代理对象并返回。
		 * 				在MyBatis的org.apache.ibatis.plugin.Plugin中提供了wrap静态(static)方法生成代理对象，
		 * 				一般情况下都会用它来生成代理对象。
		 * setProperties	允许在插件元素中配置所需参数，方法在插件初始化时就会被调用一次，
		 * 				将插件对象存入到配置中，以便以后再取出。
		 */
		testMyPlugin();
//		testPagePlugin();
	}

	private static void testMyPlugin() {
		Logger log = Logger.getLogger(Chapter8.class);
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtils.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = roleMapper.getRole(1L);
			log.info(role.getRoleName());
		} catch (Exception ex) {
		    ex.printStackTrace();
		    sqlSession.rollback();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	
	//测试时，请在配置文件mybatis-config.xml中注释掉插件MyPlugin
	private static void testPagePlugin() {
		Logger log = Logger.getLogger(Chapter8.class); 
		SqlSession sqlSession = null;
		try {
		    sqlSession = SqlSessionFactoryUtils.openSqlSession();
		    RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
		    PageParams pageParams = new PageParams();
		    pageParams.setPageSize(10);
		    List <Role> roleList = roleMapper.findRole(pageParams, "role_name_");
		    log.info(roleList.size());
		} catch (Exception ex) {
		    ex.printStackTrace();
		    sqlSession.rollback();
		} finally {
		    if (sqlSession != null) {
		        sqlSession.close();
		    }
		}
	}

}