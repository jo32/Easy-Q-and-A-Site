package info.jo32.EasyQandASite.model;

import info.jo32.EasyQandASite.controller.Signal;
import info.jo32.EasyQandASite.controller.Topic;
import info.jo32.EasyQandASite.controller.User;
import info.jo32.EasyQandASite.logging.Logger;
import info.jo32.EasyQandASite.persistence.EntityFactory;
import info.jo32.EasyQandASite.persistence.wrapper.TopicWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class newTopic
 */
public class newTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public newTopic() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		Gson gson = new Gson();
		String json = gson.toJson(new Signal(0, "no get method in this servlet!"));
		pw.write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		request.setCharacterEncoding("UTF-8");
		User user = (User) hs.getAttribute("user");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		PrintWriter pw = response.getWriter();
		Gson gson = new Gson();
		if (user != null && title != null && content != null) {
			try {
				Connection conn = (Connection) request.getSession().getAttribute("conn");
				EntityFactory ef = new EntityFactory(conn);
				Topic topic = new Topic();
				topic.setUserId(user.getId());
				topic.setTitle(title);
				topic.setContent(content);
				TopicWrapper tw = new TopicWrapper(topic);
				ef.insert(tw);
				Logger logger = new Logger(ef);
				logger.log(user, "new topic");
				String json = gson.toJson(new Signal(1, "A new topic is created!"));
				pw.write(json);
			} catch (Exception ex) {
				String json = gson.toJson(new Signal(0, "Error in creating topic."));
				pw.write(json);
			}
		} else {
			String json = gson.toJson(new Signal(0, "lack of parameter!"));
			pw.write(json);
		}
	}
}
