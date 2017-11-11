package ua.nure.kn156.khorshunova.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn156.khorshunova.User;
import ua.nure.kn156.khorshunova.db.DataBaseException;
import ua.nure.kn156.khorshunova.util.Messages;

public class DetailsPanel extends JPanel implements ActionListener{

	protected MainFrame parent;
	private JPanel fieldPanel;
	private JPanel buttonPanel;
	private JTextField getFullNameField;
	private JTextField getAgeField;
	private JButton okButton;
	
	public DetailsPanel(MainFrame parent) {
		this.parent = parent;
		initialize();
	}

	private void initialize() {
		this.setName("detailsPanel"); //$NON-NLS-1$
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel1(), BorderLayout.NORTH);
		this.add(getButtonPanel1(), BorderLayout.SOUTH);
	}
    
	private JPanel getFieldPanel1(){
		if(fieldPanel == null){
			fieldPanel = new JPanel();
			fieldPanel.setLayout(new GridLayout(3,2));
			addLabeledField(fieldPanel, Messages.getString("DetailsPanel.full_name"), getFullNameField());  //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("DetailsPanel.age"), getAgeField());  //$NON-NLS-1$
		}
		return fieldPanel;
	}
	
	protected JTextField getFullNameField() {
		if(getFullNameField == null){
			getFullNameField = new JTextField();
			getFullNameField.setName("fullNameField");  //$NON-NLS-1$
		}
		return getFullNameField;
	}
	
	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
		JLabel label = new JLabel(labelText);
		label.setLabelFor(textField);
		panel.add(label);
		panel.add(textField);
	}

	protected JTextField getAgeField() {
		if(getAgeField == null){
			getAgeField = new JTextField();
			getAgeField.setName("ageField");  //$NON-NLS-1$
		}
		return getAgeField;
	}
	
	private JPanel getButtonPanel1(){
		if(buttonPanel == null){
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton(), null);
		}
		return buttonPanel;
	}

	private JButton getOkButton() {
		if (okButton == null){
			okButton = new JButton();
			okButton.setText(Messages.getString("DetailsPanel.ok"));   //$NON-NLS-1$
			okButton.setName("okButton"); //$NON-NLS-1$
			okButton.setActionCommand("ok");  //$NON-NLS-1$
			okButton.addActionListener(this);
		}
		return okButton;
	}
	
	public void setUser(User user){
        getFullNameField().setText(user.getFullName());
        getAgeField().setText(String.valueOf(user.getAge()));
	}
	
	public void actionPerformed(ActionEvent e) {
		if("ok".equalsIgnoreCase(e.getActionCommand())){  //$NON-NLS-1$
		this.setVisible(false);
		parent.showBrowsePanel();
		}
	}
}
