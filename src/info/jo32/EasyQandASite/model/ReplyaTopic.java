package info.jo32.EasyQandASite.model;

import info.jo32.EasyQandASite.controller.Entity;
import info.jo32.EasyQandASite.controller.Reply;
import info.jo32.EasyQandASite.controller.Signal;
import info.jo32.EasyQandASite.controller.Topic;
import info.jo32.EasyQandASite.controller.User;
import info.jo32.EasyQandASite.persistence.EntityFactory;
import info.jo32.EasyQandASite.persistence.ReplyWrapper;
import info.jo32.EasyQandASite.persistence.TopicWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class ReplyaTopic
 */
public class ReplyaTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReplyaTopic() {
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
		request.setCharacterEncoding("UTF-8");
		String topicIdString = request.getParameter("topicId");
		String content = request.getParameter("content");
		HttpSession hs = request.getSession();
		request.setCharacterEncoding("UTF-8");
		User user = (User) hs.getAttribute("user");
		long userId = user.getId();
		PrintWriter pw = response.getWriter();
		Gson gson = new Gson();
		if (user != null && topicIdString != null) {
			try {
				EntityFactory ef = new EntityFactory();
				Reply r = new Reply();
				r.setUserId(userId);
				r.setContent(content);
				r.setTopicId(Long.parseLong(topicIdString));
				ReplyWrapper rw = new ReplyWrapper(r);
				ef.insert(rw);
				List<Entity> tl = ef.select("topicId = " + topicIdString, new TopicWrapper(new Topic()));
				Topic t = (Topic) tl.get(0);
				t.setReplyCount(t.getReplyCount() + 1);
				ef.modify(new TopicWrapper(t));
				String json = gson.toJson(new Signal(1, "A new reply is created!"));
				pw.write(json);
			} catch (Exception ex) {
				String json = gson.toJson(new Signal(0, "Error in creating reply."));
				pw.write(json);
			}
		} else {
			String json = gson.toJson(new Signal(0, "lack of parameter!"));
			pw.write(json);
		}

	}
}
