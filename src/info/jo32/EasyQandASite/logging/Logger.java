package info.jo32.EasyQandASite.logging;

import java.sql.SQLException;

import info.jo32.EasyQandASite.controller.Log;
import info.jo32.EasyQandASite.controller.User;
import info.jo32.EasyQandASite.persistence.EntityFactory;
import info.jo32.EasyQandASite.persistence.wrapper.LogWrapper;

public class Logger {

	private EntityFactory ef = null;

	public Logger(EntityFactory ef) {
		this.ef = ef;
	}

	public void log(User user, String actionInfo) throws SQLException {
		long userId = user.getId();
		String userName = user.getName();
		Log log = new Log();
		log.setActionInfo(actionInfo);
		log.setUserId(userId);
		log.setUserName(userName);
		ef.insert(new LogWrapper(log));
	}

}
