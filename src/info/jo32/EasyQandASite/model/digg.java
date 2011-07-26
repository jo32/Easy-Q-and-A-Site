package info.jo32.EasyQandASite.model;

import info.jo32.EasyQandASite.controller.Entity;
import info.jo32.EasyQandASite.controller.Signal;
import info.jo32.EasyQandASite.controller.Topic;
import info.jo32.EasyQandASite.persistence.EntityFactory;
import info.jo32.EasyQandASite.persistence.wrapper.TopicWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class digg
 */
public class digg extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public digg() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		@SuppressWarnings("unchecked")
		Set<Long> digged = (Set<Long>) hs.getAttribute("digged");
		String topicIdStr = request.getParameter("topicId");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		Gson gson = new Gson();
		if (topicIdStr != null) {
			try {
				long topicId = Long.parseLong(topicIdStr);
				if (!digged.contains(topicId)) {
					Connection conn = (Connection) request.getSession().getAttribute("conn");
					EntityFactory ef = new EntityFactory(conn);
					String predicate = "topicId = %d";
					List<Entity> topicList = ef.select(String.format(predicate, topicId), new TopicWrapper(new Topic()));
					if (topicList.size() > 0) {
						Topic topic = (Topic) topicList.get(0);
						topic.setDiggCount(topic.getDiggCount() + 1);
						ef.modify(new TopicWrapper(topic));
						digged.add(topicId);
						hs.setAttribute("digged", digged);
						pw.write(gson.toJson(new Signal(1, "digged!")));
					} else {
						pw.write(gson.toJson(new Signal(0, "there isn't this topic!")));
					}
				} else {
					pw.write(gson.toJson(new Signal(0, "digged!")));
				}
			} catch (Exception e) {
				e.printStackTrace();
				pw.write(gson.toJson(new Signal(0, "Exception!")));
			}
		} else {
			pw.write(gson.toJson(new Signal(0, "lack of parameter!")));
		}
	}
}
