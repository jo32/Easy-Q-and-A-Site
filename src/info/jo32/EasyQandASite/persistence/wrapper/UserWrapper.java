package info.jo32.EasyQandASite.persistence.wrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import info.jo32.EasyQandASite.controller.Entity;
import info.jo32.EasyQandASite.controller.User;
import info.jo32.EasyQandASite.persistence.EntityFactory;

public class UserWrapper extends EntityWrapper {

	public UserWrapper(Entity e) {
		super(e);
	}

	@Override
	public String getInsertStatement() {
		User user = (User) this.getEntity();
		String name = user.getName();
		String email = user.getEmail();
		String password = user.getPassword();
		String right = user.getRight();
		EntityFactory ef;
		Long userId;
		String statement;
		try {
			Connection conn = DriverManager.getConnection("proxool.mysql");
			ef = new EntityFactory(conn);
			ResultSet rs = ef.executeQuery("select max(userId) from User");
			rs.next();
			userId = rs.getLong(1) + 1;
			statement = "INSERT INTO User (userId,name,email,password,role) VALUES ("
					+ userId.toString()
					+ ",'"
					+ name
					+ "','"
					+ email
					+ "','"
					+ password + "','" + right + "')";
			conn.close();
			return statement;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public String getDeleteStatement() {
		User user = (User) this.getEntity();
		String statement = "DELETE FROM User WHERE userid = " + user.getId();
		return statement;
	}

	@Override
	public String getSelectStatement(String predicate) {
		String statement = "SELECT * FROM User WHERE " + predicate;
		return statement;
	}

	@Override
	public String getModifyStatement() {
		User user = (User) this.getEntity();
		StringBuffer sf = new StringBuffer();
		sf.append(this.getDeleteStatement() + ";");
		String insertStatement = "INSERT INTO User (userid,name,email,password,role) VALUES ("
				+ user.getId()
				+ ",'"
				+ user.getName()
				+ "','"
				+ user.getEmail()
				+ "','"
				+ user.getPassword()
				+ "','"
				+ user.getRight() + "')";
		sf.append(insertStatement);
		return sf.toString();
	}

	@Override
	public List<Entity> getEntityFromResultSet(ResultSet rs) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		try {
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getLong(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setPassword(rs.getString(4));
				user.setRight(rs.getString(5));
				list.add(user);
			}
		} catch (SQLException e) {
			return null;
		}
		return list;
	}
}
