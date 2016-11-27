package org.thuta.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.thuta.dao.interfaces.IRootDAO;

@Repository("RootDAO")
public class RootDAO implements IRootDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session currentSession() {
        Session session = null;
        if(session == null || !session.isOpen()) {
            session = sessionFactory.getCurrentSession();
        }
        return session;
    }
}
