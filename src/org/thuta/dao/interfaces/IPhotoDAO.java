package org.thuta.dao.interfaces;

import java.util.List;

import org.thuta.entities.interfaces.IPhoto;

public interface IPhotoDAO {
	public void addPhoto(IPhoto photo);
	public void updatePhoto(IPhoto photo);
	public IPhoto findPhoto(long id);
	public List<IPhoto> findAllPhoto();
	public void removePhoto(IPhoto photo);
	public int getNoOfRows();
}
