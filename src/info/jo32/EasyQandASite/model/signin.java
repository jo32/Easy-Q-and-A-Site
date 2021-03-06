package info.jo32.EasyQandASite.model;

import info.jo32.EasyQandASite.controller.Entity;
import info.jo32.EasyQandASite.controller.Signal;
import info.jo32.EasyQandASite.controller.User;
import info.jo32.EasyQandASite.persistence.EntityFactory;
import info.jo32.EasyQandASite.persistence.wrapper.UserWrapper;

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
 * Servlet implementation class signin
 */
public class signin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public signin() {
		super();
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
		String password = request.getParameter("password");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		Gson gson = new Gson();
		if (name != null && password != null) {
			try {
				Connection conn = (Connection) request.getSession()
						.getAttribute("conn");
				EntityFactory ef = new EntityFactory(conn);
				User user = new User();
				UserWrapper uw = new UserWrapper(user);
				List<Entity> list = ef.select("name = " + "'" + name + "'", uw);
				user = (User) list.get(0);
				String realPassword = user.getPassword();
				if (realPassword.equals(password)) {
					HttpSession hs = request.getSession();
					hs.setAttribute("user", user);
					String json = gson.toJson(new Signal(1, "signed in!"));
					pw.write(json);
				} else {
					String json = gson.toJson(new Signal(0, "Wrong Password!"));
					pw.write(json);
				}
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
