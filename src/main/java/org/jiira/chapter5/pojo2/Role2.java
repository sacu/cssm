package org.jiira.chapter5.pojo2;

import java.util.List;

public class Role2 {
	private Long id;
	private String roleName;
	private String note;
	// �����û���Ϣ��һ�Զ����
	private List<User2> userList;

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

	public List<User2> getUserList() {
		return userList;
	}

	public void setUserList(List<User2> userList) {
		this.userList = userList;
	}

}
