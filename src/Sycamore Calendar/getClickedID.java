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


@WebServlet("/getClickedID")
public class getClickedID extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userClicked = session.getAttribute("profileClicked").toString();
		String following = "";
		String email = session.getAttribute("email").toString();
		String data = "";
		
		//test
		System.out.println("!!!");
		System.out.println(userClicked);
		System.out.println("!!!");
		
		Connection conn = null;
		Statement st = null;
		Statement st1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Data?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			st1 = conn.createStatement();
			rs = st.executeQuery("SELECT * from Users where URL='" + userClicked + "'");
			rs1 = st1.executeQuery("SELECT * from Users where email='" + email + "'");
			while (rs1.next())
			{
				following = rs1.getString("listOfFollowing");
			}
			while (rs.next()) 
			{
				System.out.println("match");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String URL = rs.getString("URL");
				String buttonText = "Follow";
				String events = rs.getString("listOfEvents");
				String eventData = rs.getString("eventData");
				
				//test
				System.out.println("The current user's following is: ");
				System.out.println(following);
				System.out.println("The user clicked is:");
				System.out.println(URL);
				
				if (following.contains(URL))
				{
					buttonText = "Unfollow";
				}
				
				data += fname + "," + lname + "," + URL + "," + buttonText + "!!!" + events + "&&&" + eventData;
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
		writer.append(data);
		writer.flush();
	}
}
