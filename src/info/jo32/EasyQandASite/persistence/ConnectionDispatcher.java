package info.jo32.EasyQandASite.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@SuppressWarnings("serial")
public class ConnectionDispatcher extends HttpServlet implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		try {
			Connection conn = DriverManager.getConnection("proxool.mysql");
			Set<Long> digged = new HashSet<Long>();
			session.setAttribute("digged", digged);
			session.setAttribute("conn", conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		Connection conn = (Connection) session.getAttribute("conn");
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
