package org.thuta.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thuta.dao.interfaces.IUserDAO;
import org.thuta.entities.Profile;
import org.thuta.entities.User;
import org.thuta.entities.interfaces.IProfile;
import org.thuta.entities.interfaces.IUser;

@Repository("UserDAO")
@Transactional(propagation=Propagation.SUPPORTS)
public class UserDAO extends RootDAO implements IUserDAO {
	private static int noOfRows;

	@Transactional(propagation=Propagation.REQUIRED)
	public void addUser(IUser user) {
		currentSession().save(user);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateUser(IUser user) {
		currentSession().update(user);
//		currentSession().flush();
//		currentSession().close();
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeUser(IUser user) {
		if(user != null) {
			IUser userTemp = (IUser) currentSession().get(User.class, user.getId());
			currentSession().delete(userTemp);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public IUser findUser(long id) {
		return (IUser) currentSession().get(User.class, id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<IUser> findAllUser() {
		List<IUser> result = new ArrayList<IUser>();
		Query q = currentSession().createQuery("from org.thuta.entities.User");
		noOfRows = q.list().size();
		result = q.list();
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<IUser> findUserByUsernamePassword(String username, String password) {
		List<IUser> result = new ArrayList<IUser>();
		Query q = currentSession().createQuery("from org.thuta.entities.User where username = :username and password = :password");
		q.setParameter("username", username);
		q.setParameter("password", password);
		noOfRows = q.list().size();
		result = q.list();
		return result;
	}
	
	public int getNoOfRows() {
		return noOfRows;
	}
}
