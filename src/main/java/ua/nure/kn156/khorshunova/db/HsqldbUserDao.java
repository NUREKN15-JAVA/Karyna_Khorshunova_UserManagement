package ua.nure.kn156.khorshunova.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.kn156.khorshunova.User;

class HsqldbUserDao implements UserDAO {

	private static final String DELETE_USER = "DELETE FROM users WHERE id=?";
	private static final String UPDATE_USERS = "UPDATE users SET id=?, firstname=?, lastname=?, dateofBith=? WHERE id=?";
	private static final String SELECT_USERS = "SELECT * FROM users  WHERE id = ?";
	private static final String SELECT_FROM_USERS = "SELECT * FROM users";
	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofBith) VALUES (?, ?, ?)";
	private static final String SELECT_USERS_BY_NAME = "SELECT * FROM users  WHERE firstname = ? AND lastname=?";
	private ConnectionFactory connectionFactory;

	public  HsqldbUserDao(){
		
	}
	
	public  HsqldbUserDao(ConnectionFactory connectionFactory) {
	     this.connectionFactory=connectionFactory;
	}
	
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	
	
	@Override
	public User create(User user) throws DataBaseException {
		try{
			Connection connection=connectionFactory.createConnection();
			PreparedStatement statement= connection.prepareStatement(INSERT_QUERY);
			statement.setString(1,user.getFirstName());
			statement.setString(2,user.getLastName());
			statement.setDate(3,new Date(user.getDate().getTime()));
			int n=statement.executeUpdate();
			if(n!=1){
				throw new DataBaseException("Number of inserted rows:"+n);
			}
			CallableStatement callableStatement=connection.prepareCall("call IDENTITY()");
			ResultSet keys= callableStatement.executeQuery();
			User insertedUser=new User(user);
			if(keys.next()){
				insertedUser.setId(keys.getLong(1));
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return insertedUser;
		}catch(DataBaseException e){
			throw e;
		}catch(SQLException e){
			throw new DataBaseException(e);
		}finally{
			
		}
	}

	@Override
	public User find(Long id) throws DataBaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_USERS);
			statement.setLong(1, id);
	        ResultSet resultset = statement.executeQuery();
	        User user = null;
	        while(resultset.next()){
	        	user = new User();
	        	user.setId(resultset.getLong("id"));
	        	user.setFirstName(resultset.getString("firstname"));
	        	user.setLastName(resultset.getString("lastname"));
	        	user.setDate(resultset.getDate("dateofBith"));
	        	statement.close();
				connection.close();
	        }
	        return user;
		} catch (SQLException e) {
			throw new DataBaseException(e);
		}
	}

	@Override
	public void update(User user) throws DataBaseException {
		Connection connection=connectionFactory.createConnection();
		try {
			PreparedStatement statement= connection.prepareStatement(UPDATE_USERS);
			statement.setLong(5,user.getId());
			statement.setLong(1,user.getId());
			statement.setString(2,user.getFirstName());
			statement.setString(3,user.getLastName());
			statement.setDate(4,new Date(user.getDate().getTime()));
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DataBaseException(e);
		}
	}

	@Override
	public void delete(User user) throws DataBaseException {
		Connection connection=connectionFactory.createConnection();
		try {
			PreparedStatement statement= connection.prepareStatement(DELETE_USER);
			statement.setLong(1, user.getId());
			statement.executeUpdate();
			statement.close();
			connection.close();
		}catch(SQLException e){
			throw new DataBaseException(e);
		} 
	}

	@Override
	public Collection<User> findAll() throws DataBaseException {
		Collection<User> collection=new LinkedList<>();
		try {
			Connection connection=connectionFactory.createConnection();
			PreparedStatement statement= connection.prepareStatement(SELECT_FROM_USERS);
			ResultSet resultset=statement.executeQuery();
			while(resultset.next()){
				User user = new User();
				user.setId(resultset.getLong("id"));
				user.setFirstName(resultset.getString("firstname"));
				user.setLastName(resultset.getString("lastname"));
				user.setDate(resultset.getDate("dateofBith"));
				collection.add(user);
			}
			resultset.close();
			statement.close();
			connection.close();
		}catch (SQLException e) {
			throw new DataBaseException(e);
		}
		return collection;
	}
	
	public Collection<User> find(String firstName, String lastName) throws DataBaseException {

		Collection<User> collection=new LinkedList<>();
		try {
			Connection connection=connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_USERS_BY_NAME);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			ResultSet resultset=statement.executeQuery();
			while(resultset.next()){
				User user = new User();
				user.setId(resultset.getLong("id"));
				user.setFirstName(resultset.getString("firstname"));
				user.setLastName(resultset.getString("lastname"));
				user.setDate(resultset.getDate("dateofBith"));
				collection.add(user);
			}
			resultset.close();
			statement.close();
			connection.close();
		}catch (SQLException e) {
			throw new DataBaseException(e);
		}
		return collection;
	}

}
