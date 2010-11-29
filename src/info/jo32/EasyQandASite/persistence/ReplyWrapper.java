package info.jo32.EasyQandASite.persistence;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import info.jo32.EasyQandASite.controller.Entity;
import info.jo32.EasyQandASite.controller.Reply;

public class ReplyWrapper extends EntityWrapper {

	public ReplyWrapper(Entity e) {
		super(e);
	}

	@Override
	public String getInsertStatement() {
		Reply reply = (Reply) this.getEntity();
		long userId = reply.getUserId();
		long topicId = reply.getTopicId();
		long inreplyto = reply.getInreplyto();
		String content = reply.getContent();
		int rateCount = 0;
		String score = "NULL";
		EntityFactory ef;
		long replyId;
		String statement;
		try {
			ef = new EntityFactory();
			ResultSet rs = ef.executeQuery("SELECT MAX(replyId) FROM reply");
			rs.next();
			replyId = rs.getLong(1) + 1;
			statement = "INSERT INTO reply(replyId,userId,topicId,content,inreplyto,rateCount,score) VALUES ("
					+ replyId
					+ ","
					+ userId
					+ ","
					+ topicId
					+ ",'"
					+ content
					+ "'," + inreplyto + "," + rateCount + "," + score + ")";
			return statement;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getDeleteStatement() {
		Reply reply = (Reply) this.getEntity();
		String statement = "DELETE FROM reply WHERE replyId = " + reply.getId();
		return statement;
	}

	@Override
	public String getSelectStatement(String predicate) {
		String statement = "SELECT * FROM reply WHERE " + predicate;
		return statement;
	}

	@Override
	public String getModifyStatement() {
		Reply reply = (Reply) this.getEntity();
		StringBuffer sf = new StringBuffer();
		sf.append(this.getDeleteStatement() + ";");
		Timestamp ts = new Timestamp(reply.getDate().getTime());
		String statement = "INSERT INTO reply(replyId,userId,topicId,content,inreplyto,rateCount,score,replytime) VALUES ("
				+ reply.getId()
				+ ","
				+ reply.getUserId()
				+ ","
				+ reply.getTopicId()
				+ ",'"
				+ reply.getContent()
				+ "',"
				+ reply.getInreplyto()
				+ ","
				+ reply.getRateCount()
				+ ","
				+ reply.getScore() + ",'" + ts.toString() + "')";
		sf.append(statement);
		return sf.toString();
	}

	@Override
	public List<Entity> getEntityFromResultSet(ResultSet rs) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		try {
			while (rs.next()) {
				Reply reply = new Reply();
				reply.setId(rs.getLong(1));
				reply.setUserId(rs.getLong(2));
				reply.setTopicId(rs.getLong(3));
				reply.setContent(rs.getString(4));
				reply.setInreplyto(rs.getLong(5));
				reply.setRateCount(rs.getInt(6));
				reply.setScore(rs.getDouble(7));
				reply.setDate(rs.getTimestamp(8));
				list.add(reply);
			}
		} catch (Exception e) {
			return null;
		}
		return list;
	}
}
