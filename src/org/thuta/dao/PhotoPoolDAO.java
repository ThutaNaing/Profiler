package org.thuta.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thuta.dao.interfaces.IPhotoPoolDAO;
import org.thuta.entities.PhotoPool;
import org.thuta.entities.interfaces.IPhotoPool;

@Repository("PhotoPoolDAO")
@Transactional(propagation=Propagation.SUPPORTS)
public class PhotoPoolDAO extends RootDAO implements IPhotoPoolDAO {
	
	private static int noOfRows;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void addPhotoPool(IPhotoPool photoPool) {
		currentSession().save(photoPool);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updatePhotoPool(IPhotoPool photoPool) {
		currentSession().update(photoPool);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public IPhotoPool findPhotoPool(String id) {
		return (IPhotoPool) currentSession().get(PhotoPool.class, id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<IPhotoPool> findAllPhotoPool() {
		List<IPhotoPool> result = new ArrayList<IPhotoPool>();
		Query q = currentSession().createQuery("from org.thuta.entities.PhotoPool");
		noOfRows = q.list().size();
		result = q.list();
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void removePhotoPool(IPhotoPool photoPool) {
		if(photoPool != null) {
			IPhotoPool photoPoolTemp = (IPhotoPool) currentSession().get(PhotoPool.class, photoPool.getId());
			currentSession().delete(photoPoolTemp);
		}
	}
	
	public int getNoOfRows() {
		return noOfRows;
	}
}
