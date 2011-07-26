package info.jo32.EasyQandASite.persistence.wrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import info.jo32.EasyQandASite.controller.Entity;
import info.jo32.EasyQandASite.controller.Topic;
import info.jo32.EasyQandASite.persistence.EntityFactory;

public class TopicWrapper extends EntityWrapper {

	private static String INSERT_STATEMENT = "INSERT INTO topic(topicId,userId,title,content) VALUES (%d,%d,'%s','%s')";
	private static String MODIFY_STATEMENT = "UPDATE topic SET userID = %d, title = '%s', content = '%s', replyCount = %d, diggCount = %d, topictime = '%s' WHERE topicId = %d";

	public TopicWrapper(Entity e) {
		super(e);
	}

	@Override
	public String getInsertStatement() {
		Topic topic = (Topic) this.getEntity();
		long userId = topic.getUserId();
		String title = topic.getTitle();
		String content = topic.getContent();
		EntityFactory ef;
		Long topicId;
		String statement;
		try {
			Connection conn = DriverManager.getConnection("proxool.mysql");
			ef = new EntityFactory(conn);
			ResultSet rs = ef.executeQuery("SELECT MAX(topicId) FROM topic");
			rs.next();
			topicId = rs.getLong(1) + 1;
			statement = String.format(TopicWrapper.INSERT_STATEMENT, topicId, userId, title, content);
			conn.close();
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
		String statement = String.format(MODIFY_STATEMENT, topic.getUserId(), topic.getTitle(), topic.getContent(), topic.getReplyCount(), topic.getDiggCount(), ts.toString(), topic.getId());
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
				topic.setDiggCount(rs.getInt(6));
				topic.setDate(rs.getTimestamp(7));
				list.add(topic);
			}
		} catch (Exception e) {
			return null;
		}
		return list;
	}
}
