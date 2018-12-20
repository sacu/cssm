package org.jiira.chapter8;

import java.util.List;

import org.apache.ibatis.plugin.Intercepts;
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
		 * 			它可以执行SQL的全过程，包括组装参数、组装结果、返回和执行SQL过程，根据是否启动缓存，决定它是否使用CachingExecutor进行封装，
		 * 			这是我们使用拦截器时需要注意的地方。
		 * 
		 * StatementHandler	的作用是使用数据库的Statment(PreparedStatement)执行操作，他是四大队向的核心，
		 * 			起到承上启下的作用，许多重要的插件都是通过拦截它来实现的。
		 * 			它用来拦截将要执行的SQL修改其过程。
		 * ParameterHandler	是用来处理SQL参数的。
		 * 			它是用来拦截SQL组装修改其参数。
		 * ResultSetHandler	是进行数据集(ResultSet)的封装返回处理的，它相当复杂，但不常用。
		 * 			它是用来拦截SQL执行后课用来重新组装结果。
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
		 * 
		 * 拦截简介：
		 * 	通过实现Interceptor接口，创建插件类，将插件类配置在mybatis.xml配置文件中，
		 * 在插件类开始出通过@Intercepts注解添加要拦截[类型.class]和要拦截的函数名，以及函数参数，
		 * 程序启动后mybatis会先调用setProperties函数，进行属性文件件设置(也可以理解成)默认参数传参，
		 * 然后mybatis会调用plugin函数传入@Intercepts注解类实例，以供创建动态代理对象，并返回，
		 * 接下来每当调用@Intercepts注解设置的类函数时，都会触发interceptor函数，
		 * 在interceptor函数中做对应的invocation.getTarget()取值就可获得拦截的类实例，
		 * 可以通过invocation.proceed()函数反射，继续调用实例类函数，因此在其前后做需求的数据处理即可。
		 * 
		 */
//		testMyPlugin();
		testPagePlugin();
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
		    pageParams.setPageSize(2);
		    pageParams.setPage(4);
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