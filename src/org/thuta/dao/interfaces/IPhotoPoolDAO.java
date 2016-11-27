package org.thuta.dao.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thuta.entities.PhotoPool;
import org.thuta.entities.interfaces.IPhotoPool;

public interface IPhotoPoolDAO {
	public void addPhotoPool(IPhotoPool photoPool);
	public void updatePhotoPool(IPhotoPool photoPool);
	public IPhotoPool findPhotoPool(String id);
	public List<IPhotoPool> findAllPhotoPool();
	public void removePhotoPool(IPhotoPool photoPool);
	public int getNoOfRows();
}
