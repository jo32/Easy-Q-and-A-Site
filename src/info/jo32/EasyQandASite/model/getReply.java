package info.jo32.EasyQandASite.model;

import info.jo32.EasyQandASite.controller.Entity;
import info.jo32.EasyQandASite.controller.Reply;
import info.jo32.EasyQandASite.controller.Signal;
import info.jo32.EasyQandASite.controller.User;
import info.jo32.EasyQandASite.controller.UserView;
import info.jo32.EasyQandASite.persistence.EntityFactory;
import info.jo32.EasyQandASite.persistence.ReplyWrapper;
import info.jo32.EasyQandASite.persistence.UserWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class getReply
 */
public class getReply extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getReply() {
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
		String topicIdString = request.getParameter("topicId");
		HttpSession hs = request.getSession();
		User user = (User) hs.getAttribute("user");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		Gson gson = new Gson();
		if (topicIdString != null && user != null) {
			try {
				long topicId = Long.parseLong(topicIdString);
				List<UserView> userList = new ArrayList<UserView>();
				UserView author = new UserView();
				author.setName(user.getName());
				author.setEmail(user.getEmail());
				author.setRole(user.getRight());
				userList.add(author);
				EntityFactory ef = new EntityFactory();
				List<Entity> replyList = ef.select("topicId =" + topicId + " Order by replytime desc", new ReplyWrapper(new Reply()));
				for (int i = 0; i < replyList.size(); i++) {
					Reply reply = (Reply) replyList.get(i);
					List<Entity> rs = ef.select("userId = " + reply.getUserId(), new UserWrapper(user));
					User replyAuthor = (User) rs.get(0);
					UserView uv = new UserView();
					uv.setName(replyAuthor.getName());
					uv.setEmail(replyAuthor.getEmail());
					uv.setRole(replyAuthor.getRight());
					userList.add(uv);
				}
				String json = gson.toJson(new Signal(1, userList, replyList));
				pw.write(json);
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
