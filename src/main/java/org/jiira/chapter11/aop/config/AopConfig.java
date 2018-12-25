package org.jiira.chapter11.aop.config;

import org.jiira.chapter11.aop.aspect.RoleAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration//配置文件
@EnableAspectJAutoProxy//启用切面自动代理
@ComponentScan(basePackages = "org.jiira.chapter11.aop")//扫描的包
public class AopConfig {

	@Bean
    public RoleAspect getRoleAspect() {
        return new RoleAspect();
    }
}
