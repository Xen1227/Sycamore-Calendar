package xnegron_CSCI201_Assignment2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/retrieveData")
public class retrieveData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Object name = session.getAttribute("name");
		Object imageURL = session.getAttribute("imageURL");
		Object email = session.getAttribute("email");
		Object events = session.getAttribute("events");
		Object searchResults = session.getAttribute("searchResults");
		Object following = session.getAttribute("following");
		Object profileClicked = session.getAttribute("profileClicked");
		Object eventData = session.getAttribute("eventData");

		String output = name.toString() + "," + imageURL.toString() + "," + email.toString() + "&&&" + events.toString() + "%%%" + profileClicked.toString() + "###" + following.toString() + "???" + searchResults.toString() + "!!!" + eventData.toString();
		
		PrintWriter writer = response.getWriter();
		writer.append(output);
		writer.flush();
		
	}
}