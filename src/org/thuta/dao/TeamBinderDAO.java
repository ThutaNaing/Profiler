package org.thuta.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thuta.dao.interfaces.ITeamBinderDAO;
import org.thuta.entities.TeamBinder;
import org.thuta.entities.interfaces.ITeamBinder;

@Repository("TeamBinderDAO")
@Transactional(propagation=Propagation.SUPPORTS)
public class TeamBinderDAO extends RootDAO implements ITeamBinderDAO {
	
	private static int noOfRows;

	@Transactional(propagation=Propagation.REQUIRED)
	public void addTeamBinder(ITeamBinder teamBinder) {
		currentSession().save(teamBinder);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateTeamBinder(ITeamBinder teamBinder) {
		currentSession().update(teamBinder);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public ITeamBinder findTeamBinder(String id) {
		return (ITeamBinder) currentSession().get(TeamBinder.class, id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<ITeamBinder> findAllTeamBinder() {
		List<ITeamBinder> result = new ArrayList<ITeamBinder>();
		Query q = currentSession().createQuery("from org.thuta.entities.TeamBinder");
		noOfRows = q.list().size();
		result = q.list();
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeTeamBinder(ITeamBinder teamBinder) {
		if(teamBinder != null) {
			ITeamBinder teamBinderTemp = (ITeamBinder) currentSession().get(TeamBinder.class, teamBinder.getId());
			currentSession().delete(teamBinderTemp);
		}
	}
	
	public int getNoOfRows() {
		return noOfRows;
	}
	
}
