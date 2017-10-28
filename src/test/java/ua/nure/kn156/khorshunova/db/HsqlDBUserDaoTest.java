package ua.nure.kn156.khorshunova.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import ua.nure.kn156.khorshunova.User;

public class HsqlDBUserDaoTest extends DatabaseTestCase {
	HsqldbUserDao dao;
	private static final Long ID=1000L;
	private ConnectionFactory connectionFactory;
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		dao=new HsqldbUserDao(connectionFactory);
	}

	public void testCreate(){
		User user=new User();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setDate(new Date());
		assertNull(user.getId());
		User createdUser;
		try {
			createdUser = dao.create(user);
			assertNotNull(createdUser);
			assertNotNull(createdUser.getId());
			assertEquals(user.getFullName(),createdUser.getFullName());
			assertEquals(user.getAge(), createdUser.getAge());
		} catch (DataBaseException e) {
			fail(e.toString());
		}
	}
	
    public void testFindId(){
    	try {
			User user=dao.find(ID);
			assertNotNull("User is null", user);
			assertEquals(ID, user.getId());
		} catch (DataBaseException e) {
			fail(e.toString());
		}
    }
    
    public void testUpdate(){
    	try {
			User user=dao.find(ID);
			user.setFirstName("Michael");
			dao.update(user);
			User updateUser=dao.find(ID);
			assertNotNull("User is null", updateUser);
			assertEquals(user.getFirstName(),updateUser.getFirstName());
			assertEquals(user.getLastName(),updateUser.getLastName());
			assertEquals(user.getAge(),updateUser.getAge());
    	} catch (DataBaseException e) {
			fail(e.toString());
		}
    }
    
    public void testDelete(){
    	try {
			User user=dao.find(ID);
			assertNotNull(user);
			dao.delete(user);
			User deleteUser=dao.find(ID);
			assertNull(deleteUser);
		} catch (DataBaseException e) {
			fail(e.toString());
		}
    	
    }
	
	public void testFindAll(){
		try {
			Collection<User> collection=dao.findAll();
            assertNotNull("Collection is null", collection);
            assertEquals("Collection size.", 2, collection.size());
		} catch (DataBaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory=new ConnectionFactoryImpl(
				"org.hsqldb.jdbcDriver",
				"jdbc:hsqldb:file:db/usermanagement",
				"sa",
				"");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
	    IDataSet dataSet=new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
	    return dataSet;
	}

}
