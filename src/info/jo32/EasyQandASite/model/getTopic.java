package info.jo32.EasyQandASite.model;

import info.jo32.EasyQandASite.controller.Entity;
import info.jo32.EasyQandASite.controller.Signal;
import info.jo32.EasyQandASite.controller.Topic;
import info.jo32.EasyQandASite.controller.User;
import info.jo32.EasyQandASite.persistence.EntityFactory;
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
 * Servlet implementation class getTopic
 */
public class getTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getTopic() {
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
		String range = request.getParameter("range");
		String pageString = request.getParameter("page");
		int page = Integer.parseInt(pageString);
		HttpSession hs = request.getSession();
		User user = (User) hs.getAttribute("user");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		Gson gson = new Gson();
		if (range != null) {
			try {
				EntityFactory ef = new EntityFactory();
				List<Entity> list = null;
				if (range.equals("all")) {
					list = ef.select("* Order by topictime desc limit " + 10 * page + ",10", new TopicWrapper(new Topic()));
				} else if (range.equals("user")) {
					list = ef.select("userId = " + user.getId() + " Order by topictime desc limit " + 10 * page + ",10", new TopicWrapper(new Topic()));
				}
				if (list != null) {
					String json = gson.toJson(new Signal(1, list));
					pw.write(json);
				} else {
					String json = gson.toJson(new Signal(0, "failed!"));
					pw.write(json);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				String json = gson.toJson(new Signal(0, ex.getMessage()));
				pw.write(json);
			}
		} else {
			String json = gson.toJson(new Signal(0, "lack of parameter!"));
			pw.write(json);
		}
	}
}
