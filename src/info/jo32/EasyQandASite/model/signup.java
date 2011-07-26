package info.jo32.EasyQandASite.model;

import info.jo32.EasyQandASite.controller.Signal;
import info.jo32.EasyQandASite.controller.User;
import info.jo32.EasyQandASite.persistence.EntityFactory;
import info.jo32.EasyQandASite.persistence.wrapper.UserWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class signup
 */
public class signup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public signup() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		Gson gson = new Gson();
		String json = gson.toJson(new Signal(0,
				"no get method in this servlet!"));
		pw.write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		Gson gson = new Gson();
		if (name != null && email != null && password != null) {
			try {
				Connection conn = (Connection) request.getSession()
						.getAttribute("conn");
				EntityFactory ef = new EntityFactory(conn);
				User user = new User();
				user.setName(name);
				user.setEmail(email);
				user.setPassword(password);
				user.setRight("user");
				UserWrapper uw = new UserWrapper(user);
				ef.insert(uw);
				String json = gson.toJson(new Signal(1, "successful!"));
				pw.write(json);
			} catch (Exception ex) {
				String json = gson.toJson(new Signal(0, ex.getMessage()));
				pw.write(json);
			}
		} else {
			String json = gson.toJson(new Signal(0, "lack of parameter!"));
			pw.write(json);
		}
	}
}
