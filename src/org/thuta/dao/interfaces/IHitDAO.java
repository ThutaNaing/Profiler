package org.thuta.dao.interfaces;

import java.util.List;

import org.thuta.entities.interfaces.IHit;

public interface IHitDAO {
	public void addHit(IHit hit);
	public void updateHit(IHit hit);
	public IHit findHit(String id);
	public List<IHit> findAllHit();
	public void removeHit(IHit hit);
	public List<IHit> findAllPublicHit();
	public int getNoOfRows();
}
