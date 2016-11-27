package org.thuta.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thuta.dao.interfaces.IHitDAO;
import org.thuta.dao.interfaces.IProfileDAO;
import org.thuta.entities.Hit;
import org.thuta.entities.Profile;
import org.thuta.entities.interfaces.IHit;

@Repository("HitDAO")
@Transactional(propagation=Propagation.SUPPORTS)
public class HitDAO extends RootDAO implements IHitDAO {
	private static int noOfRows;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void addHit(IHit hit) {
		currentSession().save(hit);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateHit(IHit hit) {
		currentSession().update(hit);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public IHit findHit(String id) {
		return (IHit) currentSession().get(Hit.class, id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<IHit> findAllHit() {
		List<IHit> result = new ArrayList<IHit>();
		Query q = currentSession().createQuery("from org.thuta.entities.Hit");
		noOfRows = q.list().size();
		result = q.list();
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeHit(IHit hit) {
		if(hit != null) {
			IHit hitTemp = (IHit) currentSession().get(Profile.class, hit.getId());
			currentSession().delete(hitTemp);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<IHit> findAllPublicHit() {
		List<IHit> result = new ArrayList<IHit>();
		Query q = currentSession().createQuery("from org.thuta.entities.Hit h order by h.id desc where h.publicHit = true");
		q.setFirstResult(0);
		q.setMaxResults(20);
		noOfRows = q.list().size();
		result = q.list();
		return result;
	}
	
	public int getNoOfRows() {
		return noOfRows;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		IProfileDAO profileDao = (IProfileDAO) context.getBean("ProfileDAO");
		IHitDAO hitDao = (IHitDAO) context.getBean("HitDAO");
		
		List<IHit> hitList = hitDao.findAllPublicHit();
		for(IHit hit : hitList) {
			System.out.println(hit.getName());
		}
	}
}
