package org.jiira.chapter11.aop.verifier.impl;

import org.jiira.chapter11.aop.verifier.RoleVerifier;
import org.jiira.chapter11.game.pojo.Role;
import org.springframework.stereotype.Component;

public class RoleVerifierImpl implements RoleVerifier {
	@Override
	public boolean verify(Role role) {
		System.out.println("引入，检测一下角色是否为空");
		return role != null;
	}
}
