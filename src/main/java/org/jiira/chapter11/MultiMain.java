package org.jiira.chapter11;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter11.multi.bean.MultiBean;
import org.jiira.chapter11.multi.config.MultiConfig;

public class MultiMain {

	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
		/**
		 * 书上说……多个切面（多个拦截器），生成时，顺序是不固定的，需要用@Order()注解来排序
		 * 但是……为毛我这里是根据MultiConfig中拦截器获取实例函数写的顺序排序了？？？
		 */
		for (int i=0; i<10; i++) {
			System.out.println("#########################################\n\n\n");
			ApplicationContext ctx = new AnnotationConfigApplicationContext(MultiConfig.class);
			MultiBean multiBean = ctx.getBean(MultiBean.class);
			multiBean.testMulti();
		}
	}

}
