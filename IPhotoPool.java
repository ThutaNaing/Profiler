package org.thuta.entities.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface IPhotoPool {
	public long getId();
	public void setId(long id);
	public List<IPhoto> getPhotoList();
	public void setPhotoList(List<IPhoto> photoList);
	public void addPhoto(IPhoto photo);
	public void removePhoto(IPhoto photo);
	public void appendPhoto(List<IPhoto> photos);
}
