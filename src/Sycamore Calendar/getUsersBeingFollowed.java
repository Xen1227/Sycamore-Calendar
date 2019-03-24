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


@WebServlet("/getUsersBeingFollowed")
public class getUsersBeingFollowed extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Statement st1 = null;
		ResultSet rs1 = null;
		String results = "";
		
		HttpSession session = request.getSession();
		String userEmail = session.getAttribute("email").toString();
		String usersFollowed = "";
		
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Data?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * from Users");
			st1 = conn.createStatement();
			rs1 = st1.executeQuery("SELECT * from Users where email='" + userEmail + "'");
			while(rs1.next())
			{
				usersFollowed = rs1.getString("listOfFollowing");
			}
			
			String[] users = usersFollowed.split(",", 100);
			while (rs.next()) 
			{
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String URL = rs.getString("URL");
				
				for (int i = 0; i < users.length; i++)
				{
					if (users[i].equals(URL))
					{
						results += fname + "," + lname + "," + URL + "!!!";
					}
					
				}
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
		writer.append(results);
		writer.flush();
		
	}
}