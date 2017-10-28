package ua.nure.kn156.khorshunova.db;

import java.sql.SQLException;

public class DataBaseException extends Exception {

		public DataBaseException(Exception e) {
		super(e);
	}

	public DataBaseException(String string) {
	super(string);
	}

}
