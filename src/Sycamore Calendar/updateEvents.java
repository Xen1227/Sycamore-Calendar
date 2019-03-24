package xnegron_CSCI201_Assignment2;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/updateEvents")
public class updateEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String results = "";
		
		HttpSession session = request.getSession();
		String events = request.getParameter("hiddenEvents");
		String eventData = request.getParameter("hiddenEvents1");
		session.setAttribute("events", events);
		session.setAttribute("eventData", eventData);
		String currentEmail = session.getAttribute("email").toString();
		System.out.println(currentEmail);
		System.out.println(events);
		System.out.println("!!!");
		PreparedStatement ps = null;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Data?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * from Users where email='" + currentEmail + "'");
			
			ps = conn.prepareStatement("UPDATE Users SET listOfEvents = ? WHERE email = ?");
			ps.setString(1, events);
			ps.setString(2, currentEmail);
			ps.executeUpdate();
			
			ps = conn.prepareStatement("UPDATE Users SET eventData = ? WHERE email = ?");
			ps.setString(1, eventData);
			ps.setString(2, currentEmail);
			ps.executeUpdate();
			
		}
		catch (SQLException sqle) 
		{
			System.out.println (sqle.getMessage());
		} 
		catch (ClassNotFoundException cnfe) 
		{
			System.out.println (cnfe.getMessage());
		} 
		finally 
		{
			try 
			{
				if (rs != null) 
				{ 
					rs.close(); 
				}
				if (st != null) 
				{ 
					st.close(); 
				}
				if (conn != null) 
				{ 
					conn.close(); 
				}
			} 
			catch (SQLException sqle) 
			{
				System.out.println(sqle.getMessage());
			}
		}
		
		PrintWriter writer = response.getWriter();
		writer.append(results);
		writer.flush();
		
	}
}