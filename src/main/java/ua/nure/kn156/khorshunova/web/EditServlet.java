package ua.nure.kn156.khorshunova.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import ua.nure.kn156.khorshunova.User;
import ua.nure.kn156.khorshunova.db.DaoFactory;
import ua.nure.kn156.khorshunova.db.DataBaseException;

public class EditServlet extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		if(req.getParameter("okButton") != null) {
			doOk(req, resp);
		} else if(req.getParameter("cancelButton") != null) {
			doCancel(req, resp);
		} else {
			showPage(req, resp);
		}
	}

	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			req.getRequestDispatcher("/edit.jsp").forward(req, resp);
	}

	private void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.sendRedirect("/browse");
	}

	private void doOk(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
		User user = null;
		try {
			user = getUser(req);
		} catch (ValidationException e1) {
			req.setAttribute("error", e1.getMessage());
			showPage(req, resp);
			return;
		}
		try {
			processUser(user);
		} catch (DataBaseException e) {
			e.printStackTrace();
			new ServletException(e);
		}
		resp.sendRedirect("/browse");
		
	}
	
	protected void processUser(User user) throws DataBaseException {
		DaoFactory.getInstance().getUserDao().update(user);
		
	} 

	private User getUser(HttpServletRequest req) throws ValidationException{
		User user = new User();
		String idStr = req.getParameter("id");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String dateStr = req.getParameter("date");
		
		if(firstName == null) {
			throw new ValidationException("First name is empty");
		}
		if(lastName == null) {
			throw new ValidationException("Last name is empty");
		}
		if(dateStr == null) {
			throw new ValidationException("Date is empty");
		}
		
		if(idStr != null) {
			user.setId(new Long(idStr));
		}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		try {
			user.setDate(DateFormat.getDateInstance().parse(dateStr));
		} catch (ParseException e) {
			throw new ValidationException("Date format is incorecct");	
		}
		return user;
	}


}
