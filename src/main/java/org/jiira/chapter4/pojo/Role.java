package org.jiira.chapter4.pojo;

import org.apache.ibatis.type.Alias;

@Alias("role")
public class Role {

	private float id;
	private String roleName;
	private String note;

	/** setter and getter **/
	public float getId() {
		return id;
	}

	public void setId(float id) {
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