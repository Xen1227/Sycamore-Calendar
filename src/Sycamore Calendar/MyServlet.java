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


@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String imageURL = request.getParameter("imageURL");
		String email = request.getParameter("email");
		String events = "";
		System.out.println(email);
		
		HttpSession session = request.getSession();
		session.setAttribute("name", name);
		session.setAttribute("imageURL", imageURL);
		session.setAttribute("email", email);
		session.setAttribute("searchResults", "");
		session.setAttribute("profileClicked", "");
		session.setAttribute("events", events);
		session.setAttribute("following", "");
		session.setAttribute("eventData", "");
		
		PrintWriter writer = response.getWriter();
		writer.append(name);
		writer.flush();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Data?user=root&password=root&useSSL=false&AllowPublicKeyRetrieval=true");
			st = conn.createStatement();
			PreparedStatement ps = null;
			rs = st.executeQuery("SELECT * from Users where email='" + email + "'");
			int count = 0;
			String listOfUsersFollowing = "";
			while (rs.next()) 
			{
				count++;
				if (rs.getString("email") == email)
				{
					listOfUsersFollowing = rs.getString("listOfFollowing");
				}
			}
			
			if (count == 0)
			{
				String fname = name.split(" ", 2)[0].toString();
				String lname = name.split(" ", 2)[1].toString();
				ps = conn.prepareStatement("INSERT INTO Users (email, fname, lname, URL, listOfFollowing, listOfEvents, eventData) VALUES ( ?, ?, ?, ?, ?, ?, ?)");
				ps.setString(1, email);
				ps.setString(2, fname); 
				ps.setString(3, lname);
				ps.setString(4, imageURL); 
				ps.setString(5, "");
				ps.setString(6, events); 
				ps.setString(7, "");
				ps.executeUpdate();
				
				//test
				System.out.println("Inserted into database");
			} 
			/*else
			{
				ps = conn.prepareStatement("UPDATE Users SET listOfEvents = ? WHERE email = ?");
				ps.setString(1, events);
				ps.setString(2, email);
				ps.executeQuery();
				
				//test
				System.out.println("Updated database");
			}*/
			System.out.println("set attribute");
			session.setAttribute("following", listOfUsersFollowing);
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
	}
}
