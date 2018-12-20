package org.jiira.chapter10.annotation.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = { "org.jiira.chapter10.*" }, excludeFilters = {
		@Filter(type = FilterType.REGEX, pattern = "org.jiira.chapter10.annotation.config.ApplicationConfig") })
@PropertySource(value = { "classpath:config/spring10/database-config10.properties" }, ignoreResourceNotFound = true)
public class AutowiredConfig {
	
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		Properties props = new Properties();
		props.setProperty("driver", "com.mysql.jdbc.Driver");
		props.setProperty("url", "jdbc:mysql://qdm169383443.my3w.com:3306/qdm169383443_db");
		props.setProperty("username", "qdm169383443");
		props.setProperty("password", "521haiqi");
		DataSource dataSource = null;
		try {
			dataSource = BasicDataSourceFactory.createDataSource(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSource;
	}
}
