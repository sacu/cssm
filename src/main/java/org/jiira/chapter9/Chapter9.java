package org.jiira.chapter9;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.log4j.xml.DOMConfigurator;
import org.jiira.chapter9.pojo.JuiceMaker;
import org.jiira.chapter9.pojo.JuiceMaker2;

public class Chapter9 {
	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("config/log4j.xml", 2000);
//		testCommon();//正常编码
		/**
		 * 关于IOC，在spring中实现方式为依赖注入，其设计主要基于BeanFactory和ApplicationContext两个接口，
		 * ApplicationContext为BeanFactory的子接口。
		 * 
		 * BeanFactory:
		 * 	getBean:多态，用于获取spring ioc配置中的Bean。
		 * isSingleton:
		 * 	用于判断要获取的Bean配置是否为单例。
		 * isPrototype:
		 * 	用于判断要获取的Bean配置是否不为单例。
		 * isTypeMath:
		 * 	按类型匹配？什么GUI？
		 * getAliases
		 * 	用于获取别名。
		 */
		/**
		 * 下面说下IOC，简单看了下spring ioc，和我的逻辑有点类似，
		 * 简单说启动后，spring通过加载配置文件读取各种存储各种bean类的包类路径信息。
		 * 然后通过配置lazy-init判断是否需要初始化(false则默认初始化)。
		 * spring ioc容器的本质就是为了管理各种bean，
		 * bean的生命周期为：初始化-依赖注入-setBeanName-setBeanFactory-setApplicationContext
		 * 自定义初始化方法-afterPropertiesSet-postProcessBeforeInitialization┙
		 * 	┕postProcessAfterInitialization-生存期-destroy-自定义销毁方法
		 * Bean的生命周期调用，根据实现的接口不同，会调用的函数也会有所增减。
		 * ************************************************************************
		 * 看我的IOC+MV注入式框架，自动生成Bean
		 * https://weibo.com/ttarticle/p/show?id=2309404009818774963220&mod=zwenzhang
		 * **************************************************************************
		 */
		testIoC();//ioc控制反转
	}
	public static void testCommon() {
		JuiceMaker juiceMaker = new JuiceMaker();
		juiceMaker.setWater("矿泉水");
		juiceMaker.setFruit("橙子");
		juiceMaker.setSugar("少糖");
		System.out.println(juiceMaker.makeJuice());
	}
	public static void testIoC() {
		ClassPathXmlApplicationContext ctx = 
				new ClassPathXmlApplicationContext("config/spring-config9.xml");
		JuiceMaker2 juiceMaker2 = (JuiceMaker2) ctx.getBean("juiceMaker2");
		System.out.println(juiceMaker2.makeJuice());
		ctx.close();
	}
}
