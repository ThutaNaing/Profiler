package org.thuta.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thuta.dao.interfaces.IPhotoDAO;
import org.thuta.entities.Photo;
import org.thuta.entities.Profile;
import org.thuta.entities.interfaces.IPhoto;
import org.thuta.entities.interfaces.IProfile;

@Repository("PhotoDAO")
@Transactional(propagation=Propagation.SUPPORTS)
public class PhotoDAO extends RootDAO implements IPhotoDAO {
	private static int noOfRows;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void addPhoto(IPhoto photo) {
		currentSession().save(photo);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updatePhoto(IPhoto photo) {
		currentSession().update(photo);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public IPhoto findPhoto(long id) {
		return (IPhoto) currentSession().get(Photo.class, id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<IPhoto> findAllPhoto() {
		List<IPhoto> result = new ArrayList<IPhoto>();
		Query q = currentSession().createQuery("from org.thuta.entities.Photo");
		noOfRows = q.list().size();
		result = q.list();
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void removePhoto(IPhoto photo) {
		if(photo != null) {
			IPhoto photoTemp = (IPhoto) currentSession().get(Photo.class, photo.getId());
			currentSession().delete(photoTemp);
		}
	}
	
	public int getNoOfRows() {
		return noOfRows;
	}
}
