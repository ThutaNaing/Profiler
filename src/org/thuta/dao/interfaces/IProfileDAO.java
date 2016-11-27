package org.thuta.dao.interfaces;

import java.util.List;

import org.thuta.entities.interfaces.IHit;
import org.thuta.entities.interfaces.IProfile;

public interface IProfileDAO {
	public void addProfile(IProfile profile);
	public void updateProfile(IProfile profile);
	public IProfile findProfile(String id);
	public IProfile findProfile(Long id);
	public List<IProfile> findAllProfile();
	public void removeProfile(IProfile profile);
	public List<IProfile> findProfileByHit(IHit hit);
	public int getNoOfRows();
}
