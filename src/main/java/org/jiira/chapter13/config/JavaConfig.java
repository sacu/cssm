package org.jiira.chapter13.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
/**
 * 使用java配置方式实现 spring 数据库事务
 * @author time
 *
 */

@Configuration // 声明为配置
//@ComponentScan("org.jiira.chapter13.*") // 扫描路径
@EnableTransactionManagement // 启用事务管理驱动器
public class JavaConfig implements TransactionManagementConfigurer {
	// 数据源
	private DataSource dataSource;

	/**
	 * 配置数据源
	 */
	@Bean(name = "dataSource")
	public DataSource initDataSource() {
		if (null != dataSource) {
			return dataSource;
		}
		Properties props = new Properties();
		props.setProperty("driver", "com.mysql.jdbc.Driver");
		props.setProperty("url", "jdbc:mysql://qdm169383443.my3w.com:3306/qdm169383443_db");
		props.setProperty("username", "qdm169383443");
		props.setProperty("password", "521haiqi");
		props.setProperty("maxActive", "255");
		props.setProperty("maxIdle", "5");
		props.setProperty("maxWait", "10000");

		try {
			dataSource = BasicDataSourceFactory.createDataSource(props);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataSource;
	}

	/**
	 * 配置jdbcTemplate
	 */
	@Bean(name = "jdbcTemplate")
	public JdbcTemplate initjdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(initDataSource());
		return jdbcTemplate;
	}

	/**
	 * 实现接口方法，使得返回数据库事务管理器
	 */
	@Override
	@Bean(name = "transactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		// TODO Auto-generated method stub
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(initDataSource());
		System.out.println(transactionManager.getClass().toString());
		return transactionManager;
	}

}
