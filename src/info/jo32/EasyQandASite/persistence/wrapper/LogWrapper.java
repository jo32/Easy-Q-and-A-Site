package info.jo32.EasyQandASite.persistence.wrapper;

import info.jo32.EasyQandASite.controller.Entity;
import info.jo32.EasyQandASite.controller.Log;
import info.jo32.EasyQandASite.persistence.EntityFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LogWrapper extends EntityWrapper {

	private static String INSERT_STATEMENT = "INSERT INTO logs(logId,userId,userName,actionInfo) values(%d,%d,'%s','%s')";
	private static String SELECT_STATEMENT = "SELECT * FROM logs ";

	public LogWrapper(Entity e) {
		super(e);
	}

	@Override
	public String getInsertStatement() {
		Log log = (Log) this.getEntity();
		try {
			Connection conn = DriverManager.getConnection("proxool.mysql");
			EntityFactory ef = new EntityFactory(conn);
			ResultSet rs = ef.executeQuery("SELECT MAX(logId) FROM logs");
			rs.next();
			log.setLogId(rs.getLong(1) + 1);
			rs.close();
			conn.close();
			return String.format(INSERT_STATEMENT, log.getLogId(), log.getUserId(), log.getUserName(), log.getActionInfo());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getDeleteStatement() {
		return "";
	}

	@Override
	public String getSelectStatement(String predicate) {
		return SELECT_STATEMENT + predicate;
	}

	@Override
	public List<Entity> getEntityFromResultSet(ResultSet rs) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		try {
			while (rs.next()) {
				Log log = new Log();
				log.setLogId(rs.getLong(1));
				log.setUserId(rs.getLong(2));
				log.setUserName(rs.getString(3));
				log.setActionInfo(rs.getString(4));
				log.setActionTime(rs.getTimestamp(5));
				list.add(log);
			}
		} catch (Exception e) {
			return null;
		}
		return list;
	}

	@Override
	public String getModifyStatement() {
		return "";
	}

}
