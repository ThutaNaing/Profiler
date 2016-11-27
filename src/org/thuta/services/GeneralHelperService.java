package org.thuta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.thuta.dao.interfaces.IUserDAO;

public class GeneralHelperService {
	
	@Autowired
	@Qualifier("UserDAO")
	private IUserDAO userDao;
}
