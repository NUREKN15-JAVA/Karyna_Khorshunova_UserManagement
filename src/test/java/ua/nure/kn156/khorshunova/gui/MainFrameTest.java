package ua.nure.kn156.khorshunova.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.ComponentFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn156.khorshunova.User;
import ua.nure.kn156.khorshunova.db.DaoFactory;
import ua.nure.kn156.khorshunova.db.DaoFactoryImp;
import ua.nure.kn156.khorshunova.db.MockDaoFactory;
import ua.nure.kn156.khorshunova.db.MockUserDao;
import ua.nure.kn156.khorshunova.util.Messages;

public class MainFrameTest extends JFCTestCase {

	private List<User> users;
	private MainFrame mainFrame;
	private Mock mockUserDao;

	protected void setUp() throws Exception {
		super.setUp();
		try{
		  Properties properties = new Properties();
		  properties.setProperty("dao.factory", MockDaoFactory.class.getName());
		  DaoFactory.getInstance().init(properties );
		  mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
		  User expectedUser = new User(1000L, "Карина", "Хоршунова", new Date());
		  users = Collections.singletonList(expectedUser);
          mockUserDao.expectAndReturn("findAll", users);
		  setHelper(new JFCTestHelper());
		  mainFrame=new MainFrame();
		}catch( Exception e){
		  e.printStackTrace();
		}
		mainFrame.setVisible(true);
	}

	protected void tearDown() throws Exception {
		try{
		   mockUserDao.verify();
		   mainFrame.setVisible(false);
      	   getHelper().cleanUp(this);
		   super.tearDown();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private Component find(Class componentClass, String name){
		NamedComponentFinder finder=new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(mainFrame,0);
		assertNotNull("Could not find component" + name +",", component);
		return component;
	}
    
	public void testBrowseControls(){
	     find(JPanel.class, "browsePanel"); 
		 find(JButton.class, "addButton");
		 find(JButton.class, "editButton");
		 find(JButton.class, "deleteButton");
		 find(JButton.class, "detailsButton");
		 JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(3, table.getColumnCount());
			assertEquals(Messages.getString("UserTableModel.id"),table.getColumnName(0));
			assertEquals(Messages.getString("UserTableModel.first_name"),table.getColumnName(1));
			assertEquals(Messages.getString("UserTableModel.last_name"),table.getColumnName(2));
	}
	
	 public void testAddUser(){
		 try{
		 String firstName = "John";
		 String lastName = "Doe";
		 Date now = new Date();
		 User user = new User(firstName, lastName, now);
		 
		 User expectedUser = new User(new Long(1), firstName, lastName, now);
		 mockUserDao.expectAndReturn("create", user, expectedUser);
		 
		 List<User> users = new ArrayList<User>(this.users);
		 users.add(expectedUser);
		 mockUserDao.expectAndReturn("findAll", users);
		 
		 JTable table = (JTable) find(JTable.class, "userTable");
		 assertEquals(1, table.getRowCount());
		 
		 JButton addButton = (JButton) find(JButton.class, "addButton");
		 getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		 find(JPanel.class, "addPanel");
		 
		 JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		 JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		 JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		 
		 JButton okButton = (JButton) find(JButton.class, "okButton");
		 find(JButton.class, "cancelButton");
		 
		 getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		 getHelper().sendString(new StringEventData(this, lastNameField, lastName));
         DateFormat formatter = DateFormat.getDateInstance();
		 String date = formatter.format(now);
		 getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
		 getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
		 
		 find(JPanel.class, "browsePanel");
		 table = (JTable) find(JTable.class, "userTable");
		 assertEquals(2, table.getRowCount());
		 }catch(Exception e){
			 
		 }
	 }
	 
	 public void testCancelAddUser() {
	        mockUserDao.expectAndReturn("findAll", this.users);
	        find(JPanel.class, "browsePanel");
	        JTable userTable = (JTable) find(JTable.class, "userTable");
	        assertEquals(1, userTable.getRowCount());

	        JButton okButton = (JButton) find(JButton.class, "addButton");
	        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

	        find(JPanel.class, "addPanel");
	        JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
	        getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
	        find(JPanel.class, "browsePanel");

	        userTable = (JTable) find(JTable.class, "userTable");
	        assertEquals(1, userTable.getRowCount());
	    }

	 public void testDeleteUser() {
	        User expectedUser = new User(1000L, "Карина", "Хоршунова", new Date());
	        mockUserDao.expect("delete", expectedUser);
	        List<User> users = new ArrayList<>(this.users);
	        users.remove(this.users.get(0));
	        mockUserDao.expectAndReturn("findAll", users);

	        JTable userTable = (JTable) find(JTable.class, "userTable");
	        assertEquals(1, userTable.getRowCount());

	        JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
	        getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));
	        getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));

	        JDialog dialog = (JDialog) TestHelper.getShowingDialogs().get(0);
	        JButton okButton = (JButton) new ComponentFinder(JButton.class).find(dialog, 0);
	        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
	        assertEquals(0, userTable.getRowCount());
	    }

	 public void testCancelDeleteUser() {
	    	mockUserDao.expectAndReturn("findAll", users);
	        JTable userTable = (JTable) find(JTable.class, "userTable");
	        assertEquals(1, userTable.getRowCount());

	        JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
	        getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));
	        getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));

	        JDialog dialog = (JDialog) TestHelper.getShowingDialogs().get(0);
	        JButton cancelButton = (JButton) new ComponentFinder(JButton.class).find(dialog, 1);
	        getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
	        assertEquals(1, userTable.getRowCount());
	    }

	 public void testEditUser() {
	        User user = new User(this.users.get(0));
	        User expectedUser = new User(user);
	        expectedUser.setLastName(user.getLastName() + "2");

	        mockUserDao.expect("update", user);
	        List<User> users = new ArrayList<>();
	        users.add(expectedUser);
	        mockUserDao.expectAndReturn("findAll", users);

	        JTable userTable = (JTable) find(JTable.class, "userTable");
	        assertEquals(1, userTable.getRowCount());

	        getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));

	        JButton addButton = (JButton) find(JButton.class, "editButton");
	        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

	        find(JPanel.class, "editPanel");

	        JTextField lastNameTextField = (JTextField) find(JTextField.class, "lastNameField");
	        getHelper().sendString(new StringEventData(this, lastNameTextField, "2"));

	        JButton okButton = (JButton) find(JButton.class, "okButton");
	        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

	        find(JPanel.class, "browsePanel");
	        userTable = (JTable) find(JTable.class, "userTable");
	        assertEquals(1, userTable.getRowCount());
	    }

	 public void testCancelEditUser() {
	        mockUserDao.expectAndReturn("findAll", this.users);
	        find(JPanel.class, "browsePanel");
	        JTable userTable = (JTable) find(JTable.class, "userTable");
	        assertEquals(1, userTable.getRowCount());

	        getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));

	        JButton editButton = (JButton) find(JButton.class, "editButton");
	        getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

	        find(JPanel.class, "editPanel");
	        JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
	        getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
	        find(JPanel.class, "browsePanel");

	        userTable = (JTable) find(JTable.class, "userTable");
	        assertEquals(1, userTable.getRowCount());
	    }

	 public void testDetailsPanel() {
	        User expectedUser = this.users.get(0);
	        mockUserDao.expectAndReturn("findAll", this.users);

	        find(JPanel.class, "browsePanel");

	        JTable userTable = (JTable) find(JTable.class, "userTable");
	        getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));

	        JButton detailsButton = (JButton) find(JButton.class, "detailsButton");
	        getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));

	        find(JPanel.class, "detailsPanel");

	        JTextField fullNameFiled = (JTextField) find(JTextField.class, "fullNameField");
	        JTextField ageField = (JTextField) find(JTextField.class, "ageField");

	        assertEquals(fullNameFiled.getText(), expectedUser.getFullName());
	        assertEquals(ageField.getText(), String.valueOf(expectedUser.getAge()));

	        JButton okButton = (JButton) find(JButton.class, "okButton");
	        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

	        find(JPanel.class, "browsePanel");
	    }
}
