package org.thuta.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thuta.dao.interfaces.IProfilerUserDAO;
import org.thuta.entities.PhotoPool;
import org.thuta.entities.ProfilerUser;
import org.thuta.entities.interfaces.IHit;
import org.thuta.entities.interfaces.IPhotoPool;
import org.thuta.entities.interfaces.IProfilerUser;
import org.thuta.entities.interfaces.IUser;

@Repository("ProfilerUserDAO")
@Transactional(propagation=Propagation.SUPPORTS)
public class ProfilerUserDAO extends RootDAO implements IProfilerUserDAO{
	
	private static int noOfRows;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void addProfilerUser(IProfilerUser profilerUser) {
		currentSession().save(profilerUser);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateProfilerUser(IProfilerUser profilerUser) {
		currentSession().update(profilerUser);
//		currentSession().flush();
//		currentSession().clear();
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeProfilerUser(IProfilerUser profilerUser) {
		if(profilerUser != null) {
			IProfilerUser profilerUserTemp = (IProfilerUser) currentSession().get(ProfilerUser.class, profilerUser.getId());
			currentSession().delete(profilerUserTemp);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public IProfilerUser findProfilerUser(long id) {
		return (IProfilerUser) currentSession().get(ProfilerUser.class, id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<IProfilerUser> findAllProfilerUser() {
		List<IProfilerUser> result = new ArrayList<>();
		Query q = currentSession().createQuery("from org.thuta.entities.ProfilerUser");
		noOfRows = q.list().size();
		q.setFirstResult(0);
		q.setMaxResults(4);
		result = q.list();
		return result;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public List<String> findAllProfilerUserNames() {
		List<String> result = new ArrayList<String>();
		Query q = currentSession().createQuery("select pu.code from ProfilerUser pu");
		noOfRows = q.list().size();
		result = q.list();
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<IProfilerUser> findProfilerUserByCode(String code) {
		List<IProfilerUser> result = new ArrayList<IProfilerUser>();
		Query q = currentSession().createQuery("select pu from ProfilerUser pu where pu.code = :code");
		q.setParameter("code", code);
		result = q.list();
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<IProfilerUser> findProfilerUserOrderByUsage() {
		List<IProfilerUser> result = new ArrayList<>();
		String hql = "select pu from ProfilerUser pu order by pu.id DESC";
		Query q = currentSession().createQuery(hql);
		result = q.list();
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Object[]> findProfilerUserGroupByUsage() {
		List<Object[]> result = new ArrayList<>();
		String hql = "select sum(pu.id), pu.name from ProfilerUser pu group by pu.name";
		Query q = currentSession().createQuery(hql);
		result = q.list();
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateProfilerUseByexecuteUpdate() {
		int result = 0;
		String hql = "update ProfilerUser pu set pu.description = :description";
		Query q = currentSession().createQuery(hql);
		q.setParameter("description", "Developer");
		result = q.executeUpdate();
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public IProfilerUser findProfilerUserByUser(IUser userEntity) {
		List<IProfilerUser> result = new ArrayList<IProfilerUser>();
		if(userEntity != null) {
			Query q = currentSession().createQuery
					("select pu from ProfilerUser pu, User ue join pu.user puUser where puUser.id = ue.id and puUser.id = :Id");
			q.setParameter("Id", userEntity.getId());
			result = q.list();
		}
		return result.get(0);
	}
	
//	@Transactional(propagation=Propagation.REQUIRED)
//	public List<IProfilerUser> findAllProfilerUser() {
//		List<IProfilerUser> result = new ArrayList<IProfilerUser>();
//		Query q = currentSession().createQuery
//				("select pu from org.thuta.entities.Hit h, ProfilerUser pu join pu.hitDtoList puHit order by h.id desc where h.id = puHit.id and h.publicHit = true");
//		q.setFirstResult(0);
//		q.setMaxResults(20);
//		noOfRows = q.list().size();
//		result = q.list();
//		return result;
//	}
	
	public int getNoOfRows() {
		return noOfRows;
	}
	
}
