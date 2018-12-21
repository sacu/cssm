package org.jiira.chapter11.aop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import org.jiira.chapter11.aop.aspect.RoleAspect;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "org.jiira.chapter11.aop")
public class AopConfig {
	
	@Bean
    public RoleAspect getRoleAspect() {
        return new RoleAspect();
    }
}
