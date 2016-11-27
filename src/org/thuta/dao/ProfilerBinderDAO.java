package org.thuta.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thuta.dao.interfaces.IProfilerBinderDAO;
import org.thuta.dao.interfaces.ITeamBinderDAO;
import org.thuta.entities.ProfilerBinder;
import org.thuta.entities.TeamBinder;
import org.thuta.entities.interfaces.IProfilerBinder;
import org.thuta.entities.interfaces.ITeamBinder;

@Repository("ProfilerBinderDAO")
@Transactional(propagation=Propagation.SUPPORTS)
public class ProfilerBinderDAO extends RootDAO implements IProfilerBinderDAO {
	
	private static int noOfRows;

	@Transactional(propagation=Propagation.REQUIRED)
	public void addProfilerBinder(IProfilerBinder profilerBinder) {
		currentSession().save(profilerBinder);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateProfilerBinder(IProfilerBinder profilerBinder) {
		currentSession().update(profilerBinder);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public IProfilerBinder findProfilerBinder(String id) {
		return (IProfilerBinder) currentSession().get(ProfilerBinder.class, id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<IProfilerBinder> findAllProfilerBinder() {
		List<IProfilerBinder> result = new ArrayList<IProfilerBinder>();
		Query q = currentSession().createQuery("from org.thuta.entities.ProfilerBinder");
		noOfRows = q.list().size();
		result = q.list();
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeProfilerBinder(IProfilerBinder profilerBinder) {
		if(profilerBinder != null) {
			IProfilerBinder teamProfilerBinder = (IProfilerBinder) currentSession().get(ProfilerBinder.class, profilerBinder.getId());
			currentSession().delete(teamProfilerBinder);
		}
	}
	
	public int getNoOfRows() {
		return noOfRows;
	}
	
}
