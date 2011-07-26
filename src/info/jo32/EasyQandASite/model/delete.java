package info.jo32.EasyQandASite.model;

import info.jo32.EasyQandASite.controller.Entity;
import info.jo32.EasyQandASite.controller.Signal;
import info.jo32.EasyQandASite.controller.Topic;
import info.jo32.EasyQandASite.controller.User;
import info.jo32.EasyQandASite.logging.Logger;
import info.jo32.EasyQandASite.persistence.EntityFactory;
import info.jo32.EasyQandASite.persistence.wrapper.TopicWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class delete
 */
public class delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public delete() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		User user = (User) hs.getAttribute("user");
		String topicIdStr = request.getParameter("topicId");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		Gson gson = new Gson();
		if (topicIdStr != null) {
			try {
				Connection conn = (Connection) request.getSession().getAttribute("conn");
				EntityFactory ef = new EntityFactory(conn);
				long topicId = Long.parseLong(topicIdStr);
				String predicate = "topicId = %d";
				List<Entity> topicList = ef.select(String.format(predicate, topicId), new TopicWrapper(new Topic()));
				if (topicList.size() > 0) {
					Topic topic = (Topic) topicList.get(0);
					if (topic.getUserId() == user.getId() || user.getRight().equals("admin")) {
						ef.delete(new TopicWrapper(topic));
						Logger logger = new Logger(ef);
						logger.log(user, "delete");
						pw.write(gson.toJson(new Signal(1, "deleted.")));
					} else {
						pw.write(gson.toJson(new Signal(0, "U aren't author or admin!")));
					}
				} else {
					pw.write(gson.toJson(new Signal(0, "there isn't this topic!")));
				}
			} catch (Exception e) {
				e.printStackTrace();
				String json = gson.toJson(new Signal(0, "error!"));
				pw.write(json);
			}
		} else {
			String json = gson.toJson(new Signal(0, "lack of parameter!"));
			pw.write(json);
		}
	}

}
