package info.jo32.EasyQandASite.controller;

import java.util.Date;

@SuppressWarnings("serial")
public class Log extends Entity {

	private long logId;

	private long userId;

	private String userName;

	private String actionInfo;

	private Date actiontTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getActionInfo() {
		return actionInfo;
	}

	public void setActionInfo(String action) {
		this.actionInfo = action;
	}

	public Date getActionTime() {
		return actiontTime;
	}

	public void setActionTime(Date time) {
		this.actiontTime = time;
	}

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}
	
}
