package info.jo32.EasyQandASite.persistence;

import info.jo32.EasyQandASite.controller.Entity;
import info.jo32.EasyQandASite.util.PropertyLoader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

// a class that used to make Entities persistent.
public class EntityFactory {

	private static final long serialVersionUID = 1L;

	private String driverClass;
	private String connectingString;
	private String databaseUserName;
	private String password;

	private Connection connection;
	private Statement statement;

	private void initialize() throws IOException, ClassNotFoundException, SQLException {

		// get the local properties for web.properties file
		this.driverClass = PropertyLoader.getProperty("driverClass");
		this.connectingString = PropertyLoader.getProperty("connectingString");
		this.databaseUserName = PropertyLoader.getProperty("databaseUserName");
		this.password = PropertyLoader.getProperty("password");

		// get the connection of the database
		Class.forName(this.driverClass);
		this.connection = DriverManager.getConnection(this.connectingString, this.databaseUserName, this.password);
		this.statement = this.connection.createStatement();

	}

	public EntityFactory() throws IOException, ClassNotFoundException, SQLException {
		this.initialize();
	}

	public boolean insert(EntityWrapper ew) throws SQLException {
		statement.executeUpdate("SET AUTOCOMMIT=0;");
		statement.executeUpdate("START TRANSACTION;");
		int result = this.statement.executeUpdate(ew.getInsertStatement());
		if (result <= 0) {
			this.statement.executeUpdate("ROLLBACK;");
			return false;
		} else {
			this.statement.executeUpdate("COMMIT;");
			return true;
		}
	}

	public boolean modify(EntityWrapper ew) throws SQLException {
		statement.executeUpdate("SET AUTOCOMMIT=0;");
		statement.executeUpdate("START TRANSACTION;");
		String statement = ew.getModifyStatement();
		int code = this.statement.executeUpdate(statement);
		if (code <= 0) {
			this.statement.executeUpdate("ROLLBACK;");
			return false;
		}
		this.statement.executeUpdate("COMMIT;");
		return true;
	}

	public List<Entity> select(String predicate, EntityWrapper ew) throws SQLException {
		ResultSet resultSet = this.statement.executeQuery(ew.getSelectStatement(predicate));
		return ew.getEntityFromResultSet(resultSet);

	}

	public boolean delete(EntityWrapper ew) throws SQLException {
		statement.executeUpdate("SET AUTOCOMMIT=0;");
		statement.executeUpdate("START TRANSACTION;");
		int result = this.statement.executeUpdate(ew.getDeleteStatement());
		if (result <= 0) {
			this.statement.executeUpdate("ROLLBACK;");
			return false;
		} else {
			this.statement.executeUpdate("COMMIT;");
			return true;
		}
	}

	public boolean execute(String sm) throws SQLException {
		statement.executeUpdate("SET AUTOCOMMIT=0;");
		statement.executeUpdate("START TRANSACTION;");
		String[] batch = sm.split(";");
		for (int i = 0; i < batch.length; i++) {
			this.statement.addBatch(batch[i]);
		}
		int[] result = this.statement.executeBatch();
		for (int i = 0; i < result.length; i++) {
			if (result[i] <= 0) {
				this.statement.executeUpdate("ROLLBACK;");
				return false;
			}
		}
		this.statement.executeUpdate("COMMIT;");
		return true;
	}

	public ResultSet executeQuery(String sm) throws SQLException {
		return this.statement.executeQuery(sm);
	}
}
