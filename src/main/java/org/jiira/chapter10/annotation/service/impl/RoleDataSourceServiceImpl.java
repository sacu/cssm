package org.jiira.chapter10.annotation.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.jiira.chapter10.annotation.pojo.Role;
import org.jiira.chapter10.annotation.service.RoleDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
// @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RoleDataSourceServiceImpl implements RoleDataSourceService {

	@Autowired
	DataSource dataSource = null;

	@Autowired
    private Environment env;
	
	@Override
	public Role getRole(Long id) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Role role = null;
		try {
			System.out.println("自动装配env : " + env.getProperty("jdbc.database.password"));
			
			conn = dataSource.getConnection();
			ps = conn.prepareStatement("select id, role_name, note from t_role where id = ?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				role = new Role();
				role.setId(rs.getLong("id"));
				role.setRoleName(rs.getString("role_name"));
				role.setNote(rs.getString("note"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/********** close database resources ************/
		}
		return role;
	}

}
