package xnegron_CSCI201_Assignment2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/setClickedID")
public class setClickedID extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clickedID = request.getParameter("hiddenID");
		System.out.println(clickedID);
		
		HttpSession session = request.getSession();
		session.setAttribute("profileClicked", clickedID);
		
		
		PrintWriter writer = response.getWriter();
		writer.append(clickedID);
		writer.flush();
		
		
	}
}
