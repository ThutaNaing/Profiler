package org.thuta.dao.interfaces;

import java.util.List;

import org.thuta.entities.interfaces.IUser;

public interface IUserDAO {
	public void addUser(IUser user);
	public void updateUser(IUser user);
	public IUser findUser(long id);
	public List<IUser> findAllUser();
	public void removeUser(IUser user);
	public List<IUser> findUserByUsernamePassword(String username, String password);
	public int getNoOfRows();
}
