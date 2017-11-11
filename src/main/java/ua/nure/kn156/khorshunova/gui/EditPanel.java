package ua.nure.kn156.khorshunova.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ua.nure.kn156.khorshunova.User;
import ua.nure.kn156.khorshunova.db.DataBaseException;

public class EditPanel extends AddPanel {

	private User user;

	public EditPanel(MainFrame parent) {
		super(parent);
		setName("editPanel"); //$NON-NLS-1$
	}
	
	public void setUser( User user){
		this.user = user;
		DateFormat formatter = DateFormat.getDateInstance();
        getFirstNameField().setText(user.getFirstName());
        getLastNameField().setText(user.getLastName());
        getDateOfBirthField().setText(formatter.format(user.getDate()));
	}
	
	public void actionPerformed(ActionEvent e) {
		if("ok".equalsIgnoreCase(e.getActionCommand())){ //$NON-NLS-1$
			user.setFirstName(getFirstNameField().getText());
			user.setLastName(getLastNameField().getText());
			DateFormat format = DateFormat.getDateInstance();
			try {
				user.setDate(format.parse(getDateOfBirthField().getText()));
			} catch (ParseException e1) {
			    getDateOfBirthField().setBackground(Color.RED);
				return ;
			}
			try{
			    parent.getDao().update(user);
			}catch(DataBaseException e1){
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			}
		}
		this.setVisible(false);
		parent.showBrowsePanel();
		
	}

	


}
