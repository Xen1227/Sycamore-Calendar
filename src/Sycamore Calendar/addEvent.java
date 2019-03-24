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


@WebServlet("/addEvent")
public class addEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String email = session.getAttribute("email").toString();
		String newData = request.getParameter("hiddenCreateEvent").toString();
		String ID = request.getParameter("hiddenEventID").toString();
		
		//test
		System.out.println("!!!");
		System.out.println(newData);
		System.out.println("!!!");
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Data?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * from Users where email='" + email + "'");
			while (rs.next()) 
			{
				String eventData = rs.getString("eventData");
				String listOfEventIDs = rs.getString("listOfEvents");
				eventData += newData;
				listOfEventIDs += ID;
				
				ps = conn.prepareStatement("UPDATE Users SET eventData = ? WHERE email = ?");
				ps.setString(1, eventData);
				ps.setString(2, email);
				ps.executeUpdate();
				
				ps = conn.prepareStatement("UPDATE Users SET listOfEvents = ? WHERE email = ?");
				ps.setString(1, listOfEventIDs);
				ps.setString(2, email);
				ps.executeUpdate();
			}
			
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
		writer.append(newData);
		writer.flush();
	}
}
