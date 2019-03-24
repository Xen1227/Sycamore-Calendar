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


@WebServlet("/updateFollowing")
public class updateFollowing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String email = session.getAttribute("email").toString();
		String action = request.getParameter("hiddenFollow").toString();
		String clickedID = session.getAttribute("profileClicked").toString();
		
		//test
		System.out.println("!!!");
		System.out.println(action);
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
				String following = rs.getString("listOfFollowing");
				if (action.equals("Follow"))
				{
					following += clickedID + ',';
				}
				else if (action.equals("Unfollow"))
				{
					System.out.println("The clicked ID is:");
					System.out.println(clickedID);
					following = following.replace(clickedID,"");
				}
				
				
				ps = conn.prepareStatement("UPDATE Users SET listOfFollowing = ? WHERE email = ?");
				ps.setString(1, following);
				ps.setString(2, email);
				ps.executeUpdate();
				session.setAttribute("following", following);
				System.out.println("The new following is:");
				System.out.println(following);
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
		writer.append(clickedID);
		writer.flush();
	}
}
