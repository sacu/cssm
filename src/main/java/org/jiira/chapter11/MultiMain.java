package org.jiira.chapter11;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.jiira.chapter11.multi.bean.MultiBean;
import org.jiira.chapter11.multi.config.MultiConfig;

public class MultiMain {

	public static void main(String[] args) {
		for (int i=0; i<10; i++) {
			System.out.println("#########################################\n\n\n");
			ApplicationContext ctx = new AnnotationConfigApplicationContext(MultiConfig.class);
			MultiBean multiBean = ctx.getBean(MultiBean.class);
			multiBean.testMulti();
			
		}
	}

}
