package org.jiira.chapter11.multi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import org.jiira.chapter11.multi.aspect.Aspect1;
import org.jiira.chapter11.multi.aspect.Aspect2;
import org.jiira.chapter11.multi.aspect.Aspect3;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("org.jiira.chapter11.multi")
public class MultiConfig {

	@Bean
    public Aspect1 getAspect1() {
        return new Aspect1();
    }
	
	@Bean
    public Aspect2 getAspect2() {
        return new Aspect2();
    }	
	@Bean
    public Aspect3 getAspect3() {
        return new Aspect3();
    }
}