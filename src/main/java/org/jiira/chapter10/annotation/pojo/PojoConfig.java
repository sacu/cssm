package org.jiira.chapter10.annotation.pojo;

import org.springframework.context.annotation.ComponentScan;

/**
 * 注解@ComponentScan，代表扫描操作，默认是扫描当前包路径：
 * org.jiira.chapter10.annotation.pojo
 * 所有包路径内带有@Component注解的类，都将被扫描为Bean实例
 * 
 * @author time
 *
 */
@ComponentScan
public class PojoConfig {
}
