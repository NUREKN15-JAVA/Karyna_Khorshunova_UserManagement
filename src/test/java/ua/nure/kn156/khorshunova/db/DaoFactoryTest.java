package ua.nure.kn156.khorshunova.db;

import junit.framework.TestCase;

public class DaoFactoryTest extends TestCase {
    
	public void testGetUserDao(){
		
		try{
		DaoFactory daoFactory= DaoFactory.getInstance();
		assertNotNull("DaoFactory instance is null",daoFactory);
		UserDAO userDao = daoFactory.getUserDao();
		assertNotNull("UserDAO instance is null", userDao);
		}catch(RuntimeException e){
			  e.printStackTrace();
	          fail(e.toString());
		}
	}
}
