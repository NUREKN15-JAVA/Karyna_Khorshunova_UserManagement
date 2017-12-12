package ua.nure.kn156.khorshunova.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ua.nure.kn156.khorshunova.User;
import ua.nure.kn156.khorshunova.util.Messages;

public class UserTableModel extends AbstractTableModel {

	private List users = null;
	private static final String[] COLUMN_NAMES = {Messages.getString("UserTableModel.id"), Messages.getString("UserTableModel.first_name"), Messages.getString("UserTableModel.last_name")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private static final Class[] COLUMN_CLASSES = {Long.class, String.class, String.class};	
	public UserTableModel(Collection users){
		this.users = new ArrayList(users);
	}
	
	@Override
	public int getColumnCount() {
		return  COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return users.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = (User) users.get(rowIndex);
		switch(columnIndex){
		case 0:
			return user.getId();
		case 1:
			return user.getFirstName();
		case 2:
			return user.getLastName();
		}
		return null;
	}

	@Override
	public Class getColumnClass(int columnIndex) {
		return COLUMN_CLASSES[columnIndex];
	}

	public User getUser(int row) {
		User user = (User)users.get(row);
        return user;
    }
	
	@Override
	public String getColumnName(int column) {
	
		return COLUMN_NAMES[column];
	}
	
	public void addUsers(Collection users) {
		this.users.addAll(users);
	}
	
	public void clearUsers() {
		this.users = new ArrayList();
	}
	

}
