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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class getEditableTopic
 */
public class getEditableTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getEditableTopic() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		Gson gson = new Gson();
		String id = (String) hs.getAttribute("editableTopicId");
		if (id != null) {
			Connection conn = (Connection) request.getSession().getAttribute("conn");
			EntityFactory ef;
			try {
				ef = new EntityFactory(conn);
				String predicate = "topicId = %s";
				List<Entity> topicList = ef.select(String.format(predicate, id), new TopicWrapper(new Topic()));
				Topic topic = (Topic) topicList.get(0);
				String json = gson.toJson(new Signal(1, topic));
				pw.write(json);
			} catch (Exception e) {
				String json = gson.toJson(new Signal(0, "Error!"));
				pw.write(json);
			}
		} else {
			String json = gson.toJson(new Signal(0, "no permission!"));
			pw.write(json);
		}
	}
}
