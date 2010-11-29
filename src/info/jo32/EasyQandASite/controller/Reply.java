package info.jo32.EasyQandASite.controller;

import java.util.Date;

@SuppressWarnings("serial")
public class Reply extends Entity {

	long id;

	String content;

	long inreplyto;

	int rateCount;

	double score;

	int floor;

	Date date;

	long topicId;

	long userId;

	public int getRateCount() {
		return rateCount;
	}

	public void setRateCount(int rateCount) {
		this.rateCount = rateCount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getInreplyto() {
		return inreplyto;
	}

	public void setInreplyto(long inreplyto) {
		this.inreplyto = inreplyto;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public long getTopicId() {
		return topicId;
	}

	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
