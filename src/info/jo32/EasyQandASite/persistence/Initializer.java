package info.jo32.EasyQandASite.persistence;

import info.jo32.EasyQandASite.controller.User;
import info.jo32.EasyQandASite.persistence.wrapper.UserWrapper;
import info.jo32.EasyQandASite.util.PropertyLoader;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Initializer
 */
public class Initializer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Initializer() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter pw = response.getWriter();

		String keyFromParameter = request.getParameter("initializingKey");

		if (keyFromParameter == null) {
			response.sendError(500, "No initializingKey exists!");
			throw new ServletException("No initializingKey parameter exists!");
		}

		String initializingKey = this.getServletContext().getInitParameter(
				"initializingKey");

		if (!initializingKey.equals(keyFromParameter)) {
			response.sendError(500, "initializingKey mismatched!");
			throw new ServletException("initializingKey mismatched!");
		} else {
			pw.write("initializingkey matched!<br>");
		}

		EntityFactory entityFactory = null;

		try {
			Connection conn = (Connection) request.getSession().getAttribute(
					"conn");
			entityFactory = new EntityFactory(conn);
			pw.write("connection established!<br>");
		} catch (ClassNotFoundException e) {
			response.sendError(500, "ClassNotFoundException");
			throw new ServletException("ClassNotFoundException");
		} catch (SQLException e) {
			response.sendError(500, "SQLException");
			throw new ServletException("SQLException");
		}

		String statement = PropertyLoader.getSQLstatement("initSQL.sql");

		try {
			entityFactory.execute(statement);
			pw.write("tables has been created!<br>");
		} catch (SQLException e) {
			response.sendError(500, "SQLException");
			throw new ServletException("SQLException");
		}

		User admin = new User();
		String adminName = this.getServletContext().getInitParameter(
				"adminName");
		String adminEmail = this.getServletContext().getInitParameter(
				"adminEmail");
		String adminPassword = this.getServletContext().getInitParameter(
				"adminPassword");
		admin.setName(adminName);
		admin.setEmail(adminEmail);
		admin.setPassword(adminPassword);
		admin.setRight("admin");

		UserWrapper adminWrapper = new UserWrapper(admin);

		try {
			entityFactory.insert(adminWrapper);
			pw.write("admin has been created!<br>");
		} catch (SQLException e) {
			response.sendError(500,
					"Error happened in inserting admin information.");
			throw new ServletException("SQLException");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
