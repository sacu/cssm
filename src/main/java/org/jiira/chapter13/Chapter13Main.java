package org.jiira.chapter13;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter13.config.JavaConfig;
import org.jiira.chapter13.pojo.Role;
import org.jiira.chapter13.service.RoleListService;

public class Chapter13Main {
	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
		// test13();
		programTransaction();

	}

	public static void programTransaction() {
		// 编程式事务
		ApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
		JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
		// 事务定义
		TransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager transactionManager = (PlatformTransactionManager)ctx.getBean("transactionManager");
		System.out.println(transactionManager.getClass().toString());
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			jdbcTemplate
			.update("insert into t_role(role_name, note) values('111', '111')");
			jdbcTemplate
			.update("insert into t_role(role_name, note1) values('222', '222')");
			transactionManager.commit(status);// 提交事务
			System.out.println("提交成功");
		} catch (Exception ex) {
			transactionManager.rollback(status);// 回滚事务
			System.out.println("提交失败，已回滚 : " + ex.getMessage());
		}
	}

	public static void test13() {
		/**
		 * 事务	transaction	主要意义在于拦截错误、回滚
		 * 在对数据库操作中，经常有连贯提交数据，为防止中间环节出现错误，还原已提交的数据
		 * 隔离级别 和 传播行为
		 * 隔离级别（主要用于数据安全防范，根据不同功能需求进行选择）:
		 * 							Dirty reads		non-repeatable reads		phantom reads
		 * SERIALIZABLE					N					N						N
		 * REPEATABLE_READ				N					N						Y
		 * READ_COMMITTED				N					Y						Y
		 * READ_UNCOMMITTED				Y					Y						Y
		 * 
		 * 		图表解释（会出现的问题，以下所有是指在当前事务线内，做了修改未提交导致的问题）：
		 * 	1.Dirty reads，脏读。事务A未提交的数据（在服务器缓存）被事务B读走，如果事务A失败回滚，会导致事务B所读取的的数据是错误的。
		 * 		例子：（总共100元，A读取数据花了10元未提交，此时缓存为90元，此时B读取的是90元，这时A回滚后缓存变回100元，B的数据会受到影响）
		 * 	2.non-repeatable reads，不可重复读。事务A读取数据total为100。然后事务B把total改成200，事务A再次读取，造成事务A数据混乱。
		 * 		例子：（总共100元，A获取了数据100元，此时B也获取，并且买了东西并且提交，此时A的数据混乱）。
		 * 	3：phantom reads，幻象读数据。A查询数据有6条，此时B新提交了一条数据，A点进去看的时候却有7条。
		 * 
		 * 		级别（可对照以上图表）：
		 * 	1.READ_UNCOMMITTED，保证了读取过程中不会读取到非法数据。
		 * 	2.READ_COMMITTED，避免脏读，是绝大多数数据库默认隔离级别。
		 * 	3.REPEATABLE_READ，避免脏读和不可重复读，但性能损耗会增加。
		 * 	4.SERIALIZABLE，最严格的级别，事务串行执行，资源消耗最大。
		 * 
		 * 传播行为（首先细聊下事务关系）：
		 * 		根据数据的连带关系控制各种数据（甚至有很多数据是嵌套的，例如一个提交流程中，有支付和日志提交，支付提交失败需要回滚，但是日志是不需要回滚的），
		 * 		事务的提交一般会有嵌套关系，每个事务块都会有自己的事务状态（TransactionStatus）， 父事务进入子事务前会创建一个回滚点（save point），
		 * 		在执行子事务时，如果成功,子事务的事务状态会为true，如果失败，则会为false，父事务通过事务状态判断是进行提交操作或者回滚操作，
		 * 		那么传播行为，就是控制事务的下级或上级参与事务的级别，例如父子级的传播行为互不相干，或者某一块不参与事务，或参与事务等……
		 * 		
		 * 		事务：
		 * 	1.REQUIRED，默认事务级别，如事务已存在，那么就加到事务中执行，如事务不存在，则新建事务并执行。
		 * 	2.SUPPORTS，如事务存在，则加入到事务执行中，如果没有事务，则使用非事务的方式执行。使用较少。
		 * 	3.MANDATORY，要求事务必须存在，否则就会抛出异常！
		 * 	4.REQUIRES_NEW，每次都会新建事务，并且同时将上下文中的事务挂起，当新建事务执行完成以后，上下文事务再恢复执行。
		 * 	5.NOT_SUPPORTED，如果事务存在，则挂起事务，执行当前逻辑，结束后恢复上下文的事务。
		 * 	6.NEVER，比NOT_SUPPORTED更加严格，要求上下文不能存在事务，一旦有事务，就抛出runtime异常，强制停止执行！
		 * 	7.NESTED，嵌套事务，如果上下文中存在事务，则嵌套事务执行，如果不存在事务，则新建事务。
		 * 
		 */
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config/chapter13/spring-config13.xml");
		RoleListService roleListService = ctx.getBean(RoleListService.class);
		List<Role> roleList = new ArrayList<Role>();
		for (int i = 1; i <= 2; i++) {
			Role role = new Role();
			role.setRoleName("role_name_" + i);
			role.setNote("note_" + i);
			roleList.add(role);
		}
		int count = roleListService.insertRoleList(roleList);
		System.out.println(count);
	}
}