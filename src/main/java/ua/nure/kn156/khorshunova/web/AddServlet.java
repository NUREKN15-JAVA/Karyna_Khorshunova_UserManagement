package ua.nure.kn156.khorshunova.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn156.khorshunova.User;
import ua.nure.kn156.khorshunova.db.DaoFactory;
import ua.nure.kn156.khorshunova.db.DataBaseException;

public class AddServlet extends EditServlet {


	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/add.jsp").forward(req, resp);;
	}

	protected void processUser(User user) throws DataBaseException {
		DaoFactory.getInstance().getUserDao().create(user);
	}

}
