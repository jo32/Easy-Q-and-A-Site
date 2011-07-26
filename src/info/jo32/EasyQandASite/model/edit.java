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
 * Servlet implementation class edit
 */
public class edit extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public edit() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		User user = (User) hs.getAttribute("user");
		String topicIdStr = (String) hs.getAttribute("editableTopicId");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		Gson gson = new Gson();

		if (topicIdStr != null && title != null && content != null) {
			try {
				Connection conn = (Connection) request.getSession().getAttribute("conn");
				EntityFactory ef = new EntityFactory(conn);
				String predicate = "topicId = %s";
				List<Entity> topicls = ef.select(String.format(predicate, topicIdStr), new TopicWrapper(new Topic()));
				Topic topic = (Topic) topicls.get(0);
				topic.setContent(content);
				topic.setTitle(title);
				ef.modify(new TopicWrapper(topic));
				Logger logger = new Logger(ef);
				logger.log(user, "edit");
				String json = gson.toJson(new Signal(1, "OK!"));
				pw.write(json);
			} catch (Exception e) {
				e.printStackTrace();
				String json = gson.toJson(new Signal(0, "Error!"));
				pw.write(json);
			}
		} else {
			String json = gson.toJson(new Signal(0, "lack of parameter!"));
			pw.write(json);
		}
	}
}
