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


@WebServlet("/processSearchResults")
public class processSearchResults extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchTerms = request.getParameter("hiddenSearch");
		int size = searchTerms.split(" ", 2).length;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String results = "";
		
		System.out.println("The search term is: ");
		System.out.println(searchTerms);
		
		HttpSession session = request.getSession();
		String userEmail = session.getAttribute("email").toString();
		
		System.out.println("The user email is: ");
		System.out.println(userEmail);
		
		
		if (size > 2)
		{
			results = "No Users Found";
		}
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Data?user=root&password=root&useSSL=false&AllowPublicKeyRetrieval=true");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * from Users");

			while (rs.next()) 
			{
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String URL = rs.getString("URL");
				String email = rs.getString("email");
				
				System.out.println("The current email is: ");
				System.out.println(email);
				
				if (searchTerms == "")
				{
					if (!userEmail.equals(email))
					{
						results += fname + "," + lname + "," + URL + "***";
					}				
				}
				else if (size == 1)
				{
					String filter1 = searchTerms.split(" ", 0)[0].toLowerCase();
					if ((fname.toLowerCase().indexOf(filter1) != -1) || (lname.toLowerCase().indexOf(filter1) != -1))
					{
						if (!userEmail.equals(email))
						{
							results += fname + "," + lname + "," + URL + "***";
						}
					}
				}
				else if (size == 2)
				{
					String filter1 = searchTerms.split(" ", 0)[0].toLowerCase();
					String filter2 = searchTerms.split(" ", 0)[1].toLowerCase();
					if ((fname.indexOf(filter1) != -1) || (lname.indexOf(filter1) != -1) || (fname.indexOf(filter2) != -1) || (lname.indexOf(filter2) != -1))
					{
						if (!userEmail.equals(email))
						{
							results += fname + "," + lname + "," + URL + "***";
						}
					}
				}
			}
			
			if (results == "")
			{
				results = "No Users Found";
			}
			System.out.println("The results are:");
			System.out.println(results);
			System.out.println("!!!");
			
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
		
		session.setAttribute("searchResults", results);
		PrintWriter writer = response.getWriter();
		writer.append(results);
		writer.flush();
		
	}
}