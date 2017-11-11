package ua.nure.kn156.khorshunova.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ua.nure.kn156.khorshunova.User;
import ua.nure.kn156.khorshunova.db.DaoFactory;
import ua.nure.kn156.khorshunova.db.UserDAO;
import ua.nure.kn156.khorshunova.util.Messages;

public class MainFrame extends JFrame {
	/**
	 * constants for size of frame
	 */
	private static final int FRAME_HEIGHT = 600;
	private static final int FRAME_WIDTH = 800;
	private JPanel contentPanel;
	private JPanel browsePanel;
	private AddPanel addPanel;
	private EditPanel editPanel;
	private UserDAO dao;
	private DetailsPanel detailsPanel;

	public MainFrame(){
		super();
		dao = DaoFactory.getInstance().getUserDao();
		initialize();
	}
	
	public UserDAO getDao(){
		return dao;
	}
	
	private void initialize(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle(Messages.getString("MainFrame.user_management")); //$NON-NLS-1$
		this.setContentPane(getContentPanel());
	}
	
	private JPanel getContentPanel() {
		if(contentPanel == null){
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(getBrowsePanel(),BorderLayout.CENTER);
		}
		return contentPanel;
	}

	private void showPanel(JPanel panel) {
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
	}
	
	
	private JPanel getBrowsePanel() {
		if(browsePanel == null){
			browsePanel =new BrowsePanel(this);
		}
		((BrowsePanel)browsePanel).initTable();
		return browsePanel;
	}
	
	public void showBrowsePanel() {
		showPanel(getBrowsePanel());	
	}
	
	private AddPanel getAddPanel() {
		if(addPanel == null){
			addPanel = new AddPanel(this);
		}
		return addPanel;
	}

	public void showAddPanel() {
		showPanel(getAddPanel());
	}
	
	private EditPanel getEditPanel(){
		if(editPanel == null){
			editPanel = new EditPanel(this);
		}
		return editPanel;
	}
	
	public void showEditPanel(User user){
		getEditPanel().setUser(user);
		showPanel(getEditPanel());
	}
	
	private DetailsPanel getDetailsPanel(){
		if(detailsPanel == null){
			detailsPanel = new DetailsPanel(this);
		}
		return detailsPanel;
	}
	
	public void showDetailsPanel(User user){
		getDetailsPanel().setUser(user);
		showPanel(getDetailsPanel());
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

}
