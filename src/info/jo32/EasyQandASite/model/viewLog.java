package info.jo32.EasyQandASite.model;

import info.jo32.EasyQandASite.controller.Entity;
import info.jo32.EasyQandASite.controller.Log;
import info.jo32.EasyQandASite.persistence.EntityFactory;
import info.jo32.EasyQandASite.persistence.wrapper.LogWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class viewLog
 */
public class viewLog extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public viewLog() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageStr = request.getParameter("page");
		int page = Integer.parseInt(pageStr);
		String predicate = "Order by actionTime desc limit %d,20";
		Connection conn = (Connection) request.getSession().getAttribute("conn");
		PrintWriter pw = response.getWriter();
		try {
			EntityFactory ef = new EntityFactory(conn);
			List<Entity> ls = ef.select(String.format(predicate, page * 20), new LogWrapper(new Log()));
			pw.write("<table>\n");
			String row = "<tr><td>%d</td><td>%d</td><td>%s</td><td>%s</td><td>%s</td></tr>\n";
			pw.write("<tr><td>Log ID</td><td>User ID</td><td>User Name</td><td>Action Info</td><td>Action Time</td></tr>\n");
			for (int i = 0; i < ls.size(); i++) {
				Log log = (Log) ls.get(i);
				pw.write(String.format(row, log.getLogId(), log.getUserId(), log.getUserName(), log.getActionInfo(), log.getActionTime().toString()));
			}
			pw.write("</table>\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
