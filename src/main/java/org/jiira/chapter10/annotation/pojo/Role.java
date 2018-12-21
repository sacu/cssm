package org.jiira.chapter10.annotation.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 注解@Component，代表Spring会把这个类扫描成Bean实例，命名为role，也可以写成不带括号参数或不带value，
 * 不带参数则spring默认以类名+首字母小写为Bean id，不带value与带value含义一样
 * 
 * 注解@value，代表值注入，值注入操作会进行强制转换，比如id为Long型，会将字符串"1"转换成1
 * @author time
 *
 */
@Component(value = "role")
public class Role {
	@Value("1")
	private Long id;
	@Value("role_name_1")
	private String roleName;
	@Value("role_note_1")
	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
