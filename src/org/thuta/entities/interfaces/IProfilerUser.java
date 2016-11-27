package org.thuta.entities.interfaces;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public interface IProfilerUser {
	public long getId();
	public void setId(long id);
	public String getCode();
	public void setCode(String code);
	public String getName();
	public void setName(String name);
	public String getDescription();
	public void setDescription(String description);
	public Date getDateOfBirth();
	public void setDateOfBirth(Date dateOfBirth);
	public String getEmail();
	public void setEmail(String email);
	public String getPhno();
	public void setPhno(String phno);
	public String getAddress();
	public void setAddress(String address);
	public String getWebsiteaddress();
	public void setWebsiteaddress(String websiteaddress);
	public IUser getUser();
	public void setUser(IUser user);
	public IPhotoPool getPhotoPool() ;
	public void setPhotoPool(IPhotoPool photoPool);
	public List<IHit> getHitList();
	public void setHitList(List<IHit> hitList);
	public void addHit(IHit hit);
	public void removeHit(IHit hit);
	public void appendHit(List<IHit> hits);
	public ITeamBinder getTeamBinder();
	public void setTeamBinder(ITeamBinder teamBinder);
	public IProfilerBinder getProfilerBinder();
	public void setProfilerBinder(IProfilerBinder profilerBinder);
	public long getPublicNotification();
	public void setPublicNotification(long publicNotification);
	public long getTeamNotification();
	public void setTeamNotification(long teamNotification);
	public IProfilerUser duplicate();
}
