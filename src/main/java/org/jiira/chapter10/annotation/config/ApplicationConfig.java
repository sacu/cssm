package org.jiira.chapter10.annotation.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.jiira.chapter10.annotation.condition.DataSourceCondition;
import org.jiira.chapter10.annotation.pojo.Role;
import org.jiira.chapter10.annotation.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 关于basePackageClasses和basePackages的两种范围扫描配置:
 * basePackages
 * 	可以指定多个包名进行扫描。(这个比较常用)
 * 	可读性好，比较常用，但大型项目，包名、类路径变动不会有错误提示，容易发生错误，需要注意。
 * basePackageClasses
 * 	可以指定多个类或接口的class,扫描时会 在这些指定的类和接口所属的包进行扫面。
 * 	当指定类路径发生变化，会有详细错误提示，方便维护。
 * 
 * 1.多个@ComponentScan注解会生成多个Bean实例，一般来讲是没有意义的。
 * 2.在同一个@ComponentScan注解中进行重复定义的范围扫描，是不会造成同一个Bean多次扫描，所以不会生成多个对象。
 * 基于以上两点，不建议多个@ComponentScan注解，因为可能会产生多个重复对象。
 * 
 * @author time
 *
 */
//@ComponentScan(basePackageClasses = { Role.class, RoleServiceImpl.class }
//	,excludeFilters = {@Filter(type = FilterType.REGEX, pattern="org.jiira.chapter10.annotation.config.AutowiredConfig")}
//)
//@ComponentScan(basePackages = {"org.jiira.chapter10.annotation.pojo", "org.jiira.chapter10.annotation.service"})

@ComponentScan(
//		 basePackages = {"org.jiira.chapter10.annotation.pojo","org.jiira.chapter10.annotation.service"}
		 basePackageClasses = {Role.class, RoleServiceImpl.class}
)

//测试test8的时候引入下面的XML，同时注释现有数据库连接池的方法
//@ImportResource({"classpath:config/spring10/spring-dataSource10.xml"})
@PropertySource(value={"classpath:config/spring10/database-config10.properties"}, ignoreResourceNotFound=true)
public class ApplicationConfig {
	
	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	/***
	 * 测试test8的时候需要注释掉整个datasource，否则会抛异常
	 */
//	@Bean(name = "dataSource")
//	public DataSource getDataSource() {
//		Properties props = new Properties();
//		props.setProperty("driver", "com.mysql.jdbc.Driver");
//		props.setProperty("url", "jdbc:mysql://qdm169383443.my3w.com:3306/qdm169383443_db");
//		props.setProperty("username", "qdm169383443");
//		props.setProperty("password", "521haiqi");
//		DataSource dataSource = null;
//		try {
//			dataSource = BasicDataSourceFactory.createDataSource(props);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return dataSource;
//	}
	
	@Bean(name = "dataSource")
	@Conditional({DataSourceCondition.class})
	public DataSource getDataSource(
			@Value("${jdbc.database.driver}") String driver,
			@Value("${jdbc.database.url}") String url,
			@Value("${jdbc.database.username}") String username, 
			@Value("${jdbc.database.password}") String password) {
		Properties props = new Properties();
		props.setProperty("driver", driver);
		props.setProperty("url", url);
		props.setProperty("username", username);
		props.setProperty("password", password);
		DataSource dataSource = null;
		try {
			dataSource = BasicDataSourceFactory.createDataSource(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSource;
	}
	
//	@Bean(name="juiceMaker2", initMethod="init", destroyMethod="myDestroy")
//	public JuiceMaker2 initJuiceMaker2() {
//		JuiceMaker2 juiceMaker2 = new JuiceMaker2();
//		juiceMaker2.setBeverageShop("贡茶");
//		Source source = new Source();
//		source.setFruit("橙子");
//		source.setSize("大杯");
//		source.setSugar("少糖");
//	     juiceMaker2.setSource(source);
//		return juiceMaker2;
//	}
}