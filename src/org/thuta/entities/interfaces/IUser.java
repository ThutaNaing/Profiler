package org.thuta.entities.interfaces;

import org.thuta.entities.User;

public interface IUser {
	public long getId();
	public void setId(long id);
	public String getCode();
	public void setCode(String code);
	public String getDescription();
	public void setDescription(String description);
	public String getUsername();
	public void setUsername(String username);
	public String getPassword();
	public void setPassword(String password);
	public IProfilerUser getProfilerUser();
	public void setProfilerUser(IProfilerUser profilerUser);
	public IUser duplicate();
}
