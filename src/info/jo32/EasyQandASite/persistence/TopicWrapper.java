package info.jo32.EasyQandASite.persistence;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import info.jo32.EasyQandASite.controller.Entity;
import info.jo32.EasyQandASite.controller.Topic;

public class TopicWrapper extends EntityWrapper {

	public TopicWrapper(Entity e) {
		super(e);
	}

	@Override
	public String getInsertStatement() {
		Topic topic = (Topic) this.getEntity();
		long userId = topic.getUserId();
		String title = topic.getTitle();
		String content = topic.getContent();
		long replyCount = 0;
		EntityFactory ef;
		Long topicId;
		String statement;
		try {
			ef = new EntityFactory();
			ResultSet rs = ef.executeQuery("SELECT MAX(topicId) FROM topic");
			rs.next();
			topicId = rs.getLong(1) + 1;
			statement = "INSERT INTO topic(topicId,userId,title,content,replyCount) VALUES (" + topicId + "," + userId + ",'" + title + "','" + content + "'," + replyCount + ")";
			return statement;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getDeleteStatement() {
		Topic topic = (Topic) this.getEntity();
		String statement = "DELETE FROM Topic WHERE topicId = " + topic.getId();
		return statement;
	}

	@Override
	public String getSelectStatement(String predicate) {
		if (predicate.contains("*")) {
			predicate = predicate.replace("*", "");
			return "SELECT * FROM Topic " + predicate;
		}
		return "SELECT * FROM Topic WHERE " + predicate;
	}

	@Override
	public String getModifyStatement() {
		Topic topic = (Topic) this.getEntity();
		Timestamp ts = new Timestamp(topic.getDate().getTime());
		String statement = "UPDATE topic SET userId = " + topic.getUserId() + ", title='" + topic.getTitle() + "', content='" + topic.getContent() + "', replyCount = " + topic.getReplyCount()
				+ ",  topictime='" + ts.toString() + "' where topicId = " + topic.getId();
		return statement;
	}

	@Override
	public List<Entity> getEntityFromResultSet(ResultSet rs) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		try {
			while (rs.next()) {
				Topic topic = new Topic();
				topic.setId(rs.getLong(1));
				topic.setUserId(rs.getLong(2));
				topic.setTitle(rs.getString(3));
				topic.setContent(rs.getString(4));
				topic.setReplyCount(rs.getInt(5));
				topic.setDate(rs.getTimestamp(6));
				list.add(topic);
			}
		} catch (Exception e) {
			return null;
		}
		return list;
	}
}
